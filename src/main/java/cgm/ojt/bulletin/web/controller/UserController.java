package cgm.ojt.bulletin.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	

	@RequestMapping(value = "/user/list", method = RequestMethod.GET)
	public String userList() {
		return "listUser";
	}

	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public ModelAndView denied() {
		ModelAndView createStudentView = new ModelAndView("accessDenied");
		return createStudentView;
	}

	
}

//@RequestMapping(value = "/login", method = RequestMethod.GET)
//public ModelAndView login(@ModelAttribute("loginForm") LoginForm loginForm, @RequestParam(value = "error", required = false) String error, ModelMap model,
//		HttpServletRequest request, BindingResult result) {
//	ModelAndView mv = new ModelAndView("loginUser");
////	if (loginForm.getEmail() == null && loginForm.getPassword() == null) {
////		mv.addObject("emptyPassword", "Password is required");
////		mv.addObject("emptyEmail", "Email is required");
////		return mv;
////	}else if (loginForm.getEmail() == null) {
////		mv.addObject("emptyEmail", "Email is required");
////		return mv;
////	} else if (loginForm.getPassword() == null) {
////		mv.addObject("emptyPassword", "Password is required");
////		return mv;
////	}
//	if (this.authService.doIsLoggedIn()) {
////		mv.setViewName("listPost");
//	}
//	if (error != null) {
//		model.addAttribute("errorMsg", getErrorMessage(request, "SPRING_SECURITY_LAST_EXCEPTION"));
//	}
//	return mv;
//}