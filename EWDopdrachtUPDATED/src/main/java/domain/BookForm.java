package domain;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BookForm {
	@Valid
	private Book book;

	// set en return unmodifiable collection

	private Set<Auteur> auteurs = new HashSet();

	private Set<Locatie> locaties = new HashSet();

	public Book getBook() {
		return this.book;
	}

	@NotBlank(message = "{BookFormNotBlankFirstLocation}")
	@Pattern(regexp = "^[a-zA-Z]+$", message = "{PlaceNameOnlyLetters}")
	private String firstLocation;
	
	@NotNull
	@Min(value = 50, message = "{LocationCodeLessthan50}")
	@Max(value = 300, message = "LocationCodeMoreThan300}")
	private Integer firstLocationfirstcode;

	@NotNull
	@Min(value = 50, message = "{LocationCodeLessthan50}")
	@Max(value = 300, message = "LocationCodeMoreThan300}")
	private Integer firstLocationsecondCode;

	@Pattern(regexp = "^[a-zA-Z]*$", message = "{PlaceNameOnlyLetters}")

	private String secondLocation;
	@Min(value = 50, message = "{LocationCodeLessthan50}")
	@Max(value = 300, message = "{LocationCodeMoreThan300}")
	private Integer secondLocationfirstcode;

	@Min(value = 50, message = "{LocationCodeLessthan50}")
	@Max(value = 300, message = "{LocationCodeMoreThan300}")
	private Integer secondLocationsecondCode;

	@Pattern(regexp = "^[a-zA-Z]*$", message = "{PlaceNameOnlyLetters}")

	private String thirdLocation;

	@Min(value = 50, message = "{LocationCodeLessthan50}")
	@Max(value = 300, message = "{LocationCodeMoreThan300}")
	private Integer thirdLocationfirstcode;

	@Min(value = 50, message = "{LocationCodeLessthan50}")
	@Max(value = 300, message = "{LocationCodeMoreThan300}")
	private Integer thirdLocationsecondCode;

	@NotBlank(message = "{FirstNameNotBlank}")
	@Pattern(regexp = "^[a-zA-Z .-]+$", message = "{NameContainLetters}")

	private String firstAuthor;

	@NotBlank(message = "{LastNameNotBlank}")
	@Pattern(regexp = "^[a-zA-Z .-]+$", message = "{NameContainLetters}")

	private String firstAuthorName;

	@Pattern(regexp = "^[a-zA-Z .-]*$", message = "{NameContainLetters}")

	private String secondAuthor;
	@Pattern(regexp = "^[a-zA-Z .-]*$", message = "{NameContainLetters}")

	private String secondAuthorName;

	@Pattern(regexp = "^[a-zA-Z .-]*$", message = "{NameContainLetters}")

	private String thirdAuthor;
	@Pattern(regexp = "^[a-zA-Z .-]*$", message = "{NameContainLetters}")

	private String thirdAuthorName;

	public String getFirstAuthorName() {
		return firstAuthorName;
	}

	public void setFirstAuthorName(String firstAuthorName) {
		this.firstAuthorName = firstAuthorName;
	}

	public String getFirstLocation() {
		return firstLocation;
	}

	public void setFirstLocation(String firstLocation) {
		this.firstLocation = firstLocation;

	}

	public String getFirstAuthor() {
		return firstAuthor;
	}

	public void setFirstAuthor(String firstAuthor) {
		this.firstAuthor = firstAuthor;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Set<Auteur> getAuteurs() {
		if (auteurs.isEmpty()) {
			auteurs.add(new Auteur());
			auteurs.add(new Auteur());
			auteurs.add(new Auteur());
		}
		return Collections.unmodifiableSet(auteurs);
	}

	public void setAuteurs(Set<Auteur> auteurs) {
		this.auteurs = auteurs;

	}

	public void setLocaties(Set<Locatie> locaties) {
		this.locaties = locaties;
	}

	public Set<Locatie> getLocaties() {
		if (locaties.isEmpty()) {
			locaties.add(new Locatie(50, 250, "default"));
			locaties.add(new Locatie(50, 250, "default"));
		}
		return Collections.unmodifiableSet(locaties);
	}

}
