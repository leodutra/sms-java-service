package br.com.m4u.sms.api.persistence.postgre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import br.com.m4u.sms.api.domain.entities.SmsMessage;
import br.com.m4u.sms.api.domain.persistence.ISmsMessageRepository;
import br.com.m4u.sms.api.exceptions.IntegrationException;
import br.com.m4u.sms.api.jdbc.functions.IRowMapper;
import jodd.db.DbQuery;

public class SmsMessageRepository extends BaseRepository implements ISmsMessageRepository {

	private static IRowMapper<SmsMessage> smsMessageMapper = (ResultSet rs, int rowNum) -> {
		SmsMessage entity = new SmsMessage();
		entity.setBody(rs.getString("body"));
		entity.setRegistrationTime(rs.getTimestamp("registered_on").toInstant());
		entity.setReceiverInformation(rs.getString("receiver_info"));
		entity.setSenderInformation(rs.getString("sender_info"));
		entity.setExpirationTime(rs.getTimestamp("expires_on").toInstant());
		return null;
	};
	
	public SmsMessageRepository(Connection dbConnection) {
		super(dbConnection);
	}
	

	/** @throws SQLException 
	 * @see br.com.m4u.sms.api.domain.persistence.IRepository#getById(java.lang.Object) */
	@Override
	public SmsMessage getById(Object id) throws IntegrationException  {

		DbQuery query = new DbQuery(dbConnection, "SELECT id, registered_on datetime , sender_info, receiver_info, expires_on, body FROM sms_message WHERE id=:id");
		
		query.setObject("id", id);
		
		try {
			ResultSet rs = query.execute();
			if (rs.first()) return smsMessageMapper.mapRow(rs, rs.getRow());
	
		} catch(SQLException ex) {
			throw new IntegrationException(ex);
		}
		finally {
			query.close();
		}
		
		return null;
	}

	/** @see br.com.m4u.sms.api.domain.persistence.IRepository#insert(br.com.m4u.sms.api.domain.entities.Entity) */
	@Override
	public SmsMessage insert(SmsMessage entity) throws IntegrationException {
		
		DbQuery query = new DbQuery(dbConnection, 
				"INSERT INTO sms_message(registered_on, sender_info, receiver_info, body) VALUES (:registered_on, :sender_info, :receiver_info, :body)");
		
		query.setTimestamp("registered_on", Timestamp.from(entity.getRegistrationTime())); 
		query.setString("sender_info", entity.getSenderInformation()); 
		query.setString("receiver_info", entity.getReceiverInformation()); 
		query.setString("body", entity.getBody());
		
		try {
			ResultSet rs = query.execute();
			if (rs.first()) return smsMessageMapper.mapRow(rs, rs.getRow());
	
		} catch(SQLException ex) {
			throw new IntegrationException(ex);
		}
		finally {
			query.close();
		}
		return null;
	}
}
