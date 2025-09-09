<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.*, com.example.model.Item" %>
<html>
<head><title>Items</title></head>
<body>
  <h1>Items</h1>
  <p><a href="items?action=create">Create new</a></p>
  <table border="1" cellpadding="6">
    <tr><th>ID</th><th>Name</th><th>Description</th><th>Actions</th></tr>
    <%
      List<Item> items = (List<Item>) request.getAttribute("items");
      if (items != null) {
        for (Item it : items) {
    %>
      <tr>
        <td><%=it.getId()%></td>
        <td><%=it.getName()%></td>
        <td><%=it.getDescription()%></td>
        <td>
          <a href="items?action=edit&id=<%=it.getId()%>">Edit</a> |
          <a href="items?action=delete&id=<%=it.getId()%>" onclick="return confirm('Delete?')">Delete</a>
        </td>
      </tr>
    <%
        }
      }
    %>
  </table>
</body>
</html>