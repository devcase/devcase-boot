package br.com.devcase.boot.users.domain.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class GroupPermission extends Permission {

	@ManyToOne
	private UserGroup group;

	private GroupPermission(Builder builder) {
		this.validUntil = builder.validUntil;
		this.role = builder.role;
		this.group = builder.group;
	}

	public GroupPermission() {
		super();
	}

	public UserGroup getGroup() {
		return group;
	}

	public void setGroup(UserGroup group) {
		this.group = group;
	}

	/**
	 * Creates builder to build {@link GroupPermission}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link GroupPermission}.
	 */
	public static final class Builder {
		private Date validUntil;
		private String role;
		private UserGroup group;

		private Builder() {
		}

		public Builder withValidUntil(Date validUntil) {
			this.validUntil = validUntil;
			return this;
		}

		public Builder withRole(String role) {
			this.role = role;
			return this;
		}

		public Builder withGroup(UserGroup group) {
			this.group = group;
			return this;
		}

		public GroupPermission build() {
			return new GroupPermission(this);
		}
	}
	
}
