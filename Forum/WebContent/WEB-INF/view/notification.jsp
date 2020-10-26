<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>	
<%@  taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  taglib prefix="mylib" tagdir="/WEB-INF/tags"%>

<mylib:generic>
	<jsp:attribute name="title">
		所有通知
	</jsp:attribute>

	<jsp:attribute name="contentDependencies">
		<link rel="stylesheet" href="/css/notification.css">
		<script src="/js/elapse.js"></script>
	</jsp:attribute>

	<jsp:body>
		<c:choose>
			<c:when test="${empty requestScope.notifications}">
				<div id="border-box-wrapper" class="container-xl"> 
					<h3 class="text-secondary m-3">
						目前沒有任何通知
					</h3>
				</div>
			</c:when>
			<c:otherwise>
				<div class="container-xl" id="border-box-wrapper">
		
					<div id="border-box" class="mx-sm-3 my-3 p-3 bg-light">
		
						<c:set var="size" value="${fn:length(requestScope.notifications)}"/>
						<c:forEach var="notification" items="${requestScope.notifications}" varStatus="status">
							<c:set var="thread" value="${notification.key}"/>
							<c:set var="comment" value="${notification.value}"/>
							<c:set var="location" value="${requestScope.locations[status.index]}"/>
							<div class="content-box text-secondary">
								<div>
									<div>
										標題: <c:out value="${thread.caption}" />
									</div>
									<div>
										<c:set var="appending" value="" />
										<c:if test="${fn:length(comment.content) gt 30}">
											<c:set var="appending" value="..."/>
										</c:if>
										
										內容: 
										<a href="${location}" class="text-dark">
											<c:out value="${fn:substring(comment.content, 0, 30)}${appending}" />
										</a>
									</div>
								</div>
								<div>
									作者: <c:out value="${comment.user}" />
								</div>
							</div>
							<c:if test="${size - 1 ne status.index}">
								<hr/>
							</c:if>
						</c:forEach>
					</div>
		
				</div>
				<div class="d-flex justify-content-center">
					<mylib:pagination  url="/NotificationController" 
						start="${pagination.start}" end="${pagination.end}" current="${pagination.current}" last="${pagination.last}" />
				</div>
			</c:otherwise>
		</c:choose>
	</jsp:body>
</mylib:generic>

