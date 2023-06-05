package com.example.EWDOpdracht;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import domain.Auteur;
import domain.Book;
import domain.BookForm;
import domain.Locatie;
import repository.AuteurRepository;
import repository.BookRepository;
import repository.LocatieRepository;
import service.AuteurService;
import service.BookService;
import service.BookServiceImpl;
import service.FavouriteService;
import service.LocatieService;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, properties = "server.port=8080")

@AutoConfigureMockMvc
@Import(SecurityConfig.class)
public class BookControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	@Mock
	private BookRestController restController;
	


	
	@Mock
	private BookRepository bookRepository;
	
	@InjectMocks
	private BookController bookController;
	@InjectMocks	
	private BookServiceImpl bookService;

	@Autowired
	MessageSource messageSource;
	@Mock
	LocatieService locatieService;
	@Mock 
	AuteurService auteurService;
	
	
	

	
	@BeforeEach
	public void setup() {
	    MockitoAnnotations.openMocks(this); // Initialize the mocks
	}
	
	@Test
	public void loginGet() throws Exception {
	    	mockMvc.perform(get("/login"))
	        .andExpect(status().isOk())
	    	.andExpect(view().name("login"));
	}
	@Test
	public void testLogout() throws Exception {
	    mockMvc.perform(logout("/logout"))
	            .andExpect(status().is3xxRedirection())
	            .andExpect(redirectedUrl("/login?logout"))
	            .andExpect(unauthenticated());
	}

	@WithMockUser(username = "user", roles = {"USER"})
    @Test
    @Transactional
    public void testAccessWithUserRole() throws Exception {
        mockMvc.perform(get("/book"))
        .andExpect(status().isOk())
        .andExpect(view().name("book"))
        .andExpect(model().attributeExists("username"))
        .andExpect(model().attribute("username", "user"));
    }
	@Test
	public void testLoginFailure() throws Exception {
	    mockMvc.perform(formLogin("/login").user("incorrectUser").password("incorrectPassword"))
	            .andExpect(status().is3xxRedirection())
	            .andExpect(redirectedUrl("/login?error"))
	            .andExpect(unauthenticated());
	}
	
	@Test
    void testWrongPassword() throws Exception {
        mockMvc.perform(formLogin("/login")
            .user("username", "nameUser")
            .password("password", "wrongpassword"))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/login?error"));
    }
	@Test
    void testCorrectPassword() throws Exception {
        mockMvc.perform(formLogin("/login")
            .user("username", "user")
            .password("password", "user"))
            .andExpect(status().isFound())
            .andExpect(redirectedUrl("/"));
    }
	@WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    
	void createBookForm() throws Exception{
	    MockitoAnnotations.openMocks(this); 
	    bookController = new BookController();
		mockMvc = standaloneSetup(bookController).build();
		
		ReflectionTestUtils.setField(bookController, "bookService", bookService);
		ReflectionTestUtils.setField(bookController, "messageSource", messageSource);
		ReflectionTestUtils.setField(bookController, "auteurService", auteurService);
		ReflectionTestUtils.setField(bookController, "locatieService", locatieService);


	
		BookForm request = new BookForm();
		 Long ID = 123L;
		String ISBNNumber = "978-1473224469";
		String naam ="Kafka on the Shore";
		double aankoopprijs = 14.99;
		int aantalSterren =  247;
		String cover = "https://m.media-amazon.com/images/I/3190fsfp48L._SX321_BO1,204,203,200_.jpg";

		Book book = new Book(ISBNNumber, naam, aankoopprijs);
		Auteur kafkaAuteur = new Auteur("Haruki", "Murakami");
		Set<Auteur> auteurs= new HashSet<>();
		auteurs.add(kafkaAuteur);
		book.setAantalSterren(aantalSterren);
		Locatie locatie = new Locatie(50, 200, "Japan");
		Set<Locatie> locaties =new HashSet<>();
		locaties.add(locatie);
		
		book.setCover(cover);
		
	 
	    request.setFirstLocation("Location");
	    request.setFirstLocationfirstcode(50);
	    request.setFirstLocationsecondCode(250);
	    
	    
	    request.setFirstAuthor("Author");
	    request.setFirstAuthorName("Author Name");

	    request.setSecondAuthor("newName");
	    request.setSecondAuthorName("coolName");
	    
	    request.setThirdAuthor("newNameAuthor");
	    request.setThirdAuthorName("newTwoName");
	    
	   
	    
	    
	    request.setSecondLocation("NewLocation");
	    request.setSecondLocationfirstcode(50);
	    request.setSecondLocationsecondCode(250);
	    
	    request.setThirdLocation("NewLocationTwo");
	    request.setThirdLocationfirstcode(250);
	    request.setThirdLocationsecondCode(300);

//	
	    
//		book.setAuteur(auteurs);
//		book.setLocaties(locaties);
//		request.setAuteurs(auteurs);
//		request.setLocaties(locaties);
		request.setBook(book);
		Optional<Book> bookOpt = Optional.ofNullable(book);
		
		
		assertEquals(request.getFirstAuthorName(), "Author Name");
		Mockito.when(locatieService.findByPlaatsnaamAndPlaatscode1AndPlaatscode2
				(Mockito.anyString(), Mockito.anyInt(), Mockito.anyInt())).thenReturn(null);
		Mockito.when(auteurService.
				findByNaamAndFirstname(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
        mockMvc.perform(post("/book/new").with(csrf().asHeader()).flashAttr("bookForm", request))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/book"));
        bookRepository.delete(book);
        System.out.println("na dit");
		
	}
	

	
	@WithMockUser(username = "user", roles = {"USER"})
    @Test
    @Transactional
    public void testAccessWithUserRoleBookPage() throws Exception {
        mockMvc.perform(get("/book"))
        .andExpect(status().isOk())
        .andExpect(view().name("book"))
        .andExpect(model().attributeExists("username"))
        .andExpect(model().attributeExists("bookList"))
        .andExpect(model().attribute("username", "user"));
    }
	@WithMockUser(username = "user", roles = {"USER"})
    @Test
    @Transactional
    public void testAccessWithUserRoleShortBookPage() throws Exception {
        mockMvc.perform(get("/book/short"))
        .andExpect(status().isOk())
        .andExpect(view().name("bookshort"))
        .andExpect(model().attributeExists("username"))
        .andExpect(model().attributeExists("bookList"))
        .andExpect(model().attribute("username", "user"));
    }
	
	@WithMockUser(username = "user", roles = {"ADMIN"})
    @Test
    @Transactional
    public void testAccessFormPage() throws Exception {
        mockMvc.perform(get("/book/new"))
        .andExpect(status().isOk())
        .andExpect(view().name("/new"))
        .andExpect(model().attributeExists("username"))
        .andExpect(model().attributeExists("bookForm"))
        .andExpect(model().attribute("username", "user"));
    }
	@WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Transactional
    public void testAccessSpecificBookPage(@Autowired WebTestClient webClient) throws Exception {
	
		
		Book book2 = new Book("978-1501142970", "It: A Novel", 16.99);
		Auteur andereAuteur = new Auteur("Stephen", "King");
		Set<Auteur> auteurs2 = new HashSet<>();
		auteurs2.add(andereAuteur);

		book2.setAuteur(auteurs2);
		book2.setAantalSterren(200);
		Set<Locatie> locaties2 = new HashSet<>();
		Locatie locatie2 = new Locatie(50, 200, "America");
		locaties2.add(locatie2);
		book2.setCover("https://m.media-amazon.com/images/I/41MZU7A+5dL._SX326_BO1,204,203,200_.jpg");
		book2.setLocaties(locaties2);
		
		Mockito.when(restController.getBook(Mockito.anyLong())).thenReturn(book2);
        mockMvc.perform(get("/book/{id}",1L).with(csrf().asHeader()))
        .andExpect(status().isOk())
        .andExpect(view().name("/bookShow"))
        .andExpect(model().attributeExists("username"))
        .andExpect(model().attributeExists("book"))
        .andExpect(model().attributeExists("isFavourited"))
        .andExpect(model().attribute("username", "admin"));
    }
	@WithMockUser(username = "user", roles = {"USER"})
    @Test
    @Transactional
    public void testAccessSpecificBookPage_NotFound_Redirect() throws Exception {
        mockMvc.perform(get("/book/124"))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/book"));

    }
	
	@WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    @Transactional
    public void testAccessUpdatePage() throws Exception {
        mockMvc.perform(get("/book/update/61"))
        .andExpect(status().isOk())
        .andExpect(view().name("/change"));

    }
	
	
}

