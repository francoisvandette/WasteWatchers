<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Recovery</title>
<style type="text/css">
<%@include file="/css/Homepage.css" %>
<%@include file="/css/style.css" %>
</style>
</head>
<body>
    <p><a href="/Waste_Watchers/Login" id="inv">Back to Login</a></p>
	<table style=border-style:solid class="formTable" id="rcorner1">
		<tr>
			<td>
			<h1 id="enteremailtext">Enter Email</h1>
    <div id="form">
        <form action="/Waste_Watchers/Recovery" method="post">
           
             <label for="email" id="emailtext">Email</label><br> <input
                type="email" id="email" name="email" required><br>

            <input type="submit" value="submit" name="submit">
             <p>${emailError}</p>
        </form>
    </div>
    </td>
    </tr>
    </table>
</body>
</html>