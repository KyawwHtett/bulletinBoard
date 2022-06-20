package cgm.ojt.bulletin.bl.dto;

import java.io.Serializable;
import java.util.Date;

import cgm.ojt.bulletin.persistence.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private int id;
	private String username;
	private String email;
	private String password;
	private String gender;
	private String type;
	private Date created_at;
	private Date updated_at;
	private Date deleted_at;

	public UserDto() {
		super();
	}
	public UserDto(User user) {
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