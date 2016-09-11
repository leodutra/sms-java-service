package br.com.m4u.sms.api.domain.results;

import java.util.ArrayList;
import java.util.List;

public final class Result<TData> {

	private ResultTypeEnum resultType;
	private TData data;
	private List<ResultError> errors = new ArrayList<ResultError>();

	
	public static <TData> Builder<TData> build(ResultTypeEnum resultType) {
		return new Builder<TData>(resultType);
	}
	
	public Result() { }

//  BUILDER PATTERN RESOLVES THIS...
//	
//	public Result(ResultTypeEnum resultType, ResultError error) {
//		setResultType(resultType);
//		setError(error);
//	}
//	
//	public Result(ResultTypeEnum resultType, TData data) {
//		setResultType(resultType);
//		setData(data);
//	}
//	
//	public Result(ResultTypeEnum resultType, ResultError error, TData data) {
//		this(resultType, error);
//		setData(data);
//	}
//	
//	public Result(ResultTypeEnum resultType, String error) {
//		this(resultType, new ResultError(error));
//	}
//	
//	public Result(ResultTypeEnum resultType, String error, TData data) {
//		this(resultType, error);
//		setData(data);
//	}
//	
//	public Result(ResultTypeEnum resultType, List<ResultError> errors) {
//		setResultType(resultType);
//		setErrors(errors);
//	}
//	
//	public Result(ResultTypeEnum resultType, List<ResultError> errors, TData data) {
//		this(resultType, errors);
//		setData(data);
//	}
	
	public boolean isSuccessful() {
		return ResultTypeEnum.SUCCESS.equals(resultType);
	}

	public TData getData() {
		return data;
	}

	public void setData(TData data) {
		this.data = data;
	}

	public ResultTypeEnum getResultType() {
		return resultType;
	}

	public void setResultType(ResultTypeEnum type) {
		this.resultType = type;
	}

	public List<ResultError> getErrors() {
		return new ArrayList<ResultError>(errors);
	}

	public void setErrors(List<ResultError> error) {
		if (error != null)
			this.errors = error;
		else
			error = new ArrayList<ResultError>();
	}

	public void setError(ResultError error) {
		errors = new ArrayList<ResultError>();
		if (error != null) errors.add(error);
	}

	public void addError(ResultError error) {
		this.errors.add(error);
	}

	public void addErrors(List<ResultError> errors) {
		errors.addAll(errors);
	}

	public void clearErrors() {
		errors.clear();
	}
	
	public static class Builder<TData> {
		private Result<TData> built = new Result<TData>();
		
		public Builder(ResultTypeEnum resultType) {
			built.setResultType(resultType);
		}
		
		public Result<TData> result() {
			return built;
		}
		
		public Builder<TData> error(ResultError error) {
			built.setError(error);
			return this;
		}
		
		public Builder<TData> errors(List<ResultError> errors) {
			built.setErrors(errors);
			return this;
		}
		
		public Builder<TData> data(TData data) {
			built.setData(data);
			return this;
		}
	}
}
