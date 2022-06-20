package cgm.ojt.bulletin.bl.service;

import javax.servlet.http.HttpServletRequest;

import cgm.ojt.bulletin.bl.dto.UserDto;

public interface AuthenticationService {
	UserDto doGetLoggedInUser();

	boolean doIsLoggedIn();
	
	void doLoadAuth(String email);
	
	void doLogout(HttpServletRequest request);
}