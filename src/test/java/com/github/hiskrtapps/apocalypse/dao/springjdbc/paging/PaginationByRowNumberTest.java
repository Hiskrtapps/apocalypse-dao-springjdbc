/*
 * © 2020 Ceppi Productions
 */

package com.github.hiskrtapps.apocalypse.dao.springjdbc.paging;

import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.ID;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.RECEIVED_DATE;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.TYPE;

import java.util.Arrays;

import javax.persistence.Column;

import io.github.hiskrtapps.apocalypse.dao.api.criteria.And;
import io.github.hiskrtapps.apocalypse.dao.api.criteria.Criteria;
import io.github.hiskrtapps.apocalypse.dao.api.impl.entitymetadata.EntityMetadata;
import io.github.hiskrtapps.apocalypse.dao.api.impl.entitymetadata.EntityMetadataImpl;
import io.github.hiskrtapps.apocalypse.dao.api.impl.entitymetadata.statements.EntityMetadataFind;
import io.github.hiskrtapps.apocalypse.dao.api.paging.Order;
import io.github.hiskrtapps.apocalypse.dao.api.statements.Find;
import com.github.hiskrtapps.apocalypse.dao.springjdbc.TestCompleteMockEntity;
import com.github.hiskrtapps.apocalypse.dao.springjdbc.statements.paging.PaginationByRowNumber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class PaginationByRowNumberTest {

  private static final String START_WITH_ORIGINAL =
      "select * from (select rowindex_query.* from (select null as total, original_query.*, row_number() over (order by TYPE ASC, RECEIVED_DATE ASC, ID ASC) as rowindex from (select ID, MODE, REQUEST_ID, TYPE from LCP_TESTMOCKENTITY where (TYPE = :TYPE";

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
        new PaginationByRowNumber<>(originalFind, 3, 6, new Order(TYPE), new Order(RECEIVED_DATE), new Order(ID));

    String response = pageFind.command();
    Assert.assertTrue(response.startsWith(START_WITH_ORIGINAL));
  }

}
