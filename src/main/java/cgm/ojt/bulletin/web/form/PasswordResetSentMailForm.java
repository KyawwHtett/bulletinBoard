package cgm.ojt.bulletin.web.form;

import java.sql.Timestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import cgm.ojt.bulletin.persistence.entity.PasswordReset;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetSentMailForm {
	@Email
	@NotEmpty
	private String user_email;
	private String password;
	private String token;
	private Timestamp created_at;
	private Timestamp expired_at;

	public PasswordResetSentMailForm() {
		super();
	}
	
	public PasswordResetSentMailForm(PasswordReset passwordReset) {
		this.user_email = passwordReset.getUser_email();
		this.token = passwordReset.getToken();
		this.created_at = passwordReset.getCreated_at();
		this.expired_at = passwordReset.getExpired_at();
	}
}
