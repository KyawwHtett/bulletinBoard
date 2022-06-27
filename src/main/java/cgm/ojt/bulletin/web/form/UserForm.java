package cgm.ojt.bulletin.web.form;

import java.util.Date;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import cgm.ojt.bulletin.persistence.entity.User;
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
	@Email
	@NotEmpty
	@Column(unique = true)
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

	public UserForm(User user) {
		super();
		this.id = user.getId();
		this.username = user.getUsername();
		this.email = user.getEmail();
		this.password = user.getPassword();
		this.gender = user.getGender();
		this.type = user.getType();
		this.created_at = user.getCreated_at();
		this.updated_at = user.getUpdated_at();
		this.deleted_at = user.getDeleted_at();
	}
}