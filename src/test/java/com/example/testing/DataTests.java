package com.example.testing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.testing.model.Fire;
import com.example.testing.model.Fireman;
import com.example.testing.repository.FireRepository;
import com.example.testing.repository.FiremanRepository;
import jakarta.validation.ConstraintViolationException;

@DataJpaTest
public class DataTests {

    @Autowired
    FireRepository fireRepository;

    @Autowired
    FiremanRepository firemanRepository;

    @Test
    public void testCreateFire_ShouldReturnTheSameResult() {

        // Arrange
        int severity = 8;
        Instant date = Instant.now();
        Fire fire = new Fire(severity, date);

        // Act
        fireRepository.saveAndFlush(fire);
        Optional<Fire> fromDB = fireRepository.findById(fire.getId());

        // Assert
        assertTrue(fromDB.isPresent());
        assertEquals(fire.getId(), fromDB.get().getId());
        assertEquals(date, fromDB.get().getDate());
        assertEquals(severity, fromDB.get().getSeverity());
    }

    @Test
    public void testCreateFiremanWithListOfFire_shouldReturnTheSameResult() {

        // Arrange
        String name = "Toto";
        Fireman fireman = new Fireman(name);

        Instant date = Instant.now();
        Fire fireBig = new Fire(10, date);
        fireRepository.saveAndFlush(fireBig);

        Fire fireLittle = new Fire(1, date);
        fireRepository.saveAndFlush(fireLittle);

        List<Fire> fires = new ArrayList<Fire>();
        fires.add(fireBig);
        fires.add(fireLittle);

        fireman.setFires(fires);

        // Act
        firemanRepository.saveAndFlush(fireman);
        Optional<Fireman> fromDB = firemanRepository.findById(fireman.getId());

        // Assert
        assertTrue(fromDB.isPresent());
        assertEquals(fireman.getId(), fromDB.get().getId());
        assertEquals(name, fromDB.get().getName());
        assertEquals(fires, fromDB.get().getFires());
    }

    @Test
    public void testCreateNegativeFire_ShouldReturnConstraintMessage() {

        // Assert
        assertThrows(ConstraintViolationException.class, () -> {

            // Arrange
            int severity = -1;
            Instant date = Instant.now();
            Fire fire = new Fire(severity, date);

            // Act
            fireRepository.saveAndFlush(fire);

        }, "");
    }

    @Test
    public void testCreateNullFireman_ShouldReturnEmpty() {

        // Act
        Optional<Fireman> fromDB = firemanRepository.getVeteran();

        // Assert
        assertTrue(fromDB.isEmpty());
    }

    @Test
    public void testCreate_1_Fireman_ShouldReturnTheVeteran() {

        // Arrange
        String name = "Toto";
        Fireman fireman = new Fireman(name);
        firemanRepository.saveAndFlush(fireman);

        // Act
        Optional<Fireman> fromDB = firemanRepository.getVeteran();
        
        // Assert
        assertEquals(fromDB.get(), fireman);
    }

    @Test
    public void testCreate_2_Firemans_ShouldReturnTheVeteran() {
        
        // Arrange
        Instant date = Instant.now();
        Fire fireBig = new Fire(10, date);
        fireRepository.saveAndFlush(fireBig);
        Fire fireBaby = new Fire(1, date);
        fireRepository.saveAndFlush(fireBaby);
        Fire fireMedium = new Fire(5, date);
        fireRepository.saveAndFlush(fireMedium);

        String veteranName = "Toto";
        Fireman veteran = new Fireman(veteranName);
        List<Fire> veteranFires = new ArrayList<Fire>();
        veteranFires.add(fireBig);
        veteranFires.add(fireMedium);
        veteran.setFires(veteranFires);
        firemanRepository.saveAndFlush(veteran);

        String juniorName = "Titi";
        Fireman junior = new Fireman(juniorName);
        List<Fire> juniorFires = new ArrayList<Fire>();
        juniorFires.add(fireBaby);
        junior.setFires(juniorFires);
        firemanRepository.saveAndFlush(junior);

        // Act
        Optional<Fireman> fromDB = firemanRepository.getVeteran();
        
        // Assert
        assertEquals(fromDB.get(), veteran);
    }



}
