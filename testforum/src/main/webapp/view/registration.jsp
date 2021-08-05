<!DOCTYPE html>
<html>
<head>
    <title>Forum</title>
    <style>
        <%@include file="css/index&regist.css"%>
    </style>
</head>
<body>
<br><br>
<h1>Registration form</h1>
<form action="/user/reg" method="get">
    Login:
    <br>
    <input type=text name="login" minlength="3"/>
    <br><br>
    Password:
    <br>
    <input type=text name="password" minlength="3"/>
    <br><br>
    Confirm password:
    <br>
    <input type=text name="checkPassword"/>
    <br>
    <h3>${passwordMismatch}</h3>
    <h3>${loginExists}</h3>
    <h3>${noInfo}</h3>
    <button class="button">Registrarion</button>
</form>
</body>
</html>