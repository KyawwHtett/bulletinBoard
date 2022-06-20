package cgm.ojt.bulletin.web.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginForm {
	@NotEmpty
	@Email
	private String email;
	
	@NotEmpty
	private String password;
	
	public LoginForm() {
		super();
	}
}