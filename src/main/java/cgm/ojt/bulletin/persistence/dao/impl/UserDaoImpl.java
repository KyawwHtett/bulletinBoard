package cgm.ojt.bulletin.persistence.dao.impl;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cgm.ojt.bulletin.persistence.dao.UserDao;
import cgm.ojt.bulletin.persistence.entity.User;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	@Autowired
	SessionFactory sessionFactory;
	
	@Override
	public void dbSaveUser(User user) {
		this.sessionFactory.getCurrentSession().save(user);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public long dbGetUserCount() {
		Query query = this.sessionFactory.getCurrentSession().createQuery("SELECT COUNT(u) FROM User u");
		return (long) query.getSingleResult();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public User dbFindUserByEmail(String email) {
		String userQuery = "SELECT u FROM User u where u.email = :email";
		Query query = this.sessionFactory.getCurrentSession().createQuery(userQuery);
		query.setParameter("email", email);
		User user = (User) query.uniqueResult();
		return user;
	}
}