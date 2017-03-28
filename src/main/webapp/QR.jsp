<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>

<t:wrapper>
    <jsp:attribute name="title">
		View QR
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="partials/navbar.jsp"/>
	</jsp:attribute>
    <jsp:body>
        <div class=container>
            <h4 class="text-center">Your Sections</h4>
            <table class="table">
                <form name="sectionDisplay" method="post">
                    <tr>
                        <th>Order Number</th>
                        <th>Price</th>
                        <logic:iterate>
                        <s:iterator value="Map">
                    <tr>
                        <td><a onclick="generateqrcode(<s:property value="key"/>)"><s:property value="key"/></a></td>
                        <td><s:property value="value"/></td>
                        <td><div class="row" id="qrcode"></div></td>
                    <tr>
                        </s:iterator>
                        </logic:iterate>

                </form>
            </table>
        </div>
        <script type="text/javascript" src="jquery.qrcode.js"></script>
        <script type="text/javascript" src="qrcode.js"></script>
        <script type="text/javascript">
            
            var id = <s:property value="key"/>;
            new QRCode(document.getElementById("qrcode"), id);

        </script>
    </jsp:body>
</t:wrapper>