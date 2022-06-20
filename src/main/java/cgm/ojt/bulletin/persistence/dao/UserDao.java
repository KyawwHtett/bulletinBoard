package cgm.ojt.bulletin.persistence.dao;

import cgm.ojt.bulletin.persistence.entity.User;

public interface UserDao {

	public void dbSaveUser(User user);

	public long dbGetUserCount();

	public User dbFindUserByEmail(String email);
}