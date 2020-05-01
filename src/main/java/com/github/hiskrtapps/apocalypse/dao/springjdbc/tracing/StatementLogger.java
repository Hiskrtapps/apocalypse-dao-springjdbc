/*
 * © 2020 Ceppi Productions
 */

package com.github.hiskrtapps.apocalypse.dao.springjdbc.tracing;

import com.ceppi.apocalypse.dao.Dao;
import com.ceppi.apocalypse.dao.Entity;
import com.ceppi.apocalypse.dao.paging.Page;
import com.ceppi.apocalypse.dao.paging.PageFind;
import com.ceppi.apocalypse.dao.statements.Find;
import com.ceppi.apocalypse.dao.statements.Modification;
import com.ceppi.apocalypse.dao.statements.Statement;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.*;

import static java.lang.System.nanoTime;
import static java.util.concurrent.TimeUnit.NANOSECONDS;
import static org.apache.commons.lang3.StringUtils.EMPTY;

/**
 * Specific Logger for
 * {@link Statement}
 *
 * Before any Statement is executed through a
 * {@link Dao} call the StatementLogger will
 * log all important information related to the statement, including the
 * Statement name, the SQL script, the set of list of binding parameters (in
 * case of batch operation we'll have more than one set of parameters) and the
 * status set to "READY".
 *
 * Parameter information can result in long heavy logs in case of Blol/Clob
 * parameters are logged. They are still kept to be sure to be able to re-create
 * the full statement during problem investigations.
 * 
 * After the execution of the Statement a new log is done including execution
 * time and result information and the status set to "SUCCESS".:
 * <ul>
 * <li>
 * In case a {@link Modification}
 * is performed the result will contain the array (it is an array because of
 * batch operations) of the number of affected rows as they are returned by the
 * driver.</li>
 * <li>
 * In case a {@link Find} is
 * performed the result will contain the number of selected rows.</li>
 * <li>
 * In case a {@link Page} is returned
 * due to a paginated query execution the number of records in the selected page
 * is returned as result</li>
 * </ul>
 *
 * In case a PersistenceException is raised during the
 * Statement execution the message of the exception is returned as result. In
 * this case the status of the execution will be "FAILURE"
 *
 * The information logged represented by the {@link StatementInformation} object
 * are serialized as a json in a single log line. This because we want to
 * facilitate usage of "grep" for selecting interesting logs and from the other
 * side we wanted to retain the full structured information.
 * 
 * As in some cases the log itself can be time consuming it is activated only
 * when log debug mode is activated
 *
 *
 */
@Aspect
@Named
public final class StatementLogger {

  /**
   * Logger for StatementLogger
   */
  private static final Logger LOGGER = LoggerFactory.getLogger(StatementLogger.class);

  /**
   * Constant to be use when a Statement is ready to be executed
   */
  private static final String READY = "READY";

  /**
   * Constant to be use when a Statement execution is successfully completed
   */
  private static final String SUCCESS = "SUCCESS";

  /**
   * Constant to be use when a Statement execution is raises a
   * PersistenceException
   */
  private static final String FAILURE = "FAILURE";

  /**
   * Constant to be used to filter the StackTrace. This list contains
   * the elements to include
   */
  private static final Set<String> INCLUDES =
          Collections.singleton(
                  "com.lcp"
          );

  /**
   * Constant to be used to filter the StackTrace. This list contains
   * the elements to exclude
   */
  private static final Set<String> EXCLUDES =
          new HashSet<>( Arrays.asList(
                  "EnhancerBySpringCGLIB",
                  "FastClassBySpringCGLIB",
                  "PerformanceLogger",
                  "ActivableAspect",
                  "SecurityManager",
                  "RootAccessManager",
                  "MemoizableAspect",
                  "StatementLogger"
          ));
  /**
   * Jackson mapper to serialize the log information in a json
   */
  @Inject
  private ObjectMapper mapper;

  /**
   * Tracing Configuration
   */
  @Inject
  private Configuration configuration;

