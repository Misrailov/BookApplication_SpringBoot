package domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor
@Setter
@ToString
@EqualsAndHashCode(exclude = "id")
public class Locatie {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Min(value = 50, message = "{LocationCodeLessthan50}")
	@Max(value = 300, message = "{LocationCodeMoreThan300}")
	private Integer plaatscode1;
	
	@Min(value = 50, message = "{LocationCodeLessthan50}")
	@Max(value = 300, message = "{LocationCodeMoreThan300}")
	private Integer plaatscode2;

	@Pattern(regexp = "^[a-zA-Z]+$", message = "{PlaceNameOnlyLetters}")
	private String plaatsnaam;

	public Locatie(int plaatscode1, int plaatscode2, String plaatsnaam) {
		this.plaatscode1 = plaatscode1;
		this.plaatscode2 = plaatscode2;
		this.plaatsnaam = plaatsnaam;
	}
	public void setId(Long id) {
		this.id =id;
	}

}

