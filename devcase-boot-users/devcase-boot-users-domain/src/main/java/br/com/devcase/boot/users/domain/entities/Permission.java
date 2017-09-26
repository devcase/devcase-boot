package br.com.devcase.boot.users.domain.entities;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Pattern;

@MappedSuperclass
public abstract class Permission extends BasicEntity {

	@Pattern(regexp = "^[A-Z0-9]{3,}")
	protected String role;

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}
