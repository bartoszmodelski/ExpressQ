<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page import="java.util.*" %>

<s:form cssStyle="max-width: 330px; padding: 15px; margin: 0 auto;" action="UserUpdate"
        onsubmit="return validateForm()" name="UserUpdate">
    <s:textfield label="Username" cssClass="form-control" value="%{#application.Username}" name="User.Username" readonly="true"/>
    <s:textfield label="Password" cssClass="form-control" value="" name="User.Password" type="Password" required="true"/>
    <s:textfield label="Confirm Password" cssClass="form-control" value="" name="PasswordConf" type="Password" required="true"/>
    <s:textfield label="Forename" cssClass="form-control" value="%{#application.Fname}" name="User.Fname" required="true"/>
    <s:textfield label="Surname" cssClass="form-control" value="%{#application.Lname}" name="User.Lname" required="true"/>
    <s:textfield type = "email" label="Email" cssClass="form-control" value="%{#application.email}" name="User.email" required="true"/>
    <s:submit value="Update" cssClass="btn btn-primary btn-sm"/>
</s:form>

<script>
    function validateForm() {
        var password = document.forms["UserUpdate"]["User.Password"].value;
        var passwordconfirm = document.forms["UserUpdate"]["PasswordConf"].value;

        if (password != passwordconfirm) {
            alert("Passwords must match.");
            return false;
        }
    }
</script>