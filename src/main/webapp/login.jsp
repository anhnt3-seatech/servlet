<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login Page</title>
</head>
<body>
<h2>Login</h2>
<form action="login" method="post">
    <label for="username">Username:</label>
    <input type="text" id="username" name="username"><br><br>

    <label for="password">Password:</label>
    <input type="password" id="password" name="password"><br><br>

    <input type="submit" value="Login" id="loginBtn">
</form>
</body>
</html>