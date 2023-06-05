package com.example.EWDOpdracht;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import domain.Auteur;
import domain.Book;
import domain.Favourite;
import domain.Locatie;
import exception.BookNotFoundException;
import exception.DuplicateBookException;
import service.BookService;
import service.FavouriteService;

@SpringBootTest
public class BookRestTest {

	@Mock
	private BookService mock;
	private BookRestController controller;
	@Mock
	private FavouriteService mockFavourite;
	private MockMvc mockMvc;
	private final String ISBNNumber = "978-0099458326";
	private final String cover = "https://m.media-amazon.com/images/I/3190fsfp48L._SX321_BO1,204,203,200_.jpg";
	private Set<Auteur> auteurs = new HashSet<>();
	private final int aantalSterren = 247;
	private String naam = "Kafka on the Shore";
	private final double aankoopprijs = 14.99;
	private Set<Locatie> locaties = new HashSet<>();
	private final Long ID = 123L;

	@BeforeEach
	public void before() {
		MockitoAnnotations.openMocks(this);
		controller = new BookRestController();
		mockMvc = standaloneSetup(controller).build();
		ReflectionTestUtils.setField(controller, "bookService", mock);

	}

	private Book aBook(String ISBNNumber, String naam, double aankoopprijs) {
		Book book = new Book(ISBNNumber, naam, aankoopprijs);
		Auteur kafkaAuteur = new Auteur("Haruki", "Murakami");
		this.auteurs.add(kafkaAuteur);
		book.setAuteur(this.auteurs);
		book.setAantalSterren(aantalSterren);
		Locatie locatie = new Locatie(50, 200, "Japan");
		this.locaties.add(locatie);
		book.setCover(cover);
		book.setLocaties(this.locaties);

		return book;
	}

	private void performRest(String uri) throws Exception {

		String result = mockMvc.perform(get(uri)).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();
		System.out.println(result);
		mockMvc.perform(get(uri)).andExpect(status().isOk()).andExpect(jsonPath("$.naam").value(naam))
				.andExpect(jsonPath("$.isbnnumber").value(ISBNNumber))

				.andExpect(jsonPath("$.aankoopprijs").value(aankoopprijs))
				.andExpect(jsonPath("$.aantalSterren").value(aantalSterren)).andExpect(jsonPath("$.cover").value(cover))
				.andExpect(jsonPath("$.auteurs").isArray()).andExpect(jsonPath("$.locaties").isArray());

	}

	@Test
	public void testgetBook_isOk() throws Exception {
		Mockito.lenient().when(mock.getBook(ID)).thenReturn(aBook(ISBNNumber, naam, aankoopprijs));
		performRest("/rest/book/" + ID);
		Mockito.verify(mock, Mockito.times(2)).getBook(ID);

	}

	@Test
	public void testGetBook_NotFound() throws Exception {
		Mockito.when(mock.getBook(ID)).thenThrow(new BookNotFoundException(ID));

		Exception exception = assertThrows(Exception.class, () -> {
			mockMvc.perform(get("/rest/book/" + ID)).andReturn();
		});
		assertTrue(exception.getCause() instanceof BookNotFoundException);
		Mockito.verify(mock).getBook(ID);

	}

	@Test
	public void testGetAllBooks_emptyList() throws Exception {
		Mockito.when(mock.getAllBooks()).thenReturn(new ArrayList<>());
		mockMvc.perform(get("/rest/books")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isEmpty());

		Mockito.verify(mock).getAllBooks();
	}

