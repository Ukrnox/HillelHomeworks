<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Forum</title>
<style>
<%@include file="css/activeTopic.css"%>
</style>
</head>
<body>
<div class="header">
  <h1>${userName}</h1>
</div>
<div class="topnav">
<form action="/testforum/userinfo/">
<button class="button nonActive">UserInfo</button>
</form>
<form action="/testforum/">
<button class="button nonActive">Back to Forum</button>
</form>
<form action="/testforum/exit" method="get">
<button class="button nonActive">Exit</button>
</form>
</div>
<div class="column side">
<c:if test="${topics != null}">
  <c:forEach var="topic" items="${topics}">
     <c:if test="${activeTopicId == topic.id}">
          <button class="button active">${topic.title}</button>
     </c:if>
     <c:if test="${activeTopicId != topic.id}">
       <form action="/testforum/topic/${topic.id}" method="get">
          <button type="submit" class="button nonActive">${topic.title}</button>
      </form>
     </c:if>
  </c:forEach>
</c:if>
</div>
<div class="column middle">
       <c:if test="${posts != null}">
           <c:forEach var="post" items="${posts}">
             <br><br>
           <c:if test="${post.author.login == userName}">
             <form action="/testforum/topic/post/remove/${post.id}" method="get">
                <div class="X">
                  <button type="submit">X</button>
                </div>
             </form>
           </c:if>
           <div class="post">
               <p class="sline">Author: ${post.author.login}</p>
               <p>${post.text}</p>
				   <c:set var="likesum" value="${0}" />
                   <c:set var="dislikesum" value="${0}" />
                   <c:set var="userlike" value="${0}" />
                   <c:set var="userdislike" value="${0}" />
           <c:if test="${votes!=null}">
              <c:forEach var="vote" items="${votes}">
                <c:if test="${vote.post.id == post.id}">
                   <c:set var="likesum" value="${likesum + vote.upVotes}" />
                   <c:set var="dislikesum" value="${dislikesum + vote.downVotes}" />
                  <c:if test="${vote.author.login == userName}">
                   <c:if test="${vote.upVotes == 1}">
                       <c:set var="userlike" value="${1}" />
                   </c:if>
                   <c:if test="${vote.downVotes == 1}">
                      <c:set var="userdislike" value="${1}" />
                   </c:if>
                  </c:if>
				</c:if>
              </c:forEach>
              <c:choose>
                  <c:when test="${userlike == 1}">
                    <form action="/testforum/topic/post/vote/${post.id}" method="get">
                     <div class="activeLike">
                        <button type="submit" name="like" value="1">Like - ${likesum}</button>
                     </div>
                     <div class="nonActiveLike">
                        <button class="button active" type="submit" name="like" value="0">DisLike - ${dislikesum}</button>
                     </div>
                    </form>
                  </c:when>
                  <c:when test="${userdislike == 1}">
                    <form action="/testforum/topic/post/vote/${post.id}" method="get">
                     <div class="nonActiveLike">
                       <button type="submit" name="like" value="1">Like - ${likesum}</button>
                     </div>
                     <div class="activeLike">
                       <button type="submit" name="like" value="0"  >DisLike - ${dislikesum}</button>
                     </div>
                    </form>
                  </c:when>
                  <c:otherwise>
                     <form action="/testforum/topic/post/vote/${post.id}" method="get">
                       <div class="nonActiveLike">
                         <button class="button active2" type="submit" name="like" value="1">Like - ${likesum}</button>
                       </div>
                       <div class="nonActiveLike">
                         <button type="submit" name="like" value="0"  >DisLike - ${dislikesum}</button>
                       </div>
                     </form>
                  </c:otherwise>
              </c:choose>
           </c:if>
           </div>
           </c:forEach>
       </c:if>
       <form action="/testforum/topic/post/addPost" method="get">
         <br><br>
      <b>Enter the text of the new post:</b>
         <textarea name="newPostText" minlength=3 maxlength=1000></textarea>
         <div class="forPost">
         <button type="submit">Post</button>
         </div>
       </form>
</div>
</body>
</html>