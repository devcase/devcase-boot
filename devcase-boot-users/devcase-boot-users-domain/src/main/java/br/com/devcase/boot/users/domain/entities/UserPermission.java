package br.com.devcase.boot.users.domain.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserPermission extends Permission {

	@ManyToOne(optional=false)
	@JoinColumn(updatable=false)
	private User user;

	public UserPermission() {
		super();
	}

	private UserPermission(Builder builder) {
		this.role = builder.role;
		this.user = builder.user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * Creates builder to build {@link UserPermission}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link UserPermission}.
	 */
	public static final class Builder {
		private String role;
		private User user;

		private Builder() {
		}

		public Builder withRole(String role) {
			this.role = role;
			return this;
		}

		public Builder withUser(User user) {
			this.user = user;
			return this;
		}

		public UserPermission build() {
			return new UserPermission(this);
		}
	}
	
	
	
}
