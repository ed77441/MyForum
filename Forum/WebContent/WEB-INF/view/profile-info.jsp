<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@  taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  taglib prefix="mylib" tagdir="/WEB-INF/tags"%>

<c:set var="user" value="${requestScope.user}" />
<mylib:generic>
	<jsp:attribute name="title">
		<c:out value="修改${user.username}的個人資料" />
	</jsp:attribute>	

	<jsp:attribute name="contentDependencies">
		<link rel="stylesheet" href="/css/input.css">
		<link rel="stylesheet" href="/css/countable.css">
		<script src="/js/countable.js"></script>
	</jsp:attribute>
	
	<jsp:body>
		<c:set var="user" value="${requestScope.user}" />
		<div class="d-flex justify-content-center">
			<div id="input-box" class="align-self-center container m-3 p-5 shadow">
				<h5 class="text-center">個人資料編輯</h5>				
				<form action="/profile-info" method="post" class="needs-validation" novalidate> 
					  <div class="form-group">
					    <label for="birthday">生日</label>
					    <input type="date" class="form-control" id="birthday"
									name="birthday" value='<c:out value="${user.birthday}" />' required>
					  </div>	
					   
					  <div class="form-group">
					    <label for="email">Email</label>
					    <input type="email" class="form-control" id="email"
									name="email"
									value='<c:out value="${user.email}" />'
									pattern="(?=.{3,30}$)^[a-zA-Z0-9]+@[a-zA-Z0-9]+(\.[a-zA-Z0-9]+){1,2}$"
									required>
					  </div>	  
					  
					  <div class="form-group">
					    <label for="phone">電話號碼</label>
					    <input type="tel" class="form-control" id="phone" name="phone"
					    			value='<c:out value="${user.phone}" />'
									pattern="^[0-9]{10}$" required>
					  </div>
					  
		  			  <div class="form-group countable">
					    <label for="intro">自我介紹</label>
					    <textarea class="form-control" rows="10" maxlength="1000" id="intro"
					    	 name="intro"><c:out value="${user.intro}" /></textarea>
					    <div class="text-counter hide" ></div>
					  </div>
					 					  
					  <div class="row">
					  	  <div class="col-6 px-3">
			  	  			<button id="cancel-btn" type="button" class="btn btn-secondary btn-block"
							onclick="window.history.back()">取消</button>
					  	  </div>
					  	  <div class="col-6 px-3">
	  					  	<button type="submit" class="btn btn-success btn-block">確認</button>  
					  	  </div>
					  </div>
				</form>
			</div>
		</div>
	</jsp:body>
</mylib:generic>