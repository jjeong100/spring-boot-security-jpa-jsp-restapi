<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"         uri="http://java.sun.com/jsp/jstl/core" %><head>
<script type="text/javascript">
    function fn_sidebarToggle() {
		if ($("#layoutSidenav_nav").is(":visible")) {
			$("#layoutSidenav_nav").hide();
		} else {
			$("#layoutSidenav_nav").show();
		}
	}
</script>
</head>
<nav class="sb-topnav navbar navbar-expand navbar-dark bg-dark">
    <a class="navbar-brand" href="<c:url value='/dashBoardPage.do'/>">Power MES-삼보산업</a> 
    <button class="btn btn-link btn-sm order-1 order-lg-0" onclick="fn_sidebarToggle()" id="sidebarToggle" href="#"><i class="fas fa-bars"></i></button>
    <!-- Navbar Search-->
    <div class="input-group"> 
        <ol class="breadcrumb-top mb-4"> 
            <li id="topMenuNm" class="breadcrumb-top-item"></li>
            <li id="menuNm" class="breadcrumb-top-item active"></li>
        </ol>
    </div>
    <!-- Navbar-->
    <ul class="navbar-nav ml-auto ml-md-0">
        <li class="nav-item dropdown">
            <a class="nav-link dropdown-toggle" id="userDropdown" href="#" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><i class="fas fa-user fa-fw"></i></a>
            <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
                <!--  <div class="dropdown-divider"></div> 서브메뉴 분류 라인 --> 
                 <div class="login_txt">Logged in as: <div id="loginUserNm" class="font-weight-bold text-primary"></div></div>
                     
                <a class="dropdown-item" href="<c:url value='/logoutAct.do'/>"><span class="grayBox_r6">Logout</span></a>
            </div>
        </li>
    </ul>
</nav>
