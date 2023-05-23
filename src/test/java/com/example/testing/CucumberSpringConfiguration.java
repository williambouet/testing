package com.example.testing;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@DataJpaTest
public class CucumberSpringConfiguration {
}
