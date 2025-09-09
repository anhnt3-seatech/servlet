<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.example.model.Item" %>
<%
  Item item = (Item) request.getAttribute("item");
  boolean edit = item != null;
%>
<html>
<head><title><%=edit? "Edit":"Create"%> Item</title></head>
<body>
  <h1><%=edit? "Edit":"Create"%> Item</h1>
  <form method="post" action="items">
    <input type="hidden" name="id" value="<%=edit? item.getId(): ""%>"/>
    <div>
      <label>Name: <input id="name" name="name" value="<%=edit? item.getName(): ""%>" /></label>
    </div>
    <div>
      <label>Description: <input id="description" name="description" value="<%=edit? item.getDescription(): ""%>" /></label>
    </div>
    <div>
      <button id="saveBtn" type="submit">Save</button>
      <a href="items">Cancel</a>
    </div>
  </form>
</body>
</html>