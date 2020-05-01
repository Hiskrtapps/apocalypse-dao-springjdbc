/*
 * © 2020 Ceppi Productions
 */

package com.github.hiskrtapps.apocalypse.dao.springjdbc.paging;

import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.TABLE_NAME;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.CHKS.LCP_IDX_PK_REQUEST;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.API_VERSION;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.CLIENT;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.ERROR_CODE;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.ID;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.MEMBER_ID;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.MODIFIED_DATA;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.ORIGINAL_DATA;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.ORIGINATOR;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.PARTNER_CODE;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.PROCESSED_DATE;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.PROGRAM;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.RECEIVED_DATE;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.REQUEST_PARTNER_REFERENCE;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.REQ_MODE;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.STATUS;
import static com.github.hiskrtapps.apocalypse.dao.springjdbc.paging.TestRequestEntity.COLS.TYPE;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.github.hiskrtapps.apocalypse.dao.springjdbc.TestEntity;

/**
 * This class maps the table LCP_REQUEST
 *
 */
@Entity
@Table(name = TABLE_NAME,
       uniqueConstraints = { 
              @UniqueConstraint (name = LCP_IDX_PK_REQUEST, columnNames = { ID })
       }
)
public final class TestRequestEntity extends TestEntity {
    /**
     * This field maps the column ID
     */
    @Id
    @SequenceGenerator(name = "LCP_REQUEST_S")
    @Column(name = ID)
    private Number id;

    /**
     * This field maps the column TYPE
     */
    @Column(name = TYPE)
    private String type;

    /**
     * This field maps the column REQ_MODE
     */
    @Column(name = REQ_MODE)
    private String reqMode;

    /**
     * This field maps the column CLIENT
     */
    @Column(name = CLIENT)
    private String client;

    /**
     * This field maps the column PROGRAM
     */
    @Column(name = PROGRAM)
    private String program;

    /**
     * This field maps the column ORIGINATOR
     */
    @Column(name = ORIGINATOR)
    private String originator;

    /**
     * This field maps the column API_VERSION
     */
    @Column(name = API_VERSION)
    private String apiVersion;

    /**
     * This field maps the column STATUS
     */
    @Column(name = STATUS)
    private String status;

    /**
     * This field maps the column RECEIVED_DATE
     */
    @Column(name = RECEIVED_DATE)
    private Date receivedDate;

    /**
     * This field maps the column PROCESSED_DATE
     */
    @Column(name = PROCESSED_DATE)
    private Date processedDate;

    /**
     * This field maps the column MEMBER_ID
     */
    @Column(name = MEMBER_ID)
    private String memberId;

    /**
     * This field maps the column PARTNER_CODE
     */
    @Column(name = PARTNER_CODE)
    private String partnerCode;

  /**
   * This field maps the column ERROR_CODE
   */
  @Column(name = ERROR_CODE)
  private String errorCode;

  /**
   * This field maps the column REQUEST_PARTNER_REFERENCE
   */
  @Column(name = REQUEST_PARTNER_REFERENCE)
  private String requestPartnerReference;

    /**
     * This field maps the column ORIGINAL_DATA
     */
    @Lob
    @Column(name = ORIGINAL_DATA)
    private byte[] originalData;

    /**
     * This field maps the column MODIFIED_DATA
     */
    @Lob
    @Column(name = MODIFIED_DATA)
    private byte[] modifiedData;

    /**
     * Constant indicating the name of the table LCP_REQUEST
     */
    public static final String TABLE_NAME = "LCP_REQUEST";

    /**
     * Gets the value of id
     *
     * @return the id field of {@link TestRequestEntity#id}
     */
    public final Number getId() {
        return id;
    }

    /**
     * Sets the {@link TestRequestEntity#id} property
     *
     * @param id
     *          {@link TestRequestEntity#id} instance representing object
     */
    public final void setId(Number id) {
        this.id = id;
    }

    /**
     * Gets the value of type
     *
     * @return the type field of {@link TestRequestEntity#type}
     */
    public final String getType() {
        return type;
    }

