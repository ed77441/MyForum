<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@  taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@  taglib prefix="mylib" tagdir="/WEB-INF/tags"%>

<mylib:generic>
	<jsp:attribute name="title">
		<c:out value="${requestScope.thread.caption}"/>
	</jsp:attribute>

	<jsp:attribute name="contentDependencies">
		<link rel="stylesheet" href="/css/threads.css">
		<link rel="stylesheet" href="/css/countable.css">
		
		<script src="/js/elapse.js"></script>
		<c:if test="${not empty sessionScope.user}"> 
			<script src="/js/countable.js"></script>
		</c:if>
		<script src="/js/delete.js"></script>
	</jsp:attribute>
				
	
	<jsp:body>
		<c:set var="thread" value="${requestScope.thread}"/>
		<c:set var="pagination" value="${requestScope.pagination}" />
		
		<div id="content-box" class="container-xl p-4">
			<div class="row">
				<div id="thread-header" class="col-sm-12 d-flex justify-content-between">
					<div>
						<h4 class="align-self-center m-0">
							<c:out value="${thread.caption}" />
						</h4>
						
						<div class="text-secondary">
							<a href=<c:out value="/profile/${thread.user}" /> class="text-secondary" > 
								<c:out value="${thread.user}" /> 
							</a>
							 - 發佈於<mylib:elapse timestamp="${thread.startTime}" />
						</div> 
					</div>

					<div class="align-self-center"><c:out value="分類 : ${thread.genre}" /></div>
				</div>
			</div>
			<hr class=""/>
			<c:forEach var="row" items="${requestScope.rows}" varStatus="status">
				<c:set var="user" value="${row.key}"/>
				<c:set var="comment" value="${row.value}"/>
				<c:set var="base" value="${(pagination.current - 1) * 10}"/>
				<c:set var="commentIndex" value="${base + status.index + 1}" />
				
				
				<div id="comment-${commentIndex}" class="row shadow mb-3 info-box">
					<div class="col-sm-2 user-box">
						<div class="my-sm-3 pfp-wrapper">
							<img src='<c:out value="${user.pfp}" />' width="80" height="80"  
							class="mx-auto d-block border border-success rounded-circle border-3"/>
							<div class="text-center mt-2">
								<a href=<c:out value="/profile?name=${user.username}" />  class="text-secondary"> 
									<c:out value="${user.username}" /> 
								</a>
							</div>
						</div>
					</div>
					<div class="col-sm-10">					
						<div class="text-secondary my-2 d-flex justify-content-between">
							<mylib:elapse timestamp="${comment.lastUpdate}" />
							
							<span>
								#<c:out value="${commentIndex}" /> 
							</span>
						 </div>
						<hr class="m-0"/>	
						<div class="my-2 content"><c:out value="${comment.content}"/></div>
						
						<c:if test="${sessionScope.user eq user.username}">
							<hr class="m-0"/> 
							<div class="d-flex justify-content-end dropdown">
								<c:choose>
									<c:when test="${commentIndex eq 1}">
										<button class="my-2 btn" type="button" data-toggle="dropdown"> 
											<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
 										</button>
										<div class="dropdown-menu">
									      <a class="dropdown-item" href=<c:out value="/article?id=${thread.id}"/>>編輯發文串</a>
									      <a class="dropdown-item" href="#" data-toggle="modal" data-target="#thread-warning-modal">刪除發文串</a>									      
									    </div>
										<mylib:warning-modal id="thread-warning-modal" action="sendDeleteRequest('/article?id=${thread.id}')">
											你確定要刪除這個發文串嗎？
										</mylib:warning-modal>									    
									</c:when>
									<c:otherwise>
										<button class="my-2 btn" type="button" data-toggle="dropdown"> 
											<i class="fa fa-ellipsis-v" aria-hidden="true"></i>
 										</button>
										<div class="dropdown-menu">
									      <a class="dropdown-item" href="/comment?id=${comment.id}">編輯留言</a>
									      <a class="dropdown-item" href="#" data-toggle="modal" data-target="#comment-warning-modal-${commentIndex}">刪除留言</a>
									    </div>
									    <mylib:warning-modal id="comment-warning-modal-${commentIndex}" 
									    		action="sendDeleteRequest('/comment?tid=${thread.id}&cid=${comment.id}')">
											你確定要刪除這個留言嗎？
										</mylib:warning-modal>
									    
									</c:otherwise>
								</c:choose>
							</div>
						</c:if>
						
					</div>
				</div>
				
			</c:forEach>
			
			<c:choose>
				<c:when test="${empty sessionScope.user}">
					<div class="row d-flex justify-content-center shadow info-box">
						<h5 class="my-5">
							<a href="/login">必須登入才能留言</a>
						</h5>
					</div>
				</c:when>
				<c:otherwise>
					<div class="row shadow mb-3 info-box">
						<div class="col-sm-2 user-box">
							<div class="my-sm-3 pfp-wrapper">
								<img src=<c:out value="${sessionScope.pfp}" /> width="80" height="80" 
								class="mx-auto d-block border border-success rounded-circle border-3"/>
								<div class="text-center mt-2">
									<a href=<c:out value="/profile?name=${sessionScope.user}" />  class="text-secondary"> 
										<c:out value="${sessionScope.user}" /> 
									</a>
								</div>
							</div>
						</div>
						<div class="col-sm-10">
							<div class="text-secondary my-2">
								留言框
							</div>
							<hr class="m-0"/>
							<form class="needs-validation my-3" action='<c:out value="${requestScope.commentURL}" />' 
																method="post" novalidate>
				  			  <div class="form-group countable mb-0">
							    <textarea class="form-control" rows="5" minlength="10" maxlength="1500"
							    	 id="comment" name="comment" placeholder="寫下你的評論" required></textarea>
							    <div class="text-counter hide" ></div>
							  </div>			
							  <div class="d-flex justify-content-end">
							  	<button type="submit" class="btn btn-success">留言</button>  
							  </div>			
						  	</form>	
		
						</div>
					</div>
				</c:otherwise>
			</c:choose>			
		</div>
		<div class="d-flex justify-content-center">
			<mylib:pagination  url="${requestScope.currentUrl}" 
				start="${pagination.start}" end="${pagination.end}" current="${pagination.current}" last="${pagination.last}" />
		</div>
					
	</jsp:body>
</mylib:generic>