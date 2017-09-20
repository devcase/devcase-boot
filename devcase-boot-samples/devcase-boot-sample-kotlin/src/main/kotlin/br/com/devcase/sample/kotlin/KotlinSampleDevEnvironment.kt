package br.com.devcase.sample.kotlin

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.javamoney.moneta.Money

@Component
@Profile("dev")
class DevTestData : ApplicationListener<ApplicationReadyEvent> {
	
	@Autowired
	lateinit var serviceRepository : ServiceRepository;
	@Autowired
	lateinit var petRepository : PetRepository;
	
	override fun onApplicationEvent(event : ApplicationReadyEvent) {
		
		val services = listOf(
				Service(null, Money.of(13, "BRL"), "Banho")
				, Service(null, Money.of(40, "BRL"), "Tosa")
				, Service(null, Money.of(40, "BRL"), "SPA")
				, Service(null, Money.of(123, "BRL"), "Consulta médica")
				, Service(null, Money.of(123, "BRL"), "Retorno médico")
		);
		
		services.forEach { serviceRepository.save(it) };
	}
}