package br.com.m4u.sms.api.jdbc.functions;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface 
public interface IRowMapper<TEntity> {

	TEntity mapRow(ResultSet rs, int rowNum) throws SQLException;
	
}
