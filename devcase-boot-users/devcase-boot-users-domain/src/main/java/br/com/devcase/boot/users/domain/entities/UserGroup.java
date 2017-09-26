package br.com.devcase.boot.users.domain.entities;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;

@Entity
public class UserGroup extends BasicEntity {

	@NotNull
	@NaturalId(mutable=true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
