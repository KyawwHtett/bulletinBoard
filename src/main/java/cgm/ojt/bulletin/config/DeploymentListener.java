package cgm.ojt.bulletin.config;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import cgm.ojt.bulletin.bl.service.UserService;
import cgm.ojt.bulletin.web.form.UserForm;

@Component
public class DeploymentListener {
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@PostConstruct
	public void addInitialUser() {
		if (this.userService.doGetUserCount() <= 0) {
			UserForm userForm = new UserForm();
			userForm.setUsername("USER");
			userForm.setEmail("cgm.kyawhtet@gmail.com");
			userForm.setPassword(passwordEncoder.encode("123456"));
			userForm.setGender("Male");
			userForm.setType("0");
			userForm.setCreated_at(new Date());
			this.userService.doSaveUser(userForm);

			UserForm adminForm = new UserForm();
			adminForm.setUsername("ADMIN");
			adminForm.setEmail("kyawhtet@cgm-myanmar.com");
			adminForm.setPassword(passwordEncoder.encode("123456"));
			adminForm.setGender("Female");
			adminForm.setType("1");
			adminForm.setCreated_at(new Date());
			this.userService.doSaveUser(adminForm);
		}
	}
}