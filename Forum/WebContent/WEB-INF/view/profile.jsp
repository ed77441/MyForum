<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@  taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  taglib prefix="mylib" tagdir="/WEB-INF/tags"%>

<c:set var="user" value="${requestScope.user}" />
<c:set var="loggedIn" value="${user.username eq sessionScope.user}" />

<mylib:generic>
	<jsp:attribute name="title">
		<c:out value="${user.username}的個人資料" />
	</jsp:attribute>	

	<jsp:attribute name="contentDependencies">
		<link rel="stylesheet" href="/css/profile.css">
	</jsp:attribute>
	
	<jsp:body>
		<div class="min-height-wrapper p-4">
		<div id="profile-box" class="container-xl p-4 shadow">
			<div class="row">
				<div class="col-md-3">
					<div id="image-box">
						<img src=<c:out value="${user.pfp}" />
							class="mx-auto d-block border border-success rounded-circle border-3"
							width="150" height="150"/>
							
						<c:if test="${loggedIn}">
							<button id="image-chooser" class="float-right btn btn-success rounded-circle"
											 onclick="$('#image-input').click();">
								<i class="fas fa-camera"></i>
							</button>	
							<form id="image-form" class="d-none" action="/pfp-change" 
										method="post" enctype="multipart/form-data"> 
								<input type="file" name="image" id="image-input" accept="image/x-png,image/gif,image/jpeg" 
								onchange="$('#image-form').submit()" />
							</form>
						</c:if>
					</div>
					<h4 class="my-5 text-center">
						<c:out value="${user.username}" />
					</h4>
					
				</div>

				<div class="col-md mx-3">
					<p class="info-text">
						<i class="fa fa-birthday-cake" aria-hidden="true"></i> <strong>生日
							:</strong>
						<c:out value="${user.birthday}" />
					</p>
					<p class="info-text">
						<i class="fa fa-envelope" aria-hidden="true"></i> <strong>電子郵件
							:</strong> <a href=<c:out value="mailto: ${user.email}" />> <c:out
								value="${user.email}" />
						</a>
					</p>
					<p class="info-text">
						<i class="fas fa-phone"></i> <strong>電話號碼 :</strong>
						<c:out value="${user.phone}" />
					</p>
					<p class="info-text">
						<i class="fas fa-pen"></i> <strong>自我介紹 :</strong>
					</p>
					<pre class="info-text pl-3"><c:out value="${user.intro}" /></pre>
				</div>

			</div>

			<c:if test="${loggedIn}">
				<hr />
				<div class="d-flex mt-3 mr-2">
					<a href="/profile-info" class="btn btn-success ml-auto">
						編輯你的個人資料 </a>
				</div>
			</c:if>

		</div>
	</div>
	</jsp:body>
</mylib:generic>
