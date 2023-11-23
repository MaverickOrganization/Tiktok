package com.dy.dyweb.base;

import com.dy.dyweb.util.StringUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/**
 * @author: lzyCug
 * @date: 2021/3/8 15:24
 * @description: 类型转换器，用于数据库的varchar和Java中List<String>类型的相互转换
 */
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
public class ListToVarcharTypeHandler implements TypeHandler<List<Long>> {
    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, List<Long> strings, JdbcType jdbcType) throws SQLException {
        // 遍历List类型的入参，拼装为String类型，使用Statement对象插入数据库
        StringBuffer sb = new StringBuffer();
        for (int j = 0; j < strings.size(); j++) {
            if (j == strings.size() - 1) {
                sb.append(strings.get(j));
            } else {
                sb.append(strings.get(j)).append(",");
            }
        }
        preparedStatement.setString(i, sb.toString());
    }

    @Override
    public List<Long> getResult(ResultSet resultSet, String s) throws SQLException {
        // 获取String类型的结果，使用","分割为List后返回
        String resultString = resultSet.getString(s);
        return null;
    }

    @Override
    public List<Long> getResult(ResultSet resultSet, int i) throws SQLException {
        // 获取String类型的结果，使用","分割为List后返回
        String resultString = resultSet.getString(i);
        return null;
    }

    @Override
    public List<Long> getResult(CallableStatement callableStatement, int i) throws SQLException {
        // 获取String类型的结果，使用","分割为List后返回
        return null;
    }
}
