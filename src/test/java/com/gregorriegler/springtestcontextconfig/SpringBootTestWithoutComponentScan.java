package com.gregorriegler.springtestcontextconfig;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest(classes = SpringBootTestWithoutComponentScan.TestConfig.class)
class SpringBootTestWithoutComponentScan {

    @Value("${test.overridden.property}")
    String testOverriddenProperty;

    @Test
    void overrides_properties_via_test_resources_application_properties() {
        assertThat(testOverriddenProperty).isEqualTo("overridden by test/resources/application.properties");
    }

    @Value("${test.non.overridden.property}")
    String testOverriddenApplicationTestProperty;

    @Test
    void overrides_properties_via_test_profile_application_properties() {
        assertThat(testOverriddenApplicationTestProperty).isEqualTo("overridden");
    }

    @Autowired
    ApplicationContext context;

    @Test
    void doesnt_inject_scannedComponent() {
        assertThatThrownBy(() -> context.getBean(ScannedComponent.class))
            .isInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @Configuration
    @EnableAutoConfiguration
    // @ActiveProfiles("test") <-- This would not have any impact (too late)
    public static class TestConfig {

    }
}
