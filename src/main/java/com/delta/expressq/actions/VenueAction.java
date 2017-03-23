package com.delta.expressq.actions;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.ServletRequestAware;

import com.delta.expressq.database.ConnectionManager;
import com.delta.expressq.database.ConnectionManagerException;
import com.delta.expressq.util.UserNew;

public class VenueAction extends ActionSupportWithSession implements ServletRequestAware {
	public Map<String, Integer> sections = new HashMap<String, Integer>();
	public Map<String, Integer> items = new HashMap<String, Integer>();
	public Map<String, String> itemDetails = new HashMap<String, String>();
	public HttpServletRequest request;
	private String description, sectionDeleteSelection, arraySectionDeleteSelection[], arrayItemDeleteSelection[], itemname, itemdescription, allergens;
	public String selectedSectionID, Name, NewName, sectionID, selectedItemID;
	private int preparationtime, stock, price, itemID;
	
	public String execute(){
		if (isLoggedIn()){
			UserNew user = getUserObject();
			if (user.getType() == 2){
				try{
					ConnectionManager.setSections(sections, ConnectionManager.getVenueID(user.getUserID()));
				}catch (ConnectionManagerException e) {
					e.printStackTrace();
				}
				return SUCCESS;
			}else{
				return "permission_error";
			}
		}else{
			return "permission_error";
		}
	}
	
	public String NewSection(){
		return SUCCESS;
	}
	
	public String InsertSection(){
		if (isLoggedIn()){
			UserNew user = getUserObject();
			if (user.getType() == 2){
				try {
					ConnectionManager.InsertSection(ConnectionManager.getVenueID(user.getUserID()), description);
				} catch (ConnectionManagerException e) {
					e.printStackTrace();
				}
				return SUCCESS;
			}else{
				return "permission_error";
			}
		}else{
			return "permission_error";
		}
	}
	
	public String DeleteSection(){
		if (isLoggedIn()){
			UserNew user = getUserObject();
			if (user.getType() == 2){
				arraySectionDeleteSelection = request.getParameterValues("sectionDeleteSelection");//get values from jsp to pass into connection manager
				if (arraySectionDeleteSelection == null){
					System.out.println("array is null");
					return ERROR;//change to message perhaps?
				}else{
					try {
						ConnectionManager.DeleteSections(arraySectionDeleteSelection);
					} catch (ConnectionManagerException e) {
						return "db_error";
					}
				}
				return SUCCESS;
			}else{
				return "permission_error";
			}
		}else{
			return "permission_error";
		}
	}
	
	public String EditSection(){
		if(isLoggedIn()){
			UserNew user = getUserObject();
			if (user.getType() == 2){
				selectedSectionID = request.getParameter("selectedSectionID");
				try {
					Name = ConnectionManager.EditSection(selectedSectionID, Name);
					return SUCCESS;
				} catch (ConnectionManagerException e) {
					return "db_error";
				}
			}else {
				return "permission_error";
			}
		}else
			return "permission_error";
	}
	
	public String UpdateSection(){
		if(isLoggedIn()){
			UserNew user = getUserObject();
			if (user.getType() == 2){
				try {
					ConnectionManager.UpdateSection(NewName, sectionID);
				} catch (ConnectionManagerException e) {
					System.out.println(e.getMessage());
					return "db_error";
				}
				return SUCCESS;
			}else{
				return "permission_error";
			}
		}else{
			return "permission_error";
		}
	}
	
	public String DisplayItems(){
		if(isLoggedIn()){
			UserNew user = getUserObject();
			if (user.getType() == 2){
				selectedSectionID = request.getParameter("selectedSectionID");
				try {
					ConnectionManager.getItemsBySection(items, selectedSectionID);
				} catch (ConnectionManagerException e) {
					e.printStackTrace();
				}
				return SUCCESS;
			}else{
				return "permission_error";
			}
		}else{
			return "permission_error";
		}
	}
	
	public String NewItem(){
		if (isLoggedIn()){
			UserNew user = getUserObject();
			if (user.getType() == 2){
				return SUCCESS;
			}else{
				return "permission_error";				
			}
		}else{
			return "permission_error";
		}
	}
	
