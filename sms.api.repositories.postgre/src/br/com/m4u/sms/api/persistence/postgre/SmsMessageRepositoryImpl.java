package br.com.m4u.sms.api.persistence.postgre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import br.com.m4u.sms.api.domain.entities.SmsMessage;
import br.com.m4u.sms.api.domain.persistence.SmsMessageRepository;
import br.com.m4u.sms.api.exceptions.IntegrationException;
import br.com.m4u.sms.api.jdbc.functions.RowMapper;
import jodd.db.DbQuery;

public class SmsMessageRepositoryImpl extends BaseRepository implements SmsMessageRepository {

	private static RowMapper<SmsMessage> smsMessageMapper = (ResultSet rs, int rowNum) -> {
		SmsMessage entity = new SmsMessage();
		entity.setBody(rs.getString("body"));
		entity.setRegistrationTime(rs.getTimestamp("registered_on").toInstant());
		entity.setReceiverInformation(rs.getString("receiver_info"));
		entity.setSenderInformation(rs.getString("sender_info"));
		entity.setExpirationTime(rs.getTimestamp("expires_on").toInstant());
		return null;
	};
	
	public SmsMessageRepositoryImpl(Connection dbConnection) {
		super(dbConnection);
	}

	/** @throws SQLException 
	 * @see br.com.m4u.sms.api.domain.persistence.Repository#getById(java.lang.Object) */
	@Override
	public SmsMessage getById(Object id) throws IntegrationException  {

		DbQuery query = new DbQuery(dbConnection, "SELECT id, registered_on datetime , sender_info, receiver_info, expires_on, body FROM public.sms_message WHERE id = :id");
		
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

	/** @see br.com.m4u.sms.api.domain.persistence.Repository#insert(br.com.m4u.sms.api.domain.entities.Entity) */
	@Override
	public SmsMessage insert(SmsMessage entity) throws IntegrationException {
		
		DbQuery query = new DbQuery(dbConnection, 
				"INSERT INTO public.sms_message(sender_info, receiver_info, registered_on, expires_on, body) VALUES (:sender_info, :receiver_info, :registered_on, :expires_on, :body)");
		
		
		query.setString("sender_info", entity.getSenderInformation()); 
		query.setString("receiver_info", entity.getReceiverInformation()); 
		query.setString("body", entity.getBody());
		query.setTimestamp("registered_on", Timestamp.from(entity.getRegistrationTime())); 
		query.setTimestamp("expires_on", Timestamp.from(entity.getExpirationTime())); 
		
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
