package cgm.ojt.bulletin.persistence.dao.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cgm.ojt.bulletin.bl.dto.UserDto;
import cgm.ojt.bulletin.persistence.dao.UserDao;
import cgm.ojt.bulletin.persistence.entity.User;
import cgm.ojt.bulletin.web.form.UserForm;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
	private static final String SELECT_USER_LIST_HQL = "SELECT u FROM User u WHERE u.deleted_at = null";

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
		String userQuery = "SELECT u FROM User u WHERE u.email = :email AND deleted_at = null ";
		Query query = this.sessionFactory.getCurrentSession().createQuery(userQuery);
		query.setParameter("email", email);
		User user = (User) query.uniqueResult();
		return user;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public User dbFindUserByAllEmail(String email) {
		String userQuery = "SELECT u FROM User u WHERE u.email = :email";
		Query query = this.sessionFactory.getCurrentSession().createQuery(userQuery);
		query.setParameter("email", email);
		User user = (User) query.uniqueResult();
		return user;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<UserDto> dbGetAllUser() {
		StringBuffer query = new StringBuffer(SELECT_USER_LIST_HQL);
		Query queryUserList = this.sessionFactory.getCurrentSession().createQuery(query.toString());
		List<UserDto> userList = (List<UserDto>) queryUserList.getResultList();
		return userList;
	}

	@Override
	public void dbDeleteUserById(int userId) {
		User user = this.sessionFactory.getCurrentSession().load(User.class, userId);
		user.setDeleted_at(new Date());
		this.sessionFactory.getCurrentSession().update(user);
	}

	@Override
	public User dbFindUserById(int userId) {
		User user = this.sessionFactory.getCurrentSession().load(User.class, userId);
		return user;
	}

	@Override
	public void dbUpdateUser(UserForm userForm) {
		User user = this.sessionFactory.getCurrentSession().load(User.class, userForm.getId());
		if (user != null) {
			user.setUsername(userForm.getUsername());
			user.setEmail(userForm.getEmail());
			user.setGender(userForm.getGender());
			user.setType(userForm.getType());
			user.setUpdated_at(new Date());
			this.sessionFactory.getCurrentSession().update(user);
		}
	}
}