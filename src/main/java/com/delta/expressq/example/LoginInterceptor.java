package com.delta.expressq.example;

import java.util.Map;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class LoginInterceptor implements Interceptor {
	private static final long serialVersionUID = 1L;
	public void destroy() {}
	public void init() {}

	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> sessionAttributes = invocation.getInvocationContext().getSession();
		
		//if no session exists redirect user to login otherwise let them continue
		if (sessionAttributes == null
				|| sessionAttributes.get("loginId") == null) {
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
