package com.delta.expressq.actions;

import com.delta.expressq.database.*;
import com.delta.expressq.util.*;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.interceptor.ServletRequestAware;


public class ChartGenerator extends ActionSupportWithSession implements ServletRequestAware {
	public HttpServletRequest request;
	public String year = "2017";
	public String groupby = "week"; //or month
	public String labels = "";
	public String data = "";
	public String chartTitle = "";
	public String datasetTitle = "";
	public String type = "";

	public String execute() {
		if (type.equals("ACSperMonth"))
			return getACSperMonth();
		else if(type.equals("ACSperWeek"))
			return getACSperWeek();
		else
			return "menu";
	}

	public String getACSperMonth() {
		try {
			Map<Integer, Integer> ACSs = ConnectionManager.getACS(
						getUserObject().getUserID(),
						ConnectionManager.perMonth, 2017);

			for (int i = 0; i < 12; i++) {
				labels += "\"" + getMonth(i) + "\",";
				if (ACSs.containsKey(i))
					data +=	Integer.toString(ACSs.get(i)) + ",";
				else
					data += "0,";
			}

			datasetTitle = "ACS";
			chartTitle = "Average customer spending per month";

			return SUCCESS;
		} catch (ConnectionManagerException e) {
			return "db_error";
		} catch (Exception e) {
			return "error";
		}
	}

	public String getACSperWeek() {
		try {
			Map<Integer, Integer> ACSs = ConnectionManager.getACS(
						getUserObject().getUserID(),
						ConnectionManager.perWeek, 2017);

			for (int i = 0; i < 53; i++) {
				if (i % 4 == 0)
					labels += "\"" + i + "\",";
				else
					labels += "\"\",";

				if (ACSs.containsKey(i))
					data +=	Integer.toString(ACSs.get(i)) + ",";
				else
					data += "0,";
			}

			datasetTitle = "ACS";
			chartTitle = "Average customer spending per week";

			return SUCCESS;
		} catch (ConnectionManagerException e) {
			return "db_error";
		} catch (Exception e) {
			return "error";
		}
	}

	public String getData() {
		return data;
	}

	public String getDatasetTitle() {
		return datasetTitle;
	}

	public String getChartTitle(){
		return chartTitle;
	}

	public String getLabels() {
		return labels;
	}

	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return this.request;
	}

	public void setType(String type) {
		this.type = type;
	}

	private String getMonth(int number) {
		switch (number + 1) {
			case 1:
				return "January";
			case 2:
			 	return "February";
			case 3:
			  	return "March";
			case 4:
				return "April";
			case 5:
				return "May";
			case 6:
				return "June";
			case 7:
				return "July";
			case 8:
				return "August";
			case 9:
				return "September";
			case 10:
				return "October";
			case 11:
				return "November";
			case 12:
				return "December";
			default:
				return "Invalid month";
		}
	}

	private int getNumberDaysInMonth(int year, int month) {
		return 1;
	}
}
