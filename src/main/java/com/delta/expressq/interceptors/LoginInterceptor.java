package com.delta.expressq.interceptors;

import java.util.Map;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LoginInterceptor implements Interceptor {
	private static final long serialVersionUID = 1L;
	public void destroy() {}
	public void init() {}

	/**
	 * If no session currently exists redirect the user to the login page. If there is a session allow the action request to carry on as intended. 
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> sessionAttributes = invocation.getInvocationContext().getSession();
		if (sessionAttributes == null || sessionAttributes.get("loginId") == null) {
			return "login";
		} else {
			if (!((String) sessionAttributes.get("loginId")).equals(null)) {
				return invocation.invoke();
			} else {
				return "login";
			}
		}
	}
}
