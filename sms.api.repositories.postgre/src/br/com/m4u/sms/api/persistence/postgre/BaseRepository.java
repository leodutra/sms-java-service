package br.com.m4u.sms.api.persistence.postgre;

import java.sql.Connection;

public abstract class BaseRepository {

	protected final Connection dbConnection;

	public BaseRepository(Connection dbConnection) {
		this.dbConnection = dbConnection;
	}
}
