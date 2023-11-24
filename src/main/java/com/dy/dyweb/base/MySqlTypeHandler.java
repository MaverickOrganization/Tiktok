package com.dy.dyweb.base;

import com.alibaba.fastjson.JSON;
import com.dy.dyweb.bean.Comment;
import com.dy.dyweb.dao.CommentDao;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@MappedJdbcTypes(JdbcType.VARCHAR)
@MappedTypes(List.class)
public class MySqlTypeHandler<T extends Object> extends BaseTypeHandler<T> {
 
    private Class<T> clazz;
    /**
     * 设置非空参数
     * @param ps
     * @param i
     * @param parameter
     * @param jdbcType
     * @throws SQLException
     */
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, JSON.toJSONString(parameter));
    }
 
    /**
     * 根据列名，获取可以为空的结果
     * @param rs
     * @param columnName
     * @return
     * @throws SQLException
     */
    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String sqlJson = rs.getString(columnName);
        if (null != sqlJson){
            return JSON.parseObject(sqlJson, clazz);
        }
        return null;
    }
 
    /**
     * 根据列索引，获取可以为空的结果
     * @param rs
     * @param columnIndex
     * @return
     * @throws SQLException
     */
    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String sqlJson = rs.getString(columnIndex);
        if (null != sqlJson){
            return JSON.parseObject(sqlJson, clazz);
        }
        return null;
    }
 
    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String sqlJson = cs.getString(columnIndex);
        if (null != sqlJson){
            return JSON.parseObject(sqlJson, clazz);
 
        }
        return null;
    }
}