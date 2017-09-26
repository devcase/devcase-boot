package br.com.devcase.boot.users.domain.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(PasswordCredential.DTYPE)
public class PasswordCredential extends Credential {
	public static final String DTYPE = "password";

	private String password;

	public PasswordCredential() {
		super(DTYPE);
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
