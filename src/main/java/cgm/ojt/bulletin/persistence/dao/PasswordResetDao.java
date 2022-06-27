package cgm.ojt.bulletin.persistence.dao;

import cgm.ojt.bulletin.persistence.entity.PasswordReset;

public interface PasswordResetDao {

	void deleteTokenByEmail(String user_email);

	void createToken(PasswordReset pswToken);

	PasswordReset getTokenDataByEmail(String user_email);

	PasswordReset dbGetDataByToken(String token);

	void dbDeleteToken(String token);
}