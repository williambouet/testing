package com.example.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.time.Instant;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.testing.model.Fire;
import com.example.testing.repository.FireRepository;
import io.cucumber.java.en.*;

public class CaserneFeatureTests {

    private Fire oldestFire = new Fire(0, null, "");

    @Autowired
    FireRepository fireRepository;

    @Given("the following fires:")
    public void givenProducts(List<List<String>> firesData) {
        for (List<String> fireData : firesData) {
            Fire fire = new Fire(0, Instant.parse(fireData.get(1) + "-01-01T00:00:00.000Z"), fireData.get(0));
            fireRepository.saveAndFlush(fire);
        }
    }

    @When("I look for oldest fire")
    public void selectOldestFire() {
       oldestFire.setName(fireRepository.getOldestFire().get().getName());
    }

    @Then("{int} fire(s) returned and name is {string}")
    public void assertFires(int count, String name) {
        assertEquals(name,oldestFire.getName());
    }
}