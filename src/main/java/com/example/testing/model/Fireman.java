package com.example.testing.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Fireman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "fireman")
    private List<Fire> fires;
   
    public Fireman(String name) {
        this.name = name;
    }

    public Fireman(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Fire> getFires() {
        return fires;
    }

    public void setFires(List<Fire> fires) {
        this.fires = fires;
        for (Fire fire : fires) {
            fire.setFireman(this);
          }
    }

}
