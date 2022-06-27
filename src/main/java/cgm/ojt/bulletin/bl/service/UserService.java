package cgm.ojt.bulletin.bl.service;

import java.util.List;

import cgm.ojt.bulletin.bl.dto.UserDto;
import cgm.ojt.bulletin.web.form.UserForm;

public interface UserService {
	public void doSaveUser(UserForm userForm);

	public long doGetUserCount();

	public UserDto doFindUserByEmail(String email);

	public boolean doIsEmailExist(String email);

	public List<UserDto> doGetAllUser();

	public void doDeleteUserById(int userId);

	public UserDto dbFindUserById(int userId);

	public void doUpdateUser(UserForm userForm);
}