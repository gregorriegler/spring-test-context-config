package com.gregorriegler.springtestcontextconfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MyApplicationTests {

	@Value("${some.property}")
	String someProperty;

	@Test
	void reads_property_from_main_application_properties() {
		assertThat(someProperty).isEqualTo("default");
	}

	@Test
	void contextLoads() {
	}
}
