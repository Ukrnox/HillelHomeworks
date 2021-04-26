<html>
<body>
<form action="/hello" method="POST">
    Name: <input name="username" />
    <br><br>
    Password: <input name="userPassword" />
    <br><br>
</select>
    Captcha:
    <br><br>
    <img alt="captcha" src="/captcha">
    <br><br>
    Enter captcha cod:
    <br><br>
    <input name="userCaptcha" />
    <br><br>
    <% Integer tryingCounter = (Integer) request.getSession().getAttribute("tryingCounter");
        if(tryingCounter != null)
        {
            out.println("Trying Counter: " + tryingCounter);
        }
     %>
    <br><br>
    <input type="submit" value="Submit" />
</form>
</body>
</html>