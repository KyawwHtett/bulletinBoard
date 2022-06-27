package cgm.ojt.bulletin.persistence.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import cgm.ojt.bulletin.bl.dto.UserDto;
import cgm.ojt.bulletin.web.form.UserForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name = "username")
	private String username;

	@Email
	@Column(name = "email", unique = true)
	private String email;

	@Column(name = "password")
	private String password;

	@Column(name = "gender")
	private String gender;

	@Column(name = "type")
	private String type;

	@Column(name = "created_at")
	private Date created_at;

	@Column(name = "updated_at")
	private Date updated_at;

	@Column(name = "deleted_at")
	private Date deleted_at;

	public User() {
		super();
	}

	public User(UserForm userForm) {
		super();
		this.id = userForm.getId();
		this.username = userForm.getUsername();
		this.email = userForm.getEmail();
		this.password = userForm.getPassword();
		this.gender = userForm.getGender();
		this.type = userForm.getType();
		this.created_at = userForm.getCreated_at();
		this.updated_at = userForm.getUpdated_at();
		this.deleted_at = userForm.getDeleted_at();
	}

	public User(UserDto userDto) {
		super();
		this.id = userDto.getId();
		this.username = userDto.getUsername();
		this.email = userDto.getEmail();
		this.password = userDto.getPassword();
		this.gender = userDto.getGender();
		this.type = userDto.getType();
		this.created_at = userDto.getCreated_at();
		this.updated_at = userDto.getUpdated_at();
		this.deleted_at = userDto.getDeleted_at();
	}
}