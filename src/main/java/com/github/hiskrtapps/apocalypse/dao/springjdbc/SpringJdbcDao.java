/*
 * © 2020 Ceppi Productions
 */
package com.github.hiskrtapps.apocalypse.dao.springjdbc;

import static java.beans.Introspector.decapitalize;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.ceppi.apocalypse.dao.Dao;
import com.ceppi.apocalypse.dao.Entity;
import com.ceppi.apocalypse.dao.impl.entitymetadata.EntityMetadataDaoImpl;
import com.ceppi.apocalypse.dao.paging.Order;
import com.ceppi.apocalypse.dao.paging.Page;
import com.ceppi.apocalypse.dao.paging.PageFind;
import com.ceppi.apocalypse.dao.paging.PagedResult;
import com.github.hiskrtapps.apocalypse.dao.springjdbc.find.EntityRowMapper;
import com.github.hiskrtapps.apocalypse.dao.springjdbc.statements.paging.PaginationByRowNumber;
import com.ceppi.apocalypse.dao.statements.Find;
import com.ceppi.apocalypse.dao.statements.Modification;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * SpringJdbcDao provide Dao implementation for Spring Jdbc framework. Then
 * statement is executed by NamedParameterJdbcTemplate template.
 * <p>
 * Where possible batch operations are implemented to improve performance.
 * <p>
 * SpringJdbcDao implementation is strongly dependent from springframework
 * library;
 *
 * @param <E> entity handled by generic dao
 */
public final class SpringJdbcDao<E extends Entity> extends EntityMetadataDaoImpl<E> {

  /**
   * Spring JDBC template
   */
  @Inject
  private NamedParameterJdbcTemplate template;

  /**
   * spring factory dao bean request is delegated to
   */
  @Inject
  private BeanFactory factory;

  /**
   * Sets the {@link SpringJdbcDao#template} property.
   * <p>
   * Method exposed to provide an alternative way to instantiate this class
   * instead of using injection
   *
   * @param template the {@link SpringJdbcDao#template} instance representing
   *          object.
   */
  public final void setTemplate(final NamedParameterJdbcTemplate template) {
    this.template = template;
    ((SpringJdbcSao)getSao()).setTemplate(template);
  }

  /**
   * Sets the {@link SpringJdbcDao#factory} property.
   * <p>
   * Method exposed to provide an alternative way to instantiate this class
   * instead of using injection
   *
   * @param factory the {@link SpringJdbcDao#factory} instance representing
   *          object.
   */
  public final void setBeanFactory(final BeanFactory factory) {
    this.factory = factory;
  }

  @Override
  public final int[] modify(final Modification modification) {
    if (modification.command() != null) {
      if (modification.valuesMaps().length == 1) {
        return new int[] { template.update(modification.command(), source(modification.valuesMaps())[0]) };
      } else {
        return template.batchUpdate(modification.command(), source(modification.valuesMaps()));
      }
    } else {
      return new int[modification.valuesMaps().length];
    }
  }

  @Override
  public final List<E> find(final Find<E> find) {
    return template.query(find.command(), source(find.valuesMap())[0], new EntityRowMapper<>(find.entityClass()));
  }

  @Override
  public final Page<E> find(final PageFind<E> pageFind) {

    final List<PagedResult<E>> result =
        template.query(pageFind.command(), source(pageFind.valuesMap())[0],
            (RowMapper<PagedResult<E>>)pageFind.resultMapper());

    Long total = null;
    final List<E> entities = new ArrayList<>();
    for (PagedResult<E> pagedResult : result) {
      if (pagedResult.first()) {
        total = pagedResult.total();
      }
      if (pagedResult.entity() != null) {
        entities.add(pagedResult.entity());
      }
    }

    return new Page(total, pageFind.offset(), pageFind.limit(), entities);
  }

  @Override
  public final Page<E> find(final Find<E> find, final long pageOffset, final int pageLimit, final Order... orders) {
    return self().find(new PaginationByRowNumber<>(find, pageOffset, pageLimit, orders));
  }

  /**
   * translate Maps of values in the proper source usable by the implementation
   *
   * @param valuesMaps values to be translated
   * @return proper source
   */
  private SqlParameterSource[] source(final Map<String, Object>... valuesMaps) {
    final List<SqlParameterSource> sqlParameterSources = new ArrayList();
    MapSqlParameterSource parameters;
    for (Map<String, Object> valuesMap : valuesMaps) {
      parameters = new MapSqlParameterSource();
      for (Map.Entry<String, Object> entry : valuesMap.entrySet()) {
        parameters.addValue(entry.getKey(), entry.getValue());
      }
      sqlParameterSources.add(parameters);
    }
    return sqlParameterSources.toArray(new SqlParameterSource[0]);
  }

  @Override
  protected Dao<E> self() {
    final SpringJdbcDao<E> dao =
        factory.getBean(decapitalize(entityMetadata.getEntityClass().getSimpleName()) + "Dao", SpringJdbcDao.class);
    dao.setTemplate(this.template);
    return dao;
  }

}