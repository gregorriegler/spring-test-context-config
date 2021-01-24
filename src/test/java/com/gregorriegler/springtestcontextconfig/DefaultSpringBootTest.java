package com.gregorriegler.springtestcontextconfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class DefaultSpringBootTest {

//	 Fails trying to load this property as it is only contained in main/resources/application.properties
//	 but test/resources/application.properties completely replaces the file as there can only be one
//	 application.properties file.
//
//	@Value("${some.property}")
//	String someProperty;
//
//	@Test
//	void reads_property_from_main_application_properties() {
//		assertThat(someProperty).isEqualTo("default");
//	}

	@Value("${test.overridden.property}")
	String testOverriddenProperty;

	@Test
	void overrides_properties_via_test_resources_application_properties() {
		assertThat(testOverriddenProperty).isEqualTo("overridden by test/resources/application.properties");
	}

	@Value("${test.non.overridden.property}")
	String nonOverriddenProperty;

	/**
	 * application-test.properties is not automatically loaded
	 */
	@Test
	void doesnt_implicitly_load_test_application_properties() {
		assertThat(nonOverriddenProperty).isEqualTo("default");
	}

	@Autowired
	ScannedComponent component;

	@Test
	void injects_scannedComponent() {
		assertThat(component).isNotNull();
	}
}
