package br.com.devcase.boot.sample.crud.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.envers.Audited;
import org.hibernate.validator.constraints.NotBlank;
import org.javamoney.moneta.Money;

@Entity
@Audited
public class Campanha {
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private String id;

	@NotBlank
	private String nome;

	private LocalDate inicio;

	@Type(type="br.com.devcase.boot.crud.hibernate.types.MoneyType")
	@Columns(columns= {@Column(name="orcamento_cur"), @Column(name="orcamento_val")})
	private Money orcamento;

	
	public Campanha() {
		super();
	}

	private Campanha(Builder builder) {
		this.id = builder.id;
		this.nome = builder.nome;
		this.inicio = builder.inicio;
		this.orcamento = builder.orcamento;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String name) {
		this.nome = name;
	}

	public LocalDate getInicio() {
		return inicio;
	}

	public void setInicio(LocalDate startDate) {
		this.inicio = startDate;
	}

	public Money getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Money budget) {
		this.orcamento = budget;
	}
	
	@Override
	public String toString() {
		return getNome();
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((inicio == null) ? 0 : inicio.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((orcamento == null) ? 0 : orcamento.hashCode());
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
		Campanha other = (Campanha) obj;
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
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (orcamento == null) {
			if (other.orcamento != null)
				return false;
		} else if (!orcamento.equals(other.orcamento))
			return false;
		return true;
	}

	/**
	 * Creates builder to build {@link Campanha}.
	 * @return created builder
	 */
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Campanha}.
	 */
	public static final class Builder {
		private String id;
		private String nome;
		private LocalDate inicio;
		private Money orcamento;

		private Builder() {
		}

		public Builder withId(String id) {
			this.id = id;
			return this;
		}

		public Builder withNome(String nome) {
			this.nome = nome;
			return this;
		}

		public Builder withInicio(LocalDate inicio) {
			this.inicio = inicio;
			return this;
		}

		public Builder withOrcamento(Money orcamento) {
			this.orcamento = orcamento;
			return this;
		}

		public Campanha build() {
			return new Campanha(this);
		}
	}

}
