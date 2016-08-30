package br.com.m4u.sms.api.domain.valueobjects;

public final class OperationResult<TData> extends OperationInfo {
	private TData data;
	
	public OperationResult() {}
	public OperationResult(TData data) {
		this.data = data;
	}

	public TData getData() {
		return data;
	}

	public void setData(TData data) {
		this.data = data;
	}
}
