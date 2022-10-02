<%@page import="java.util.ArrayList"%>
<%@page import="WasteWatchers.FoodItem"%>
<%@page import="java.util.Calendar" %> 
<%@page import="java.util.Date" %> 
<%@page import= "WasteWatchers.AppDao"%>
<%@page import= "WasteWatchers.Item"%>
<%@page import= "WasteWatchers.User" %>
<%@page import= "WasteWatchers.CheckExpiry"%>
<%@page import= "java.text.SimpleDateFormat" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Home</title>
<style type="text/css">
<%@include file="/css/style.css" %>
<%@include file="/css/Homepage.css" %>
</style>

</head>


<body>
	<a id="colorLink" href="/Waste_Watchers/Logout">Logout</a>
	   <br>
	<a id="colorLink" href="Profile">Profile</a>
	<h1 class="header1text">Welcome ${username}</h1>
	<h3 class="header3text">Add your expiry dates</h3>
	<table style=border-style:solid class="formTable" id="rcorner1">
		<tr>
			<td>
				<form method="post" action="/Waste_Watchers/InsertFood">
					<input type="hidden" id="username" name="username" value="${username}">
					<label for="itemname" id="bold">Item Name:</label>
					<input type="text" id="itemname" name="itemname" required><br>
					<label for="expirationdate" id="bold">Expiration Date:</label>
					<input type="date" id="expiry" name="expiry" min="" required><br><br>
					<input type="submit" id="submit" value="Submit">
				</form>
			</td>
		</tr>
	</table>
<br><br>

<form method='post' action='/Waste_Watchers/FoodDelete'>

    <table style=border-style:solid class="formTable"  id="rcorner2">  
        
        <caption id="inv">Inventory</caption> 
        <tr> <th>Select</th> <th>Item</th> <th>Expiry</th></tr>                     
              <%
              AppDao app; 
        	  String[] array = new String[100];
        	  String[] newArray = new String[100];        	  
                  try {
                	  int i = 0;                	
                	  CheckExpiry check = new CheckExpiry();
                      Date curDate = new Date();
                      Calendar c = Calendar.getInstance();
                      c.setTime(curDate);
                      c.add(Calendar.HOUR, 72);
                      Date dateRange = c.getTime();
                      app = new AppDao();
                      ArrayList<User> users = app.getUsers();
                      for (User user : users) {                       
                    ArrayList<Item> items = app.getItems(user.getUsername());                        
                    for (Item item : items) {
                    Date date = item.getExpiry();
					if (date.before(dateRange) && date.after(curDate)) {
                   String newDate = date.toString();		
                  array[i] = newDate;
       				 i++;                              	  
 	}}} 
					int t = 0;
                    int o = 0;
                    int inc = 0;
                    String test = null;
                    while(array[t] != null) {	  	
      			if(array[t].equals(test)) {
      							t++;
      						}
      						else {
      							test = array[t];
      							newArray[o] = test;
      							o++;
      							t++;
      						}     						      				
                      }
                      int tt = 0;
                      while(newArray[tt] != null) {
                    	  System.out.println(newArray[tt]);
                    	  tt++;
                      } 
                  } catch (ClassNotFoundException e) {
                      e.printStackTrace();
                  }
                  
                  ArrayList<FoodItem> inventory = (ArrayList<FoodItem>) request.getAttribute("inventory");
               		
                  for(FoodItem item: inventory){ %>
                      <tr>                     
                           <td><input type='checkbox' name='itemCheck' value='<%out.println(item.getId());%>'></td>
                           <td><%out.println(item.getName());%></td>
                           <td><span class="redText"><%
                          int test = 1;
                           int x = 0;
                           if(newArray[x] != null && newArray[x+1] == null) {
                           if(newArray[x].equals(item.getExpDate())) {
                        	
                           out.println(item.getExpDate()); 
                           test = 0;
                           }}
                           if(newArray[x] != null && newArray[x+1] != null && newArray[x+2] == null) {
                               if(newArray[x].equals(item.getExpDate()) || newArray[x+1].equals(item.getExpDate())) {
                               	
                                   out.println(item.getExpDate()); 
                                   test = 0;
                                   }
                           }                                                      
                           if( newArray[x] != null && newArray[x+1] != null && newArray[x+2] != null) {
                               if(newArray[x].equals(item.getExpDate()) || newArray[x+1].equals(item.getExpDate()) || newArray[x+2].equals(item.getExpDate())) {
                               	
                                   out.println(item.getExpDate()); 
                                   test = 0;
                                   }
                           }
                           %>
                           </span>
                           <% if(test == 1) {
                        	   out.println(item.getExpDate());                        	                              
                           }}%>              
                          </td>
                          <tr>
	</table>
	<input type='hidden' id='username' name='username' value='${username}'>
	  <br>
    <input type='submit' id='submitdelete' value='Delete Item' class="deletecenter">
	</form>
	<script>
	// Minimum date set, so it can't be earlier than today's date.
	var date = new Date();
	var dd = date.getDate() + 1;
	var mm = date.getMonth() + 1;
	var yyyy = date.getFullYear();
	if (dd < 10) {
		dd = '0' + dd;
	}
	if (mm < 10) {
		mm = '0' + mm;
	}
	var today = yyyy + '-' + mm + '-' + dd;
	document.getElementById("expiry").setAttribute("min", today);
	document.getElementById("expiry").valueAsDate = new Date(today);
</script>
</body>
</html>