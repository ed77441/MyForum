<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@  taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  taglib prefix="mylib" tagdir="/WEB-INF/tags"%>


<mylib:generic>
	<jsp:attribute name="title">
		錯誤
	</jsp:attribute>

	<jsp:attribute name="contentDependencies">
		<link rel="stylesheet" href="/css/error.css">
	</jsp:attribute>

	<jsp:body>
		<div id="error-box" class="container-xl p-4">
			<h3 class="text-danger">
				<i class="fa fa-times" aria-hidden="true"></i>發生了某種錯誤</h3>
			<h4 class="mt-5 text-dark">
				為什麼我會看到這個頁面?
			 </h4>
			 <ul class="mt-4"> 
			 	<li>當你已經被登出 但還嘗試使用你的帳號</li>
			 	<li>URL使用不當</li>
			 	<li>參數錯誤</li>
			 	<li>嘗試修改不是你的東西</li>
			 	<li>其他</li>
			 </ul>
			 <h4 class="mt-5"> 
			 	如果有發現任何BUG 或 有任何疑問請聯絡我們
			 </h4>
			 <h5 class="mt-4">
			 	我的Email: <a href="mailto: ed77441@gmail.com">我的信箱</a>
			 </h5>
		</div>  
	</jsp:body>
</mylib:generic>