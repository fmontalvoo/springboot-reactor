package com.fmontalvoo.springboot.reactor.app;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringbootReactorApplication implements CommandLineRunner {

	public static final Logger logger = LoggerFactory.getLogger(SpringbootReactorApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringbootReactorApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Flux<Integer> datos = Flux.just(0, 1, 2, 3, 4, 5, 6, 7).doOnNext(dato -> {
			if (dato < 0)
				throw new RuntimeException("No pueden existir valores negativos");
//			logger.info(dato.toString());
		}).filter(dato -> !(dato % 2 == 0)).map(dato -> dato * 2);

		datos.subscribe(d -> logger.warn("Dato: ".concat(d.toString())), e -> logger.error(e.getMessage()),
				() -> logger.info("Completado"));

		List<String> names = Arrays.asList("Fulano", "Alan", "Jose", "Pedro");
		Flux<String> nombres = Flux.fromIterable(names).map(nombre -> "Nombre: ".concat(nombre.toUpperCase()));

		nombres.subscribe(logger::info);
	}

}
