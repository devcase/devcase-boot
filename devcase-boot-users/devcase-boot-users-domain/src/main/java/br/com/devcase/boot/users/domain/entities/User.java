package br.com.devcase.boot.users.domain.entities;

import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.NaturalId;

@Entity
public class User extends BasicEntity {

	@Pattern(regexp = "^[a-z0-9]{3,}")
	@NaturalId(mutable = true)
	private String name;
	private boolean locked = false;
	private boolean enabled = true;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

}
