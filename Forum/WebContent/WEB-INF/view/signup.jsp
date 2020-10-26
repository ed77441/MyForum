<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@  taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  taglib prefix="mylib" tagdir="/WEB-INF/tags"%>


<mylib:generic>
	<jsp:attribute name="title">
		註冊
	</jsp:attribute>

	<jsp:attribute name="contentDependencies">
		<link rel="stylesheet" href="/css/input.css">
	</jsp:attribute>

	<jsp:body>
		<c:set var="tmp" value="${sessionScope.tmp}" />
		<c:set var="userTaken" value="${not empty tmp}" />
		<c:choose>
			<c:when test="${userTaken}">
				<c:set var="validation" value="was-validated"/> 
			</c:when>
			<c:otherwise>
				<c:set var="validation" value="needs-validation"/> 
			</c:otherwise>
		 </c:choose>
		<div class="d-flex justify-content-center mx-3" style="min-height:600px">
			<div id="input-box" class="p-5 shadow align-self-center"> 
			 <h4 class="text-center mb-4"> 會員註冊 </h4>
			 <form id="signup" action="/signup" method="post"
					class="${validation} container" novalidate>
			 	<div class="row">
			 		<div class="col-sm-6">
					  <div class="form-group">
					    <label for="uname">使用者名稱</label>
					    <input type="text" class="form-control" id="uname" name="user"
									pattern="^[a-zA-Z0-9]{6,20}$" autocomplete="off"
								value='<c:out value="${tmp['user']}"/>'			
						 required>
					    <div class="valid-feedback ">OK</div>
					    <div class="invalid-feedback ">必須是6-20個數字或字母</div>
					  </div>
	   				  
	   				  <div class="form-group">
					    <label for="password">密碼</label>
					    <input type="password" class="form-control" id="password"
									name="pass" pattern="^[a-zA-Z0-9]{6,20}$"
									value='<c:out value="${tmp['pass']}"/>' 
						required>
					    <div class="valid-feedback">OK</div>
					    <div class="invalid-feedback">必須是6-20個數字或字母</div>
					  </div>
			
			   		<div class="form-group">
					    <label for="password-comfirm">確認密碼</label>
					    <input type="password" class="form-control"
									id="password-confirm" name="password-confirm"
									value='<c:out value="${tmp['pass']}"/>' required>
					    <div class="valid-feedback">OK</div>
					    <div class="invalid-feedback">必須和密碼一致</div>
					  </div>		  
				  </div>
				  <div class="col-sm-6">
					  <div class="form-group">
					    <label for="birthday">生日</label>
					    <input type="date" class="form-control" id="birthday"
									name="birthday" 
									value='<c:out value="${tmp['birthday']}"/>'required>
					    <div class="valid-feedback">OK</div>
					    <div class="invalid-feedback">格式錯誤</div>
					  </div>	
					  
					  <div class="form-group">
					    <label for="email">Email</label>
					    <input type="email" class="form-control" id="email"
									name="email"
									pattern="(?=.{3,30}$)^[a-zA-Z0-9]+@[a-zA-Z0-9]+(\.[a-zA-Z0-9]+){1,2}$"
									autocomplete="off"
									value='<c:out value="${tmp['email']}"/>' required>
					    <div class="valid-feedback">OK</div>
					    <div class="invalid-feedback">格式錯誤或太長</div>
					  </div>	  
					  
					  <div class="form-group">
					    <label for="phone">電話號碼</label>
					    <input type="tel" class="form-control" id="phone" name="phone"
									pattern="^[0-9]{10}$" autocomplete="off" 
									value='<c:out value="${tmp['phone']}"/>'required>
					    <div class="valid-feedback">OK</div>
					    <div class="invalid-feedback">必須式數字且十碼</div>
					  </div>	
					  </div>
					</div>
							      	 
				 <c:if test="${userTaken}">
			      	 <div class="text-danger"> 
			      	 	使用者名稱 <c:out value="${tmp['user']}"/> 已經被使用 請重新選擇
			      	 </div>
		      	 </c:if>
			      <button type="submit" class="btn btn-success btn-block mt-3">註冊</button>		
			</form>
			</div>
		</div>
	</jsp:body>
</mylib:generic>