<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@  taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@  taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@  taglib prefix="mylib" tagdir="/WEB-INF/tags"%>


<mylib:generic>
	<jsp:attribute name="title">
		<c:choose> 
			<c:when test="${requestScope.page eq 'home'}"> 
				首頁
			</c:when>
			<c:otherwise>
				搜尋
			</c:otherwise>
		</c:choose>
	</jsp:attribute>

	<jsp:attribute name="contentDependencies">
		<link rel="stylesheet" href="/css/home.css">
		<script src="/js/elapse.js"></script>
	</jsp:attribute>

	<jsp:body>
		<div class="container-xl p-3"> 
			<jsp:useBean id="genreConstants" class="com.ed77441.utils.GenreConstants"/>
			
			<div id="page-header">
				<div> 
					<c:choose> 
						<c:when test="${requestScope.page eq 'home'}">
							<c:set var="btype" value="btn-default" />
						
							<c:if test="${requestScope.genre eq '全部主題'}">
								<c:set var="btype" value="btn-success" />
							</c:if>
							<a class="btn ${btype}" href="${fn:replace(requestScope.currentUrl, requestScope.genre, '全部主題')}">
								全部主題
							</a>
							
							<c:forEach var="genre" items="${genreConstants.listOfGenre}"> 
								<c:set var="btype" value="btn-default" />
								<c:if test="${requestScope.genre eq genre}"> 
									<c:set var="btype" value="btn-success" />
								</c:if>
								
								<a class="btn ${btype}" href="${fn:replace(requestScope.currentUrl, requestScope.genre, genre)}">
									<c:out value="${genre}" />
								</a>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<a id="search-string"class="btn btn-default"> 
								搜尋字串 : <c:out value="${requestScope.q}"/>
							</a>
						</c:otherwise>
					</c:choose>
				

				</div>
				
				<div id="order-button-div">
					<c:set var="btype" value="btn-default border border-light"/>
					<c:set var="orderByParam" value="&orderBy=發文時間"/>
					<c:set var="location" value="/search?q=${requestScope.q}"/>
					
					<c:if test="${requestScope.page eq 'home'}"> 
						<c:set var="location" value="/home?genre=${requestScope.genre}"/>
					</c:if>
					
					<c:if test="${requestScope.orderBy eq '發文時間'}"> 
						<c:set var="btype" value="btn-primary"/>
						<c:set var="orderByParam" value=""/>
					</c:if>
					
					<a href="${location}${orderByParam}" class="text-nowrap btn ${btype}">依照發串時間排序</a>
				</div>
			</div>
			
			<c:choose>
				<c:when test="${not empty requestScope.threads}">
					<div id="content-box-wrapper"> 
						<div id="content-box" class="p-3 my-4"> 
							<c:set var="size" value="${fn:length(requestScope.threads)}"/>
							<c:forEach var="thread" items="${requestScope.threads}" varStatus="status">
								<div class="thread-row"> 
									<div class="thread-genre">
										<c:out value="${thread.genre}"/>
									 </div>
									
									<div class="thread-caption">
										<a href="/threads?id=${thread.id}" class="text-secondary"><c:out value="${thread.caption}"/></a>
									 </div>
									
									<div class="thread-info">
										<div class="d-flex justify-content-between"> 
											<div class="align-self-center">回覆 : <c:out value="${thread.commentCount - 1}"/></div>
											<div class="align-self-center">
												<div class="text-right"><mylib:elapse timestamp="${thread.lastUpdate}" /></div>
												<div class="text-right">
													<a href="profile?name=${thread.user}" class="text-secondary">
														<c:out value="${thread.user}"/>
													</a>
												</div>
											</div> 
										</div>
									</div>
			
								 </div>
								 <c:if test="${size - 1 ne status.index}">
								 	<hr/> 
								 </c:if>
							</c:forEach>
						</div>
					</div>
					<div class="d-flex justify-content-center">
						<mylib:pagination  url="${requestScope.currentUrl}" 
							start="${pagination.start}" end="${pagination.end}" current="${pagination.current}" last="${pagination.last}" />
					</div>
				</c:when>
				<c:otherwise>
					<div id="content-box-wrapper" class="mt-3 pl-2"> 
						<h3 class="text-secondary">
							找不到任何資料
						</h3>
					</div>
				</c:otherwise>
			</c:choose>

		</div>
	</jsp:body>
</mylib:generic>