package domain;


import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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
	@NotEmpty(message = "Name must not be blank.")
    private String name;
    @NotEmpty(message = "First name must not be blank.")
    private String firstName;
    @NotBlank
    @NotEmpty(message = "Password must not be blank.")
    @Size(min = 1, max = 10, message = "Password must between 1 to 10 Characters.")
    private String password;
	public Admin(String name, String firstName, String password) {
		this.name = name;
		this.firstName = firstName;
		this.password = password;
	}
	
}
