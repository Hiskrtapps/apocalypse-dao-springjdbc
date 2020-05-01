/*
 * © 2020 Ceppi Productions
 */

package com.github.hiskrtapps.apocalypse.dao.springjdbc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "LCP_TESTMOCKENTITY", uniqueConstraints = {
    @UniqueConstraint(name = TestCompleteMockEntity.CHKS.LCP_IDX_PK_TESTMOCKENTITY, columnNames = { TestCompleteMockEntity.COLS.ID }),
    @UniqueConstraint(name = TestCompleteMockEntity.CHKS.LCP_IDX_AK1_TESTMOCKENTITY, columnNames = { TestCompleteMockEntity.COLS.TYPE }) })

public class TestCompleteMockEntity extends TestEntity {

  /**
   * This field maps the column ID
   */
  @Id
  @SequenceGenerator(name = "LCP_TESTMOCKENTITY_S")
  @Column(name = COLS.ID)
  private Number id;

  /**
   * This field maps the column TYPE
   */
  @Column(name = COLS.TYPE)
  private String type;

  /**
   * This field maps the column REQUEST_ID
   */
  @Column(name = COLS.REQUEST_ID)
  private Number requestId;

  /**
   * This field maps the column REQUEST_ID
   */
  @Column(name = COLS.MODE)
  private Number mode;

  public Number getId() {
    return id;
  }

  public void setId(Number id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Number getRequestId() {
    return requestId;
  }

  public void setRequestId(Number requestId) {
    this.requestId = requestId;
  }
  
  public Number getMode() {
    return mode;
  }
  
  public void setMode(Number mode) {
    this.mode = mode;
  }

  @Override
  public <T> T get(String columnName) {
    return null;
  }

  @Override
  public void set(String columnName, Object columnValue) {

  }

  @Override
  public final void primaryKey(Object... values) {
    setId((Number)values[0]);
  }

  /**
   * This class collects all constants regarding COLumnS
   */
  public static final class COLS {
    /**
     * This field gives the column name of ID
     */
    public static final String ID = "ID";

    /**
     * This field gives the column name of Type
     */
    public static final String TYPE = "TYPE";

    /**
     * This field gives the column name of Request_ID
     */
    public static final String REQUEST_ID = "REQUEST_ID";

    /**
     * This field gives the column name of Mode
     */
    public static final String MODE = "MODE";

    /**
     * Constuctor for columns constants class
     */
    private COLS() {
      super();
    }
  }

  /**
   * This class collects all constants regarding CHecK constraintS
   */
  public static final class CHKS {
    /**
     * Constant indicating name of constraint LCP_IDX_PK_ACTIVITY
     */
    public static final String LCP_IDX_PK_TESTMOCKENTITY = "LCP_IDX_PK_TESTMOCKENTITY";
    /**
     * Constant indicating name of constraint LCP_IDX_PK_ACTIVITY
     */
    public static final String LCP_IDX_AK1_TESTMOCKENTITY = "LCP_IDX_AK1_TESTMOCKENTITY";

    /**
     * Constuctor for check constraint constants class
     */
    private CHKS() {
      super();
    }
  }

}
