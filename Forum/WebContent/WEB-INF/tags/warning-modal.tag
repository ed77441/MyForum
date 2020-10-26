<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ attribute name="id" required="true" %>
<%@ attribute name="action" required="true" %>

 <div class="modal fade" id="${id}">
  <div class="modal-dialog modal-sm modal-dialog-centered">
    <div class="modal-content">
    
      <!-- Modal Header -->
      <div class="modal-header bg-warning text-light">
        <h5 class="modal-title ">
        	<i class="fas fa-exclamation-triangle"></i>
        	警告訊息
        	</h5>
        <button type="button" class="close" data-dismiss="modal">&times;</button>
      </div>
      
      <!-- Modal body -->
      <div class="modal-body my-3 text-center">
		<jsp:doBody />		     
      </div>
      
      <!-- Modal footer -->
      <div class="modal-footer">
        <button type="button" class="btn btn-warning text-light"
				onclick="${action}">確認</button>
        <button type="button" class="btn border border-warning text-warning cancel-btn"
				data-dismiss="modal">關閉</button>
      </div>
    </div>
  </div>
</div>
