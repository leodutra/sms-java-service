package br.com.m4u.sms.infrastructure.data.persistence.postgre;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import br.com.m4u.sms.domain.model.SmsMessage;
import br.com.m4u.sms.domain.persistence.SmsMessageRepository;
import br.com.m4u.sms.infrastructure.crosscutting.exceptions.IntegrationException;


public class PostgreSmsMessageRepository extends BaseRepository implements SmsMessageRepository {

	public PostgreSmsMessageRepository(Connection dbConnection) {
		super(dbConnection);
	}

	/**
	 * @throws SQLException
	 * @see br.com.m4u.sms.domain.persistence.Repository#findById(java.lang.Object)
	 */
	@Override
	public SmsMessage findById(Object id) throws IntegrationException {

		ResultSetHandler<SmsMessage> resultSetHandler = new BeanHandler<SmsMessage>(SmsMessage.class,
				new SmsMessageRowProcessor());
		try {
			return new QueryRunner().query(dbConnection,
					"SELECT id, registered_on, sender_info, addressee_info, expires_on, body FROM public.sms_message WHERE id = ?",
					resultSetHandler, id);

		}
		catch (SQLException ex) {
			throw new IntegrationException(ex);
		}
	}

	/** @see br.com.m4u.sms.domain.persistence.Repository#insert(br.com.m4u.sms.domain.model.Entity) */
	@Override
	public SmsMessage insert(SmsMessage entity) throws IntegrationException {

		try {
			Long scalar = new QueryRunner().insert(dbConnection,

					"INSERT INTO public.sms_message(sender_info, addressee_info, registered_on, expires_on, body) VALUES (?, ?, ?, ?, ?)",

					new ScalarHandler<Long>(),

					entity.getSenderInformation(), entity.getAddresseeInformation(),
					Timestamp.from(entity.getRegistrationTime()),
					entity.getExpirationTime() != null ? Timestamp.from(entity.getExpirationTime()) : null,
					entity.getBody());

			entity.setId(scalar);

		}
		catch (SQLException ex) {
			throw new IntegrationException(ex);
		}

		return entity;
	}

	@Override
	public List<SmsMessage> listAll() throws IntegrationException {

		ResultSetHandler<List<SmsMessage>> smsMessageListHandler = new BeanListHandler<SmsMessage>(SmsMessage.class,
				new SmsMessageRowProcessor());
		try {
			return new QueryRunner().query(dbConnection,
					"SELECT id, registered_on, sender_info, addressee_info, expires_on, body FROM public.sms_message",
					smsMessageListHandler);

		}
		catch (SQLException ex) {
			throw new IntegrationException(ex);
		}
	}

	private static class SmsMessageRowProcessor extends BasicRowProcessor {

		@Override
		public <T> T toBean(ResultSet rs, Class<T> type) throws SQLException {

			SmsMessage sms = new SmsMessage(); // map by model name

			sms.setId(rs.getLong("id"));
			sms.setBody(rs.getString("body"));
			sms.setAddresseeInformation(rs.getString("addressee_info"));
			sms.setSenderInformation(rs.getString("sender_info"));

			sms.setRegistrationTime(Instant.ofEpochMilli(rs.getTimestamp("registered_on").getTime()));

			final Date expirationTime = rs.getTimestamp("expires_on");
			if (expirationTime != null) sms.setExpirationTime(expirationTime.toInstant());
			return (T) sms;
		}

		@Override
		public <T> List<T> toBeanList(ResultSet rs, Class<T> type) throws SQLException {
			List<T> result = new ArrayList<T>();
			while (rs.next())
				result.add(toBean(rs, type));
			return result;
		}
	}
}
