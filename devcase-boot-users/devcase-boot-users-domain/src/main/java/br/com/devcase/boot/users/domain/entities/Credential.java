package br.com.devcase.boot.users.domain.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
public abstract class Credential {

	public Credential(String dtype) {
		super();
		this.dtype = dtype;
	}

	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	@ManyToOne(optional = false)
	@JoinColumn(updatable = false)
	@NaturalId(mutable = false)
	private User user;

	@Column(name = "dtype", updatable = false, insertable = false)
	@NaturalId(mutable = false)
	private String dtype;

	@CreationTimestamp
	@Column(updatable = false)
	@JsonIgnore
	private Date createDate;
	@UpdateTimestamp
	@JsonIgnore
	private Date updateDate;
	private Date validUntil;

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

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Transient
	public boolean isExpired() {
		return this.validUntil != null && validUntil.before(new Date());
	}

}
