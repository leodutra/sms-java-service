package br.com.m4u.sms.domain.model;

public abstract class Entity<TIdentifier> {

	TIdentifier id;

	public TIdentifier getId() {
		return id;
	}

	public void setId(TIdentifier id) {
		this.id = id;
	}
}
