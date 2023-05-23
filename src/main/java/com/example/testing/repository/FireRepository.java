package com.example.testing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.testing.model.Fire;

public interface FireRepository extends JpaRepository<Fire, Long> {
    @Query("SELECT f FROM Fire f WHERE f.date = (SELECT min(f2.date) FROM Fire f2)")
    Optional<Fire> getOldestFire();
}
