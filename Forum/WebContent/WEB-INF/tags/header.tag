<%@ tag language="java" pageEncoding="UTF-8"%>
<%@  taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ attribute name="user" required="true" %>

<header class="bg-light shadow mb-5">
	<div class="container-xl">
		<nav class="navbar navbar-expand-sm bg-light navbar-light px-2">
			<a class="navbar-brand" href="/home?genre=全部主題"> <img src="/pics/logo.png"
				height="70" />
			</a>

			<button class="navbar-toggler collapsed" type="button"
				data-toggle="collapse" data-target="#content-div"
				aria-controls="content-div" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>

			<div class="navbar-collapse collapse" id="content-div">
				<form class="form-inline m-0 needs-validation my-nav-item" action="/search" novalidate>
					<div class="input-group" id="search-field">
						<input type="text" class="form-control" placeholder="輸入關鍵字" 
							name="q" required>
						<div class="input-group-append input-group-btn">
							<button class="btn btn-secondary btn-outline-default"
								type="submit">
								<i class="fa fa-search" aria-hidden="true"></i>
							</button>
						</div>
					</div>
				</form>

				<ul class="navbar-nav ml-auto">
					<c:choose>
						<c:when test="${not empty user}">
							<li id="noti-dropdown" class="nav-item dropdown my-nav-item">
							  <a class="text-secondary nav-link text-nowrap" data-toggle="dropdown">
							    <i class="fas fa-bell"></i> 通知 
							    <span id="unseen-badge" class="badge badge-pill badge-danger text-light"></span>
							  </a>
							  <div id="notification-box" class="dropdown-menu">
							  </div>
							</li>
						
							<li class="nav-item my-nav-item"><a class="nav-link text-nowrap"
								href="/article"><i class="fa fa-comments"
									aria-hidden="true"></i> 發文 </a></li>
							<li class="nav-item my-nav-item"><a class="nav-link text-nowrap"
								href=<c:out value = "/profile?name=${user}"/>> <i
									class="fa fa-user" aria-hidden="true"></i> <c:out
										value="${user}" />
							</a></li>
							<li class="nav-item my-nav-item"><a class="nav-link text-nowrap"
								href="/logout"> <i class="fas fa-sign-out-alt"></i>
									登出
							</a></li>
						</c:when>
						<c:otherwise>
							<li class="nav-item my-nav-item"><a class="nav-link text-nowrap"
								href="/signup"><i class="far fa-edit"></i> 註冊 </a></li>
							<li class="nav-item my-nav-item"><a class="nav-link text-nowrap"
								href="/login"><i class="fas fa-sign-in-alt"></i> 登入 </a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
		</nav>
	</div>
</header>