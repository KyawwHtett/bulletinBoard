package cgm.ojt.bulletin.persistence.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import cgm.ojt.bulletin.web.form.PasswordResetSentMailForm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "password_reset")
public class PasswordReset {
	@Column(name = "user_email")
	private String user_email;

	@Id
	@Column(name = "token")
	private String token;

	@Column(name = "created_at")
	private Timestamp created_at;

	@Column(name = "expired_at")
	private Timestamp expired_at;

	public PasswordReset() {
		super();
	}

	public PasswordReset(PasswordResetSentMailForm passwordResetSentMailForm) {
		super();
		this.user_email = passwordResetSentMailForm.getUser_email();
		this.token = passwordResetSentMailForm.getToken();
		this.created_at = passwordResetSentMailForm.getCreated_at();
		this.expired_at = passwordResetSentMailForm.getExpired_at();
	}
}