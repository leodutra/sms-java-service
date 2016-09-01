package br.com.m4u.sms.api.domain.valueobjects;

import java.util.ArrayList;
import java.util.List;

public abstract class OperationInfo {
	
	private List<String> errors = new ArrayList<String>(10);
	
	public boolean isSuccessful() {
		return getErrors().size() == 0;
	}
	
	public List<String> getErrors() {
		return new ArrayList<String>(errors);
	}
	
	public void setErrors(List<String> errors) {
		if (errors != null) 
			this.errors = errors;
		else 
			errors = new ArrayList<String>();
	}
	
	public void resetError(String error) {
		errors.clear();
		if (error != null) errors.add(error);
	}
	
	public void addError(String error) {
		this.errors.add(error);
	}
	
	public void addError(OperationInfo operationInfo) {
		errors.addAll(operationInfo.getErrors());
	} 
	
	public void clearErrors() {
		errors.clear();
	}
}
