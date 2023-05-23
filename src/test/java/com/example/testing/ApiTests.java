package com.example.testing;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import com.example.testing.controller.FiremanController;
import com.example.testing.model.Fire;
import com.example.testing.model.Fireman;
import com.example.testing.repository.FireRepository;
import com.example.testing.repository.FiremanRepository;

@WebMvcTest(FiremanController.class)
public class ApiTests {
    
    @Autowired
	MockMvc mockMvc;

    @MockBean
    FiremanRepository firemanRepository;
    
    @MockBean
    FireRepository fireRepository;

    @Test
    public void testGetVeteran_ShouldReturnStatusOk() throws Exception {

        //Arrange and Act
        Fireman fireman = mock(Fireman.class);
        when(fireman.getId()).thenReturn(1L);
        when(fireman.getName()).thenReturn("champion");
        when(firemanRepository.getVeteran()).thenReturn(Optional.of(fireman));

        //Assert
        mockMvc.perform(get("/fireman/veteran"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(fireman.getId()))
                .andExpect(jsonPath("$.name").value("champion"));
    }

    @Test
    public void testGetVeteran_ShouldReturnNotFoundStatus() throws Exception {

        //Arrange and Act
        when(firemanRepository.getVeteran()).thenReturn(Optional.empty());

        //Assert
		mockMvc.perform(get("/fireman/veteran")).andExpect(status().isNotFound());
    }

    @Test
    public void testStats_ShouldReturnGoodStats() throws Exception {

        //Arrange and Act
        Fireman fmOne = new Fireman("Toto");
        Fireman fmTwo = new Fireman("Titi");
        Fireman fmThree = new Fireman("Tata");
        
        List<Fireman> fmList = new ArrayList<Fireman> ();
        fmList.add(fmOne);
        fmList.add(fmTwo);
        fmList.add(fmThree);

        Fire fireBaby = new Fire(1, Instant.now());
        Fire fireMedium = new Fire(5, Instant.now());
        Fire fireBig = new Fire(10, Instant.now());

        List<Fire> fList = new ArrayList<Fire> ();
        fList.add(fireBaby);
        fList.add(fireMedium);
        fList.add(fireBig);

        when(firemanRepository.findAll()).thenReturn(fmList);
        when(fireRepository.findAll()).thenReturn(fList);

        //Assert
        mockMvc.perform(get("/fireman/stats"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firemenCount").value(fmList.size()))
                .andExpect(jsonPath("$.firesCount").value(fList.size()));
    }


}
