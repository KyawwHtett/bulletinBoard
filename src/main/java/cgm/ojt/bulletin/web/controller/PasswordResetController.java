package cgm.ojt.bulletin.web.controller;

import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import cgm.ojt.bulletin.bl.service.PasswordResetService;
import cgm.ojt.bulletin.bl.service.UserService;
import cgm.ojt.bulletin.web.form.PasswordResetForm;
import cgm.ojt.bulletin.web.form.PasswordResetSentMailForm;

@Controller
public class PasswordResetController {
	@Autowired
	private UserService userService;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private PasswordResetService passwordResetService;

	@RequestMapping(value = "/password/reset", method = RequestMethod.GET)
	public String passwordResetSendMailj(ModelMap modelMap) {
		PasswordResetSentMailForm passwordResetSentMailForm = new PasswordResetSentMailForm();
		modelMap.addAttribute("passwordResetSentMailForm", passwordResetSentMailForm);
		return "passwordResetSendMail";
	}

	@RequestMapping(value = "/password/reset/sendMail", method = RequestMethod.POST)
	public ModelAndView passwordResetSendMail(
			@Valid @ModelAttribute("passwordResetSentMailForm") PasswordResetSentMailForm passwordResetSentMailForm,
			BindingResult result, HttpServletRequest request) {
		ModelAndView model = new ModelAndView("passwordResetSendMail");
		if (result.hasErrors()) {
			return model;
		}
		if (!userService.doIsEmailExist(passwordResetSentMailForm.getUser_email())) {
			model.addObject("errorMsg", messageSource.getMessage("M_SC_USR_0003", null, null));
			return model;
		}
		passwordResetSentMailForm = this.passwordResetService
				.createResetToken(passwordResetSentMailForm.getUser_email());
		String url = getBaseUrl(request) + request.getServletPath() + "/" + passwordResetSentMailForm.getToken();
		this.sendMail(url, passwordResetSentMailForm);
		ModelAndView newModel = new ModelAndView("sendMailSuccess");
		newModel.addObject("msg", messageSource.getMessage("M_SC_USR_0004", null, null));
		return newModel;
	}

	@RequestMapping(value = "/password/reset/sendMail/{token}", method = RequestMethod.GET)
	public ModelAndView showResetPassword(@PathVariable String token) {
		ModelAndView mv = new ModelAndView("invalidMail");
		PasswordResetSentMailForm passwordResetForm = passwordResetService.getDataByToken(token);
		if (passwordResetForm == null) {
			mv.addObject("errorMsg", messageSource.getMessage("M_SC_USR_0005", null, null));
			return mv;
		}
		if (isTokenExpired(passwordResetForm.getExpired_at())) {
			mv.addObject("errorMsg", messageSource.getMessage("M_SC_USR_0006", null, null));
			return mv;
		}
		PasswordResetForm passwordChangeResetForm = new PasswordResetForm();
		passwordChangeResetForm.setToken(token);
		mv.setViewName("passwordResetForm");
		mv.addObject("passwordResetForm", passwordChangeResetForm);
		return mv;
	}

	@RequestMapping(value = "password/changeReset", method = RequestMethod.POST)
	public ModelAndView resetPassword(@Valid @ModelAttribute("passwordResetForm") PasswordResetForm passwordResetForm,
			BindingResult result) {
		if (result.hasErrors()) {
			return new ModelAndView("passwordResetForm");
		}
		String userEmail = passwordResetService.getDataByToken(passwordResetForm.getToken()).getUser_email();
		PasswordResetSentMailForm newPasswordResetForm = new PasswordResetSentMailForm();
		newPasswordResetForm.setUser_email(userEmail);
		newPasswordResetForm.setPassword(passwordResetForm.getPassword());
		this.passwordResetService.doUpdatePassword(newPasswordResetForm);
		this.passwordResetService.doDeleteToken(passwordResetForm.getToken());
		ModelAndView mv = new ModelAndView("sendMailSuccess");
		mv.addObject("msg", messageSource.getMessage("M_SC_USR_0007", null, userEmail, null));
		return mv;
	}

	private String getBaseUrl(HttpServletRequest request) {
		String url = request.getScheme() + "://" + request.getServerName();
		if (request.getServerPort() != 0) {
			url = url + ":" + request.getServerPort();
		}
		url = url + request.getContextPath();
		return url;
	}

	private void sendMail(String url, @Valid PasswordResetSentMailForm passwordResetForm) {
		String sender = "cgm.kyawhtet@gmail.com";
		String subject = "Reset Your Password";
		String body = "Reset your password from following url : \n";
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(passwordResetForm.getUser_email());
		email.setFrom(sender);
		email.setSubject(subject);
		email.setText(body + url);
		mailSender.send(email);
	}

	private boolean isTokenExpired(Timestamp expired_at) {
		Timestamp now = new Timestamp(new Date().getTime());
		return now.after(expired_at);
	}
}