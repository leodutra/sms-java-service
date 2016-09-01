package br.com.m4u.sms.api.persistence.postgre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import br.com.m4u.sms.api.domain.entities.SmsMessage;
import br.com.m4u.sms.api.domain.persistence.ISmsMessageRepository;
import br.com.m4u.sms.api.domain.valueobjects.PaginationSpecification;
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

	/** @see br.com.m4u.sms.api.domain.persistence.IRepository#update(br.com.m4u.sms.api.domain.entities.Entity) */
	@Override
	public int update(SmsMessage entity) throws IntegrationException {
	
		entity = getById(entity.getId());
		
		if (entity != null) {
		
			DbQuery query = new DbQuery(dbConnection, 
					"UPDATE sms_message SET registered_on=:registered_on, sender_info=:sender, receiver_info=:receiver, body=:body, expires_on:expires_on WHERE id=:id");
			
			query.setObject("id", entity.getId()); 
			
			query.setString("sender", entity.getSenderInformation());
			query.setString("receiver", entity.getReceiverInformation());
			query.setTimestamp("registered_on", Timestamp.from(entity.getRegistrationTime()));
			query.setTimestamp("expires_on", Timestamp.from(entity.getExpirationTime()));
			query.setString("body", entity.getBody());
			
			return query.autoClose().executeUpdate();
		}
		
		return 0;
	}

	/** @see br.com.m4u.sms.api.domain.persistence.IRepository#delete(java.lang.Object) */
	@Override
	public int delete(Object id) throws IntegrationException  {
		DbQuery query = new DbQuery(dbConnection, "DELETE FROM sms_message WHERE id = :id");
		
		query.setObject("id", id); 
	
		return query.autoClose().executeUpdate();
	}

	/** @see br.com.m4u.sms.api.domain.persistence.IRepository#delete(br.com.m4u.sms.api.domain.entities.Entity) */
	@Override
	public int delete(SmsMessage entity) throws IntegrationException {
		return delete(entity.getId());
	}
	
	
	@Override
	public List<SmsMessage> listAll(PaginationSpecification pagination) throws IntegrationException {
		
		List<SmsMessage> result = new ArrayList<SmsMessage>();
		
		String sql = new StringBuilder(300)
				.append("SELECT id, registered_on datetime , sender_info, receiver_info, expires_on, body ")
				.append(" FROM sms_message ")
				.append(" ORDER BY id ASC OFFSET :offset LIMIT :max_rows")
				.toString();
		
		DbQuery query = new DbQuery(dbConnection, sql);
		
		query.setObject(":offset", pagination.getCurrentPage());
		query.setObject(":max_rows", pagination.getPageMaxRows());
		
		try {
			ResultSet rs = query.execute();
			
			while(rs.next()) {
				result.add(smsMessageMapper.mapRow(rs, rs.getRow()));
			}
			
		} catch(SQLException ex) {
			throw new IntegrationException(ex);
		}
		finally {
			query.close();
		}
		
		return result;
	}
}
