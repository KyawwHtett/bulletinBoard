package cgm.ojt.bulletin.web.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cgm.ojt.bulletin.bl.dto.UserDto;
import cgm.ojt.bulletin.bl.service.AuthenticationService;
import cgm.ojt.bulletin.bl.service.UserService;
import cgm.ojt.bulletin.web.form.RegisterForm;
import cgm.ojt.bulletin.web.form.UserForm;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private AuthenticationService authService;

	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public ModelAndView userList() {
		ModelAndView mv = new ModelAndView("listUser");
		List<UserDto> userList = this.userService.doGetAllUser();
		mv.addObject("userList", userList);
		return mv;
	}

	@RequestMapping(value = "/user/create", method = RequestMethod.GET)
	public ModelAndView createUser() {
		ModelAndView createUser = new ModelAndView("createUser");
		UserForm userForm = new UserForm();
		createUser.addObject("userForm", userForm);
		return createUser;
	}

	@RequestMapping(value = "/user/createConfirm", method = RequestMethod.POST)
	public ModelAndView createUserConfirm(@Valid @ModelAttribute("userForm") RegisterForm userForm,
			BindingResult result) {
		ModelAndView createUserConfirm = new ModelAndView("createUser");
		if (result.hasErrors()) {
			return createUserConfirm;
		}
		if (this.userService.doIsEmailExist(userForm.getEmail())) {
			createUserConfirm.addObject("errorEmailMsg", messageSource.getMessage("M_SC_USR_0001", null, null));
			return createUserConfirm;
		}
		createUserConfirm.setViewName("createUserConfirm");
		createUserConfirm.addObject("userForm", userForm);
		return createUserConfirm;
	}

	@RequestMapping(value = "/user/createConfirm", params = "back", method = RequestMethod.POST)
	public ModelAndView backCreateUser() {
		return new ModelAndView("redirect:/user/list");
	}

	@RequestMapping(value = "/user/save", method = RequestMethod.POST)
	public ModelAndView saveUser(@ModelAttribute("userForm") UserForm userForm) {
		ModelAndView userList = new ModelAndView("redirect:/user/list");
		this.userService.doSaveUser(userForm);
		return userList;
	}

	@RequestMapping(value = "/user/save", params = "back", method = RequestMethod.POST)
	public ModelAndView backSaveUser(@ModelAttribute("userForm") UserForm userForm) {
		ModelAndView userList = new ModelAndView("createUser");
		userList.addObject("userForm", userForm);
		return userList;
	}

	@RequestMapping(value = "/user/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edituser(@PathVariable("id") int userId) {
		ModelAndView editUser = new ModelAndView("editUser");
		UserDto editUserForm = this.userService.dbFindUserById(userId);
		editUser.addObject("userForm", editUserForm);
		return editUser;
	}

	@RequestMapping(value = "/user/editConfirm", params = "confirm", method = RequestMethod.POST)
	public ModelAndView editUserConfirm(@Valid @ModelAttribute("userForm") UserForm userForm, BindingResult resutlt) {
		ModelAndView editUserConfirm = new ModelAndView("editUser");
		UserDto oldUserForm = this.userService.dbFindUserById(userForm.getId());
		if (resutlt.hasErrors()) {
			return editUserConfirm;
		} else if (!oldUserForm.getEmail().equals(userForm.getEmail())
				&& this.userService.doIsEmailExist(userForm.getEmail())) {
			editUserConfirm.addObject("errorEmailMsg", messageSource.getMessage("M_SC_USR_0001", null, null));
			return editUserConfirm;
		}
		editUserConfirm.addObject("userForm", userForm);
		editUserConfirm.setViewName("editUserConfirm");
		return editUserConfirm;
	}

	@RequestMapping(value = "/user/editConfirm", params = "back", method = RequestMethod.POST)
	public ModelAndView backEditUserConfirm() {
		return new ModelAndView("redirect:/user/list");
	}

	@RequestMapping(value = "/user/update", params = "update", method = RequestMethod.POST)
	public ModelAndView updateUser(@ModelAttribute("userForm") UserForm userForm) {
		ModelAndView listUser = new ModelAndView("redirect:/user/list");
		this.userService.doUpdateUser(userForm);
		return listUser;
	}

	@RequestMapping(value = "/user/update", params = "back", method = RequestMethod.POST)
	public ModelAndView backEditUser(@ModelAttribute("userForm") UserForm userform) {
		ModelAndView editUser = new ModelAndView("editUser");
		editUser.addObject("userForm", userform);
		return editUser;
	}

	@RequestMapping(value = "/user/profile/{id}", method = RequestMethod.GET)
	public ModelAndView editProfile(@PathVariable("id") int userId) {
		ModelAndView editUser = new ModelAndView("editProfile");
		UserDto editUserForm = this.userService.dbFindUserById(userId);
		editUser.addObject("userForm", editUserForm);
		return editUser;
	}

	@RequestMapping(value = "/user/profileConfirm", params = "confirm", method = RequestMethod.POST)
	public ModelAndView editProfileConfirm(@Valid @ModelAttribute("userForm") UserForm userForm,
			BindingResult resutlt) {
		ModelAndView editUserConfirm = new ModelAndView("editProfile");
		UserDto oldUserForm = this.userService.dbFindUserById(userForm.getId());
		if (resutlt.hasErrors()) {
			return editUserConfirm;
		} else if (!oldUserForm.getEmail().equals(userForm.getEmail())
				&& this.userService.doIsEmailExist(userForm.getEmail())) {
			editUserConfirm.addObject("errorEmailMsg", messageSource.getMessage("M_SC_USR_0001", null, null));
			return editUserConfirm;
		}
		editUserConfirm.addObject("userForm", userForm);
		editUserConfirm.setViewName("editProfileConfirm");
		return editUserConfirm;
	}

	@RequestMapping(value = "/user/profileConfirm", params = "back", method = RequestMethod.POST)
	public ModelAndView backEditProfileConfirm() {
		UserDto userProfile = this.authService.doGetLoggedInUser();
		if (Integer.parseInt(userProfile.getType()) == 1) {
			return new ModelAndView("redirect:/user/list");
		}
		return new ModelAndView("redirect:/post/list");
	}

	@RequestMapping(value = "/user/profileUpdate", params = "update", method = RequestMethod.POST)
	public ModelAndView updateProfile(@ModelAttribute("userForm") UserForm userForm, HttpSession session) {
		ModelAndView listUser = new ModelAndView("redirect:/user/list");
		this.userService.doUpdateUser(userForm);
		this.authService.doLoadAuth(userForm.getEmail());
		UserDto userProfile = this.authService.doGetLoggedInUser();
		session.setAttribute("LOGIN_USER", authService.doGetLoggedInUser());
		if (Integer.parseInt(userProfile.getType()) == 1) {
			return listUser;
		}
		return new ModelAndView("redirect:/post/list");
	}

	@RequestMapping(value = "/user/profileUpdate", params = "back", method = RequestMethod.POST)
	public ModelAndView backEditProfile(@ModelAttribute("userForm") UserForm userform) {
		ModelAndView editUser = new ModelAndView("editProfile");
		editUser.addObject("userForm", userform);
		return editUser;
	}

	@RequestMapping(value = "/user/delete/{id}", method = RequestMethod.GET)
	public ModelAndView deleteUser(@PathVariable("id") int userId) {
		ModelAndView deleteUser = new ModelAndView("redirect:/user/list");
		this.userService.doDeleteUserById(userId);
		return deleteUser;
	}

	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public ModelAndView denied() {
		ModelAndView createStudentView = new ModelAndView("accessDenied");
		return createStudentView;
	}
}