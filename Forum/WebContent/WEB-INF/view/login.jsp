<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@  taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  taglib prefix="mylib" tagdir="/WEB-INF/tags"%>

<mylib:generic>
	<jsp:attribute name="title">
		登入
	</jsp:attribute>

	<jsp:attribute name="contentDependencies">
		<link rel="stylesheet" href="/css/login.css">
	</jsp:attribute>

	<jsp:body>
		<div id="login-box-wrapper" class="d-flex justify-content-center">
		<div id="login-box" class="align-self-center row shadow m-3">
			<div id="banner" class="col-sm-6 p-3 text-light bg-primary">
				<h2 class="text-center mt-4"> 
					Discussion 
				</h2>
				<h3 class="text-center mt-4">
					歡迎你回來
				 </h3>
				 <p class="text-center mt-4">
				 	讓Discussion帶給你更好的用戶體驗
				 </p>
			</div>
			 <div class="col-sm-6 p-4">
				  <h4 class="text-center mb-4">會員登入</h4>
				  <form id="login" action="/login" method="post" class="needs-validation" novalidate>
				    <div class="input-group mb-4">
				    	 <div class="input-group-prepend">
				    	 	<div class="input-group-text">
				    	 		<i class="fas fa-user"></i>
				    	 	</div>
		  			  	</div>
		   		       	<input type="text" class="form-control" id="uname"
								placeholder="輸入帳號" name="user" required>
				    </div>
				    <div class="input-group  mb-4">
				    	 <div class="input-group-prepend">
				    	 	<div class="input-group-text">
				    	 		<i class="fas fa-key"></i>
				    	 	</div>
		  			  	</div>
				      <input type="password" class="form-control" id="pwd"
								placeholder="輸入密碼" name="pass" required>
				    </div>
				    
				    <c:if test="${not empty sessionScope.error}">
					    <div class="text-danger"> 
					    	帳號或密碼錯誤 請重新輸入
					    </div>
				    </c:if>
				    <button type="submit" class="btn btn-success btn-block my-4">登入</button>
				  </form>
				  
				  <div class="text-center"> 還沒有帳號嗎？ <a href="/signup">請點這裡註冊</a> </div>
			  </div>
			</div>
		 </div>
	</jsp:body>
</mylib:generic>

