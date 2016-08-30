package br.com.m4u.sms.api.crosscutting.sql.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface IRowMapper<TEntity> {

	TEntity mapRow(ResultSet rs, int rowNum) throws SQLException;
	
}