	@Test
	public void testGetAllBooks_noEmptyList() throws Exception {
		Book book = new Book(ISBNNumber, naam, aankoopprijs);
		Auteur kafkaAuteur = new Auteur("Haruki", "Murakami");
		this.auteurs.add(kafkaAuteur);
		book.setAuteur(this.auteurs);
		book.setAantalSterren(aantalSterren);
		Locatie locatie = new Locatie(50, 200, "Japan");
		this.locaties.add(locatie);
		book.setCover(cover);
		book.setLocaties(this.locaties);

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
		List<Book> books = List.of(book, book2);
		Mockito.when(mock.getAllBooks()).thenReturn(books);
		mockMvc.perform(get("/rest/books")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isNotEmpty()).andExpect(jsonPath("$[0].naam").value(naam))
				.andExpect(jsonPath("$[0].isbnnumber").value(ISBNNumber))

				.andExpect(jsonPath("$[0].aankoopprijs").value(aankoopprijs))
				.andExpect(jsonPath("$[0].aantalSterren").value(aantalSterren))
				.andExpect(jsonPath("$[0].cover").value(cover)).andExpect(jsonPath("$[0].auteurs").isArray())
				.andExpect(jsonPath("$[0].locaties").isArray()).andExpect(jsonPath("$[1].naam").value("It: A Novel"))
				.andExpect(jsonPath("$[1].aankoopprijs").value(16.99));
		Mockito.verify(mock).getAllBooks();
	}

	@Test
	public void testCreateBook() throws Exception {
		Book book = new Book(ISBNNumber, naam, aankoopprijs);
		Auteur kafkaAuteur = new Auteur("Haruki", "Murakami");
		this.auteurs.add(kafkaAuteur);
		book.setAuteur(this.auteurs);
		book.setAantalSterren(aantalSterren);
		Locatie locatie = new Locatie(50, 200, "Japan");
		this.locaties.add(locatie);
		book.setCover(cover);
		book.setLocaties(this.locaties);
		book.setId(ID);
		Mockito.lenient().when(mock.getBook(ID)).thenReturn(book);
		String bookJson = new ObjectMapper().writeValueAsString(book);
		mockMvc.perform(post("/rest/book/create").contentType(MediaType.APPLICATION_JSON).content(bookJson))
				.andExpect(status().isOk());
		mockMvc.perform(get("/rest/book/" + ID)).andExpect(status().isOk()).andExpect(status().isOk())
				.andExpect(jsonPath("$.naam").value(naam)).andExpect(jsonPath("$.isbnnumber").value(ISBNNumber))

				.andExpect(jsonPath("$.aankoopprijs").value(aankoopprijs))
				.andExpect(jsonPath("$.aantalSterren").value(aantalSterren)).andExpect(jsonPath("$.cover").value(cover))
				.andExpect(jsonPath("$.auteurs").isArray()).andExpect(jsonPath("$.locaties").isArray());

		Mockito.verify(mock).getBook(ID);
		Mockito.verify(mock).createBook(Mockito.any(Book.class));

		;
	}

	@Test
	public void testCreateBook_duplicateKey() throws Exception {
		Book book = new Book(ISBNNumber, naam, aankoopprijs);
		Auteur kafkaAuteur = new Auteur("Haruki", "Murakami");
		this.auteurs.add(kafkaAuteur);
		book.setAuteur(this.auteurs);
		book.setAantalSterren(aantalSterren);
		Locatie locatie = new Locatie(50, 200, "Japan");
		this.locaties.add(locatie);
		book.setCover(cover);
		book.setLocaties(this.locaties);
		book.setId(ID);
		Mockito.lenient().when(mock.getBook(ID)).thenReturn(book);
		String bookJson = new ObjectMapper().writeValueAsString(book);
		Mockito.when(mock.createBook(Mockito.any(Book.class))).thenThrow(new DuplicateBookException(ID));

		Exception exception = assertThrows(Exception.class, () -> {
			mockMvc.perform(post("/rest/book/create").contentType(MediaType.APPLICATION_JSON).content(bookJson))
					.andReturn();
		});
		assertTrue(exception.getCause() instanceof DuplicateBookException);
		Mockito.verify(mock).createBook(Mockito.any(Book.class));
	}

	@Test
	public void deleteBook_notFound() throws Exception {
		Mockito.when(mock.deleteBook(ID)).thenThrow(new BookNotFoundException(ID));
		Exception exception = assertThrows(Exception.class, () -> {
			mockMvc.perform(delete("/rest/book/delete/" + ID)).andReturn();
		});
		System.out.println("hello");
		System.out.println(exception);

		assertTrue(exception.getCause() instanceof BookNotFoundException);
		Mockito.verify(mock).deleteBook(ID);
	}

