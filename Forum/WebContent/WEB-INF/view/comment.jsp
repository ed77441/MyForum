<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@  taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  taglib prefix="mylib" tagdir="/WEB-INF/tags"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<mylib:generic>
	<jsp:attribute name="title">
		留言
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
		
		<div class="d-flex justify-content-center" style="min-height: 600px">
		
			<div id="input-box" class="align-self-center m-3 p-5 shadow" style="width: 100%">
				<h5 class="text-center">
					更改在"<c:out value="${thread.caption}" />"下的留言
				</h5>
				<form action="/comment" method="post" class="needs-validation" 
							onsubmit="return sendPutRequest(this,'/threads?id=${thread.id}&page=1')" novalidate>
				   <div class="form-group d-none">
				    <label for="tid">Thread</label>
				    <input type="number" class="form-control" min="-1" name="tid" 
				    		value='<c:out value="${thread.id}"/>' id="tid" required />
				  </div>
				  <div class="form-group d-none">
				    <label for="cid">Comment</label>
				    <input type="number" class="form-control" min="-1" name="cid" 
				    		value='<c:out value="${comment.id}"/>' id="cid" required />
				  </div>

				  <div class="form-group countable">
				    <label for="comment">留言內容</label>
				    <textarea class="form-control" id="comment" name="comment" 
				    		rows="8" minlength="10" maxLength="1500" required><c:out value="${comment.content}"/></textarea>
				     <div class="text-counter hide" ></div>
				  </div>
				  <button type="submit" class="btn btn-success btn-block">修正</button>
				</form>
			</div>
		</div>
	</jsp:body>
</mylib:generic>
