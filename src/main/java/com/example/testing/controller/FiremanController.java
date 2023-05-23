package com.example.testing.controller;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.testing.dto.FiremanStatsDTO;
import com.example.testing.model.Fire;
import com.example.testing.model.Fireman;
import com.example.testing.repository.FireRepository;
import com.example.testing.repository.FiremanRepository;

@RestController
@RequestMapping("/fireman")
public class FiremanController {

    @Autowired
    FiremanRepository firemanRepository;

    @Autowired
    FireRepository fireRepository;

    record FiremanData(Long id, String name, int firesCount) {
        static FiremanData fromFireman(Fireman fireman) {
            return new FiremanData(fireman.getId(), fireman.getName(), fireman.getFires().size());
        }
    }

    @GetMapping("/veteran")
    public FiremanData getVeteran() {
        Optional<Fireman> veteranMaybe = firemanRepository.getVeteran();
        Fireman veteran = veteranMaybe.orElseThrow(() -> new NotFoundException());
        return FiremanData.fromFireman(veteran);
    }

    @GetMapping("/stats")
    public FiremanStatsDTO getStats() {
        List<Fireman> firemen = firemanRepository.findAll();
        List<Fire> fires = fireRepository.findAll();
        
        FiremanStatsDTO stats = new FiremanStatsDTO();
        stats.setFiremenCount(firemen.size());
        stats.setFiresCount(fires.size());

        return stats;
    }


    @ResponseStatus(HttpStatus.NOT_FOUND)
    public static class NotFoundException extends RuntimeException {
    }
}

