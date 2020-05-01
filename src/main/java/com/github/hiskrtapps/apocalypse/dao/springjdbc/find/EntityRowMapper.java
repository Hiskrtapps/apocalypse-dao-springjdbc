/*
 * © 2020 Ceppi Productions
 */

package com.github.hiskrtapps.apocalypse.dao.springjdbc.find;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import com.ceppi.apocalypse.dao.Entity;
import com.ceppi.apocalypse.dao.impl.entitymetadata.EntityDataRegistry;
import org.springframework.beans.BeanUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;

/**
 * EntityRowMapper is the row processor used to process entities selections.
 * <p>
 *
 * @param <E> Entity to read in the resultset
 */
public final class EntityRowMapper<E extends Entity> implements RowMapper<E> {

  /**
   * entity class to map entity fields
   */
  private final Class<E> entityClass;

  /**
   * Constructs a new EntityRowMapper
   *
   * @param entityClass to map entity fields
   */
  public EntityRowMapper(final Class<E> entityClass) {
    this.entityClass = entityClass;
  }

  @Override
  public final E mapRow(final ResultSet rs, final int rowNum) throws SQLException {
    final E entity = BeanUtils.instantiateClass(this.entityClass);

    final ResultSetMetaData rsmd = rs.getMetaData();
    final int columnCount = rsmd.getColumnCount();

    for (int index = 1; index <= columnCount; index++) {
      final String column = JdbcUtils.lookupColumnName(rsmd, index);
      final Field field = EntityDataRegistry.instance().field(this.entityClass, column);
      if (field != null) {
        entity.set(column, JdbcUtils.getResultSetValue(rs, index, field.getType()));
      }
    }
    return entity;
  }

}