  /**
   * jointpoint for any Modification statement
   */
  @Around("execution(public * com.ceppi.apocalypse.Dao.modify(..)) && args(modification)")
  public Object log(final ProceedingJoinPoint pjp, final Modification modification) throws Throwable {
    return log(pjp, modification, modification.valuesMaps());
  }

  /**
   * jointpoint for any Find statement
   */
  @Around("execution(public * com.ceppi.apocalypse.Dao.find(..)) && args(find)")
  public Object log(final ProceedingJoinPoint pjp, final Find find) throws Throwable {
    return log(pjp, find, find.valuesMap());
  }

  /**
   * jointpoint for any paginated Find statement
   */
  @Around("execution(public * com.ceppi.apocalypse.Dao.find(..)) && args(pageFind)")
  public Object log(final ProceedingJoinPoint pjp, final PageFind pageFind) throws Throwable {
    return log(pjp, pageFind, pageFind.valuesMap());
  }

  /**
   * Main log method performing the log. Statement information gahering and log
   * itself are executed only if logger debug mode is enabled
   *
   * @param pjp jointpoint
   * @param statement to be logged
   * @param valuesMaps parameters binded to the statement
   * @return result of the real call
   * @throws Throwable any exception raise by the real call
   */
  @SuppressWarnings("squid:S1141")
  private Object log(final ProceedingJoinPoint pjp, final Statement statement, final Map<String, Object>... valuesMaps)
      throws Throwable {

    if (configuration.enabled()) {

      Object result = null;

      try {

        // information object is initialized with the input information
        final StatementInformation info = new StatementInformation();
        info.setName(statement.name());
        info.setStatus(READY);
        info.setCommand(statement.command() == null ? EMPTY : statement.command().replaceAll("\n", " ")
            .replaceAll("\t", " "));
        info.setParameters(valuesMaps);
        info.setTrace(StatementInformation.format(filter(Thread.currentThread().getStackTrace(), INCLUDES, EXCLUDES)));

        // log is done for the Statement READY to be executed
        LOGGER.info(mapper.writeValueAsString(info));

        // start calculation time
        long start = start();

        try {

          // statement is executed here
          result = pjp.proceed();

          // statement has been successfully executed; setting SUCCESS status
          // and result information
          info.setStatus(SUCCESS);
          info.setResult(parse(result));

        } catch (RuntimeException pe) {

          // statement execution failed; setting FAILURE status and error
          // information as result
          info.setStatus(FAILURE);
          info.setResult(pe.getMessage());
          throw pe;

        } finally {

          // execution time is set
          info.setDuration(end(start));

          // log is done for the ended Statement
          LOGGER.info(mapper.writeValueAsString(info));
        }

      } catch (JsonProcessingException e) {

        // it should never happen; in any case we do not want to fail for this
        LOGGER.warn(e.getOriginalMessage(), e);
      }

      return result;

    } else {

      // simple case, just proceed
      return pjp.proceed();
    }

  }

  public static final StackTraceElement[] filter(StackTraceElement[] elements, Set<String> includes, Set<String> excludes) {

    //List of filtered elements
    List<StackTraceElement> filteredStackList = new ArrayList<>();

    for (StackTraceElement element : elements) {
      String packageName = element.getClassName();

      for (String include : includes) {
        if (packageName.contains(include)) {

          boolean toInclude = true;

          for (String exclude : excludes) {
            if (packageName.contains(exclude)) {
              toInclude = false;
              break;
            }
          }

          if (toInclude) {
            filteredStackList.add(element);
          }
          break;
        }
      }
    }
    return filteredStackList.toArray(new StackTraceElement[0]);
  }

  /**
   * The result is parsed and returned in different ways, epending from his
   * nature
   *
   * @param result
   * @return
   */
  private Object parse(Object result) {
    if (result instanceof Collection) {
      return ((Collection)result).size();
    } else if (result instanceof Page) {
      return ((Page)result).content().size();
    } else if (result instanceof Entity) {
      return 1;
    } else {
      return result;
    }
  }

  /**
   * @return start time
   */
  private long start() {
    return nanoTime();
  }

