package br.com.devcase.boot.users.domain.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.NaturalId;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Credential extends BasicEntity {

	public Credential(String dtype) {
		super();
		this.dtype = dtype;
	}

	@ManyToOne(optional = false)
	@JoinColumn(updatable = false)
	@NaturalId(mutable = false)
	private User user;

	@Column(name = "dtype", updatable = false, insertable = false)
	@NaturalId(mutable = false)
	private String dtype;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDtype() {
		return dtype;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

}
