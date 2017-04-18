package com.delta.expressq.actions;

import com.delta.expressq.database.*;
import com.delta.expressq.util.*;
import java.util.*;
import java.time.LocalDate;
import java.sql.Date;
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
	public String currency = "";
	public String chartType = "bar";
	public String canvasId = "";
	public String fromDate = "";
	public String toDate = "";
	public String pId = "";
	public String itemID = "";

	public String execute() {
		if (type.equals("ACSperMonth"))
			return getACSperMonth();
		else if(type.equals("ACSperWeek"))
			return getACSperWeek();
		else if (type.equals("ItemSale"))
			return getItemSale();
		else if (type.equals("MostCommonlyTogether"))
			return getMostCommonlyPurchasedTogether();
		else
			return ERROR;
	}

	public String getMostCommonlyPurchasedTogether() {
		try {
			LocalDate start = LocalDate.parse(fromDate);
			LocalDate end = LocalDate.parse(toDate);
			Map<String, Integer> pairAndShare = ConnectionManager.getMostCommonlyPurchasedTogether(
				getUserObject().getUserID(),
				java.sql.Date.valueOf(start),
				java.sql.Date.valueOf(end));


			for (Map.Entry<String, Integer> entry: pairAndShare.entrySet()) {
				labels += "\"" + entry.getKey() + "\",";
				data +=	Integer.toString(entry.getValue()) + ",";
			}

			chartType = "doughnut";
			datasetTitle = "Pair sale";
			chartTitle = "Items most commonly purchased together";
			return "doughnut_chart";
		} catch (ConnectionManagerException e) {
			System.out.println(e);
			return "db_error";
		} catch (Exception e) {
			System.out.println(e);
			return "error";
		}
	}

	public String getACSperMonth() {
		try {
			Map<Integer, Integer> ACSs = ConnectionManager.getACS(
						getUserObject().getUserID(),
						ConnectionManager.perMonth, Integer.parseInt(year));

			for (int i = 0; i < 12; i++) {
				labels += "\"" + getMonth(i) + "\",";
				if (ACSs.containsKey(i))
					data +=	Integer.toString(ACSs.get(i)) + ",";
				else
					data += "0,";
			}

			currency = "\u00a3";
			datasetTitle = "ACS";
			chartType = "bar";
			chartTitle = "Average customer spending per month";

			return "barchart";
		} catch (ConnectionManagerException e) {
			return "db_error";
		} catch (Exception e) {
			return "error";
		}
	}

	public String getItemSale() {
		try {
			LocalDate start = LocalDate.parse(fromDate);
			LocalDate end = LocalDate.parse(toDate);
			Map<java.sql.Date, Integer> salePerDay = ConnectionManager.getItemSale(
						getUserObject().getUserID(), Integer.parseInt(itemID),
						java.sql.Date.valueOf(start),
						java.sql.Date.valueOf(end));

			for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
				labels += "\"" + date + "\",";

				if (salePerDay.containsKey(java.sql.Date.valueOf(date))) {
					data += Integer.toString(salePerDay.get(java.sql.Date.valueOf(date))) + ",";
				} else {
					data += "0,";
				}
			}

			datasetTitle = "Sale";
			chartType = "bar";
			chartTitle = "Daily sale of item x";

			return "barchart";
		} catch (ConnectionManagerException e) {
			System.out.println(e.getMessage());
			return "db_error";
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "error";
		}
	}

	public String getACSperWeek() {
		try {
			Map<Integer, Integer> ACSs = ConnectionManager.getACS(
						getUserObject().getUserID(),
						ConnectionManager.perWeek, Integer.parseInt(year));

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
			chartType = "bar";
			chartTitle = "Average customer spending per week";

			return "barchart";
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

	public String getCurrency() {
		return currency;
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

	public void setCanvasId(String canvasId) {
		this.canvasId = canvasId;
	}

	public String getCanvasId() {
		return canvasId;
	}

	public void setPId(String pId) {
		this.pId = pId;
	}

	public String getPId() {
		return pId;
	}

	public void setItemID(String itemID) {
		this.itemID = itemID;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
}
