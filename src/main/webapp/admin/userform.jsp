<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="java.util.*" %>

<s:form action="update" cssStyle="max-width: 330px;padding: 15px; margin: 0 auto;" name="edit">
    <s:textfield label="UserID" cssClass="form-control" value="%{#application.UserID}" name="User.UserID"
                 readonly="true"/>
    <s:textfield label="Username" cssClass="form-control" value="%{#application.Username}" name="User.Username" required="true"/>
    <s:textfield label="Forename" cssClass="form-control" value="%{#application.Fname}" name="User.Fname" required="true"/>
    <s:textfield label="Surname" cssClass="form-control" value="%{#application.Lname}" name="User.Lname" required="true"/>
    <s:textfield label="Email" cssClass="form-control" value="%{#application.email}" name="User.email" type="email" required="true"/>
    <s:select label="Type" value="{#application.Type}"  name="User.Type" list ="{0,1,2}" cssClass="form-control"/>
    <s:submit value="Update" cssClass="btn btn-primary btn-sm"/>
</s:form>