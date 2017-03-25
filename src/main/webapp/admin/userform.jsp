<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="java.util.*" %>

<s:form action="update" cssStyle="max-width: 330px;padding: 15px; margin: 0 auto;">
    <s:textfield label="UserID" cssClass="form-control" value="%{#application.UserID}" name="User.UserID"
                 readonly="true"/>
    <s:textfield label="Username" cssClass="form-control" value="%{#application.Username}" name="User.Username"/>
    <s:textfield label="Forename" cssClass="form-control" value="%{#application.Fname}" name="User.Fname"/>
    <s:textfield label="Surname" cssClass="form-control" value="%{#application.Lname}" name="User.Lname"/>
    <s:textfield label="Email" cssClass="form-control" value="%{#application.email}" name="User.email" type="email"/>
    <s:textfield label="Type" cssClass="form-control" value="%{#application.Type}" name="User.Type"/>
    <s:submit value="Update" cssClass="btn btn-primary btn-sm"/>
</s:form>
