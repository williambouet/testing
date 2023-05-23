// package com.example.testing;

// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import com.example.testing.controller.FiremanController;

// @WebMvcTest(FiremanController.class)
// public class ApiTests {
    
//     @MockBean
//     FiremanRepository firemanRepository;

//     @Test
//     public void testGetVeteran_ShouldReturnStatusOk(){
//         mockMvc.perform(get("/fireman/veteran"))
// 				.andExpect(status().isOk())
// 				.andExpect(jsonPath("$.id").value(fireman.getId()))
// 				.andExpect(jsonPath("$.name").value("champion"));
//     }

// }
