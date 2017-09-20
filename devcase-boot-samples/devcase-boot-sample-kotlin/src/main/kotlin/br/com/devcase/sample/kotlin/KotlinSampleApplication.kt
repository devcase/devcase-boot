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

@SpringBootApplication
@EnableDevcaseLabels
@EnableUndertowJsp
@EnableJpaRepositories(repositoryBaseClass = CriteriaJpaRepository::class)
@EnableSiteMesh
@EntityScan
open class KotlinSampleConfig

@Entity data class Pet(
		@Id
		@GeneratedValue
		var id: Long,
		var name: String,
		var weight: BigDecimal
)

@Entity data class Order(
		@Id
		@GeneratedValue
		var id: Long,
		@ManyToOne
		var pet: Pet,
		@Type(type = "br.com.devcase.boot.crud.hibernate.types.MoneyType")
		@Columns(columns = arrayOf(Column(name = "price_cur"), Column(name = "price_val")))
		var price: Money,
		var service : String,
		var date : LocalDate
)

@Repository interface PetRepository : CriteriaRepository<Pet, Long>

@Repository interface OrderRepository : CriteriaRepository<Order, Long>

@Controller @RequestMapping("/pet") class PetController (petRepository : PetRepository) : CrudController<Pet, Long> (Pet::class.java, petRepository, "pet")

@Controller class IndexController () {
	@RequestMapping("/")
	fun index() : String {
		return "redirect:/pet";
	}
}

fun main(args: Array<String>) {
	SpringApplication.run(KotlinSampleConfig::class.java, *args);
}