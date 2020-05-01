/*
 * © 2020 Ceppi Productions
 */

package com.github.hiskrtapps.apocalypse.dao.springjdbc;

import static java.lang.String.format;

import javax.inject.Inject;
import javax.inject.Named;

import io.github.hiskrtapps.apocalypse.dao.api.Sao;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * SpringJdbcSao provide Sao implementation for Spring Jdbc framework. Then
 * statement is executed by NamedParameterJdbcTemplate template.
 * <p>
 * SpringJdbcSao implementation is strongly dependent from springframework
 * library;
 * <p>
 * Implementation is also actually tied with Oracle DBMS and this is explained
 * in @Named annotation value
 *
 */
@Named("Oracle")
public final class SpringJdbcSao implements Sao {

  /**
   * template for next values
   */
  private static final String NEXT = "select %s.nextval as value from dual connect by level <= ?";

  /**
   * template for current value
   */
  private static final String CURRENT = "select %s.currval as value from dual";

  /**
   * Spring JDBC template
   */
  @Inject
  private NamedParameterJdbcTemplate template;

  /**
   * Sets the {@link SpringJdbcSao#template} property.
   * <p>
   * Method exposed to provide an alternative way to instantiate this class
   * instead of using injection
   *
   * @param template the {@link SpringJdbcSao#template} instance representing
   *                 object.
   */
  public final void setTemplate(final NamedParameterJdbcTemplate template) {
    this.template = template;
  }

  @Override
  public final Iterable<Integer> next(final String name, final int number) {
    return template.getJdbcOperations().queryForList(format(NEXT, name), new Object[] { number }, Integer.class);
  }

  @Override
  public final Integer current(final String name) {
    return template.getJdbcOperations().queryForList(format(CURRENT, name), Integer.class).get(0);
  }

}