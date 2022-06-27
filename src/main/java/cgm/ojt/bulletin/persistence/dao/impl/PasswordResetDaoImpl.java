package cgm.ojt.bulletin.persistence.dao.impl;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cgm.ojt.bulletin.persistence.dao.PasswordResetDao;
import cgm.ojt.bulletin.persistence.entity.PasswordReset;

@Repository
@Transactional
public class PasswordResetDaoImpl implements PasswordResetDao {

	@Autowired
	private SessionFactory sessionFactory;

	@SuppressWarnings("rawtypes")
	@Override
	public PasswordReset getTokenDataByEmail(String user_email) {
		String userHqlQuery = "SELECT pw FROM PasswordReset pw WHERE pw.user_email = :email";
		Query queryUserByEmail = this.sessionFactory.getCurrentSession().createQuery(userHqlQuery);
		queryUserByEmail.setParameter("email", user_email);
		PasswordReset passwordReset = (PasswordReset) queryUserByEmail.uniqueResult();
		return passwordReset;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void deleteTokenByEmail(String user_email) {
		String userHqlQuery = "DELETE FROM PasswordReset pw WHERE pw.user_email = :email";
		Query queryUserByEmail = this.sessionFactory.getCurrentSession().createQuery(userHqlQuery);
		queryUserByEmail.setParameter("email", user_email);
		queryUserByEmail.executeUpdate();
	}

	@Override
	public void createToken(PasswordReset pswToken) {
		this.sessionFactory.getCurrentSession().save(pswToken);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public PasswordReset dbGetDataByToken(String token) {
		String pswHqlQuery = "SELECT pw FROM  PasswordReset pw WHERE pw.token = :token";
		Query queryDataByToken = this.sessionFactory.getCurrentSession().createQuery(pswHqlQuery);
		queryDataByToken.setParameter("token", token);
		PasswordReset passwordReset = (PasswordReset) queryDataByToken.uniqueResult();
		return passwordReset;
	}

	@Override
	public void dbDeleteToken(String token) {
		PasswordReset passwordReset = this.sessionFactory.getCurrentSession().load(PasswordReset.class, token);
		this.sessionFactory.getCurrentSession().delete(passwordReset);
	}
}