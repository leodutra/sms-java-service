package br.com.m4u.sms.api.domain.entities;

public abstract class Entity<TIdentifier> {
	
    private TIdentifier id;
    
    public void setId(TIdentifier id) {
      this.id = id;
    }

    public TIdentifier getId() {
      return id;
    }
}
