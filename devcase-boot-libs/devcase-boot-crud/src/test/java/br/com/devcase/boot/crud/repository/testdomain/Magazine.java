package br.com.devcase.boot.crud.repository.testdomain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NaturalId;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class Magazine {
	@Id
	@GeneratedValue
	private Long id;
	@NaturalId
	private String name;
	@ManyToOne
	private Publisher publisher;


	private Magazine(Builder builder) {
		this.id = builder.id;
		this.name = builder.name;
		this.publisher = builder.publisher;
	}
	
	
	public Magazine() {
		super();
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
	public Publisher getPublisher() {
		return publisher;
	}
	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}
	/**
	 * Creates builder to build {@link Magazine}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link Magazine}.
	 */
	public static final class Builder {
		private Long id;
		private String name;
		private Publisher publisher;

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

		public Builder withPublisher(Publisher publisher) {
			this.publisher = publisher;
			return this;
		}

		public Magazine build() {
			return new Magazine(this);
		}
	}
	
}
