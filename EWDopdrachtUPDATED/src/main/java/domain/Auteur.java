package domain;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Auteur implements Serializable{
	
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@JsonProperty("author_id")
	private long id;
	
	
    
    @JsonProperty("lastname")
	private String naam;
    
    
	private String firstname;
	
	public Auteur(String naam, String firstname) {
		this.naam = naam;
		this.firstname = firstname;
	}

	@Override
	public String toString() {
		return  naam + " "  + firstname ;
	}
	

}
