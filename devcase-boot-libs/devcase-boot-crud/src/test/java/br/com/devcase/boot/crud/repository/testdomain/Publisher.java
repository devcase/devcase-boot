package br.com.devcase.boot.crud.repository.testdomain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class Publisher {
	@Id
	@GeneratedValue
	private Long id;
	@NaturalId(mutable=true)
	private String name;

	
	public Publisher() {
		super();
	}

	private Publisher(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Creates builder to build {@link Publisher}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link Publisher}.
	 */
	public static final class Builder {
		private Long id;
		private String name;

		private Builder() {
		}

		public Builder withId(Long id) {
			this.id = id;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Publisher build() {
			return new Publisher(this);
		}
	}
	
	
}
