package domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.format.annotation.NumberFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import utility.Message;
import validator.ValidISBNNumberChecksum;
import validator.ValidISBNNumberPattern;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "id")
public class Book implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(unique = true)
	@ValidISBNNumberChecksum(message = "{checksum_fail}")
	@ValidISBNNumberPattern(message = "{patternISBN_fail}")
	private String ISBNNumber;

	@ManyToMany
	private final Set<Auteur> auteurs = new HashSet<>(3);

	@NotEmpty(message = "{BookNameNotEmpty}")
	private String naam;
	@NotEmpty(message = "{BookCoverNotEmpty}")

	@Pattern(regexp = "^(https?|ftp)://[\\w-]+(\\.[\\w-]+)+([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?$", message = "{CoverUrlFail}")
	private String cover;

	
	
	@DecimalMin(value = "0.00", message = "{PriceMoreThan0}")
	@DecimalMax(value = "99.99", message = "{PriceLessThan100}")
	@NumberFormat(pattern = "#,##0.00")
	private Double aankoopprijs;

	private int aantalSterren = 0;

	@OneToMany
	private Set<Locatie> locaties;

	public Book(String ISBNNumber, Set<Auteur> auteurs, String naam, double aankoopprijs, Set<Locatie> locaties) {

		setISBNNumber(ISBNNumber);
		setAuteur(auteurs);
		setNaam(naam);
		setAankoopprijs(aankoopprijs);
		setLocaties(locaties);
	}
	public void setCover(String cover) {
		this.cover = cover;
	}

	public Book(String ISBNNumber, String naam, double aankoopprijs) {

		setISBNNumber(ISBNNumber);
		setNaam(naam);
		setAankoopprijs(aankoopprijs);
		setAantalSterren(aantalSterren);
		
	}


	public void setISBNNumber(String ISBNNumber) {
		this.ISBNNumber = ISBNNumber;
	}

	public void setAuteur(Set<Auteur> auteurs) {
		auteurs.forEach((auteur) -> this.auteurs.add(auteur));
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public void addAuteur(Auteur auteur) {
		this.auteurs.add(auteur);
	}

	public String getNaam() {
		return this.naam;
	}

	public void setAankoopprijs(double aankoopprijs) {
		this.aankoopprijs = aankoopprijs;
	}

	public void setAantalSterren(int aantalSterren) {
		this.aantalSterren = aantalSterren;
	}

	public void setLocaties(Set<Locatie> locaties) {
		this.locaties = locaties;
	}

	public Set<Auteur> getAuteurs() {
		return Collections.unmodifiableSet(auteurs);
	}

//	private static final Pattern ISBN_PATTERN = Pattern
//			.compile("^(?:ISBN(?:-13)?:)?(?=[0-9]{13}$|(?=(?:[0-9]+[-]){4})[-0-9]{17}$)97[89][-]?[0-9]{1,5}[-]?[0-9]+[-]?[0-9]+[-]?[0-9]$");
//
//	public static boolean isValidISBN(String isbn) {
//		Matcher matcher = ISBN_PATTERN.matcher(isbn);
//		return matcher.matches();
//	}

	public void setId(Long id) {
		this.id = id;
	}
}