package com.gregorriegler.springtestcontextconfig;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Should be similar to
 * @ContextConfiguration(classes = SpringBootTestWithoutComponentScan.TestConfig.class, initializers = ConfigFileApplicationContextInitializer.class)
 */
@ActiveProfiles("test")
@BootstrapWith(SpringBootTestContextBootstrapper.class)
@ExtendWith(SpringExtension.class)
@Transactional
class SpringBootTestWithoutAnnotations {

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

    @Autowired
    EntityManager em;

    @Test
    void doesnt_inject_scannedComponent() {
        assertThatThrownBy(() -> context.getBean(ScannedComponent.class))
            .isInstanceOf(NoSuchBeanDefinitionException.class);
    }

    @Test
    @Transactional
    void db_access() {
        em.persist(new Book("123", "title"));
        em.flush();
        em.clear();

        Book book = em.find(Book.class, "123");

        assertThat(book.title).isEqualTo("title");
    }

    @Configuration
    @EnableAutoConfiguration
    // @ActiveProfiles("test") <-- This would not have any impact (too late)
    public static class TestConfig {

    }
}
