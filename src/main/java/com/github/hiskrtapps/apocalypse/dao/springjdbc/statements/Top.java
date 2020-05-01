/*
 * © 2020 Ceppi Productions
 */

package com.github.hiskrtapps.apocalypse.dao.springjdbc.statements;

import io.github.hiskrtapps.apocalypse.dao.api.Entity;
import com.github.hiskrtapps.apocalypse.dao.springjdbc.statements.paging.PaginationByRowNumber;
import io.github.hiskrtapps.apocalypse.dao.api.statements.Find;

import java.util.Map;

import static java.lang.String.format;

/**
 * This class provide capping capabilities to the given query. The practical
 * effect is to limit the selection of the rows up the maximum number provided
 * by the parameter
 *
 * @param <E>
 */
public final class Top<E extends Entity> implements Find<E> {

  /**
   * placeholder for maximumRecords parameter
   */
  private static final String MAXIMUM_RECORDS = "maximumRecords";

  /**
   * query template to add capping capabilities to the given query
   */
  private static final String TOP_TEMPLATE = "select * from (%s) where rownum <= :" + MAXIMUM_RECORDS;

  /**
   * name of the statement, should be unique
   */
  private String name;

  /**
   * statement parameters
   */
  private Map<String, Object> valueMap;

  /**
   * inner query to be capped
   */
  private final Find<E> find;

  /**
   * Constructor
   *
   * @param find to be capped
   * @param maximumRecords maximum number of recod to be selected
   */
  public Top(final Find<E> find, long maximumRecords) {
    this.name = PaginationByRowNumber.class.getSimpleName() + "#" + find.name();
    this.valueMap = createValueMap(find, maximumRecords);
    this.find = find;
  }

  /**
   * add maximumRecords parameter to the map of input parameters
   *
   * @param find
   * @param maximumRecords
   * @return
   */
  private Map<String, Object> createValueMap(final Find<?> find, final long maximumRecords) {
    final Map<String, Object> valueMap = find.valuesMap();
    valueMap.put(MAXIMUM_RECORDS, maximumRecords);
    return valueMap;
  }

  @Override
  public String command() {
    final String originalQuery = find.command();
    return template(originalQuery);
  }

  @Override
  public Class<E> entityClass() {
    return find.entityClass();
  }

  /**
   * returned the decorated find template
   *
   * @param original
   * @return
   */
  private String template(final String original) {
    return format(TOP_TEMPLATE, original);
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

}