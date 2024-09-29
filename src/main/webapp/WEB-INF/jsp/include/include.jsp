<%--------------------------------------------------------
 FileName        : cmmHead.jsp
 Description     : JSP 공통 헤더 파일
----------------------------------------------------------%>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ page import = "java.util.Calendar" %>
<%@ page import = "java.util.Date" %>
<%@ page import = "java.text.SimpleDateFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- <%@ page import="org.rnt.com.session.SessionManager" %> --%>
<%-- <%@ page import="org.rnt.com.session.SessionData" %> --%>
<%
// SessionData sessionData = SessionManager.getUserData();
// String userId = sessionData.getUserId();
// String userName = sessionData.getUserName();
// String shortId = sessionData.getShortId();
// String sabunId = sessionData.getSabunId();
// String roleId = sessionData.getRoleId();
// String mobileYn = sessionData.getMobileYn();
// String levelCd  = sessionData.getRoleId();

/** 로컬일때 **/
// if("0:0:0:0:0:0:0:1".equals(pageContext.getRequest().getRemoteAddr())) {
// 	mobileYn = "N";
	
// 	if (request.getRequestURI().indexOf("/mobile/") > -1 ) {
// 		mobileYn = "Y";
// 	}
// }
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<link rel="shortcut icon" href="<c:url value='/img/favicon.ico'/>" />
<!-- =================================================================================================================== -->
<!-- JQUERY                                                                                                              -->
<!-- =================================================================================================================== -->
<script type="text/javascript" src="<c:url value='/resource/js/ext/jquery/jquery-1.9.1.min.js'/>" ></script>
<link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">
<!-- =================================================================================================================== -->
<!-- SBADMIN(bootstrap) : https://startbootstrap.com/templates/sb-admin/ 참조                                             -->
<!-- =================================================================================================================== -->
<%
//    if ("Y".equals(mobileYn)) {
%>
<%-- <link type="text/css" rel="stylesheet" href="<c:url value='/resource/css/sb_admin_mobile_style.css'/>" /> --%>
<%
//    } else {
%>
<link type="text/css" rel="stylesheet" href="<c:url value='/resource/css/sb_admin_style.css'/>" />
<%
//    }
%>
<link type="text/css" rel="stylesheet" href="<c:url value='/resource/css/dataTables.bootstrap4.min.css'/>" />
<link type="text/css" rel="stylesheet" href="<c:url value='/resource/css/bootstrap-select.min.css'/>">
<script type="text/javascript" src="<c:url value='/resource/js/ext/bootstrap.bundle.min.js'/>" ></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js" crossorigin="anonymous"></script>

<!-- =================================================================================================================== -->
<!-- JQUERY-UI : https://jqueryui.com/                                                                                   -->
<!-- =================================================================================================================== -->
<link type="text/css" rel="stylesheet" href="<c:url value='/resource/css/jquery-ui.css'/>" />
<script type="text/javascript" src="<c:url value='/resource/js/ext/jquery/jquery-ui.js'/>" ></script>

<!-- =================================================================================================================== -->
<!-- JQUERY-TREE : https://www.jstree.com/                                                                               -->
<!-- =================================================================================================================== -->
<link type="text/css" rel="stylesheet" href="<c:url value='/resource/css/jquery.treeview.css'/>" />
<script type="text/javascript" src="<c:url value='/resource/js/ext/jquery/jstree.js'/>" ></script>

<!-- =================================================================================================================== -->
<!-- JQUERY-BARCODE                                                                                                      -->
<!-- =================================================================================================================== -->
<script type="text/javascript" src="<c:url value='/resource/js/ext/barcode/jquery-barcode.js'/>" ></script>

<!-- =================================================================================================================== -->
<!-- JQUERY-QRCODE                                                                                                       -->
<!-- =================================================================================================================== -->
<script type="text/javascript" src="<c:url value='/resource/js/ext/qrcode/qrcode.js'/>" ></script>

<!-- =================================================================================================================== -->
<!-- JQUERY-BARCODE-SCAN                                                                                                 -->
<!-- =================================================================================================================== -->
<script type="text/javascript" src="<c:url value='/resource/js/ext/barcode/jquery.scannerdetection.js'/>" ></script>


<!-- =================================================================================================================== -->
<!-- JQUERY-IMAGE CANVAS                                                                                                 -->
<!-- =================================================================================================================== -->
<%-- <script type="text/javascript" src="<c:url value='/resource/js/ext/html2canvas.js'/>" ></script> --%>
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.5.0-beta4/html2canvas.min.js" crossorigin="anonymous"></script> -->
<script type="text/javascript" src="<c:url value='/resource/js/ext/html2canvas.min.custom.js'/>" ></script>

<!-- =================================================================================================================== -->
<!-- COMMON SCRIPT                                                                                                       -->
<!-- =================================================================================================================== -->
<script type="text/javascript" src="<c:url value='/resource/js/com/common.js'/>" ></script>
<script type="text/javascript" src="<c:url value='/resource/js/com/message.js'/>" ></script>
<script type="text/javascript" src="<c:url value='/resource/js/com/utils.js'/>" ></script>

<!-- =================================================================================================================== -->
<!-- SPINER(loading) :http://fgnass.github.io/spin.js/#! 참조                                                             -->
<!-- =================================================================================================================== -->
<script type="text/javascript" src="<c:url value='/resource/js/ext/spin/spin.min.js'/>"></script>
<script type="text/javascript" src="<c:url value='/resource/js/com/com-jquery.js'/>" ></script> 

<!-- =================================================================================================================== -->
<!-- 구글차트                                                                                                          	 -->
<!-- =================================================================================================================== -->
<script type="text/javascript" src="<c:url value='/resource/js/ext/gchart/loader.js'/>" ></script>

<!-- =================================================================================================================== -->
<!-- JQUERY-UI : select box search                                                                                   	 -->
<!-- =================================================================================================================== -->
<script type="text/javascript" src="<c:url value='/resource/js/ext/bootstrap-select.min.js'/>" ></script>

<!-- =================================================================================================================== -->
<!-- 파일업로드                                                                                                             -->
<!-- =================================================================================================================== -->
<script type="text/javascript" src="<c:url value='/resource/js/ext/fileupload/jquery.form.js'/>" ></script>

<style type="text/css">
    .table th, .table td {
        font-size: 0.9rem;
        padding: 5px 5px 5px 5px;
        font-family: -apple-system, BlinkMacSystemFont, "Segoe UI", Roboto, "Helvetica Neue", Arial, "Noto Sans", sans-serif, "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol", "Noto Color Emoji";
    }
</style>

<script type="text/javaScript" language="javascript" defer="defer">
    AJAX_TIMEOUT  = 9000;
    SERVER_ADDR   = "<c:out value="${pageContext.request.serverName}"/>";
    WAS_IP        = "<c:out value="${pageContext.request.localName}"/>";
    SERVER_PORT   = "<c:out value="${pageContext.request.serverPort}"/>";
    CTX_ROOT      = "<c:out value="${pageContext.request.contextPath}"/>";
    
    if (CTX_ROOT == '/') {
        CTX_ROOT = "";
    }
    
<%--     SHORT_ID = '<%=shortId%>'; --%>
<%--     SABUN_ID = '<%=sabunId%>'; --%>
<%--     LOGIN_USER_ID = '<%=userId%>'; --%>
<%--     LOGIN_USER_NAME = '<%=userName%>'; --%>
<%--     LEVEL_CD        = '<%=levelCd%>'; --%>
    
</script>
        