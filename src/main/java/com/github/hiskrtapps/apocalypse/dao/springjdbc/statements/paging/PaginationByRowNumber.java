/*
 * © 2020 Ceppi Productions
 */

package com.github.hiskrtapps.apocalypse.dao.springjdbc.statements.paging;

import io.github.hiskrtapps.apocalypse.dao.api.Entity;
import io.github.hiskrtapps.apocalypse.dao.api.paging.Order;
import io.github.hiskrtapps.apocalypse.dao.api.paging.PageFind;
import io.github.hiskrtapps.apocalypse.dao.api.paging.PagedResult;
import io.github.hiskrtapps.apocalypse.dao.api.statements.Find;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * implementation class for generic paged find statement adopting an high
 * reliability approach.
 * 
 * Implementation uses row_number() over() windows function to index rows.
 * 
 * The total number of record is calculated using the same query with the same
 * filter. The count value is always provided in output even when no results are
 * returned.
 *
 * Query can end in no results if:
 * <ul>
 * <li>filter select no records</li>
 * <li>page number requested iss too high</li>
 * </ul>
 *
 *
 * @param <E> entity returned in the page by the find
 */
public final class PaginationByRowNumber<E extends Entity> implements PageFind<E> {

  /**
   * regex to find commas inside a couple of parenthesis nested parenthesis are
   * not supported
   */
  private static final String COMMAS_INSIDE_PARENTHESIS_REGEX = ",(?=[^()]*\\))";

  /*
   * regex to find text between words "select" and "from", case-insensitive
   */
  private static final String SQL_SELECT_LIST_REGEX = "(?s)(?i)\\bselect\\b(.*?)\\bfrom\\b";

  /*
   * Pattern for finding text between words "select" and "from",
   * case-insensitive
   */
  private static final Pattern SQL_SELECT_LIST_PATTERN = Pattern.compile(SQL_SELECT_LIST_REGEX);

  /**
   * replacement string
   */
  private static final String REPLACEMENT = " REPLACED";

  /**
   * placeholder for startIndex parameter
   */
  private static final String START_INDEX = "startIndex";

  /**
   * placeholder for endIndex parameter
   */
  private static final String END_INDEX = "endIndex";

  /**
   * name of the statement, should be unique
   */
  private String name;

  /**
   * offset
   */
  private long offset;

  /**
   * limit
   */
  private int limit;

  /**
   * statement parameters
   */
  private Map<String, Object> valueMap;

  /**
   * query part indexing rows to select data
   */
  private static final String ROWINDEX_TEMPLATE =
      "select rowindex_query.* from (select null as total, original_query.*, row_number() over (order by %s) "
          + "as rowindex from (%s) original_query ) rowindex_query where rowindex_query.rowindex between :"
          + START_INDEX + " and :" + END_INDEX;

  /**
   * template query decorating the original query for pagination
   */
  private static final String PAGE_TEMPLATE = "select * from (%s union all %s) page_query order by 1, rowindex";

  /**
   * find to be paginated
   */
  private final Find<E> find;

  /**
   * list of order fields: needed to calculate pagination One is mandatory
   */
  private final List<Order> orders = new ArrayList<>();

  /**
   * Create a pageContext
   *
   * @param find original find to be paginated
   * @param pageOffset Offset in the whole dataset
   * @param pageLimit number of elements to retrieve
   * @param orders list of order fields: needed to calculate pagination. One is
   *          mandatory
   */
  public PaginationByRowNumber(final Find<E> find, final long pageOffset, final int pageLimit, final Order... orders) {
    this(find, pageOffset, pageLimit, Arrays.asList(orders));
  }

