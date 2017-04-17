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
            <h4 class="text-center">Your Active Orders</h4>
            <table class="table">
                    <tr>
                        <th>Order Number</th>
                        <th>Price</th>
                        <logic:iterate>
                        <s:iterator value="Map">
                    <tr>
                        <td><a href="#" onclick="generateqrcode(<s:property value="key"/>)"><s:property value="key"/></a></td>
                        <td><s:property value="value"/></td>
                    <tr>
                        </s:iterator>
                        </logic:iterate>

            </table>
            <div class="row" align = "center" id="qrcode" ></div>
        </div>
        <script type="text/javascript" src="jquery.qrcode.js"></script>
        <script type="text/javascript" src="qrcode.js"></script>
        <script type="text/javascript">
        
            function generateqrcode(id){
            	document.getElementById("qrcode").innerHTML = "";
           		var qrid = id.toString();
            	new QRCode(document.getElementById("qrcode"), qrid);
			}
        </script>
    </jsp:body>
</t:wrapper>