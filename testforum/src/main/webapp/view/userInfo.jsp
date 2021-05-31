<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Forum</title>
<style>
<%@include file="css/userInfo.css"%>
</style>
</head>
<body>
<div class="header">
  <h1>${userName}</h1>
</div>
<div class="topnav">
<form action="/testforum/userinfo/">
<button class="button active">UserInfo</button>
</form>
<form action="/testforum/">
<button class="button nonActive">Back to Forum</button>
</form>
<form action="/testforum/exit" method="get">
<button class="button nonActive">Exit</button>
</form>
</div>
<div class="row">
  <div class="column side">
      <form action="/testforum/userinfo/edituserconfig" method="get">
      <button type="submit" class="button nonActive">Edit user config</button>
      </form>
      <form action="/testforum/userinfo/topics" method="get">
      <button type="submit" class="button nonActive">YourTopics</button>
      </form>
      <form action="/testforum/userinfo/posts" method="get">
      <button type="submit" class="button nonActive">YourPosts</button>
      </form>
  </div>
  <div class="column middle">
<div class="editeduserinfo" ${editeduserinfo}>
Your login: ${userName}
<br>
<form action="/testforum/userinfo/changeuserlogin" method="get">
  Enter your new login:
  <input type="text" name="newLogin" minlength="3" maxlength="10">
  Enter your password:
  <input type="text" name="userPassword" minlength="3" maxlength="10">
  ${wrongInformation}
  <button class="button nonActive">Change your Login</button>
</form>
<form action="/testforum/userinfo/changeuserpassword" method="get">
  Enter your old password:
  <input type="text" name="oldPassword" minlength="3" maxlength="10">
  Enter your new password:
  <input type="text" name="newPassword" minlength="3" maxlength="10">
  Confirm your new password:
  <input type="text" name="confirmPassword" minlength="3" maxlength="10">
  ${wrongChangePasswordInformation}
  <button class="button nonActive">Change your Password</button>
</form>
</div>
       <c:if test="${topics != null}">
            <h3>Your Topics</h3>
            <c:forEach var="topic" items="${topics}">
            <form action="/testforum/topic/${topic.id}" method="get">
                 <button class="button active">Group: ${topic.group.name} Title: ${topic.title}</button>
            </form>
            </c:forEach>
       </c:if>
       <c:if test="${posts != null}">
       <h3>Your Posts</h3>
            <c:forEach var="post" items="${posts}">
             <div class="post">
             <p class="sline">Group: ${post.topic.group.name} / Topic title: ${post.topic.title} / Author: ${post.author.login}</p>
             <p>${post.text}</p>
             </div>
            </c:forEach>
       </c:if>
  </div>
</div>
</body>
</html>