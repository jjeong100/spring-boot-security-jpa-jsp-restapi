<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"  uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form"      uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<script type="text/javascript">
<!--
function fn_go_menu(pageUrl, menuId, menuTopNm, menuNm){
    frm = document.headMenuObjForm; 
    frm.menuId.value = menuId;
    frm.menuTopNm.value = menuTopNm;
    frm.menuNm.value = menuNm;
    frm.pageUrl.value = pageUrl;
    frm.action = "<c:url value='/goTopMenuPage.do'/>";
    frm.submit();
}

(function($) {
    $(function(){
    	$("#loginUserNm").html(LOGIN_USER_NAME);
    	$("#topMenuNm").html('${MY_TOP_MENU_NM}');
    	$("#menuNm").html('${MY_MENU_NM}');
    });
})(jQuery);
//-->
</script>

<body>
<form:form commandName="menuInfoVO" id="headMenuObjForm" name="headMenuObjForm">
    <input type="hidden" name="menuId" value=""/>
    <input type="hidden" name="menuTopNm" value=""/>
    <input type="hidden" name="menuNm" value=""/>
    <input type="hidden" name="pageUrl" value=""/>
</form:form>
<div id="layoutSidenav_nav">
    <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
        <div class="sb-sidenav-menu">
            <div class="nav" id="boardHTML">
                <c:forEach var="menu" items="${MY_MENU_LIST}" varStatus="status" >
                    <c:if test="${menu.menuLvl eq 1}">
                      <!-- 1차 메뉴 -->
                      <c:choose>
                         <c:when test="${menu.menuId eq MY_TOP_MENU_ID}">
                             <a class="nav-link" href="#" data-toggle="collapse" data-target="#collapseLayouts<c:out value='${menu.menuId}'/>" aria-expanded="true" aria-controls="collapseLayouts<c:out value='${menu.menuId}'/>">
                         </c:when>
                         <c:otherwise>
                             <a class="nav-link collapsed" href="#" data-toggle="collapse" data-target="#collapseLayouts<c:out value='${menu.menuId}'/>" aria-expanded="false" aria-controls="collapseLayouts<c:out value='${menu.menuId}'/>">
                         </c:otherwise>
                      </c:choose>
                          <div class="sb-nav-link-icon"><i class="fas fa-columns"></i></div>
                          <c:out value='${menu.menuNm}'/> 
                          <div class="sb-sidenav-collapse-arrow"><i class="fas fa-angle-down"></i></div>
                      </a>
                      <!-- 2차 메뉴 -->
                      <c:choose>
                         <c:when test="${menu.menuId eq MY_TOP_MENU_ID}">
                             <div class="collapse show" id="collapseLayouts<c:out value='${menu.menuId}'/>" aria-labelledby="headingOne" data-parent="#sidenavAccordion">
                         </c:when>
                         <c:otherwise>
                             <div class="collapse" id="collapseLayouts<c:out value='${menu.menuId}'/>" aria-labelledby="headingOne" data-parent="#sidenavAccordion">
                         </c:otherwise>
                      </c:choose>
                          <nav class="sb-sidenav-menu-nested nav">
                              <c:forEach var="menu2" items="${MY_MENU_LIST}" varStatus="status" >
                                  <c:if test="${menu2.menuLvl eq 2 && menu2.upMenuId eq menu.menuId}">
                                      <a href="javascript:fn_go_menu('<c:out value='${menu2.pageUrl}'/>','<c:out value='${menu.menuId}'/>','<c:out value='${menu.menuNm}'/>','<c:out value='${menu2.menuNm}'/>');" id="menuSubId" class="nav-link" onclick=""><c:out value='${menu2.menuNm}'/> </a>
                                  </c:if>
                               </c:forEach>
                          </nav>
                      </div>
                    </c:if>
                </c:forEach>
            </div>
        </div>
        <!--<div class="sb-sidenav-footer">
            <div class="small">Logged in as:</div>
            <span id="loginUserNm"></span>
        </div>-->
    </nav>
</div>
</body>
