<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"      uri="http://java.sun.com/jsp/jstl/core" %>

<div id="glv-dialog-error-message" title="에러 확인">
    <div style="font-size:1.65em; text-align:center; margin-top:20px;">
        <p id="glv-err-msg"></p>
    </div>
</div>

<div id="glv_del_confirm" title="확인" style="display:none;">
    <div style="font-size:1.3em; text-align:center; margin-top:20px;">
        <p>삭제 하시겠습니까 ?</p>
    </div> 
</div> 

<div id="glv_upd_confirm" title="확인" style="display:none;">
    <div style="font-size:1.3em; text-align:center; margin-top:20px;">
        <p>변경 하시겠습니까 ?</p>
    </div> 
</div>

<div id="glv_ins_confirm" title="확인" style="display:none;">
    <div style="font-size:1.3em; text-align:center; margin-top:20px;">
        <p>생성 하시겠습니까 ?</p>
    </div> 
</div>

<div id="glv-dialog-alert" title="확인">
    <div style="font-size:1.65em; text-align:center; margin-top:20px;">
        <p id="glv-dialog-alert-msg"></p>
    </div>
</div>


<div id="glv-dialog-alert-and-go" title="확인">
    <div style="font-size:1.65em; text-align:center; margin-top:20px;">
        <p id="glv-dialog-alert-and-go-msg"></p>
    </div>
    <div id="glvForm" style="display:none;"></div>
    <div id="glvLink" style="display:none;"></div>
</div>

<div id="glv_confirm" title="확인" style="display:none;">
    <div style="font-size:1.3em; text-align:center; margin-top:20px;">
        <p id="glv-confirm-msg"></p>
    </div> 
</div>

<script>
    $( "#glv-dialog-error-message" ).dialog({
        autoOpen: false,
        modal: true,
        width: 500,
        height: 235,
        buttons: {
            "확인": function() {
              $( this ).dialog( "close" );
            }
        }
    });
    
    
    $( "#glv-dialog-alert" ).dialog({
        autoOpen: false,
        resizable: false,
        width: 500,
        height: 200,
        modal: true,
        buttons: {
            "확인": function() {
                $( this ).dialog( "close" );
                return true;
            }
        }
    });
    
    $( "#glv-dialog-alert-and-go" ).dialog({
        autoOpen: false,
        resizable: false,
        width: 500,
        height: 200,
        modal: true,
        buttons: {
            "확인": function() {
                $("#layoutSidenav").loadingClose();
                $( this ).dialog( "close" );
                $('#'+$("#glvForm").html()).attr("action", $("#glvLink").html()).submit();
                return true;
            }
        }
    });
    
</script>