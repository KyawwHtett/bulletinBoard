package cgm.ojt.bulletin.bl.service.impl;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cgm.ojt.bulletin.bl.dto.UserDto;
import cgm.ojt.bulletin.bl.service.UserService;
import cgm.ojt.bulletin.persistence.dao.UserDao;
import cgm.ojt.bulletin.persistence.entity.User;
import cgm.ojt.bulletin.web.form.UserForm;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;

	@Override
	public void doSaveUser(UserForm userForm) {
		User user = new User(userForm);
		user.setCreated_at(new Date());
		this.userDao.dbSaveUser(user);
	}

	@Override
	public long doGetUserCount() {
		return this.userDao.dbGetUserCount();
	}

	@Override
	public UserDto doFindUserByEmail(String email) {
		User user = this.userDao.dbFindUserByEmail(email);
		if (user == null) {
			return null;
		}
		UserDto userDto = new UserDto(user);
		return userDto;
	}

	@Override
	public boolean doIsEmailExist(String email) {
		boolean result = false;
		User user = this.userDao.dbFindUserByAllEmail(email);
		if (user != null) {
			result = true;
		}
		return result;
	}

	@Override
	public List<UserDto> doGetAllUser() {
		return this.userDao.dbGetAllUser();
	}

	@Override
	public void doDeleteUserById(int userId) {
		this.userDao.dbDeleteUserById(userId);
	}

	@Override
	public UserDto dbFindUserById(int userId) {
		User user = this.userDao.dbFindUserById(userId);
		if (user == null) {
			return null;
		}
		UserDto userDto = new UserDto(user);
		return userDto;
	}

	@Override
	public void doUpdateUser(UserForm userForm) {
		this.userDao.dbUpdateUser(userForm);
	}
}