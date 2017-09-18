package br.com.devcase.boot.sample.crud.entity;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

@Entity
@Audited
public class Anuncio {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;
	
	@ManyToOne
	private Campanha campanha;
	private String titulo;
	private String midia;
	private ZonedDateTime inicio;
	private ZonedDateTime fim;

	private Anuncio(Builder builder) {
		this.id = builder.id;
		this.campanha = builder.campanha;
		this.titulo = builder.titulo;
		this.midia = builder.midia;
		this.inicio = builder.inicio;
		this.fim = builder.fim;
	}
	
	public Anuncio() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public Campanha getCampanha() {
		return campanha;
	}
	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}
	public String getMidia() {
		return midia;
	}
	public void setMidia(String midia) {
		this.midia = midia;
	}
	public ZonedDateTime getInicio() {
		return inicio;
	}
	public void setInicio(ZonedDateTime inicio) {
		this.inicio = inicio;
	}
	public ZonedDateTime getFim() {
		return fim;
	}
	public void setFim(ZonedDateTime fim) {
		this.fim = fim;
	}
	/**
	 * Creates builder to build {@link Anuncio}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}
	/**
	 * Builder to build {@link Anuncio}.
	 */
	public static final class Builder {
		private String id;
		private Campanha campanha;
		private String titulo;
		private String midia;
		private ZonedDateTime inicio;
		private ZonedDateTime fim;

		private Builder() {
		}

		public Builder withId(String id) {
			this.id = id;
			return this;
		}

		public Builder withCampanha(Campanha campanha) {
			this.campanha = campanha;
			return this;
		}

		public Builder withTitulo(String titulo) {
			this.titulo = titulo;
			return this;
		}

		public Builder withMidia(String midia) {
			this.midia = midia;
			return this;
		}

		public Builder withInicio(ZonedDateTime inicio) {
			this.inicio = inicio;
			return this;
		}

		public Builder withFim(ZonedDateTime fim) {
			this.fim = fim;
			return this;
		}

		public Anuncio build() {
			return new Anuncio(this);
		}
	}
	
	
}
