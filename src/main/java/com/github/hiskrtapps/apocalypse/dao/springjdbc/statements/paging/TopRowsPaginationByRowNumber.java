/*
 * © 2020 Ceppi Productions
 */

package com.github.hiskrtapps.apocalypse.dao.springjdbc.statements.paging;

import io.github.hiskrtapps.apocalypse.dao.api.Entity;
import io.github.hiskrtapps.apocalypse.dao.api.paging.Order;
import io.github.hiskrtapps.apocalypse.dao.api.paging.PageFind;
import io.github.hiskrtapps.apocalypse.dao.api.paging.PagedResult;
import io.github.hiskrtapps.apocalypse.dao.api.statements.Find;
import com.github.hiskrtapps.apocalypse.dao.springjdbc.statements.Top;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * implementation class for generic paged find statement adopting an best effort
 * approach.
 * 
 * Implementation uses row_number() over() windows function to index rows.
 * 
 * The total number of record is calculated using count() over() windows
 * function. The count value is provided in output only when at least one record
 * is returned.
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
public final class TopRowsPaginationByRowNumber<E extends Entity> implements PageFind<E> {

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
      "select rowindex_query.* from (select count(*) over() as total, capped_original_query.*, "
          + "row_number() over (order by %s) as rowindex from (%s) capped_original_query ) rowindex_query "
          + "where rowindex_query.rowindex between :" + START_INDEX + " and :" + END_INDEX;

  /**
   * find to be paginated
   */
  private final Top<E> find;

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
  public TopRowsPaginationByRowNumber(final Find<E> find, final long pageOffset, final int pageLimit, final long top,
                                      final Order... orders) {
    this(find, pageOffset, pageLimit, top, Arrays.asList(orders));
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
  public TopRowsPaginationByRowNumber(final Find<E> find, final long pageOffset, final int pageLimit, final long top,
      final List<Order> orders) {
    this.name = TopRowsPaginationByRowNumber.class.getSimpleName() + "#" + find.name();
    this.offset = pageOffset;
    this.limit = pageLimit;
    this.find = new Top(find, top);
    this.valueMap = createValueMap(this.find, pageOffset, pageLimit, orders);
    this.orders.addAll(orders);
  }

  private Map<String, Object> createValueMap(final Find<?> find, final long pageOffset, final long pageLimit,
      List<Order> orders) {
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

  @Override
  public final Class<E> entityClass() {
    return find.entityClass();
  }

  @Override
  public final String command() {
    final String originalQuery = find.command();
    return String.format(ROWINDEX_TEMPLATE, this.format(orders), originalQuery);
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
      return new PagedResult<>(rowNum == 0, rs.getLong(1), new BeanPropertyRowMapper<E>(entityClass, false).mapRow(rs,
          rowNum));
    }

  }

}