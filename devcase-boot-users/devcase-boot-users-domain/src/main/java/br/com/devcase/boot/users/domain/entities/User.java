package br.com.devcase.boot.users.domain.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.NaturalId;

@Entity
public class User extends BasicEntity {

	@Pattern(regexp = "^[?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+@]{3,}")
	@NaturalId(mutable = true)
	private String username;
	@ManyToMany
	private List<UserGroup> groups;
	private boolean locked = false;
	private boolean enabled = true;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public List<UserGroup> getGroups() {
		return groups;
	}

	public void setGroups(List<UserGroup> groups) {
		this.groups = groups;
	}

}