  /**
   * Create a pageContext
   *
   * @param find original find to be paginated
   * @param pageOffset Offset in the whole dataset
   * @param pageLimit number of elements to retrieve
   * @param orders list of order fields: needed to calculate pagination. One is
   *          mandatory
   */
  public PaginationByRowNumber(final Find<E> find, long pageOffset, int pageLimit, final List<Order> orders) {
    this.name = PaginationByRowNumber.class.getSimpleName() + "#" + find.name();
    this.valueMap = createValueMap(find, pageOffset, pageLimit, orders);
    this.offset = pageOffset;
    this.limit = pageLimit;
    this.find = find;
    this.orders.addAll(orders);
  }

  private Map<String, Object> createValueMap(final Find<?> find, long pageOffset, int pageLimit,
      final List<Order> orders) {
    // take original find parameters
    final Map<String, Object> valueMap = find.valuesMap();

    /*
     * add parameters for pagination
     */
    valueMap.put(START_INDEX, pageOffset + 1L);
    valueMap.put(END_INDEX, pageOffset + pageLimit);

    // adding sorting fields: they are used to discriminate cached queries
    for (Order order : orders) {
      valueMap.put(order.toString(), order);
    }

    return valueMap;
  }

  /**
   * create the count template part for the paginated query
   *
   * @param original to be paginated
   * @return count template query
   */
  private String countTemplate(final String original) {
    /*
     * select select list token (until "from" word) and replace all commas
     * inside parenthesis nested parenthesis are not supported right now
     * 
     * Important note: In the query, the "from" must correspond to the first
     * select. If you include some comment to document the query, do not use the
     * words "select" and "from" inside the comment before the start of the
     * select
     */
    final Matcher matcher = SQL_SELECT_LIST_PATTERN.matcher(original);
    matcher.find();

    final String selectList = ("select" + matcher.group(1)).replaceAll(COMMAS_INSIDE_PARENTHESIS_REGEX, REPLACEMENT);

    /*
     * split on commas do calculate number of elements in the select list
     */
    return "select count(*)" + StringUtils.repeat(", null", selectList.split(",").length + 1) + " from (" + original
        + ")";
  }

  @Override
  public final Class<E> entityClass() {
    return find.entityClass();
  }

  @Override
  public final String command() {
    final String originalQuery = find.command();
    return String.format(PAGE_TEMPLATE, String.format(ROWINDEX_TEMPLATE, this.format(orders), originalQuery),
        this.countTemplate(originalQuery));
  }

  /**
   * return the list of Orders (property + direction)
   *
   * @param orders to format
   * @return orders (property + direction)
   */
  private String format(final List<Order> orders) {
    return StringUtils.join(orders, ", ");
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.ceppi.apocalypse.templates.Statement#valuesMap()
   */
  @Override
  public Map<String, Object> valuesMap() {
    return valueMap;
  }

  /*
   * (non-Javadoc)
   * 
   * @see Statement#name()
   */
  @Override
  public final String name() {
    return name;
  }

  @Override
  public long offset() {
    return offset;
  }

  @Override
  public int limit() {
    return limit;
  }

  @Override
  public Order[] orders() {
    return new Order[0];
  }

  @Override
  public ResultMapper<E> resultMapper() {
    return new ResultMapper<>(entityClass());
  }

  /**
   * ResultMapper is the row processor used to process paged queries.
   * <p>
   * It read resultset and create a PagedResult mapping paging related
   * information and delegating to a BeanPropertyRowMapper the mapping of entity
   * information
   *
   * @param <E> Entity to read in the resultset
   */
  public static final class ResultMapper<E extends Entity> implements RowMapper<PagedResult<E>> {

    /**
     * entity class to map entity fields
     */
    private final Class<E> entityClass;

    /**
     * Constructs a new ResultMapper
     *
     * @param entityClass to map entity fields
     */
    public ResultMapper(final Class<E> entityClass) {
      this.entityClass = entityClass;
    }

    @Override
    public final PagedResult<E> mapRow(final ResultSet rs, final int rowNum) throws SQLException {
      return new PagedResult<>(rowNum == 0, rs.getLong(1), rowNum == 0 ? null : new BeanPropertyRowMapper<E>(
          entityClass, false).mapRow(rs, rowNum));
    }

  }

}