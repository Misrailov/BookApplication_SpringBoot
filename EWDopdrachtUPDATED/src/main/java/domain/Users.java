package domain;


import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "users")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(exclude = "id")
public class Users {
    @Column(name = "username", unique = true)
	@Id
	private String username;
	
	@Column(name = "password")
	private String password;
	@Column(name = "max_favourites")
	@Min(value =3)
	@Max(value = 10)
	private Integer maxFavourites;
	private Integer enabled;
	public void setPassword(String password) {
		BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
		this.password = encode.encode(password);
	}
	
}
