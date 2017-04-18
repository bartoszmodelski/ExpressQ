package com.delta.expressq.actions;

import com.delta.expressq.database.*;
import com.delta.expressq.util.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;
import java.time.LocalDate;
import java.sql.Date;


public class Analytics extends ActionSupportWithSession {
	public String execute() {
		computeItemPopularity(32);

		return "menu";
	}

	private String computeItemPopularity(int itemID) {
		try {
			LocalDate start = LocalDate.parse("2016-12-20");
			LocalDate end = LocalDate.parse("2017-06-20");

			double popularity = ConnectionManager.getItemPopularity(422, 32,
			java.sql.Date.valueOf(start),
			java.sql.Date.valueOf(end));
			//System.out.println(popularity);
			return SUCCESS;
		} catch (ConnectionManagerException e) {
			System.out.println(e.getMessage());
			return "db_error";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "error";
		}
	}
}
