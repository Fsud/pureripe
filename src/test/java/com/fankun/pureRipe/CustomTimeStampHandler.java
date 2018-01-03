package com.fankun.pureRipe;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

/**
 * Created by k on 2017/6/22.
 */
public class CustomTimeStampHandler extends BaseTypeHandler<Long> {
    @Override
    public void setNonNullParameter(PreparedStatement preparedStatement, int i, Long aLong, JdbcType jdbcType) throws SQLException {
        preparedStatement.setTime(i, new Time(aLong));
    }

    @Override
    public Long getNullableResult(ResultSet resultSet, String s) throws SQLException {
        return resultSet.getTime(s).getTime();
    }

    @Override
    public Long getNullableResult(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getTime(i).getTime();
    }

    @Override
    public Long getNullableResult(CallableStatement callableStatement, int i) throws SQLException {
        return callableStatement.getTime(i).getTime();
    }
}
