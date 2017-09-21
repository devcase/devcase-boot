package br.com.devcase.sample.kotlin

import br.com.devcase.boot.crud.repository.criteria.CriteriaRepository
import br.com.devcase.boot.crud.jpa.repository.support.CriteriaJpaRepository
import br.com.devcase.boot.crud.repository.FindByNameExecutor
import br.com.devcase.boot.jsp.undertow.EnableUndertowJsp
import br.com.devcase.boot.labels.EnableDevcaseLabels
import br.com.devcase.boot.sitemesh.EnableSiteMesh
import br.com.devcase.boot.webcrud.CrudController
import br.com.devcase.boot.webcrud.webbinding.NamedEntityPropertyEditor
import org.hibernate.annotations.Columns
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.NaturalId
import org.hibernate.annotations.Type
import org.hibernate.annotations.UpdateTimestamp
import org.hibernate.envers.Audited
import org.javamoney.moneta.Money
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Repository
import org.springframework.web.bind.WebDataBinder
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.InitBinder
import org.springframework.web.bind.annotation.RequestMapping
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDate
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.NotNull
import java.util.Date
import br.com.devcase.boot.crud.validation.constraints.PropertyNotNull
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Email

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
		@field:Id
		@field:GeneratedValue
		var id: Long?,
		@field:NotEmpty
		@field:NaturalId
		var name: String,
		@field:NotEmpty
		var owner: String,
		@field:Email
		var email: String,
		var weight: BigDecimal,
		var dayOfBirth: LocalDate,
		@field:CreationTimestamp
		var created: Date = Date(0),
		@field:UpdateTimestamp
		var updated: Date = Date(0)
) {
	constructor() : this(null, "", "", "", BigDecimal.valueOf(0), LocalDate.MIN) //construtor sem parâmetros obrigatório
//
//	override fun toString(): String {
//		return MessageFormat.format("{0} ({1})", name, owner);
//	}
}

@Entity @Audited data class Service(
		@field:Id
		@field:GeneratedValue
		var id: Long?,
		@field:Type(type = "br.com.devcase.boot.crud.hibernate.types.MoneyType")
		@field:Columns(columns = arrayOf(Column(name = "price_cur"), Column(name = "price_val")))
		var price: Money,
		@field:NaturalId
		var name: String,
		@field:CreationTimestamp
		var created: Date = Date(0),
		@field:UpdateTimestamp
		var updated: Date = Date(0)
) {
	constructor() : this(null, Money.of(0, "USD"), "")
}

@Entity @Audited @Table(name = "pet_order") data class Order(
		@field:Id
		@field:GeneratedValue
		var id: Long?,
		@field:ManyToOne(optional = false)
		@field:NotNull
		@field:PropertyNotNull(value="id", message="{petNotFound.message}")
		var pet: Pet?,
		@field:Type(type = "br.com.devcase.boot.crud.hibernate.types.MoneyType")
		@field:Columns(columns = arrayOf(Column(name = "price_cur"), Column(name = "price_val")))
		var price: Money,
		var date: LocalDate,
		@field:OneToMany
		var items: List<OrderItem>,
		@field:CreationTimestamp
		var created: Date = Date(0),
		@field:UpdateTimestamp
		var updated: Date = Date(0)
) {
	constructor() : this(null, null, Money.of(0, "USD"), LocalDate.now(), emptyList()) //construtor sem parâmetros obrigatório
}

@Entity @Audited @Table(name = "pet_order_item") data class OrderItem(
		@field:Id
		@field:GeneratedValue
		var id: Long?,
		@field:ManyToOne
		var order: Order?,
		var quantity : Int,
		@field:Type(type = "br.com.devcase.boot.crud.hibernate.types.MoneyType")
		@field:Columns(columns = arrayOf(Column(name = "price_cur"), Column(name = "price_val")))
		var price: Money,
		@field:ManyToOne
		var service: Service?,
		@field:CreationTimestamp
		var created: Date = Date(0),
		@field:UpdateTimestamp
		var updated: Date = Date(0)
) {
	constructor() : this(null, null, 1, Money.of(0, "USD"), null) //construtor sem parâmetros obrigatório
}


@Repository interface PetRepository : CriteriaRepository<Pet, Long>, FindByNameExecutor<Pet>
@Controller @RequestMapping("/pets") class PetCrudController(petRepository: PetRepository) : CrudController<Pet, Long>(Pet::class.java, petRepository, "pets")

@Repository interface OrderRepository : CriteriaRepository<Order, Long>
@Controller @RequestMapping("/orders") class OrderCrudController(orderRepository: OrderRepository) : CrudController<Order, Long>(Order::class.java, orderRepository, "orders")

@Repository interface ServiceRepository : CriteriaRepository<Service, Long>, FindByNameExecutor<Service>
@Controller @RequestMapping("/services") class ServiceCrudController(serviceRepository: ServiceRepository) : CrudController<Service, Long>(Service::class.java, serviceRepository, "services")

@Controller class HomeController() {
	@RequestMapping("/", "", "/home")
	fun index(): String {
		return "home";
	}
}

@ControllerAdvice class KotlinSampleControllerAdvice {
	@Autowired
	lateinit var serviceRepository : ServiceRepository;
	@Autowired
	lateinit var petRepository : PetRepository;
	@InitBinder
	fun initBinder(binder : WebDataBinder) {
		binder.registerCustomEditor(Service::class.java, NamedEntityPropertyEditor(serviceRepository, Service::class.java));
		binder.registerCustomEditor(Pet::class.java, NamedEntityPropertyEditor(petRepository, Pet::class.java));
	}
}