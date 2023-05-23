package com.example.testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.testing.model.Fire;
import com.example.testing.model.Fireman;
import com.example.testing.repository.FireRepository;
import com.example.testing.repository.FiremanRepository;

import io.cucumber.java.en.*;

public class VeteranFeatureTests {

    private Fireman veteran = new Fireman("");

    @Autowired
    FiremanRepository firemanRepository;

    @Autowired
    FireRepository fireRepository;

    @Given("the following firemen:")
    public void givenProducts(List<List<String>> firemenData) {
        for (List<String> firemanData : firemenData) {
            Fireman fireman = new Fireman(firemanData.get(0));
            List<Fire> fires = new ArrayList<Fire>();

            if (firemanData.get(1) != null) {
                Fire fire = new Fire(Integer.parseInt(firemanData.get(1)), Instant.now(), firemanData.get(2));
                fireRepository.saveAndFlush(fire);
                firemanRepository.saveAndFlush(fireman);
                fires.add(fire);
            }

            fireman.setFires(fires);
        }
    }

    @When("I look for oldest fireman")
    public void selectOldestFireman() {
        veteran.setName(firemanRepository.getVeteran().get().getName());
    }

    @Then("{int} veteran returned and name is {string}")
    public void assertFireman(int count, String name) {
        assertEquals(name, veteran.getName());
    }
}