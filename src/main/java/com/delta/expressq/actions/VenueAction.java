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
	private int preparationtime, stock, itemID;
	private float price;
	 
	/**
	 * Displays a venue's list of sections given the that is logged in is a venue user. Otherwise the no permission page is displayed.
	 * @return Success if user passes login check, otherwise permission_error.
	 */
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
	
	/**
	 * Displays the page that allows the venue user to add a new section to their menu.
	 * @return Success if user passes login check otherwise permission_error.
	 */
	public String NewSection(){
		if(isLoggedIn()){
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
	
	/**
	 * Takes value description from jsp file and passes it along with the venueid of the user to ConnectionManager.getVenueID(). This adds the section to the database.
	 * @return SUCCESS if operation is successfully otherwise return db_error. If user login is not valid return permission_error.
	 */
	public String InsertSection(){
		if (isLoggedIn()){
			UserNew user = getUserObject();
			if (user.getType() == 2){
				try {
					ConnectionManager.InsertSection(ConnectionManager.getVenueID(user.getUserID()), description);
				} catch (ConnectionManagerException e) {
					e.printStackTrace();				
					return "db_error";}
				return SUCCESS;
			}else{
				return "permission_error";
			}
		}else{
			return "permission_error";
		}
	}
	
	/**
	 * This method gets the values from the jsp that have been selected for deletion and passes them into the DeleteSections method
	 * in ConnectionManager.This method will then carry out the required processes to delete the section(s). 
	 * If no sections have been selected for deletion the function will return an error
	 * @return ERROR if no item has been selected for deletion. SUCCESS if a section has been selected. "permission_error" if the user trying to access the method is not the valid venue owner.
	 */
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
	
	/**
	 * Gets the ID of the section that has been selected to edit by the user and populates the jsp file with it's current properties so the user can make changes.
	 * "Name" is used to pass the name of the section into the jsp.
	 * @return SUCCESS if the fetch from the database was successful. db_error if it was not. permission_error if the user currently logged in is not valid.
	 */
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
	
	/**
	 * Update section given the changes the user made in the page generated in the EditSection() method.
	 * @return SUCCESS if the update was a success. db_error if it was a fail. permission_error if the user is not valid.
	 */
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
	
	/**
	 * Displays the items belonging to the section that the user has selected. Passes the sectionID into ConnectionManager.getItemsBySection() which returns a map contained all the items.
	 * JSP iterates through the map to display the items.
	 * @return SUCCESS if operation is successful. permission_error if user is invalid.
	 */
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
	
	/**
	 * Displays the page that allows the venue user to add a new item to their section.
	 * @return Success if user passes login check otherwise permission_error.
	 */
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
	
	/**
	 * Insert a new item to the database with the values the user has filled into the fields in the jsp file. 
	 * @return SUCCESS if the operation was a success. db_error if it failed. permission_error if the user is not valid.
	 */
	public String InsertItem(){
		if (isLoggedIn()){
			UserNew user = getUserObject();
			if (user.getType() == 2){
				try {
					ConnectionManager.InsertItem(sectionID, price, itemname, itemdescription, stock, allergens, preparationtime);
				} catch (ConnectionManagerException e) {
					e.printStackTrace();
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
	
	/**
	 * This method gets the values from the jsp that have been selected for deletion and passes them into the DeleteItems method
	 * in ConnectionManager.This method will then carry out the required processes to delete the item(s). 
	 * If no items have been selected for deletion the function will return an error
	 * @return ERROR if no item has been selected for deletion. SUCCESS if a item has been selected. "permission_error" if the user trying to access the method is not the valid venue owner.
	 */
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
	
	/**
	 * Gets the ID of the item that has been selected to edit by the user and populates the jsp file with it's current properties so the user can make changes.
	 * "itemDetails" is used to pass the details of the item into the jsp.
	 * @return SUCCESS if the fetch from the database was successful. db_error if it was not. permission_error if the user currently logged in is not valid.
	 */	
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
	
	/**
	 * Update item given the changes the user made in the page generated in the EditItem() method.
	 * @return SUCCESS if the update was a success. db_error if it was a fail. permission_error if the user is not valid.
	 */
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
	
	public int getPreparationtime(){
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
	
	public float getPrice(){
		return price;
	}
	
	public void setPrice(float price){
		this.price = price;
	}
	
	public int getItemID(){
		return itemID;
	}
	
	public void setItemID(int itemID){
		this.itemID = itemID;
	}
}