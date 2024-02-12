<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title> Mera jsp page</title>
<style>
body {
  font-size: 40px;
  text-align:center;
  background: linear-gradient(to right, #f57e7e, #315f72);
}

h1,h3{
color: white;
margin:15px;
}
input, button {
  font-size:40px;
  color: white;
  border-radius: 5%;
  background-color: black;
  border: 2px solid white;
}

img{
width: 20%;
height:20%;
margin-top: 20px;
}
img:hover{
  -ms-transform: scale(1.1); /* IE 9 */
  -webkit-transform: scale(1.1); /* Safari 3-8 */
   transform: scale(1.1); 
}
</style>
</head>
<body>
<img src="C:\Users\Asus\Downloads\hyE.gif">
<h1>Joke of the day</h1>
<h3><%=request.getParameter("joke")%> </h3>

<h1>Ans=<%=request.getParameter("ans") %></h1>
</body>
</html>