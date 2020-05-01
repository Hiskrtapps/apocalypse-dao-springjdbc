/*
 * © 2020 Ceppi Productions
 */

package com.github.hiskrtapps.apocalypse.dao.springjdbc.paging;

import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;

import com.ceppi.apocalypse.dao.paging.PagedResult;
import com.github.hiskrtapps.apocalypse.dao.springjdbc.TestCompleteMockEntity;
import com.github.hiskrtapps.apocalypse.dao.springjdbc.statements.paging.PaginationByRowNumber;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class ResultMapperTest {

  /**
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
  }
  @Test
  public void testMapRowZero() throws SQLException {
    ResultSet rs = mockResultSet();

    PaginationByRowNumber.ResultMapper<TestCompleteMockEntity> ResultMapper = new PaginationByRowNumber.ResultMapper<>(TestCompleteMockEntity.class);

    PagedResult<TestCompleteMockEntity> pageResult = ResultMapper.mapRow(rs,0);
    Assert.assertNotNull(pageResult);
  }
  @Test
  public void testMapRowFirstRow() throws SQLException {
    ResultSet rs = mockResultSet();

    PaginationByRowNumber.ResultMapper<TestCompleteMockEntity> ResultMapper = new PaginationByRowNumber.ResultMapper<>(TestCompleteMockEntity.class);

    PagedResult<TestCompleteMockEntity> pageResult = ResultMapper.mapRow(rs, 1);
    Assert.assertNotNull(pageResult);
  }

  @Test
  public void testMapRowSecondRow() throws SQLException {
    ResultSet rs = mockResultSet();

    PaginationByRowNumber.ResultMapper<TestCompleteMockEntity> ResultMapper = new PaginationByRowNumber.ResultMapper<>(TestCompleteMockEntity.class);

    PagedResult<TestCompleteMockEntity> pageResult = ResultMapper.mapRow(rs, 2);
    Assert.assertNotNull(pageResult);
  }

  /**
   * @return resultset mocket, just getMetadata is implemented
   */
  private ResultSet mockResultSet() {
    return new ResultSet() {

      @Override
      public boolean isWrapperFor(Class<?> arg0) throws SQLException {

        return false;
      }

      @Override
      public <T> T unwrap(Class<T> arg0) throws SQLException {

        return null;
      }

      @Override
      public boolean absolute(int arg0) throws SQLException {

        return false;
      }

      @Override
      public void afterLast() throws SQLException {

      }

      @Override
      public void beforeFirst() throws SQLException {

      }

      @Override
      public void cancelRowUpdates() throws SQLException {

      }

      @Override
      public void clearWarnings() throws SQLException {

      }

      @Override
      public void close() throws SQLException {

      }

      @Override
      public void deleteRow() throws SQLException {

      }

      @Override
      public int findColumn(String arg0) throws SQLException {

        return 0;
      }

      @Override
      public boolean first() throws SQLException {

        return false;
      }

      @Override
      public Array getArray(int arg0) throws SQLException {

        return null;
      }

      @Override
      public Array getArray(String arg0) throws SQLException {

        return null;
      }

      @Override
      public InputStream getAsciiStream(int arg0) throws SQLException {

        return null;
      }

      @Override
      public InputStream getAsciiStream(String arg0) throws SQLException {

        return null;
      }

      @Override
      public BigDecimal getBigDecimal(int arg0) throws SQLException {

        return null;
      }

      @Override
      public BigDecimal getBigDecimal(String arg0) throws SQLException {

        return null;
      }

      @Override
      public BigDecimal getBigDecimal(int arg0, int arg1) throws SQLException {

        return null;
      }

      @Override
      public BigDecimal getBigDecimal(String arg0, int arg1) throws SQLException {

        return null;
      }

      @Override
      public InputStream getBinaryStream(int arg0) throws SQLException {

        return null;
      }

      @Override
      public InputStream getBinaryStream(String arg0) throws SQLException {

        return null;
      }

      @Override
      public Blob getBlob(int arg0) throws SQLException {

        return null;
      }

      @Override
      public Blob getBlob(String arg0) throws SQLException {

        return null;
      }

      @Override
      public boolean getBoolean(int arg0) throws SQLException {

        return false;
      }

      @Override
      public boolean getBoolean(String arg0) throws SQLException {

        return false;
      }

      @Override
      public byte getByte(int arg0) throws SQLException {

        return 0;
      }

      @Override
      public byte getByte(String arg0) throws SQLException {

        return 0;
      }

      @Override
      public byte[] getBytes(int arg0) throws SQLException {

        return null;
      }

      @Override
      public byte[] getBytes(String arg0) throws SQLException {

        return null;
      }

      @Override
      public Reader getCharacterStream(int arg0) throws SQLException {

        return null;
      }

      @Override
      public Reader getCharacterStream(String arg0) throws SQLException {

        return null;
      }

      @Override
      public Clob getClob(int arg0) throws SQLException {

        return null;
      }

      @Override
      public Clob getClob(String arg0) throws SQLException {

        return null;
      }

      @Override
      public int getConcurrency() throws SQLException {

        return 0;
      }

      @Override
      public String getCursorName() throws SQLException {

        return null;
      }

      @Override
      public Date getDate(int arg0) throws SQLException {

        return null;
      }

      @Override
      public Date getDate(String arg0) throws SQLException {

        return null;
      }

      @Override
      public Date getDate(int arg0, Calendar arg1) throws SQLException {

        return null;
      }

      @Override
      public Date getDate(String arg0, Calendar arg1) throws SQLException {

        return null;
      }

      @Override
      public double getDouble(int arg0) throws SQLException {

        return 0;
      }

      @Override
      public double getDouble(String arg0) throws SQLException {

        return 0;
      }

      @Override
      public int getFetchDirection() throws SQLException {

        return 0;
      }

      @Override
      public int getFetchSize() throws SQLException {

        return 0;
      }

      @Override
      public float getFloat(int arg0) throws SQLException {

        return 0;
      }

      @Override
      public float getFloat(String arg0) throws SQLException {

        return 0;
      }

      @Override
      public int getHoldability() throws SQLException {

        return 0;
      }

      @Override
      public int getInt(int arg0) throws SQLException {

        return 0;
      }

      @Override
      public int getInt(String arg0) throws SQLException {

        return 0;
      }

      @Override
      public long getLong(int arg0) throws SQLException {

        return 0;
      }

      @Override
      public long getLong(String arg0) throws SQLException {

        return 0;
      }

      @Override
      public ResultSetMetaData getMetaData() throws SQLException {
        return createMetadata();
      }

      /**
       * @return ResultSetMetaData mocked
       */
      private ResultSetMetaData createMetadata() {
        return new ResultSetMetaData() {

          @Override
          public boolean isWrapperFor(Class<?> iface) throws SQLException {

            return false;
          }

          @Override
          public <T> T unwrap(Class<T> iface) throws SQLException {

            return null;
          }

          @Override
          public String getCatalogName(int column) throws SQLException {

            return null;
          }

          @Override
          public String getColumnClassName(int column) throws SQLException {

            return null;
          }

          @Override
          public int getColumnCount() throws SQLException {
            return 0;
          }

          @Override
          public int getColumnDisplaySize(int column) throws SQLException {

            return 0;
          }

          @Override
          public String getColumnLabel(int column) throws SQLException {

            return null;
          }

          @Override
          public String getColumnName(int column) throws SQLException {

            return null;
          }

          @Override
          public int getColumnType(int column) throws SQLException {

            return 0;
          }

          @Override
          public String getColumnTypeName(int column) throws SQLException {

            return null;
          }

          @Override
          public int getPrecision(int column) throws SQLException {

            return 0;
          }

          @Override
          public int getScale(int column) throws SQLException {

            return 0;
          }

          @Override
          public String getSchemaName(int column) throws SQLException {

            return null;
          }

          @Override
          public String getTableName(int column) throws SQLException {

            return null;
          }

          @Override
          public boolean isAutoIncrement(int column) throws SQLException {

            return false;
          }

          @Override
          public boolean isCaseSensitive(int column) throws SQLException {

            return false;
          }

          @Override
          public boolean isCurrency(int column) throws SQLException {

            return false;
          }

          @Override
          public boolean isDefinitelyWritable(int column) throws SQLException {

            return false;
          }

          @Override
          public int isNullable(int column) throws SQLException {

            return 0;
          }

          @Override
          public boolean isReadOnly(int column) throws SQLException {

            return false;
          }

          @Override
          public boolean isSearchable(int column) throws SQLException {

            return false;
          }

          @Override
          public boolean isSigned(int column) throws SQLException {

            return false;
          }

          @Override
          public boolean isWritable(int column) throws SQLException {

            return false;
          }

        };
      }

      @Override
      public Reader getNCharacterStream(int arg0) throws SQLException {

        return null;
      }

      @Override
      public Reader getNCharacterStream(String arg0) throws SQLException {

        return null;
      }

      @Override
      public NClob getNClob(int columnIndex) throws SQLException {

        return null;
      }

      @Override
      public NClob getNClob(String columnLabel) throws SQLException {

        return null;
      }

      @Override
      public String getNString(int columnIndex) throws SQLException {

        return null;
      }

      @Override
      public String getNString(String columnLabel) throws SQLException {

        return null;
      }

      @Override
      public Object getObject(int columnIndex) throws SQLException {

        return null;
      }

      @Override
      public Object getObject(String columnLabel) throws SQLException {

        return null;
      }

      @Override
      public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {

        return null;
      }

      @Override
      public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {

        return null;
      }

      @Override
      public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {

        return null;
      }

      @Override
      public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {

        return null;
      }

      @Override
      public Ref getRef(int columnIndex) throws SQLException {

        return null;
      }

      @Override
      public Ref getRef(String columnLabel) throws SQLException {

        return null;
      }

      @Override
      public int getRow() throws SQLException {

        return 0;
      }

      @Override
      public RowId getRowId(int columnIndex) throws SQLException {

        return null;
      }

      @Override
      public RowId getRowId(String columnLabel) throws SQLException {

        return null;
      }

      @Override
      public SQLXML getSQLXML(int columnIndex) throws SQLException {

        return null;
      }

      @Override
      public SQLXML getSQLXML(String columnLabel) throws SQLException {

        return null;
      }

      @Override
      public short getShort(int columnIndex) throws SQLException {

        return 0;
      }

      @Override
      public short getShort(String columnLabel) throws SQLException {

        return 0;
      }

      @Override
      public Statement getStatement() throws SQLException {

        return null;
      }

      @Override
      public String getString(int columnIndex) throws SQLException {

        return null;
      }

      @Override
      public String getString(String columnLabel) throws SQLException {

        return null;
      }

      @Override
      public Time getTime(int columnIndex) throws SQLException {

        return null;
      }

      @Override
      public Time getTime(String columnLabel) throws SQLException {

        return null;
      }

      @Override
      public Time getTime(int columnIndex, Calendar cal) throws SQLException {

        return null;
      }

      @Override
      public Time getTime(String columnLabel, Calendar cal) throws SQLException {

        return null;
      }

      @Override
      public Timestamp getTimestamp(int columnIndex) throws SQLException {

        return null;
      }

      @Override
      public Timestamp getTimestamp(String columnLabel) throws SQLException {

        return null;
      }

      @Override
      public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {

        return null;
      }

      @Override
      public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {

        return null;
      }

      @Override
      public int getType() throws SQLException {

        return 0;
      }

      @Override
      public URL getURL(int columnIndex) throws SQLException {

        return null;
      }

      @Override
      public URL getURL(String columnLabel) throws SQLException {

        return null;
      }

      @Override
      public InputStream getUnicodeStream(int columnIndex) throws SQLException {

        return null;
      }

      @Override
      public InputStream getUnicodeStream(String columnLabel) throws SQLException {

        return null;
      }

      @Override
      public SQLWarning getWarnings() throws SQLException {

        return null;
      }

      @Override
      public void insertRow() throws SQLException {

      }

      @Override
      public boolean isAfterLast() throws SQLException {

        return false;
      }

      @Override
      public boolean isBeforeFirst() throws SQLException {

        return false;
      }

      @Override
      public boolean isClosed() throws SQLException {

        return false;
      }

      @Override
      public boolean isFirst() throws SQLException {

        return false;
      }

      @Override
      public boolean isLast() throws SQLException {

        return false;
      }

      @Override
      public boolean last() throws SQLException {

        return false;
      }

      @Override
      public void moveToCurrentRow() throws SQLException {

      }

      @Override
      public void moveToInsertRow() throws SQLException {

      }

      @Override
      public boolean next() throws SQLException {

        return false;
      }

      @Override
      public boolean previous() throws SQLException {

        return false;
      }

      @Override
      public void refreshRow() throws SQLException {

      }

      @Override
      public boolean relative(int rows) throws SQLException {

        return false;
      }

      @Override
      public boolean rowDeleted() throws SQLException {

        return false;
      }

      @Override
      public boolean rowInserted() throws SQLException {

        return false;
      }

      @Override
      public boolean rowUpdated() throws SQLException {

        return false;
      }

      @Override
      public void setFetchDirection(int direction) throws SQLException {

      }

      @Override
      public void setFetchSize(int rows) throws SQLException {

      }

      @Override
      public void updateArray(int columnIndex, Array x) throws SQLException {

      }

      @Override
      public void updateArray(String columnLabel, Array x) throws SQLException {

      }

      @Override
      public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {

      }

      @Override
      public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {

      }

      @Override
      public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {

      }

      @Override
      public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {

      }

      @Override
      public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {

      }

      @Override
      public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {

      }

      @Override
      public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {

      }

      @Override
      public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {

      }

      @Override
      public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {

      }

      @Override
      public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {

      }

      @Override
      public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {

      }

      @Override
      public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {

      }

      @Override
      public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {

      }

      @Override
      public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {

      }

      @Override
      public void updateBlob(int columnIndex, Blob x) throws SQLException {

      }

      @Override
      public void updateBlob(String columnLabel, Blob x) throws SQLException {

      }

      @Override
      public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {

      }

      @Override
      public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {

      }

      @Override
      public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {

      }

      @Override
      public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {

      }

      @Override
      public void updateBoolean(int columnIndex, boolean x) throws SQLException {

      }

      @Override
      public void updateBoolean(String columnLabel, boolean x) throws SQLException {

      }

      @Override
      public void updateByte(int columnIndex, byte x) throws SQLException {

      }

      @Override
      public void updateByte(String columnLabel, byte x) throws SQLException {

      }

      @Override
      public void updateBytes(int columnIndex, byte[] x) throws SQLException {

      }

      @Override
      public void updateBytes(String columnLabel, byte[] x) throws SQLException {

      }

      @Override
      public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {

      }

      @Override
      public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {

      }

      @Override
      public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {

      }

      @Override
      public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {

      }

      @Override
      public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {

      }

      @Override
      public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {

      }

      @Override
      public void updateClob(int columnIndex, Clob x) throws SQLException {

      }

      @Override
      public void updateClob(String columnLabel, Clob x) throws SQLException {

      }

      @Override
      public void updateClob(int columnIndex, Reader reader) throws SQLException {

      }

      @Override
      public void updateClob(String columnLabel, Reader reader) throws SQLException {

      }

      @Override
      public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {

      }

      @Override
      public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {

      }

      @Override
      public void updateDate(int columnIndex, Date x) throws SQLException {

      }

      @Override
      public void updateDate(String columnLabel, Date x) throws SQLException {

      }

      @Override
      public void updateDouble(int columnIndex, double x) throws SQLException {

      }

      @Override
      public void updateDouble(String columnLabel, double x) throws SQLException {

      }

      @Override
      public void updateFloat(int columnIndex, float x) throws SQLException {

      }

      @Override
      public void updateFloat(String columnLabel, float x) throws SQLException {

      }

      @Override
      public void updateInt(int columnIndex, int x) throws SQLException {

      }

      @Override
      public void updateInt(String columnLabel, int x) throws SQLException {

      }

      @Override
      public void updateLong(int columnIndex, long x) throws SQLException {

      }

      @Override
      public void updateLong(String columnLabel, long x) throws SQLException {

      }

      @Override
      public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {

      }

      @Override
      public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {

      }

      @Override
      public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {

      }

      @Override
      public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {

      }

      @Override
      public void updateNClob(int columnIndex, NClob nClob) throws SQLException {

      }

      @Override
      public void updateNClob(String columnLabel, NClob nClob) throws SQLException {

      }

      @Override
      public void updateNClob(int columnIndex, Reader reader) throws SQLException {

      }

      @Override
      public void updateNClob(String columnLabel, Reader reader) throws SQLException {

      }

      @Override
      public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {

      }

      @Override
      public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {

      }

      @Override
      public void updateNString(int columnIndex, String nString) throws SQLException {

      }

      @Override
      public void updateNString(String columnLabel, String nString) throws SQLException {

      }

      @Override
      public void updateNull(int columnIndex) throws SQLException {

      }

      @Override
      public void updateNull(String columnLabel) throws SQLException {

      }

      @Override
      public void updateObject(int columnIndex, Object x) throws SQLException {

      }

      @Override
      public void updateObject(String columnLabel, Object x) throws SQLException {

      }

      @Override
      public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {

      }

      @Override
      public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {

      }

      @Override
      public void updateRef(int columnIndex, Ref x) throws SQLException {

      }

      @Override
      public void updateRef(String columnLabel, Ref x) throws SQLException {

      }

      @Override
      public void updateRow() throws SQLException {

      }

      @Override
      public void updateRowId(int columnIndex, RowId x) throws SQLException {

      }

      @Override
      public void updateRowId(String columnLabel, RowId x) throws SQLException {

      }

      @Override
      public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {

      }

      @Override
      public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {

      }

      @Override
      public void updateShort(int columnIndex, short x) throws SQLException {

      }

      @Override
      public void updateShort(String columnLabel, short x) throws SQLException {

      }

      @Override
      public void updateString(int columnIndex, String x) throws SQLException {

      }

      @Override
      public void updateString(String columnLabel, String x) throws SQLException {

      }

      @Override
      public void updateTime(int columnIndex, Time x) throws SQLException {

      }

      @Override
      public void updateTime(String columnLabel, Time x) throws SQLException {

      }

      @Override
      public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {

      }

      @Override
      public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {

      }

      @Override
      public boolean wasNull() throws SQLException {

        return false;
      }

    };
  }

}
