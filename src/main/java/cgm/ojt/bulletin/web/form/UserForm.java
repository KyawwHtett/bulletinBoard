package cgm.ojt.bulletin.web.form;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class UserForm {
	private int id;
	@NotEmpty
	private String username;
	@NotEmpty
	private String email;
	private String password;
	private String gender;
	private String type;
	private Date created_at;
	private Date updated_at;
	private Date deleted_at;

	public UserForm() {
		super();
	}
}