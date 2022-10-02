<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Profile</title>
<style type="text/css">
<%@include file="/css/Homepage.css" %>
<%@include file="/css/style.css" %>
</style>
</head>
<body>
<a href="/Waste_Watchers/Home" id = "inv">Home</a>

	<table style=border-style:solid class="formTable" id="rcorner1">
		<tr>
			<td>
    <form method="post" action="/Waste_Watchers/Profile">
        <label for="newUsername" id="bold">New Username</label><br>
        <input type="text" id=newUsername name=newUsername required></input>
        
        <input type="submit" id=usernameSubmit name=usernameSubmit value="Submit">
    </form><br>
    <form method="post" action="/Waste_Watchers/Profile">
        <label for="password" id="boldcenter">New password</label>
        <input type="text" id=password name=password required></input>
        
        <input type="submit" id=passwordSubmit name=passwordSubmit value="Submit">
    </form>
    </td>
    </tr>
    </table>
    <p id = "inv">${profileStatus}</p>
</body>
</html>
<% 
String username = (String) session.getAttribute("username");

if (username == null) {
    response.sendRedirect("/Waste_Watchers/Login");
    }
         
%>