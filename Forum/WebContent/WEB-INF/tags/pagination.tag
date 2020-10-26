<%@ tag language="java" pageEncoding="UTF-8"%>
<%@  taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@ attribute name="url" required="true" %>
<%@ attribute name="current" required="true" %>
<%@ attribute name="start" required="true" %>
<%@ attribute name="end" required="true" %>
<%@ attribute name="last" required="true" %>


<ul class="pagination">
	<c:set var="appending" value="?" />
	<c:if test="${fn:contains(url, '?')}">
		<c:set var="appending" value="&"/>
	</c:if>

	<li class="page-item">
		<a class="page-link" href="${url}">
			<i class="fas fa-angle-double-left"></i>
		</a>
	</li>
	
	<li class="page-item">
		<c:set var="pageNumber" value="${current - 1}"/>
		<c:set var="pageParam" value="${appending}page=${pageNumber}"/>
		<c:if test="${pageNumber le 1}">
			<c:set var="pageParam" value=""/>			
		</c:if>
	
		<a class="page-link" href="${url}${pageParam}">
			<i class="fas fa-angle-left"></i>
		</a>
	</li>

	<c:forEach begin="${start}" end="${end}" varStatus="status">
		<c:set var="index" value="${status.index}"/>
		<c:set var="pageParam" value="${appending}page=${index}" />
		<c:set var="activeTag" value="" />
		
		<c:if test="${index eq 1}">
			<c:set var="pageParam" value=""/> 
		</c:if>
		<c:if test="${index eq current}"> 
			<c:set var="activeTag" value="active" />
		</c:if>
		
		<li class='<c:out value="page-item ${activeTag}"/>'>
			<a class="page-link" href='<c:out value="${url}${pageParam}"/>'>
				<c:out value="${index}" />
			</a>
		</li>
		
	</c:forEach>
	
	<li class="page-item">
		<c:set var="pageNumber" value="${current + 1}"/>
		<c:set var="pageParam" value="${appending}page=${pageNumber}"/>
		<c:if test="${pageNumber gt end}">
			<c:set var="pageParam" value="${appending}page=${end}"/>			
		</c:if>
		<c:if test="${end eq 1}">
			<c:set var="pageParam" value=""/>			
		</c:if>
		<a class="page-link" href="${url}${pageParam}">
			<i class="fas fa-angle-right"></i>
		</a>
	</li>
	
	
	<li class="page-item">
		<c:set var="pageParam" value="${appending}page=${last}"/>
		<c:if test="${last eq 1}">
			<c:set var="pageParam" value=""/>			
		</c:if>
		
		<a class="page-link" href="${url}${pageParam}">
			<i class="fas fa-angle-double-right"></i>
		</a>
	</li>
	
</ul>