	@Test
	public void testDeleteBook() throws Exception {
		Mockito.when(mock.deleteBook(ID)).thenReturn(aBook(ISBNNumber, naam, aankoopprijs));

		mockMvc.perform(delete("/rest/book/delete/" + ID)).andExpect(status().isOk())
				.andExpect(jsonPath("$.naam").value(naam)).andExpect(jsonPath("$.isbnnumber").value(ISBNNumber))

				.andExpect(jsonPath("$.aankoopprijs").value(aankoopprijs))
				.andExpect(jsonPath("$.aantalSterren").value(aantalSterren)).andExpect(jsonPath("$.cover").value(cover))
				.andExpect(jsonPath("$.auteurs").isArray()).andExpect(jsonPath("$.locaties").isArray());

		Mockito.verify(mock).deleteBook(ID);
	}

	@Test
	public void testGetAllFavourites() throws Exception {
		mockMvc = standaloneSetup(controller).build();
		ReflectionTestUtils.setField(controller, "favService", mockFavourite);
		Favourite favourite = new Favourite("user", "978-0099458326");
		Favourite favourite2 = new Favourite("user", "978-1501142970");
		List<Favourite> favourites = List.of(favourite, favourite2);
		Mockito.when(mockFavourite.getAllFavourites()).thenReturn(favourites);
		mockMvc.perform(get("/rest/favourite")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
				.andExpect(jsonPath("$").isNotEmpty()).andExpect(jsonPath("$[0].naam").value("user"))
				.andExpect(jsonPath("$[1].naam").value("user"))
				.andExpect(jsonPath("$[0].isbnnummer").value("978-0099458326"))
				.andExpect(jsonPath("$[1].isbnnummer").value("978-1501142970"));

		Mockito.verify(mockFavourite).getAllFavourites();

	}

	@Test
	public void getBookByISBN() throws Exception {
		Mockito.lenient().when(mock.getByISBNNumber(ISBNNumber)).thenReturn(aBook(ISBNNumber, naam, aankoopprijs));
		mockMvc.perform(get("/rest/ISBN/" + ISBNNumber)).andExpect(status().isOk())
				.andExpect(jsonPath("$.naam").value(naam)).andExpect(jsonPath("$.isbnnumber").value(ISBNNumber))

				.andExpect(jsonPath("$.aankoopprijs").value(aankoopprijs))
				.andExpect(jsonPath("$.aantalSterren").value(aantalSterren)).andExpect(jsonPath("$.cover").value(cover))
				.andExpect(jsonPath("$.auteurs").isArray()).andExpect(jsonPath("$.locaties").isArray());

		Mockito.verify(mock).getByISBNNumber(ISBNNumber);
	}
	@Test
	public void getBooksByAuthor() throws Exception{
		Book book = new Book(ISBNNumber, naam, aankoopprijs);
		Auteur kafkaAuteur = new Auteur("Haruki", "Murakami");
		this.auteurs.add(kafkaAuteur);
		book.setAuteur(this.auteurs);
		book.setAantalSterren(aantalSterren);
		Locatie locatie = new Locatie(50, 200, "Japan");
		this.locaties.add(locatie);
		book.setCover(cover);
		book.setLocaties(this.locaties);

		Book book2 = new Book("978-1501142970", "It: A Novel", 16.99);
		Auteur andereAuteur = new Auteur("Haruki", "Murakami");
		Set<Auteur> auteurs2 = new HashSet<>();
		auteurs2.add(andereAuteur);

		book2.setAuteur(auteurs2);
		book2.setAantalSterren(200);
		Set<Locatie> locaties2 = new HashSet<>();
		Locatie locatie2 = new Locatie(50, 200, "America");
		locaties2.add(locatie2);
		book2.setCover("https://m.media-amazon.com/images/I/41MZU7A+5dL._SX326_BO1,204,203,200_.jpg");
		book2.setLocaties(locaties2);
		List<Book> books = List.of(book, book2);
		Mockito.when(mock.getBooksByAuteur("Haruki", "Murakami")).thenReturn(books);
		
		
		mockMvc.perform(get("/rest/author/" + "Murakami_Haruki")).andExpect(status().isOk()).andExpect(jsonPath("$").isArray())
		.andExpect(jsonPath("$").isNotEmpty()).andExpect(jsonPath("$[0].naam").value(naam))
		.andExpect(jsonPath("$[0].isbnnumber").value(ISBNNumber))

		.andExpect(jsonPath("$[0].aankoopprijs").value(aankoopprijs))
		.andExpect(jsonPath("$[0].aantalSterren").value(aantalSterren))
		.andExpect(jsonPath("$[0].cover").value(cover)).andExpect(jsonPath("$[0].auteurs").isArray())
		.andExpect(jsonPath("$[0].locaties").isArray()).andExpect(jsonPath("$[1].naam").value("It: A Novel"))
		.andExpect(jsonPath("$[1].aankoopprijs").value(16.99));
		Mockito.verify(mock).getBooksByAuteur("Haruki", "Murakami");

	}
	@Test
	public void updateBookTest() throws Exception {
		Book book = new Book(ISBNNumber, naam, aankoopprijs);
		Auteur kafkaAuteur = new Auteur("Haruki", "Murakami");
		this.auteurs.add(kafkaAuteur);
		book.setAuteur(this.auteurs);
		book.setAantalSterren(aantalSterren);
		Locatie locatie = new Locatie(50, 200, "Japan");
		this.locaties.add(locatie);
		book.setCover(cover);
		book.setLocaties(this.locaties);
		Mockito.lenient().when(mock.getBook(ID)).thenReturn(book);
		String bookJson = new ObjectMapper().writeValueAsString(book);
		mockMvc.perform(post("/rest/book/create").contentType(MediaType.APPLICATION_JSON).content(bookJson))
				.andExpect(status().isOk());
		mockMvc.perform(get("/rest/book/" + ID)).andExpect(status().isOk()).andExpect(status().isOk())
				.andExpect(jsonPath("$.naam").value(naam)).andExpect(jsonPath("$.isbnnumber").value(ISBNNumber))

				.andExpect(jsonPath("$.aankoopprijs").value(aankoopprijs))
				.andExpect(jsonPath("$.aantalSterren").value(aantalSterren)).andExpect(jsonPath("$.cover").value(cover))
				.andExpect(jsonPath("$.auteurs").isArray()).andExpect(jsonPath("$.locaties").isArray());

		Mockito.verify(mock).getBook(ID);
		Mockito.verify(mock).createBook(Mockito.any(Book.class));
		
		book.setAankoopprijs(12.99);
		book.setAantalSterren(4);
		book.setCover("cover123");
		book.setISBNNumber("978-1501142970");
		book.setNaam("naamboek");
		Mockito.lenient().when(mock.updateBook(Mockito.any(Book.class))).thenReturn(book);
		String bookJson2 = new ObjectMapper().writeValueAsString(book);
		mockMvc.perform(put("/rest/book/" + ID).contentType(MediaType.APPLICATION_JSON)
				.content(bookJson2)).andExpect(status().isOk())
		.andExpect(jsonPath("$.naam").value("naamboek")).andExpect(jsonPath("$.isbnnumber").value("978-1501142970"))

		.andExpect(jsonPath("$.aankoopprijs").value(12.99))
		.andExpect(jsonPath("$.aantalSterren").value(4)).andExpect(jsonPath("$.cover").value("cover123"))
		.andExpect(jsonPath("$.auteurs").isArray()).andExpect(jsonPath("$.locaties").isArray());
		Mockito.verify(mock).getBook(ID);
		Mockito.verify(mock).updateBook(Mockito.any(Book.class));

		
		
	}
}