  /**
   * @param start
   * @return end time
   */
  private long end(long start) {
    return NANOSECONDS.toMillis(nanoTime() - start);
  }

  /**
   * class that represent the full information regarding a Statement execution
   */
  @SuppressWarnings("squid:S2384")
  private static class StatementInformation {

    /**
     * functional name of the Statement
     */
    private String name;

    /**
     * status of the Statement. It can be READY (for execution), SUCCESSFUL,
     * FAILURE
     */
    private String status;

    /**
     * execution time in milliseconds. Filled after the statement is executed.
     */
    private Long duration;

    /**
     * result of the statement (or error message in case of failure). Filled
     * after the statement is executed.
     */
    private Object result;

    /**
     * command of the statement
     */
    private String command;

    /**
     * array of maps of parameters (in case of batch we'll have more than one
     * element in the array)
     */
    private Map<String, Object>[] parameters;

    /**
     * List of stackTrace values
     */
    private List<String> trace;

    /**
     * @return the field #status
     **/
    public String getStatus() {
      return status;
    }

    /**
     * @param status the new value of the field #status
     **/
    public void setStatus(String status) {
      this.status = status;
    }

    /**
     * @return the field #duration
     **/
    public Long getDuration() {
      return duration;
    }

    /**
     * @param duration the new value of the field #duration
     **/
    public void setDuration(Long duration) {
      this.duration = duration;
    }

    /**
     * @return the field #parse
     **/
    public Object getResult() {
      return result;
    }

    /**
     * @param result the new value of the field #parse
     **/
    public void setResult(Object result) {
      this.result = result;
    }

    /**
     * @return the field #name
     **/
    public String getName() {
      return name;
    }

    /**
     * @param name the new value of the field #name
     **/
    public void setName(String name) {
      this.name = name;
    }

    /**
     * @return the field #command
     **/
    public String getCommand() {
      return command;
    }

    /**
     * @param command the new value of the field #command
     **/
    public void setCommand(String command) {
      this.command = command;
    }

    /**
     * @return the field #parameters
     **/
    public Map<String, Object>[] getParameters() {
      return parameters;
    }

    /**
     * @param parameters the new value of the field #parameters
     **/
    public void setParameters(Map<String, Object>[] parameters) {
      this.parameters = parameters;
    }

    /**
     * @return the field #trace
     **/
    public List<String> getTrace() {
      return trace;
    }

    /**
     * @param trace the new value of the field #trace
     **/
    public void setTrace(List<String> trace) {
      this.trace = trace;
    }

    /**
     * Format stackTrace as a List of String to be attached to a log
     * @param stackTrace array of StackTraceElement
     * @return list representation of the array of StackTraceElement
     */
    public static final List<String> format(StackTraceElement[] stackTrace) {

      List<String> formattedStackTrace = new ArrayList<>();

      for (StackTraceElement element : stackTrace) {
        formattedStackTrace.add(String.format("%s.%s(%s)", element.getClassName(), element.getMethodName(), element.getLineNumber()));
      }
      return formattedStackTrace;
    }
  }

  /**
   * Configuration for statement logger. Currently it only defines if tracing is
   * enabled or not.
   */
  public interface Configuration {

    /**
     * it defines if tracing is enabled
     *
     * @return true if tracing is enabled
     */
    boolean enabled();

  }

  /**
   * Default implementation for {@link StatementLogger.Configuration}.
   *
   * This configuration is used unless another one is provided
   */
  @Named
  public static class DefaultConfiguration implements Configuration {

    /*
     * This logger refers to {@link StatementLogger} and not to {@link
     * DefaultConfiguration}. The reason is that it is used not to log but to
     * check if the tracing should be enabled based on the log level of {@link
     * StatementLogger}
     */
    private final Logger LOGGER = LoggerFactory.getLogger(StatementLogger.class);

    /**
     * return if the tracing is enabled
     * 
     * @return true if debug log level is enabled for {@link StatementLogger}
     */
    public boolean enabled() {
      return LOGGER.isDebugEnabled();
    }

  }

}
