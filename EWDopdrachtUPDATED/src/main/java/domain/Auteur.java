package domain;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "id")
public class Auteur implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Id
	private String naam;
	@Id
	private String firstname;
	
	public Auteur(String naam, String firstname) {
		this.naam = naam;
		this.firstname = firstname;
	}

}
