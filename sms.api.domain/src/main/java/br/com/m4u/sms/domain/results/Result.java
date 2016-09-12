package br.com.m4u.sms.domain.results;

import java.util.ArrayList;
import java.util.List;

public class Result<TData> {

	private ResultTypeEnum resultType;
	private TData data;
	private List<ResultError> errors = new ArrayList<ResultError>();

	public static <TData> Builder<TData> getBuilder(ResultTypeEnum resultType) {
		return new Builder<TData>(resultType);
	}
	
	public Result() { }

//  BUILDER PATTERN RESOLVES THE CONSTRUCTOR HELL
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

	public void addError(ResultError error) {
		this.errors.add(error);
	}

	public void addErrors(List<ResultError> errors) {
		this.errors.addAll(errors);
	}

	public void clearErrors() {
		errors.clear();
	}
	
	public static class Builder<TData> {
		private Result<TData> built;
		
		
		public Builder(ResultTypeEnum resultType) {
			buildNew();
			built.setResultType(resultType);
		}
		
		public void buildNew() {
			built = new Result<TData>();
		}
		
		public Result<TData> result() {
			return built;
		}
		
		public Builder<TData> error(String errorMsg) {
			return error(new ResultError(errorMsg));
		}
		
		public Builder<TData> error(ResultError error) {
			built.addError(error);
			return this;
		}
		
		public Builder<TData> errors(List<ResultError> errors) {
			built.addErrors(errors);
			return this;
		}
		
		public Builder<TData> clearErrors() {
			built.clearErrors();
			return this;
		}
		
		public Builder<TData> data(TData data) {
			built.setData(data);
			return this;
		}
	}
}
