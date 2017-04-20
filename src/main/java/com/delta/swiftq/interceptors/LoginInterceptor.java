package com.delta.swiftq.interceptors;

import java.util.Map;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.delta.swiftq.util.UserNew;

public class LoginInterceptor implements Interceptor {
	private static final long serialVersionUID = 1L;
	public void destroy() {}
	public void init() {}

	/**
	 * If no session currently exists redirect the user to the login page. If there is a session allow the action request to carry on as intended.
	 */
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> sessionAttributes = invocation.getInvocationContext().getSession();
		if (sessionAttributes == null || sessionAttributes.get("user") == null) {
			return "login";
		} else {
			if ((UserNew) sessionAttributes.get("user") != null) {
				return invocation.invoke();
			} else {
				return "login";
			}
		}
	}
}