    /**
     * Sets the {@link TestRequestEntity#type} property
     *
     * @param type
     *          {@link TestRequestEntity#type} instance representing object
     */
    public final void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the value of reqMode
     *
     * @return the reqMode field of {@link TestRequestEntity#reqMode}
     */
    public final String getReqMode() {
        return reqMode;
    }

    /**
     * Sets the {@link TestRequestEntity#reqMode} property
     *
     * @param reqMode
     *          {@link TestRequestEntity#reqMode} instance representing object
     */
    public final void setReqMode(String reqMode) {
        this.reqMode = reqMode;
    }

    /**
     * Gets the value of client
     *
     * @return the client field of {@link TestRequestEntity#client}
     */
    public final String getClient() {
        return client;
    }

    /**
     * Sets the {@link TestRequestEntity#client} property
     *
     * @param client
     *          {@link TestRequestEntity#client} instance representing object
     */
    public final void setClient(String client) {
        this.client = client;
    }

    /**
     * Gets the value of program
     *
     * @return the program field of {@link TestRequestEntity#program}
     */
    public final String getProgram() {
        return program;
    }

    /**
     * Sets the {@link TestRequestEntity#program} property
     *
     * @param program
     *          {@link TestRequestEntity#program} instance representing object
     */
    public final void setProgram(String program) {
        this.program = program;
    }

    /**
     * Gets the value of originator
     *
     * @return the originator field of {@link TestRequestEntity#originator}
     */
    public final String getOriginator() {
        return originator;
    }

    /**
     * Sets the {@link TestRequestEntity#originator} property
     *
     * @param originator
     *          {@link TestRequestEntity#originator} instance representing object
     */
    public final void setOriginator(String originator) {
        this.originator = originator;
    }

    /**
     * Gets the value of apiVersion
     *
     * @return the apiVersion field of {@link TestRequestEntity#apiVersion}
     */
    public final String getApiVersion() {
        return apiVersion;
    }

    /**
     * Sets the {@link TestRequestEntity#apiVersion} property
     *
     * @param apiVersion
     *          {@link TestRequestEntity#apiVersion} instance representing object
     */
    public final void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    /**
     * Gets the value of status
     *
     * @return the status field of {@link TestRequestEntity#status}
     */
    public final String getStatus() {
        return status;
    }

    /**
     * Sets the {@link TestRequestEntity#status} property
     *
     * @param status
     *          {@link TestRequestEntity#status} instance representing object
     */
    public final void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets the value of receivedDate
     *
     * @return the receivedDate field of {@link TestRequestEntity#receivedDate}
     */
    public final Date getReceivedDate() {
        return clone(receivedDate);
    }

    /**
     * Sets the {@link TestRequestEntity#receivedDate} property
     *
     * @param receivedDate
     *          {@link TestRequestEntity#receivedDate} instance representing object
     */
    public final void setReceivedDate(Date receivedDate) {
        this.receivedDate = clone(receivedDate);
    }

    /**
     * Gets the value of processedDate
     *
     * @return the processedDate field of {@link TestRequestEntity#processedDate}
     */
    public final Date getProcessedDate() {
        return clone(processedDate);
    }

    /**
     * Sets the {@link TestRequestEntity#processedDate} property
     *
     * @param processedDate
     *          {@link TestRequestEntity#processedDate} instance representing object
     */
    public final void setProcessedDate(Date processedDate) {
        this.processedDate = clone(processedDate);
    }

    /**
     * Gets the value of memberId
     *
     * @return the memberId field of {@link TestRequestEntity#memberId}
     */
    public final String getMemberId() {
        return memberId;
    }

    /**
     * Sets the {@link TestRequestEntity#memberId} property
     *
     * @param memberId
     *          {@link TestRequestEntity#memberId} instance representing object
     */
    public final void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    /**
     * Gets the value of partnerCode
     *
     * @return the partnerCode field of {@link TestRequestEntity#partnerCode}
     */
    public final String getPartnerCode() {
        return partnerCode;
    }

    /**
     * Sets the {@link TestRequestEntity#partnerCode} property
     *
     * @param partnerCode
     *          {@link TestRequestEntity#partnerCode} instance representing object
     */
    public final void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

  /**
   * Gets the value of errorCode
   *
   * @return the errorCode field of {@link TestRequestEntity#errorCode}
   */
  public final String getErrorCode() {
    return errorCode;
  }

  /**
   * Sets the {@link TestRequestEntity#errorCode} property
   *
   * @param errorCode {@link TestRequestEntity#errorCode} instance representing object
   */
  public final void setErrorCode(String errorCode) {
    this.errorCode = errorCode;
  }

  /**
   * Gets the value of requestPartnerReference
   *
   * @return the requestPartnerReference field of {@link TestRequestEntity#requestPartnerReference}
   */
  public final String getRequestPartnerReference() {
    return requestPartnerReference;
  }

  /**
   * Sets the {@link TestRequestEntity#requestPartnerReference} property
   *
   * @param requestPartnerReference {@link TestRequestEntity#requestPartnerReference} instance representing object
   */
  public final void setRequestPartnerReference(String requestPartnerReference) {
    this.requestPartnerReference = requestPartnerReference;
  }

  /**
   * Gets the value of originalData
   *
   * @return the originalData field of {@link TestRequestEntity#originalData}
   */
  public final byte[] getOriginalData() {
    return clone(originalData);
  }

    /**
     * Sets the {@link TestRequestEntity#originalData} property
     *
     * @param originalData
     *          {@link TestRequestEntity#originalData} instance representing object
     */
    public final void setOriginalData(byte[] originalData) {
        this.originalData = clone(originalData);
    }

    /**
     * Gets the value of modifiedData
     *
     * @return the modifiedData field of {@link TestRequestEntity#modifiedData}
     */
    public final byte[] getModifiedData() {
        return clone(modifiedData);
    }

    /**
     * Sets the {@link TestRequestEntity#modifiedData} property
     *
     * @param modifiedData
     *          {@link TestRequestEntity#modifiedData} instance representing object
     */
    public final void setModifiedData(byte[] modifiedData) {
        this.modifiedData = clone(modifiedData);
    }

  @Override
  public <T> T get(String columnName) {
    return null;
  }

  @Override
  public void set(String columnName, Object columnValue) {

  }

  /* (non-Javadoc)
     * @see com.ceppi.apocalypse.Entity#setPrimaryKey(java.lang.Object[])
     */
    @Override
    public final void primaryKey(Object ... values) {
        setId((Number)values[0]);
    }

    /**
     * This class collects all constants regarding COLumnS
     */
    public static final class COLS {
        /**
         * Constant indicating name of column ID
         */
        public static final String ID = "ID";

        /**
         * Constant indicating name of column TYPE
         */
        public static final String TYPE = "TYPE";

        /**
         * Constant indicating name of column REQ_MODE
         */
        public static final String REQ_MODE = "REQ_MODE";

        /**
         * Constant indicating name of column CLIENT
         */
        public static final String CLIENT = "CLIENT";

        /**
         * Constant indicating name of column PROGRAM
         */
        public static final String PROGRAM = "PROGRAM";

        /**
         * Constant indicating name of column ORIGINATOR
         */
        public static final String ORIGINATOR = "ORIGINATOR";

        /**
         * Constant indicating name of column API_VERSION
         */
        public static final String API_VERSION = "API_VERSION";

        /**
         * Constant indicating name of column STATUS
         */
        public static final String STATUS = "STATUS";

        /**
         * Constant indicating name of column RECEIVED_DATE
         */
        public static final String RECEIVED_DATE = "RECEIVED_DATE";

        /**
         * Constant indicating name of column PROCESSED_DATE
         */
        public static final String PROCESSED_DATE = "PROCESSED_DATE";

        /**
         * Constant indicating name of column MEMBER_ID
         */
        public static final String MEMBER_ID = "MEMBER_ID";

        /**
         * Constant indicating name of column PARTNER_CODE
         */
        public static final String PARTNER_CODE = "PARTNER_CODE";

        /**
         * Constant indicating name of column ERROR_CODE
         */
        public static final String ERROR_CODE = "ERROR_CODE";

      /**
       * Constant indicating name of column REQUEST_PARTNER_REFERENCE
       */
      public static final String REQUEST_PARTNER_REFERENCE = "REQUEST_PARTNER_REFERENCE";

      /**
       * Constant indicating name of column ORIGINAL_DATA
       */
      public static final String ORIGINAL_DATA = "ORIGINAL_DATA";

        /**
         * Constant indicating name of column MODIFIED_DATA
         */
        public static final String MODIFIED_DATA = "MODIFIED_DATA";

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
         * Constant indicating name of constraint LCP_IDX_PK_REQUEST
         */
        public static final String LCP_IDX_PK_REQUEST = "LCP_IDX_PK_REQUEST";

        /**
         * Constuctor for check constraint constants class
         */
        private CHKS() {
            super();
        }
    }
}