package com.sfu_hikers_hub.sfu_hikers_hub.Controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.sfu_hikers_hub.sfu_hikers_hub.controllers.GalleryController;

@WebMvcTest(GalleryController.class)
public class GalleryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGallery() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/gallery/view"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.view().name("gallery/gallery"));
    }
}
