package cgm.ojt.bulletin.persistence.dao;

import java.util.List;

import cgm.ojt.bulletin.bl.dto.UserDto;
import cgm.ojt.bulletin.persistence.entity.User;
import cgm.ojt.bulletin.web.form.UserForm;

public interface UserDao {

	public void dbSaveUser(User user);

	public long dbGetUserCount();

	public User dbFindUserByEmail(String email);

	public List<UserDto> dbGetAllUser();

	public void dbDeleteUserById(int userId);

	public User dbFindUserById(int userId);

	public User dbFindUserByAllEmail(String email);

	public void dbUpdateUser(UserForm userForm);

	public List<User> dbGetAllUsers();
}