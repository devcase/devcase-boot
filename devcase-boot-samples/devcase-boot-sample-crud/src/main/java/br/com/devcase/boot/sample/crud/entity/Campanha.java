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

}
