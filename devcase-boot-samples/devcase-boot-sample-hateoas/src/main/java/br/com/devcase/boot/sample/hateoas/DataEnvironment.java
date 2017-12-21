package br.com.devcase.boot.sample.hateoas;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.com.devcase.boot.sample.crud.entity.Anuncio;
import br.com.devcase.boot.sample.crud.entity.Campanha;
import br.com.devcase.boot.sample.crud.repository.AnuncioRepository;
import br.com.devcase.boot.sample.crud.repository.CampanhaRepository;

@Component
public class DataEnvironment {
	@Autowired
	private AnuncioRepository anuncioRepository;
	@Autowired
	private CampanhaRepository campanhaRepository;
	
	@EventListener(ApplicationReadyEvent.class)
	public void loadDemoData() {
		Random rdm = new Random();
		
		final LocalDate today = LocalDate.now();
		final String[] currencies = new String[] {"BRL", "USD", "EUR", "JPY", "CNY", "GBP", "CAD"};
		for(int i = 0; i < 50; i++) {
			Campanha c = Campanha.builder()
				.withInicio(today.plusDays(rdm.nextInt(200)))
				.withOrcamento(Money.of(BigDecimal.valueOf(rdm.nextInt(100000000),2), currencies[rdm.nextInt(currencies.length)]))
				.withNome("Exemplo de campanha "  + (i + 1))
				.build();
			c = campanhaRepository.save(c);
			Anuncio a = Anuncio.builder().withCampanha(c).withTitulo("Nnonono non ono nono").build();
			anuncioRepository.save(a);
			
		}	
	}
}
