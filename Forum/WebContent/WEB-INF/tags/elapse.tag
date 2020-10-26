<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>

<%@ attribute name="timestamp" required="true" %>

<span class="time-box">
	<span class="time-value d-none">
		<c:out value="${timestamp}" /> 
	</span>
							
	<span class="time-display" data-toggle="tooltip" 
			title='<c:out value="${fn:substring(timestamp, 0, 19)}" />' >
	</span>
</span>
