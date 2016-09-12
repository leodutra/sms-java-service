package br.com.m4u.sms.jdbc.functions;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface 
public interface RowMapper<TEntity> {

	TEntity mapRow(ResultSet rs, int rowNum) throws SQLException;
	
}
