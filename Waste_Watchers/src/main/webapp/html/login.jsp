<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<style type="text/css">
<%@include file="/css/Homepage.css" %>
<%@include file="/css/style.css" %>
</style>
<title>Login</title>
</head>
<body>
<p><a href="/Waste_Watchers/Recovery"id="inv">Forgot your password?</a></p>
	<p><a href="/Waste_Watchers/Register"id="inv">Register</a></p>
<table style=border-style:solid class="formTable" id="rcorner1">

		<tr>
			<td>
	<div id="form">
	
		<form action="/Waste_Watchers/Login" method="post">
		<h1>Login</h1>
			
			<label for="username"id="bold">Username</label><br> <input
				type="text" id="username" name="username" required><br>

			<label for="password" id="bold">Password</label><br> <input
				type="password" id="password" name="password" required><br>

			<input type="submit" value="submit" name="submit" id="submit">
			<p>${loginError}</p>
		</form>
	</div>
	</td>
	</tr>
	</table>
	
</body>
</html>