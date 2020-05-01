/*
 * © 2020 Ceppi Productions
 */

package com.github.hiskrtapps.apocalypse.dao.springjdbc.paging;

import com.ceppi.apocalypse.dao.criteria.And;
import com.ceppi.apocalypse.dao.criteria.Criteria;
import com.ceppi.apocalypse.dao.impl.entitymetadata.EntityMetadata;
import com.ceppi.apocalypse.dao.impl.entitymetadata.EntityMetadataImpl;
import com.ceppi.apocalypse.dao.impl.entitymetadata.statements.EntityMetadataFind;
import com.ceppi.apocalypse.dao.paging.Order;
import com.ceppi.apocalypse.dao.statements.Find;
import com.github.hiskrtapps.apocalypse.dao.springjdbc.TestCompleteMockEntity;
import com.github.hiskrtapps.apocalypse.dao.springjdbc.statements.paging.TopRowsPaginationByRowNumber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.Column;
import java.util.Arrays;

/**
 *
 */
public class TopRowsPaginationByRowNumberTest {

  private static final String START_WITH_ORIGINAL =
      "select rowindex_query.* from (select count(*) over() as total, capped_original_query.*, row_number() over (order by TYPE ASC, RECEIVED_DATE ASC, ID ASC) as rowindex from (select * from (select ID, MODE, REQUEST_ID, TYPE from LCP_TESTMOCKENTITY where (TYPE = :TYPE";

  private EntityMetadata<TestCompleteMockEntity> entityMetadata;

  /**
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    entityMetadata = new EntityMetadataImpl<>(TestCompleteMockEntity.class);
  }

  @Test
  public void testFormattedWithNotNullFilterColumn() {
    Column[] columns = new Column[1];
    columns[0] = entityMetadata.getColumnByName(TestRequestEntity.COLS.TYPE);

    Find<TestCompleteMockEntity> originalFind =
        new EntityMetadataFind(entityMetadata, new And(Criteria.convert(columns, Arrays.asList(new TestRequestEntity()))));

    Find<TestCompleteMockEntity> pageFind =
        new TopRowsPaginationByRowNumber<>(originalFind, 3, 6, 10, new Order(TestRequestEntity.COLS.TYPE), new Order(TestRequestEntity.COLS.RECEIVED_DATE), new Order(TestRequestEntity.COLS.ID));

    String response = pageFind.command();
    Assert.assertTrue(response.startsWith(START_WITH_ORIGINAL));
  }

}