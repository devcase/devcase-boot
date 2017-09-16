package br.com.devcase.boot.sample.webcrud;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

import org.javamoney.moneta.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import br.com.devcase.boot.sample.crud.entity.Campanha;
import br.com.devcase.boot.sample.crud.repository.CampanhaRepository;

@Component
public class CreateSampleData implements ApplicationListener<ApplicationReadyEvent> {
	@Autowired
	private CampanhaRepository campanhaRepository;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		Random rdm = new Random();
		
		final LocalDate today = LocalDate.now();
		final String[] currencies = new String[] {"BRL", "USD", "EUR", "JPY", "CNY", "GBP", "CAD"};
		for(int i = 0; i < 100; i++) {
			Campanha c = Campanha.builder()
				.withInicio(today.plusDays(rdm.nextInt(200)))
				.withOrcamento(Money.of(BigDecimal.valueOf(rdm.nextInt(100000000),2), currencies[rdm.nextInt(currencies.length)]))
				.withNome("Exemplo de campanha "  + (i + 1))
				.build();
			campanhaRepository.save(c);
		}
	}

}
