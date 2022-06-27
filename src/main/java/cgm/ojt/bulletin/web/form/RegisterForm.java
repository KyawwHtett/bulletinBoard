package cgm.ojt.bulletin.web.form;

import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RegisterForm {
	private int id;
	@NotEmpty
	private String username;
	@Email
	@NotEmpty
	@Column(unique = true)
	private String email;
	@NotEmpty
	@Size(min = 6, max = 12)
	private String password;
	private String gender;
	private String type;
	private Date created_at;
	private Date updated_at;
	private Date deleted_at;

	public RegisterForm() {
		super();
	}
}