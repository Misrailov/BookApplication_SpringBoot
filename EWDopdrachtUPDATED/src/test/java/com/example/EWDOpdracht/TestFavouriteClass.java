package com.example.EWDOpdracht;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.security.Principal;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;import org.hibernate.internal.build.AllowSysOut;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import domain.Auteur;
import domain.Book;
import domain.Favourite;
import domain.Locatie;
import repository.BookRepository;
import repository.FavouriteRepository;
import service.BookService;
import service.BookServiceImpl;
import service.FavouriteService;
import service.FavouriteServiceImpl;
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT, properties = "server.port=8080")

@AutoConfigureMockMvc
@Import(SecurityConfig.class)
class TestFavouriteClass{
	

	
	@Autowired
	private MockMvc mockMvc;
	@Mock
	private BookRestController restController;
	@Mock
	BookService bookService;
	@Mock 
	FavouriteService favouriteService;
	
	@InjectMocks
	private BookController bookController;

	@BeforeEach
	public void setup() {
	    MockitoAnnotations.openMocks(this); 
	    bookController = new BookController();
		mockMvc = standaloneSetup(bookController).build();
		ReflectionTestUtils.setField(bookController, "favouriteService", favouriteService);
		ReflectionTestUtils.setField(bookController, "bookService", bookService);

	    
	}
	
	@WithMockUser(username = "user", roles = {"USER"})
    @Test
    @Transactional
    public void testSetFavouriteBookPost(@Autowired WebTestClient webClient) throws Exception{
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
		book2.setId(100L);
		Favourite favourite =new Favourite("user", "978-1444707861") ;
		Optional<Favourite> favouriteOpt =Optional.ofNullable(new Favourite("user", "978-1444707861")) ;
		Mockito.when(bookService.getBook(Mockito.anyLong())).thenReturn(book2);
		Mockito.when(restController.getBook(Mockito.anyLong())).thenReturn(book2);
		
		Mockito.when(favouriteService.getFavourite(favourite.getId())).thenReturn(favouriteOpt.get());
		Mockito.when(favouriteService.findByISBNNummerAndNaam(Mockito.anyString(),Mockito.anyString())).thenReturn(favourite);

		

        mockMvc.perform(post("/book/"+ 9).param("isFavourited", "false").principal(new PrincipalImpl()).with(csrf().asHeader()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/home"));
        
        
        
        
	}
	
	@WithMockUser(username = "user", roles = {"USER"})
    @Test
    @Transactional
    public void testSetFavouriteTrueBookPost(@Autowired WebTestClient webClient) throws Exception{
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
		book2.setId(100L);
		Favourite favourite =new Favourite("user", "978-1444707861") ;
		Mockito.when(bookService.getBook(Mockito.anyLong())).thenReturn(book2);
		Mockito.when(favouriteService.createFavourite(Mockito.any(Favourite.class))).thenReturn(favourite);
		Mockito.when(favouriteService.findByISBNNummerAndNaam(Mockito.anyString(),Mockito.anyString())).thenReturn(favourite);
		
        mockMvc.perform(post("/book/"+ 9).param("isFavourited", "true").principal(new PrincipalImpl()).with(csrf().asHeader()))
        .andExpect(status().isFound())
        .andExpect(redirectedUrl("/home"));
        
        
        
        
	}
	
}
class PrincipalImpl implements Principal {

    @Override
    public String getName() {

        return "user";
    }

}