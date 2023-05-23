package com.example.testing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.testing.model.Fire;

public interface FireRepository extends JpaRepository<Fire, Long> {
    
}
