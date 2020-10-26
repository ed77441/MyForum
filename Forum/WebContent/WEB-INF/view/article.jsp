<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@  taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  taglib prefix="mylib" tagdir="/WEB-INF/tags"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="genreConstants" class="com.ed77441.utils.GenreConstants"/>

<mylib:generic>
	<jsp:attribute name="title">
		文章
	</jsp:attribute>	

	<jsp:attribute name="contentDependencies">
		<link rel="stylesheet" href="/css/input.css">
		<link rel="stylesheet" href="/css/countable.css">
		<script src="/js/countable.js"></script>
		<script src="/js/put.js"></script>
	</jsp:attribute>
	
	<jsp:body>
		<c:set var="put" value="${requestScope.put}" />
		<c:set var="thread" value="${requestScope.thread}" />
		<c:set var="comment" value="${requestScope.comment}" />
		
		<div class="d-flex justify-content-center">
		
			<div id="input-box" class="container m-3 p-5 shadow">
				<h5 class="text-center">
					<c:choose> 
						<c:when test="${put}"> 
							更新文章
						</c:when>
						<c:otherwise> 
							發表文章
						</c:otherwise>
					</c:choose>
				</h5>
				<form action="/article" method="post" class="needs-validation" 
					<c:if test="${put}">
						onsubmit="return sendPutRequest(this)"
					</c:if> 
				novalidate>
				
				  <c:if test="${put}"> 
					  <div class="form-group d-none">
					    <label for="id">Thread</label>
					    <input type="number" class="form-control" min="-1" name="id" 
					    		value='<c:out value="${thread.id}"/>' id="id" required />
					  </div>
				  </c:if>

				  <div class="form-group">
				    <label for="caption">文章標題</label>
				    <input type="text" class="form-control" name="caption" id="caption"
				    autocomplete="off" minlength="4" maxlength="50" value='<c:out value="${thread.caption}"/>' required>
				  </div>
				  <div class="form-group">
				    <label for="genre">文章類別</label>
				    <select class="form-control" name="genre" id="genre" required>
				    	<c:forEach var="genre" items="${genreConstants.listOfGenre}">
				    		<option
				    			<c:if test="${genre eq thread.genre}" > 
				    				selected
				    			</c:if>>
				    			 <c:out value="${genre}" />
			    		 	</option>
				    	</c:forEach>
				    </select>
				  </div>
				  <div class="form-group countable">
				    <label for="content">文章內容</label>
				    <textarea class="form-control" id="content" name="content" 
				    		rows="10" minlength="10" maxLength="1500" required><c:out value="${comment.content}"/></textarea>
				     <div class="text-counter hide" ></div>
				  </div>
				  <button type="submit" class="btn btn-success btn-block">發佈</button>
				</form>
			</div>
		</div>
	</jsp:body>
</mylib:generic>
