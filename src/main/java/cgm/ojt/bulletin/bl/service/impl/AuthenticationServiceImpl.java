package cgm.ojt.bulletin.bl.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import cgm.ojt.bulletin.bl.dto.UserDto;
import cgm.ojt.bulletin.bl.service.AuthenticationService;
import cgm.ojt.bulletin.bl.service.UserService;

@Service("authService")
public class AuthenticationServiceImpl implements AuthenticationService {
	/**
	 * <h2>userService</h2>
	 * <p>
	 * userService
	 * </p>
	 */
	@Autowired
	private UserService userService;
	/**
	 * <h2>userDetailsService</h2>
	 * <p>
	 * userDetailsService
	 * </p>
	 */
	@Autowired
	@Qualifier("myUserDetailsService")
	private UserDetailsService userDetailsService;

	/**
	 * <h2>doGetLoggedInUser</h2>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @return
	 */
	public UserDto doGetLoggedInUser() {
		String user_email = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			user_email = ((UserDetails) principal).getUsername();
		} else {
			user_email = principal.toString();
		}

		return userService.doFindUserByEmail(user_email);
	}

	/**
	 * <h2>doIsLoggedIn</h2>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @return
	 */
	public boolean doIsLoggedIn() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		return authentication != null && !(authentication instanceof AnonymousAuthenticationToken)
		        && authentication.isAuthenticated();
	}

	/**
	 * <h2>doLoadAuth</h2>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param email
	 */
	public void doLoadAuth(String email) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(email);
		Authentication auth = new UsernamePasswordAuthenticationToken(userDetails.getUsername(),
		        userDetails.getPassword(), userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(auth);
	}

	/**
	 * <h2>doLogout</h2>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param request
	 */
	public void doLogout(HttpServletRequest request) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null) {
			SecurityContextHolder.getContext().setAuthentication(null);
		}
		request.getSession(true).invalidate();
	}
}