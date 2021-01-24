package com.gregorriegler.springtestcontextconfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("local")
class LocalProfileSpringBootTest {

	@Value("${profile.overridden.property}")
	String profileOverriddenProperty;

	@Test
	void overrides_properties_via_profile_application_properties() {
		assertThat(profileOverriddenProperty).isEqualTo("overridden");
	}
}
