/*
 * © 2020 Ceppi Productions
 */
package com.github.hiskrtapps.apocalypse.dao.springjdbc;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 *
 */
public class SpringJdbcSaoTest {

  /**
   * template for next values
   */
  private static final String NEXT = "select %s.nextval as value from dual connect by level <= ?";

  /**
   * template for current value
   */
  private static final String CURRENT = "select %s.currval as value from dual";

  private static final String NAME = "sequence_name";

  private static final int OCCURRENCES = 3;

  @Mock
  private NamedParameterJdbcTemplate template;

  @Mock
  private JdbcOperations operations;

  @InjectMocks
  private SpringJdbcSao sao;

  @Before
  public void setUp() throws Exception {
    initMocks(this);
    when(template.getJdbcOperations()).thenReturn(operations);
  }

  @Test
  public void testNext() {
    List result = asList(1, 2);

    when(operations.queryForList(eq(String.format(NEXT, NAME)), eq(new Object[] { OCCURRENCES }), eq(Integer.class))).thenReturn(result);

    assertEquals(result, sao.next(NAME, OCCURRENCES));
  }

  @Test
  public void testCurrent() {
    List result = asList(1);

    when(operations.queryForList(eq(String.format(CURRENT, NAME)), eq(Integer.class))).thenReturn(result);

    assertEquals(result.get(0), sao.current(NAME));
  }

}
