<!DOCTYPE html>
<html>
<head>
<title>Forum</title>
<style>
<%@include file="css/index&regist.css"%>
</style>
</head>
<body>
<h1>Welcome!</h1>
<form action="/testforum/user/login" method="get">
    Login:
     <br>
    <input type="text" name="login" minlength="3"/>
    <br><br>
    Password:
     <br>
    <input type="text" name="password" minlength="3"/>
    <h3>${wrongInformation}</h3>
    <br><br>
    <button class="button">Login</button>
</form>
<form action="/testforum/user/regPage" method="get">
   <button class="button">Registrarion</button>
</form>
</body>
</html>