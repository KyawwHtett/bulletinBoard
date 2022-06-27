package cgm.ojt.bulletin.bl.service;

import cgm.ojt.bulletin.web.form.PasswordResetSentMailForm;

public interface PasswordResetService {
	PasswordResetSentMailForm createResetToken(String user_email);

	PasswordResetSentMailForm getDataByToken(String token);

	void doUpdatePassword(PasswordResetSentMailForm newPasswordResetForm);

	void doDeleteToken(String token);
}