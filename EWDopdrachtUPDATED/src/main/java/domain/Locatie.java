package domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "id")
public class Locatie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long Id;
	private int plaatscode1;
	private int plaatscode2;
	private String plaatsnaam;
	public Locatie(int plaatscode1, int plaatscode2, String plaatsnaam) {
		setPlaatscode1(plaatscode1);
		setPlaatscode2(plaatscode2);
		setPlaatsnaam(plaatsnaam);
	}
	private void setPlaatsnaam(String plaatsnaam) {
		// TODO Auto-generated method stub
	
		if (plaatsnaam.matches("^[a-zA-Z]+$"))
			this.plaatsnaam = plaatsnaam;
		
	}
	private void setPlaatscode2(int plaatscode2) {
		// TODO Auto-generated method stub
		if(plaatscode2 >=50 && plaatscode2 <=300) {
			if(this.plaatscode1 -plaatscode2 <=50 ||this.plaatscode1 -plaatscode2 >=50 )
			this.plaatscode2 = plaatscode2;
		}
		
	}
	private void setPlaatscode1(int plaatscode1) {
		// TODO Auto-generated method stub
		if(plaatscode1 >=50 && plaatscode1 <=300) {
			this.plaatscode1 = plaatscode1;
		}
		
	}

	
	
}