	public String InsertItem(){
		if (isLoggedIn()){
			UserNew user = getUserObject();
			if (user.getType() == 2){
				try {
					ConnectionManager.InsertItem(sectionID, price, itemname, itemdescription, stock, allergens, preparationtime);
				} catch (ConnectionManagerException e) {
					e.printStackTrace();
				}
				return SUCCESS;
			}else{
				return "permission_error";
			}
		}else{
			return "permission_error";
		}
	}
	
	public String DeleteItem(){
		if (isLoggedIn()){
			UserNew user = getUserObject();
			if (user.getType() == 2){
				arrayItemDeleteSelection = request.getParameterValues("itemDeleteSelection");//get values from jsp to pass into connection manager
				if (arrayItemDeleteSelection == null){
					return ERROR;//change to message perhaps?
				}else{
					try {
						ConnectionManager.DeleteItems(arrayItemDeleteSelection);
					} catch (ConnectionManagerException e) {
						return "db_error";
					}
				}
				return SUCCESS;
			}else{
				return "permission_error";
			}
		}else{
			return "permission_error";
		}
	}
	
	public String EditItem(){
		if(isLoggedIn()){
			UserNew user = getUserObject();
			if (user.getType() == 2){
				selectedItemID = request.getParameter("selectedItemID");
				try {
					ConnectionManager.EditItem(selectedItemID, itemDetails);
					request.setAttribute("itemDetails", itemDetails);
					System.out.println(itemDetails);
					return SUCCESS;
				} catch (ConnectionManagerException e) {
					e.printStackTrace();
					return "db_error";
				}
			}else {
				return "permission_error";
			}
		}else
			return "permission_error";
	}
	
	public String UpdateItem(){
		if(isLoggedIn()){
			UserNew user = getUserObject();
			if (user.getType() == 2){
				try {
					ConnectionManager.UpdateItem(itemID, sectionID, Name, description, price, stock, allergens, preparationtime);
				} catch (ConnectionManagerException e) {
					System.out.println(e.getMessage());
					return "db_error";
				}
				return SUCCESS;
			}else{
				return "permission_error";
			}
		}else{
			return "permission_error";
		}
	}

	public Map<String, Integer> getMap(){
		return sections;
	}
	
	public Map<String, Integer> getItems(){
		return items;
	}
	
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletRequest getServletRequest() {
		return this.request;
	}
	
	public String getDescription(){
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getSectionDeleteSelection(){
		return sectionDeleteSelection;
	}
	
	public void setSectionDeleteSelection(String sectionDeleteSelection){
		this.sectionDeleteSelection = sectionDeleteSelection;
	}
	
	public String getSectionSelectedID(){
		return selectedSectionID;
	}
	/*
	public void setSectionSelectedID(String selectedSectionID){
		this.selectedSectionID = selectedSectionID;
	}*/
	
	public String getName(){
		return Name;
	}
	
	public String getNewName(){
		return NewName;
	}
	
	public String getSectionID(){
		return sectionID;
	}
	
	public String getItemname(){
		return itemname;
	}
	
	public void setItemname(String itemname){
		this.itemname = itemname;
	}
	
	public String getItemDescription(){
		return itemdescription;
	}
	
	public void setItemdescription(String itemdescription){
		this.itemdescription = itemdescription;
	}
	
	public int getStock(){
		return stock;
	}
	
	public void setStock(int stock){
		this.stock = stock;
	}
	
	public int getPreparationTime(){
		return preparationtime;
	}
	
	public void setPreparationtime(int preparationtime){
		this.preparationtime = preparationtime;
	}
	
	public String getAllergens(){
		return allergens;
	}
	
	public void setAllergens(String allergens){
		this.allergens = allergens;
	}
	
	public int getPrice(){
		return price;
	}
	
	public void setPrice(int price){
		this.price = price;
	}
	
	public int getItemID(){
		return itemID;
	}
	
	public void setItemID(int itemID){
		this.itemID = itemID;
	}
}