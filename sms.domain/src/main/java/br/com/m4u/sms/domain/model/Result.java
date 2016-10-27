package br.com.m4u.sms.domain.model;

import java.util.ArrayList;
import java.util.List;


public class Result<TData> {

	private Type resultType;
	private TData data;
	private List<String> details = new ArrayList<String>();

	public Result() {}

	public Result(Type resultType) {
		withResultType(resultType);
	}

	public boolean isSuccessful() {
		return Type.SUCCESS.equals(resultType);
	}

	public TData getData() {
		return data;
	}

	public Result<TData> withData(TData data) {
		this.data = data;
		return this;
	}

	public Type getResultType() {
		return resultType;
	}

	private Result<TData> withResultType(Type type) {
		this.resultType = type;
		return this;
	}

	public List<String> getDetails() {
		return new ArrayList<String>(details);
	}

	public Result<TData> withDetail(String detail) {
		details.add(detail);
		return this;
	}

	public Result<TData> withDetails(List<String> details) {
		this.details.addAll(details);
		return this;
	}

	public static enum Type {

		SUCCESS(1),
		VALIDATION_ERROR(2),
		INTERNAL_SERVER_ERROR(3),
		OPERATION_FAILURE(4),
		RESOURCE_NOT_FOUND(5);

		private int code;

		private Type(int code) {
			this.code = code;
		}

		public int getCode() {
			return code;
		}

		public String getName() {
			return toString();
		}
	}
}
