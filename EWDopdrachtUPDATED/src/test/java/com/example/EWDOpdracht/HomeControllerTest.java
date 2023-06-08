package com.example.EWDOpdracht;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlTemplate;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import repository.BookRepository;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, properties = "server.port=8080")

@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class HomeControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private BookRepository repository;
	
	@InjectMocks
	private HomeController homeController;
	
	@BeforeEach
	public void setup() {
	    MockitoAnnotations.openMocks(this);
	    
	}
	@WithMockUser(username = "user", roles = {"USER"})
	@Test
	public void homeGet() throws Exception{
		mockMvc.perform(get("/home"))
        .andExpect(status().isOk())
    	.andExpect(view().name("home"))
    	.andExpect(model().attributeExists("favBooks"))
    	.andExpect(model().attributeExists("popularBooks"));
    	
	}
	@Test
	public void homeGetWithoutLogin() throws Exception{
		mockMvc.perform(get("/"))
        .andExpect(status().isFound())
    	.andExpect(redirectedUrl("http://localhost/login"));
	}
	@WithMockUser(username = "admin", roles = {"ADMIN"})
	@Test
	public void adminGet() throws Exception{
		mockMvc.perform(get("/admin"))
        .andExpect(status().isOk())
    	.andExpect(view().name("admin"));
    	
    	
	}
	@WithMockUser(username = "admin", roles = {"USER"})
	@Test
	public void adminGetUserRole() throws Exception{
		mockMvc.perform(get("/admin"))
        .andExpect(status().isForbidden());
    	
    	
    	
	}
	@WithMockUser(username = "admin", roles = {"USER"})
	@Test
	@Transactional
	public void searchGet() throws Exception{
		mockMvc.perform(get("/search"))
        .andExpect(status().isOk())
    	.andExpect(view().name("search"));

	}
	@WithMockUser(username = "admin", roles = {"USER"})
	@Test
	@Transactional
	public void searchPost() throws Exception{
		mockMvc.perform(post("/search").with(csrf().asHeader())
				.param("keyword", "The Lord of the Rings: The classic fantasy masterpiece (English Edition)")
				.param("nameSearch", "true"))
        .andExpect(status().isOk())
    	.andExpect(view().name("search"))
    	.andExpect(model().attributeExists("list"));
    	
    	
	}
	@WithMockUser(username = "admin", roles = {"USER"})
	@Test
	@Transactional
	public void searchPostBookDoesntExist() throws Exception{
		mockMvc.perform(post("/search").with(csrf().asHeader())
				.param("keyword", "To Kill A MockingBird")
				.param("nameSearch", "true"))
        .andExpect(status().isOk())
    	.andExpect(view().name("search"))
    	.andExpect(model().attributeExists("list"));
    	
    	
	}
	@WithMockUser(username = "admin", roles = {"USER"})
	@Test
	@Transactional
	public void searchPostEmptySearch() throws Exception{
		mockMvc.perform(post("/search").with(csrf().asHeader())
				.param("keyword", "")
				.param("nameSearch", "true"))
        .andExpect(status().isOk())
    	.andExpect(view().name("search"))
    	.andExpect(model().attributeExists("list"));
    	
    	
	}
	@WithMockUser(username = "admin", roles = {"USER"})
	@Test
	@Transactional
	public void searchPostAuthorSearch() throws Exception{
		mockMvc.perform(post("/search").with(csrf().asHeader())
				.param("keyword", "Mojang AB")
				.param("nameSearch", "false"))
        .andExpect(status().isOk())
    	.andExpect(view().name("search"))
    	.andExpect(model().attributeExists("list"))
    	.andExpect(model().attributeExists("list"));
    	
    	
	}
	@WithMockUser(username = "admin", roles = {"USER"})
	@Test
	@Transactional
	public void searchPostByISBN() throws Exception{
		mockMvc.perform(post("/search").with(csrf().asHeader())
				.param("keyword", "978-3453441064")
				.param("nameSearch", "false"))
        .andExpect(status().isFound())
    	.andExpect(redirectedUrl("/book/77"));
    	
    	
	}
	    
}
