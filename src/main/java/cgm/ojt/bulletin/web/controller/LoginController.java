package cgm.ojt.bulletin.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cgm.ojt.bulletin.bl.dto.UserDto;
import cgm.ojt.bulletin.bl.service.AuthenticationService;
import cgm.ojt.bulletin.bl.service.UserService;
import cgm.ojt.bulletin.web.form.LoginForm;
import cgm.ojt.bulletin.web.form.RegisterForm;
import cgm.ojt.bulletin.web.form.UserForm;

@Controller
public class LoginController {
	@Autowired
	private AuthenticationService authService;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private HttpSession session;

	@RequestMapping(value = { "/", "login" }, method = RequestMethod.GET)
	public ModelAndView init() {
		ModelAndView mv = new ModelAndView("loginUser");
		if (this.authService.doIsLoggedIn()) {
			mv.setViewName("redirect:/post/list");
		}
		LoginForm loginForm = new LoginForm();
		mv.addObject("loginForm", loginForm);
		return mv;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("loginForm") LoginForm loginForm,
			@RequestParam(value = "error", required = false) String error, ModelMap model, HttpServletRequest request,
			BindingResult result, RedirectAttributes redirectAttributes) {
		ModelAndView mv = new ModelAndView("loginUser");
		if (result.hasErrors()) {
			return mv;
		}
		if (loginForm.getEmail().length() <= 0 && loginForm.getPassword().length() <= 0) {
			mv.addObject("emptyPassword", "Password is required");
			mv.addObject("emptyEmail", "Email is required");
			return mv;
		} else if (loginForm.getEmail().length() <= 0) {
			mv.addObject("emptyEmail", "Email is required");
			return mv;
		} else if (loginForm.getPassword().length() <= 0) {
			mv.addObject("emptyPassword", "Password is required");
			return mv;
		}
		UserDto userDto = this.userService.doFindUserByEmail(loginForm.getEmail());
		if (userDto == null) {
			mv.addObject("errorMsg", "Email doesn't exit");
			return mv;
		}
		boolean passwordMatch = this.passwordEncoder.matches(loginForm.getPassword(), userDto.getPassword());
		if (!passwordMatch) {
			mv.addObject("errorMsg", "Email and password doesn't match!");
			return mv;
		}

		this.authService.doLoadAuth(loginForm.getEmail());
		if (this.authService.doIsLoggedIn()) {
			mv.setViewName("redirect:/post/list");
		}
		session.setAttribute("LOGIN_USER", this.authService.doGetLoggedInUser());
		redirectAttributes.addFlashAttribute("loginSuccessMsg", messageSource.getMessage("M_SC_USR_0002", null, null));
		return mv;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView getRegisterForm() {
		ModelAndView mv = new ModelAndView("registerUser");
		if (this.authService.doIsLoggedIn()) {
			mv.setViewName("redirect:/post/list");
			return mv;
		}
		RegisterForm registerForm = new RegisterForm();
		mv.addObject("registerUserForm", registerForm);
		return mv;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView registerUser(@ModelAttribute("registerUserForm") @Valid RegisterForm registerForm,
			BindingResult result) {
		ModelAndView mv = new ModelAndView("registerUser");
		if (result.hasErrors()) {
			return mv;
		}
		if (this.userService.doIsEmailExist(registerForm.getEmail())) {
			mv.addObject("errorEmailMsg", messageSource.getMessage("M_SC_USR_0001", null, null));
			return mv;
		}
		mv.setViewName("registerUserConfirm");
		mv.addObject("registerConfirmForm", registerForm);
		return mv;
	}

	@RequestMapping(value = "/register/confirm", method = RequestMethod.POST)
	public ModelAndView registerUserConfirm(@ModelAttribute("registerConfirmForm") UserForm registerConfrim,
			RedirectAttributes redirectAttribute) {
		ModelAndView mv = new ModelAndView("redirect:/post/list");
		registerConfrim.setPassword(passwordEncoder.encode(registerConfrim.getPassword()));
		registerConfrim.setType("0");
		this.userService.doSaveUser(registerConfrim);
		this.authService.doLoadAuth(registerConfrim.getEmail());
		session.setAttribute("LOGIN_USER", this.authService.doGetLoggedInUser());
		redirectAttribute.addFlashAttribute("loginSuccessMsg", messageSource.getMessage("M_SC_USR_0002", null, null));
		return mv;
	}

	@RequestMapping(value = "/register/confirm", params = "back", method = RequestMethod.POST)
	public ModelAndView backRegisterConfirm(@ModelAttribute("registerConfirmForm") RegisterForm registerForm) {
		ModelAndView backregisterForm = new ModelAndView("registerUser");
		backregisterForm.addObject("registerUserForm", registerForm);
		return backregisterForm;
	}

	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request) {
		authService.doLogout(request);
		session.removeAttribute("LOGIN_USER");
		return "redirect:/login";
	}
}