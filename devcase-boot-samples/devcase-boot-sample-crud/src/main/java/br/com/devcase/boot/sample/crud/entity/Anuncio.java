package br.com.devcase.boot.sample.crud.entity;

import java.time.ZonedDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;
import org.springframework.hateoas.Identifiable;

@Entity
@Audited
public class Anuncio implements Identifiable<String> {
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
	

	@Override
	public String toString() {
		return getTitulo();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((campanha == null) ? 0 : campanha.hashCode());
		result = prime * result + ((fim == null) ? 0 : fim.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inicio == null) ? 0 : inicio.hashCode());
		result = prime * result + ((midia == null) ? 0 : midia.hashCode());
		result = prime * result + ((titulo == null) ? 0 : titulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Anuncio other = (Anuncio) obj;
		if (campanha == null) {
			if (other.campanha != null)
				return false;
		} else if (!campanha.equals(other.campanha))
			return false;
		if (fim == null) {
			if (other.fim != null)
				return false;
		} else if (!fim.equals(other.fim))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (inicio == null) {
			if (other.inicio != null)
				return false;
		} else if (!inicio.equals(other.inicio))
			return false;
		if (midia == null) {
			if (other.midia != null)
				return false;
		} else if (!midia.equals(other.midia))
			return false;
		if (titulo == null) {
			if (other.titulo != null)
				return false;
		} else if (!titulo.equals(other.titulo))
			return false;
		return true;
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
