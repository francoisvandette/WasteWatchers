<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Register</title>
<style type="text/css">
<%@include file="/css/Homepage.css" %>
<%@include file="/css/style.css" %>
</style>

</head>

<body>
    <p><a href="/Waste_Watchers/Login" id="inv">Login</a></p>
<table style=border-style:solid class="formTable" id="rcorner1">

	<tr>
			<td>
    
    <div id="form">
   
		<form action="/Waste_Watchers/Register" method="post">
		 <h1 id="h1">Register</h1>
			<label for="username" id="bold">Username</label><br>
			<input type="text" id="username" name="username" required><br>
			
			<label for="email" id="bold">Email</label><br>
			<input type="email" id="email" name="email" required><br>
			
			<label for="password" id="bold">Password</label><br>
			<input type="password" id="password" name="password" required><br>

			<input type="submit" value="submit" name="submit">
				<p>${error}</p><br>
		</form>
		
    </div>
    </td>
    </tr>
</table>

</body>

</html>