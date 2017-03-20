package com.delta.expressq.actions;

public class ExampleOfMessages extends ActionSupportWithSession {
	public String execute() {
		addInformationMessage("Info! ", "Indicates a neutral informative change or action.");
		addInformationMessage("Info! ", "Indicates a neutral informative change or action.");
		addDangerMessage("Danger! ", "Indicates a dangerous or potentially negative action.");
		addSuccessMessage("Success! ", "Indicates a successful or positive action.");
		addWarningMessage("Warning! " , "Indicates a warning that might need attention.");
		return SUCCESS;
	}
}
