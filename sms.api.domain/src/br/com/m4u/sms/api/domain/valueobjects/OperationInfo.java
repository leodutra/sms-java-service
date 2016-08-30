package br.com.m4u.sms.api.domain.valueobjects;

import java.util.ArrayList;
import java.util.List;

public abstract class OperationInfo {
	private List<String> brokenRules = new ArrayList<String>(10);
	
	public boolean isValid() {
		return getBrokenRules().size() == 0;
	}
	
	public List<String> getBrokenRules() {
		return new ArrayList<String>(brokenRules);
	}
	
	public void setBrokenRules(List<String> brokenRules) {
		if (brokenRules != null) 
			this.brokenRules = brokenRules;
		else 
			brokenRules = new ArrayList<String>();
	}
	
	public void addBrokenRule(String brokenRule) {
		brokenRules.add(brokenRule);
	}
	
	public void addBrokenRule(OperationInfo operationInfo) {
		brokenRules.addAll(operationInfo.getBrokenRules());
	} 
}
