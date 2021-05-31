<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Forum</title>
<style>
<%@include file="css/forum.css"%>
</style>
</head>
<body>
<div class="header">
  <h1>${userName}</h1>
</div>
<div class="topnav">
<form action="/userinfo/">
<button class="button nonActive">UserInfo</button>
</form>
<button class="button active">Forum</button>
<form action="/exit" method="get">
<button class="button nonActive">Exit</button>
</form>
</div>
  <div class="column side">
   <c:forEach var="group" items="${groups}">
     <c:if test="${activeGroupId == group.id}">
          <button class="button active">${group.name}</button>
     </c:if>
     <c:if test="${activeGroupId != group.id}">
       <form action="/group/${group.id}" method="get">
          <button type="submit" class="button nonActive">${group.name}</button>
       </form>
     </c:if>
   </c:forEach>
  </div>
  <div class="column middle">
       <c:if test="${topics != null}">
          <h3>Topics</h3>
          <c:forEach var="topic" items="${topics}">
            <form action="/topic/${topic.id}" method="get">
                 <button class="button active">${topic.title}</button>
            </form>
          </c:forEach>
       </c:if>
       <c:if test="${activeGroupId != null}">
            <form action="/topic/addTopic" method="get">
                 <label for="lname">Write a title for a new topic:</label>
                   <input type="text" name="newTopicTitle" minlength="3" maxlength="60">
                 <button type="submit" class="button nonActive">Create new topic</button>
            </form>
        </c:if>
  </div>
</body>
</html>