package br.com.devcase.sample.kotlin

import br.com.devcase.boot.crud.jpa.repository.query.CriteriaRepository
import br.com.devcase.boot.crud.jpa.repository.support.CriteriaJpaRepository
import br.com.devcase.boot.jsp.undertow.EnableUndertowJsp
import br.com.devcase.boot.labels.EnableDevcaseLabels
import br.com.devcase.boot.sitemesh.EnableSiteMesh
import br.com.devcase.boot.webcrud.CrudController
import org.hibernate.annotations.Columns
import org.hibernate.annotations.Type
import org.javamoney.moneta.Money
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.RequestMapping
import java.math.BigDecimal
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import org.hibernate.envers.Audited
import java.time.Instant
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import javax.persistence.Table
import org.hibernate.validator.constraints.NotEmpty
import org.hibernate.validator.constraints.Email
import org.hibernate.annotations.NaturalId
import java.text.MessageFormat

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CriteriaJpaRepository::class)
@EntityScan
@EnableDevcaseLabels
@EnableUndertowJsp
@EnableSiteMesh
open class KotlinSampleConfig

fun main(args: Array<String>) {
	SpringApplication.run(KotlinSampleConfig::class.java, *args);
}


@Entity @Audited data class Pet(
		@Id
		@GeneratedValue
		var id: Long?,
		@NotEmpty
		@NaturalId
		var name: String,
		@NotEmpty
		@NaturalId
		var owner: String,
		@Email
		var email: String,
		var weight: BigDecimal,
		@CreationTimestamp
		var created: Instant,
		@UpdateTimestamp
		var updated: Instant
) {
	constructor() : this(null, "", "", "", BigDecimal.valueOf(0), Instant.now(), Instant.now()) //construtor sem parâmetros obrigatório
	
	override fun toString() : String {
		return MessageFormat.format("{0} ({1})", name, owner);
	}
}

@Entity @Audited @Table(name = "pet_order") data class Order(
		@Id
		@GeneratedValue
		var id: Long?,
		@ManyToOne
		var pet: Pet?,
		@Type(type = "br.com.devcase.boot.crud.hibernate.types.MoneyType")
		@Columns(columns = arrayOf(Column(name = "price_cur"), Column(name = "price_val")))
		var price: Money,
		var service: String,
		var date: LocalDate,
		@CreationTimestamp
		var created: Instant,
		@UpdateTimestamp
		var updated: Instant
) {
	constructor() : this(null, null, Money.of(0, "USD"), "", LocalDate.now(), Instant.now(), Instant.now()) //construtor sem parâmetros obrigatório
}

@Repository interface PetRepository : CriteriaRepository<Pet, Long>
@Controller @RequestMapping("/pets") class PetController(petRepository: PetRepository) : CrudController<Pet, Long>(Pet::class.java, petRepository, "pets")

@Repository interface OrderRepository : CriteriaRepository<Order, Long>
@Controller @RequestMapping("/orders") class OrderController(orderRepository: OrderRepository) : CrudController<Order, Long>(Order::class.java, orderRepository, "orders")

@Controller class HomeController() {
	@RequestMapping("/", "", "/home")
	fun index(): String {
		return "home";
	}
}