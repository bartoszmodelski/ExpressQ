Skip to content
This repository
Search
Pull requests
Issues
Gist
 @GrantChristie
 Sign out
 Unwatch 5
  Star 0
  Fork 0 bartoszmodelski/delta Private
 Code  Issues 0  Pull requests 0  Projects 0  Wiki  Pulse  Graphs
Tree: ff19e19445 Find file Copy pathdelta/src/main/webapp/order/summary.jsp
3cfa8dc  15 hours ago
@MBrighty MBrighty Add Stripe payment button
3 contributors @bartoszmodelski @MBrighty @GrantChristie
RawBlameHistory    
74 lines (72 sloc)  2.61 KB
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<t:wrapper>
    <jsp:attribute name="title">
		Summary
	</jsp:attribute>
    <jsp:attribute name="navbar">
		<jsp:include page="../partials/navbar.jsp"/>
	</jsp:attribute>
    <jsp:body>
        <div class="container">
        <s:actionerror/>
        <div class="row">
            <div class="col-5 offset-2">
                <h3>Check your order</h3>
            </div>
        </div>
        <s:form method="POST" action="confirmOrder">
            <br>
            <table class="table table-responsive">
                <thead>
                <tr>
                    <th>Item</th>
                    <th>Price</th>
                    <th>Quantity</th>
                    <th>Total</th>
                </tr>
                </thead>
                <tbody>
                <s:iterator value="order.itemsAndQuantities">
                    <tr>
                        <td><s:property value="key.name"/></td>
                        <td>£<s:property value="getText('{0,number,#,##0.00}',{key.price})"/></td>
                        <td><s:property value="value"/></td>
                        <td>£<s:property value="getText('{0,number,#,##0.00}',{(key.price*value)})"/></td>
                    </tr>
                </s:iterator>
                <td colspan="2"></td>
                <th scope="row">Grand Total:</th>
                <td>£<s:property value="getText('{0,number,#,##0.00}',{total})"/></td>
                </tbody>
            </table>
            <br>
            <h3 class="row">Specify time of collection</h3>
            <br>
            <jsp:include page="selects.jsp"/>
            - (strongly advised when ordering any not off-the-shelf items)
            <br>
            <br>
            <div class="row">
            <div class="col-6">
                <h3>Confirm your order</h3>
            </div>
            <div class="col-6">
            <script
                src="https://checkout.stripe.com/checkout.js" class="stripe-button"
                data-key="pk_test_gJ2xZioR7mLdDZ3kDZ05OHy5"
                data-name="SwiftQ"
                data-email=<s:property value="%{(#session['user']).getEmail()}"/>
                data-currency="gbp"
                data-description="Enter Card Details"
                data-amount=<s:property value="amount"/>>
            </script>
            </div>
            </div>
        </s:form>
        </div>
    </jsp:body>

</t:wrapper>
Contact GitHub API Training Shop Blog About
© 2017 GitHub, Inc. Terms Privacy Security Status Help