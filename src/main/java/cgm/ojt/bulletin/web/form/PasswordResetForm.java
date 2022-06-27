package cgm.ojt.bulletin.web.form;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetForm {
	@NotEmpty
	@Size(min = 6, max = 12)
	private String password;
	private String token;

	public PasswordResetForm() {
		super();
	}
}