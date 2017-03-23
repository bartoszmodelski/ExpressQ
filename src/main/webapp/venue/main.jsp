<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<t:wrapper>
    <jsp:attribute name="title">
		Venue Home
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="../partials/navbar.jsp"/>
	</jsp:attribute>
    <jsp:body>
        <div class="container">
            <h1>Sections</h1>
            <a href="<s:url action="NewSection.action"/>">Add a new Section</a><br><br>
            <h4 class="text-center">Your Sections</h4>
            <table class="table">
                <form name="sectionDisplay" method="post">
                    <tr>
                        <th></th>
                        <th>Section Name</th>
                        <logic:iterate>
                        <s:iterator value="Map">
                    <tr>
                        <td><input type="checkbox" value=
                            <s:property value="value"/> name= "sectionDeleteSelection">
                        </td>
                        <td><a href="javascript:viewItems(<s:property value="value"/>)"><s:property value="key"/></a>
                        </td>
                        <td><a href="javascript:editSection(<s:property value="value"/>)">Change Section Name</a></td>
                    <tr>
                        </s:iterator>
                        </logic:iterate>

                </form>
            </table>
            <input type="button" class="btn btn-primary btn-sm" style="float: right"
            	value="Delete Selected Sections"
                onclick="deleteRecord();"/>
            <script type="text/javascript">

                function deleteRecord() {
                    document.sectionDisplay.action = "delsection.action";
                    document.sectionDisplay.submit();
                }

                function editSection(id) {
                    document.sectionDisplay.action = "editsection.action?selectedSectionID=" + id;
                    document.sectionDisplay.submit();
                }

                function viewItems(id) {
                    document.sectionDisplay.action = "viewItems.action?selectedSectionID=" + id;
                    document.sectionDisplay.submit();
                }
            </script>
        </div>
    </jsp:body>
</t:wrapper>