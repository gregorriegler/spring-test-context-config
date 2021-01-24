package com.gregorriegler.springtestcontextconfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("local")
class MyApplicationTests {

	@Value("${some.property}")
	String someProperty;

	@Test
	void reads_property_from_main_application_properties() {
		assertThat(someProperty).isEqualTo("default");
	}

	@Value("${profile.overridden.property}")
	String profileOverriddenProperty;

	@Test
	void overrides_properties_via_profile_application_properties() {
		assertThat(profileOverriddenProperty).isEqualTo("overridden");
	}

	@Test
	void contextLoads() {
	}
}
