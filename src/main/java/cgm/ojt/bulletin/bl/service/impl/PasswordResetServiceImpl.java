package cgm.ojt.bulletin.bl.service.impl;

import java.sql.Timestamp;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cgm.ojt.bulletin.bl.service.PasswordResetService;
import cgm.ojt.bulletin.common.TokenGenerator;
import cgm.ojt.bulletin.persistence.dao.PasswordResetDao;
import cgm.ojt.bulletin.persistence.dao.UserDao;
import cgm.ojt.bulletin.persistence.entity.PasswordReset;
import cgm.ojt.bulletin.persistence.entity.User;
import cgm.ojt.bulletin.web.form.PasswordResetSentMailForm;
import cgm.ojt.bulletin.web.form.UserForm;

@Service
@Transactional
public class PasswordResetServiceImpl implements PasswordResetService {
	public static final int psw_token_length = 20;
	public static final int psw_token_expired_minute = 3;
	private Timestamp now = new Timestamp(new Date(System.currentTimeMillis()).getTime());

	@Autowired
	private PasswordResetDao passwordResetDao;

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public PasswordResetSentMailForm createResetToken(String user_email) {
		if (isEmailExist(user_email)) {
			passwordResetDao.deleteTokenByEmail(user_email);
		}
		String token = new TokenGenerator(psw_token_length).getToken();
		Timestamp expired = new Timestamp(
				new Date(System.currentTimeMillis() + psw_token_expired_minute * 60 * 1000).getTime());
		System.out.println(expired);
		PasswordResetSentMailForm passwordResetForm = new PasswordResetSentMailForm();
		passwordResetForm.setUser_email(user_email);
		passwordResetForm.setToken(token);
		passwordResetForm.setCreated_at(now);
		passwordResetForm.setExpired_at(expired);
		this.passwordResetDao.createToken(this.getPswToken(passwordResetForm));
		return passwordResetForm;
	}

	private boolean isEmailExist(String user_email) {
		PasswordReset pwToken = this.passwordResetDao.getTokenDataByEmail(user_email);
		return pwToken != null;
	}

	private PasswordReset getPswToken(PasswordResetSentMailForm passwordResetSentMailForm) {
		PasswordReset pwToken = new PasswordReset();
		pwToken.setUser_email(passwordResetSentMailForm.getUser_email());
		pwToken.setToken(passwordResetSentMailForm.getToken());
		pwToken.setCreated_at(passwordResetSentMailForm.getCreated_at());
		pwToken.setExpired_at(passwordResetSentMailForm.getExpired_at());
		return pwToken;
	}

	@Override
	public PasswordResetSentMailForm getDataByToken(String token) {
		try {
			PasswordResetSentMailForm passwordResetSentMailForm = new PasswordResetSentMailForm(
					passwordResetDao.dbGetDataByToken(token));
			return passwordResetSentMailForm;
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public void doUpdatePassword(PasswordResetSentMailForm newPasswordResetForm) {
		newPasswordResetForm.setPassword(passwordEncoder.encode(newPasswordResetForm.getPassword()));
		User user = this.userDao.dbFindUserByEmail(newPasswordResetForm.getUser_email());
		user.setPassword(newPasswordResetForm.getPassword());
		UserForm userForm = new UserForm(user);
		this.userDao.dbUpdateUser(userForm);
	}

	@Override
	public void doDeleteToken(String token) {
		this.passwordResetDao.dbDeleteToken(token);
	}
}