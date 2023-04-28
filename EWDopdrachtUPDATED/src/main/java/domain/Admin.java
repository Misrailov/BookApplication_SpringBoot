package domain;


import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor( access = AccessLevel.PROTECTED)
@EqualsAndHashCode(exclude = "id")
@ToString(exclude = "id")
public class Admin implements Serializable{
	
	private static final long  serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
    private String name;
    
    private String firstName;
    
    private String password;
	public Admin(String name, String firstName, String password) {
		this.name = name;
		this.firstName = firstName;
		this.password = password;
	}
	
}
