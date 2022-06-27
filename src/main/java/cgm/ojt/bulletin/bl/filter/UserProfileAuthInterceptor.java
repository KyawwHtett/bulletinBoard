package cgm.ojt.bulletin.bl.filter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import cgm.ojt.bulletin.bl.dto.PostDto;
import cgm.ojt.bulletin.bl.dto.UserDto;
import cgm.ojt.bulletin.bl.service.AuthenticationService;
import cgm.ojt.bulletin.bl.service.PostService;
import cgm.ojt.bulletin.bl.service.UserService;

public class UserProfileAuthInterceptor implements HandlerInterceptor {
	@Autowired
	private AuthenticationService authservice;

	@Autowired
	private UserService userService;

	@SuppressWarnings("unchecked")
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		Map<String, String> path = (Map<String, String>) request
				.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		int id = Integer.parseInt(path.get("id"));
		UserDto oldUser = this.userService.dbFindUserById(id);
		UserDto loginUser = this.authservice.doGetLoggedInUser();
		if (oldUser.getId() != loginUser.getId()) {
			response.sendRedirect(request.getContextPath() + "/denied");
		}
		return true;
	}
}