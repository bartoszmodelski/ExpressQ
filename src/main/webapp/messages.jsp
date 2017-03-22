<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<s:iterator value="Messages">
  <s:iterator value="value">
    <div class="alert alert-<s:property value="key" escape="false" />">
      <s:property escape="false" />
    </div>
  </s:iterator>
</s:iterator>
</logic:iterate>
