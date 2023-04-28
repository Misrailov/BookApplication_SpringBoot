package domain;

import java.io.Serializable;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "id")
public class Book implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long ID;
//	private String ISBNNumber;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Auteur> auteurs;
	private String naam;
	private double aankoopprijs;
	private int aantalSterren;
	@OneToMany(cascade = CascadeType.ALL)
	private List<Locatie> locaties;

	public Book(String ISBNNummer, List<Auteur> auteurs, String naam, double aankoopprijs, int aantalSterren,List<Locatie> locaties) {
		setISBNNummer(ISBNNummer);
		setAuteur(auteurs);
		setNaam(naam);
		setAankoopprijs(aankoopprijs);
		setAantalSterren(aantalSterren);
		setLocaties(locaties);
	}

	public void setISBNNummer(String iSBNNummer) {
//		if(isValidISBN(iSBNNummer))ISBNNumber = iSBNNummer;
	}

	public void setAuteur(List auteurs) {
		this.auteurs = auteurs;
	}

	public void setNaam(String naam) {
		this.naam = naam;
	}

	public void setAankoopprijs(double aankoopprijs) {
		this.aankoopprijs = aankoopprijs;
	}

	public void setAantalSterren(int aantalSterren) {
		this.aantalSterren = aantalSterren;
	}

	public void setLocaties(List<Locatie> locaties) {
		this.locaties = locaties;
	}

	private static final Pattern ISBN_PATTERN = Pattern
			.compile("^(?:(?=.{17}$)97[89][ -]?(?=.{13}$)\\d{1,3}[ -]?)?(?:\\d[ -]?){9}[\\dx]$");

	public static boolean isValidISBN(String isbn) {
		Matcher matcher = ISBN_PATTERN.matcher(isbn);
		return matcher.matches();
	}

}
