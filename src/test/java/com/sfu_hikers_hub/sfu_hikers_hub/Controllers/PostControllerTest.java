package com.sfu_hikers_hub.sfu_hikers_hub.Controllers;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import static org.hamcrest.Matchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.sfu_hikers_hub.sfu_hikers_hub.controllers.PostController;
import com.sfu_hikers_hub.sfu_hikers_hub.models.Post;
import com.sfu_hikers_hub.sfu_hikers_hub.models.PostRepository;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @MockBean
    private PostRepository PostRepository;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetAllPosts() throws Exception {

        Post p1 = new Post();
        p1.setPid(1);
        p1.setOp("wow");
        p1.setTitle("Big Post");
        p1.setBody("Wowsers Post!");
        p1.setCreatedAt(LocalDateTime.of(2024, 03, 24, 7, 23, 32));

        Post p2 = new Post();
        p2.setPid(2);
        p2.setOp("hehy!");
        p2.setTitle("smaller post");
        p2.setBody("Smaller wow post");
        p2.setCreatedAt(LocalDateTime.of(2024, 03, 25, 4, 32, 12));

        List<Post> posts = new ArrayList<Post>();
        posts.add(p1);
        posts.add(p2);

        when(PostRepository.findAll()).thenReturn(posts);

        mockMvc.perform(MockMvcRequestBuilders.get("/posts/view"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.model().attribute("posts", hasItem(
                allOf(
                    hasProperty("pid",       Matchers.is(1)),
                    hasProperty("op",        Matchers.is("wow")),
                    hasProperty("title",     Matchers.is("Big Post")),
                    hasProperty("body",      Matchers.is("Wowsers Post!")),
                    hasProperty("createdAt", Matchers.is(LocalDateTime.of(2024, 03, 24, 7, 23, 32)))
                )
            )))
            .andExpect(MockMvcResultMatchers.model().attribute("posts", hasItem(
                        allOf(
                            hasProperty("pid",       Matchers.is(2)),
                            hasProperty("op",        Matchers.is("hehy!")),
                            hasProperty("title",     Matchers.is("smaller post")),
                            hasProperty("body",      Matchers.is("Smaller wow post")),
                            hasProperty("createdAt", Matchers.is(LocalDateTime.of(2024, 03, 25, 4, 32, 12)))
                        )
                )));
    }
}