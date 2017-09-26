package br.com.devcase.boot.users.domain.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserPermission extends Permission {

	@ManyToOne(optional=false)
	@JoinColumn(updatable=false)
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
