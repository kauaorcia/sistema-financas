package com.kaua.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DemoApplicationTests {

	@Test
	void deveSerConfiguradaComoAplicacaoSpringBoot() {
		assertTrue(DemoApplication.class.isAnnotationPresent(SpringBootApplication.class));
	}

}
