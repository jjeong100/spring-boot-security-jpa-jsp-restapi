//-------------------------------------------------------------------
// Context Root 정보
//-------------------------------------------------------------------
var CTX_ROOT='/ndata';

var MY_PAGE;
//-------------------------------------------------------------------
//site common 
//-------------------------------------------------------------------
function cfCheckRtnObj(rtn) {
    if (rtn.rc != 0) {
        $("#glv-err-msg").html(rtn.msg);
        $("#glv-dialog-error-message" ).dialog( "open" );
        return false;
    }
    return true;
}

function cfConfirm(msg, callback) {
    var html = "";
    html += "<div id='glv-dialog-confirm-dynamic' title='확인'>";
    html += "    <div style='font-size:1.65em; text-align:center; margin-top:20px;'>";
    html += "        <p id='glv-dialog-alert-msg'>" + msg + "</p>";
    html += "    </div>";
    html += "</div>";
    
    // 중복방지
    if( $("#glv-dialog-confirm-dynamic").length > 0 ) {
        return false;
    }
    
    $(html).dialog({
        autoOpen: false,
        resizable: false,
        width: 500,
        height: 200,
        modal: true,
        buttons: [
            {
                text : "확인",
                click : function() {
                    $(this).remove();
                    if( typeof callback == "function") {
                        callback();
                    }
                    return true;
                }
            },
            {
                text : "취소",
                click : function() {
                    $(this).remove();
                    return false;
                }
            }
        ]
    }).dialog("open");
}

function cfAlert(msg) {
//    $("#glv-dialog-alert-msg").html(msg);
//    $("#glv-dialog-alert" ).dialog( "open" );
    
    var alertHtml = "";
    alertHtml += "<div id='glv-dialog-alert-dynamic' title='확인'>";
    alertHtml += "    <div style='font-size:1.65em; text-align:center; margin-top:20px;'>";
    alertHtml += "        <p id='glv-dialog-alert-msg'>" + msg + "</p>";
    alertHtml += "    </div>";
    alertHtml += "</div>";
    
    // 중복방지
    if( $("#glv-dialog-alert-dynamic").length > 0 ) {
        return false;
    }
    
    $(alertHtml).dialog({
        autoOpen: false,
        resizable: false,
        width: 500,
        height: 200,
        modal: true,
        buttons: {
            "확인": function() {
                //$( this ).dialog( "close" );
                $( this ).remove();
                return true;
            }
        }
    }).dialog("open");
}

function cfAlertAnGo(msg, callback) {
    $("#glv-dialog-alert-and-go-msg").html(msg);
    $("#glv-dialog-alert-and-go" ).dialog( "open" );
}

function cfAjaxCall(url,objForm,msg) {
    $.ajax({
        url : url,
        type: 'POST',
        data: objForm,
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        context:this,
        dataType:'json',
        beforeSend:function(){
            $("#layoutSidenav").loading();
        },
        success: function(data) {
            $("#layoutSidenav").loadingClose();
            if(!cfCheckRtnObj(data.rtn)) return;
            cfAlert(msg);
        },
        error: function(request, status, error){
            console.log("code = "+ request.status + " message = " + request.responseText + " error = " + error);
            cfAlert('시스템 오류입니다.');
        },
        complete:function(){
            $("#layoutSidenav").loadingClose();
        }
    });
}

function cfAjaxCallAndGoLink(url,objForm,msg,frm,link) {
    $.ajax({
        url : url,
        type: 'POST',
        data: objForm,
        contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
        context:this,
        dataType:'json',
        beforeSend:function(){
            $("#layoutSidenav").loading();
        },
        success: function(data) {
            console.log(data);
            if(!cfCheckRtnObj(data.rtn)) return;
            $("#layoutSidenav").loadingClose();
            $( "#glvForm" ).html(frm);
            $( "#glvLink" ).html(link);
            cfAlertAnGo(msg);
        },
        error: function(request, status, error){
            console.log("code = "+ request.status + " message = " + request.responseText + " error = " + error);
            cfAlert('시스템 오류입니다.');
        },
        complete:function(){
            $("#layoutSidenav").loadingClose();
        }
    });
}

function cfAjaxFileCallAndGoLink(url,objForm,msg,frm,link) {
    objForm.ajaxSubmit({
        url : url,
        type: 'POST',
        processData : false,  // file전송시 필수
        contentType : false,  // file전송시 필수
        data: objForm.serialize(),
        context:this,
        dataType:'json',
        beforeSend:function(){
            $("#layoutSidenav").loading();
        },
        success: function(data) {
            if(!cfCheckRtnObj(data.rtn)) return;
            $("#layoutSidenav").loadingClose();
            $( "#glvForm" ).html(frm);
            $( "#glvLink" ).html(link);
            cfAlertAnGo(msg);
        },
        error: function(request, status, error){
            console.log("code = "+ request.status + " message = " + request.responseText + " error = " + error);
            cfAlert('시스템 오류입니다.');
        },
        complete:function(){
            $("#layoutSidenav").loadingClose();
        }
    });
}

function cfAjaxCallAndCallback(param) {
    
    ajaxval = {
            url            : param.url != null ? param.url:"",
            type        : "post",
            data        : param.data != null ? param.data:{},
            contentType    : "application/x-www-form-urlencoded; charset=UTF-8",
            dataType    : "json",
            msg            : param.msg != null ? param.msg:""
    };
    
    $.ajax({
        url            : ajaxval.url,
        type        : "post",
        data        : ajaxval.data,
        contentType    : ajaxval.contentType,
        context        : this,
        dataType    : ajaxval.dataType,
        beforeSend    : function(){
            $("#layoutSidenav").loading();
        },
        success        : function(data) {
            $("#layoutSidenav").loadingClose();
            if(!cfCheckRtnObj(data.rtn)) return;
//            cfAlert(ajaxval.msg);
            
            if( typeof param.callback == "function" ) {
                param.callback(data);
            }
        },
        error        : function(request, status, error){
            console.log("code = "+ request.status + " message = " + request.responseText + " error = " + error);
            cfAlert('시스템 오류입니다.');
        },
        complete    : function(){
            $("#layoutSidenav").loadingClose();
        }
    });
}


//-------------------------------------------------------------------
// site common
//-------------------------------------------------------------------
function on(obj) {
    obj.src = obj.src.replace('_off.png','_on.png');
    obj.style.cursor = 'pointer';
}

function off(obj) {
    obj.src = obj.src.replace('_on.png','_off.png');
    obj.style.cursor = 'pointer';
}

// <!-- Link -->
function admin0101(){window.location="../fdps_admin/0101.html";} //수집관리 > 작업결과조회
function admin0102(){window.location="../fdps_admin/0102.html";} //수집관리 > 수집통계현황
function admin0201(){window.location="../fdps_admin/0201.html";} //사용자관리 > 사용자관리
function admin0201_reg(){window.location="../fdps_admin/0201_reg.html";} //사용자관리 > 사용자등록
function admin0201_mod(){window.location="../fdps_admin/0201_mod.html";} //사용자관리 > 사용자수정
function admin_login(){window.location="../fdps_admin/login.html";} //사용자관리 > 사용자수정




/**
 * Table을 매개변수로 Excel파일을 생성하고 생성된 파일을 다운로드한다.
 * @param table
 * @param startIndex
 * @param endIndex
 */
function downloadExcelFromTable(table) {
    
    var json = '';
    
    // Table 헤더를 제외한 tr 갯수가 없는 경우 리턴
    if (table.find('tr').find('.no-data').length >= 1) {
        alert("데이터가 없습니다.");
        return false;
    }
    
    json += '[';
    table.find('tr').each(function(trIndex, element) {
        if (trIndex != 0) {
            json += ',';
        }
        
        json += '{';
        $(this).find('th').not('.no-sort').each(function(thIndex, element) {
            if (thIndex != 0) {
                json += ',';
            }
            json += '"'+$(this).attr("id")+'":"'+$(this).text().replace(/\n/gi, "").replace(/\t/gi, "").trim()+'"';
        });
        
        $(this).find('td').not('.no-sort').find('span').each(function(tdIndex, element) {
            if (tdIndex != 0) {
                json += ',';
            }
            json += '"'+$(this).attr("class")+'":"'+$(this).text().replace(/\n/gi, "").replace(/\t/gi, "").trim()+'"';
        });
        json += '}';
    });
    json += ']';
    
    $.ajax({
        url : 'makeListTypeExcel.do',
        type: 'POST',
        data: json,
        contentType: "application/json; charset=UTF-8",
        context : this,
        dataType: 'json',
        success: function(data) {
            var url = "downloadFile.do"
                    + "?folderName="+data.folderName
                    + "&realFileName="+data.realFileName
                    + "&saveFileName="+data.saveFileName;
            downloadFile(url);
        },
        error: function(){
            alert("시스템 오류입니다.\n관리자에게 문의해주세요.");
        }
    });
}

/**
 * Table을 매개변수로 Excel파일을 생성하고 생성된 파일을 다운로드한다.
 * @param table
 * @param startIndex
 * @param endIndex
 */
function downloadExcelFromTableInput(table) {
    
    var json = '';
    
    // Table 헤더를 제외한 tr 갯수가 없는 경우 리턴
    if (table.find('tr').find('.no-data').length >= 1) {
        alert("데이터가 없습니다.");
        return false;
    }
    
    json += '[';
    table.find('tr').each(function(trIndex, element) {
        if (trIndex != 0) {
            json += ',';
        }
        
        json += '{';
        $(this).find('th').not('.no-sort').each(function(thIndex, element) {
            if (thIndex != 0) {
                json += ',';
            }
            json += '"'+$(this).attr("id")+'":"'+$(this).text().replace(/\n/gi, "").replace(/\t/gi, "").trim()+'"';
        });
        
        $(this).find('td').not('.no-sort').find('span').each(function(tdIndex, element) {
            if (tdIndex != 0) {
                json += ',';
            }
            if($(this).find("input[type='text']").length > 0){//input text 확인
                json += '"'+$(this).attr("class")+'":"'+$(this).find("input[type='text']").val()+'"';
            }else if($(this).find("select").length > 0){//input select 확인
                json += '"'+$(this).attr("class")+'":"'+$(this).find(":selected").text()+'"';
            }else{//기타
                json += '"'+$(this).attr("class")+'":"'+$(this).text().replace(/\n/gi, "").replace(/\t/gi, "").trim()+'"';
            }
        });
        json += '}';
    });
    json += ']';
    
    $.ajax({
        url : 'makeListTypeExcel.do',
        type: 'POST',
        data: json,
        contentType: "application/json; charset=UTF-8",
        context : this,
        dataType: 'json',
        success: function(data) {
            var url = "downloadFile.do"
                    + "?folderName="+data.folderName
                    + "&realFileName="+data.realFileName
                    + "&saveFileName="+data.saveFileName;
            downloadFile(url);
        },
        error: function(){
            alert("시스템 오류입니다.\n관리자에게 문의해주세요.");
        }
    });
}

function downloadFile(url) {
    //alert("url===="+url);
    var newIfrm = document.createElement("iframe");
    //newIfrm.src = url;
    newIfrm.src = encodeURI(url);    //스크립트에서 기존파라미터(특수문자,공백포함)넘길떄 encodeURI로 감싸서 보내준다.
    //alert(newIfrm.src);
    newIfrm.style.display = 'none';
    document.body.appendChild(newIfrm);
}



//-------------------------------------------------------------------
// 문자열 처리 함수
//-------------------------------------------------------------------

/**
 * @type   : function
 * @access : public
 * @desc   : 값이 null 이거나 white space 문자로만 이루어진 경우 true를 리턴한다.
 * <pre>
 *     cfIsNull("  ");
 * </pre>
 * 위와같이 사용했을 경우 true를 리턴한다.
 * @sig    : value
 * @param  : value - required 입력값
 * @return : boolean. null(혹은 white space) 여부
 * @author : 박재현
 */
function cfIsNull(value) {
    if ( typeof value == "undefined" ) {
        return true;
    }

    if (value == null || value == "null" || (typeof(value) == "string" && value.trim() == "") ) {
        return true;
    }
    return false;
}

/**
 * @type   : function
 * @access : public
 * @desc   : 값이 null 이거나 white space 문자로만 이루어진 경우 공백문자 또는 대체문자를 리턴한다.
 * <pre>
 *     cfIsNull("  ");
 *     cfIsNull("  ", "1");
 * </pre>
 * 위와같이 사용했을 경우 공백문자 또는 대체문자를 리턴한다.
 * @sig    : value
 * @param  : value - required 입력값
 * @param  : defaultVal - null 대체문자
 * @return : "" 또는 defaultVal 
 * @author : 이봉규
 */
function cfIsNullString(value, defaultVal) {
    if( cfIsNull(defaultVal) ) {
        defaultVal = "";
    }
     
    if ( cfIsNull(value) ) {
        return defaultVal;
    }
    
    return value;
}

/**
* @type   : function
* @access : public
* @desc   : 문자필드의 길이를 체크한다.
* @param  : sParam - 문자값
* @return : boolean
* @author : 박재현
*/
function cfGetByteLength(param) {

   var blen = 0;
   param= String(param);
   var tmp;

   for (var i=0;i<param.length;i++) {
       tmp = escape(param.charAt(i));
       if(tmp.length==1)
           blen = blen + 1;
       else if(tmp.indexOf("%u") != -1)
           blen = blen + 2;
       else if(tmp.indexOf("%") != -1)
           blen = blen + tmp.length/3;
   }
   return blen;
}
/**
 * @type   : function
 * @access : public
 * @desc   : 문자열 중간의 공백을 없애는 함수
 * <pre>
 *     var resultStr = cfSkipSpaces("ab  cd");
 * </pre>
 * 위의 예에서 resultStr 은 "abcd" 가 된다.
 * @sig    : str
 * @param  : str      - 공백이 포함된 문자열
 * @return : 공백이 제거된 문자열
 * @author : 박재현
 */
function cfSkipSpaces(str) {
    var newStr = "";
    for (var i=0; i< str.length; i++) {
        if ((str.substr(i,1) != " ") && str.substr(i,1) != "    ") {
            newStr = newStr + str.substr(i,1);
        }
    }
    return newStr;
}

function cfParseIn(str, start, end) {
    var newStr = "";
    var tmp = str.split(start);
    if (cfIsNull(tmp[1])) {
        return newStr;
    }
    
    var tmp2 = tmp[1].split(end);
    if (cfIsNull(tmp2[0])) { 
        return newStr;
    }
    newStr = tmp2[0];
    return newStr;
}

/**
 * @type   : prototype_function
 * @access : public
 * @desc   : 자바스크립트의 내장 객체인 String 객체에 replaceAll 메소드를 추가한다.
             replaceAll 메소드는 스트링의 문자열 내에 포함된 모든 특정 문자열을 다른 문자열로 대체한다.
 * @return : 대체된 String.
 * @author : 박재현
 */
String.prototype.replaceAll = function(str1, str2) {
    var temp_str = "";
    if( this.trim() != "" && str1 != str2 ){
        temp_str = this.trim();
        while(temp_str.indexOf(str1)>-1){
            temp_str = temp_str.replace(str1, str2);
        }//end while
    }//end if
    return temp_str;
}//end function

/**
 * @type   : prototype_function
 * @access : public
 * @desc   : 자바스크립트의 내장 객체인 String 객체에 trim 메소드를 추가한다. trim 메소드는 스트링의 앞과 뒤에
 *           있는 white space를 제거한다.
 * <pre>
 *     var str = " abcde "
 *     str = str.trim();
 * </pre>
 * 위의 예에서 str는 "abede"가 된다.
 * @return : white space가 제거된 문자열
 * @author : 박재현
 */
String.prototype.trim = function() {
    return this.replace(/(^\s*)|(\s*$)/g, "");
}

/**
 * @type   : prototype_function
 * @access : public
 * @desc   : 자바스크립트의 내장 객체인 String 객체에 stripSpecial 메소드를 추가한다.
 *           stripSpecial 메소드는 스트링에 포함된 특수문자를 제거한다.
 * <pre>
 *     var str = " (*-abcde) "
 *     str = str.stripSpecial();
 * </pre>
 * 위의 예에서 str는 "abede"가 된다.
 * @return : 특수문자가 제거된 문자열
 * @author : 박재현
 */
String.prototype.stripSpecial = function() {
    if( !cfIsNull(this) && "" != this.name ) {
        return this.replace(/(\(|\)|\[|\]|\{|\}|\<|\>|\"|\'|\`|\~|\$|\!|\#|\%|\^|\&|\@|\,|\.|\;|\:|\\|\/|\||\*|\=|\-|\?)*/g,"");
    }
}

/**
 * @type   : prototype_function
 * @access : public
 * @desc   : 자바스크립트의 내장 객체인 String 객체에 stripSpecialNum 메소드를 추가한다.
 *           stripSpecial 메소드는 스트링에 포함된 특수문자를 제거한다.
 *           stripSpecial()과 동일하나 '-' 기호를 제거하지 않는다는 점이 다르다.
 * <pre>
 *     var str = " (*-abcde) "
 *     str = str.stripSpecial();
 * </pre>
 * 위의 예에서 str는 "-abede"가 된다.
 * @return : trimed String.
 * @author : 박재현
 */
String.prototype.stripSpecialNum = function() {
    if( !cfIsNull(this) && "" != this.name ) {
        return this.replace(/(\(|\)|\[|\]|\{|\}|\<|\>|\"|\'|\`|\~|\$|\!|\#|\%|\^|\&|\@|\,|\.|\;|\:|\\|\/|\||\*|\=|\?)*/g,"");
    }
}

//-------------------------------------------------------------------
// 메시지 출력 함수
//-------------------------------------------------------------------

/**
 * @type   : function
 * @access : public
 * @desc   : message.js에 정의된 메세지를 alert box로 보여준 후 리턴한다. cfGetMsg 참조.
 * @sig    : msgId[, paramArray]
 * @param  : msgId - required - common.js의 공통 메세지 영역에 선언된 메세지 ID
 * @param  : paramArray - optional - 메세지에서 '@' 문자와 치환될 데이터 Array.
             Array의 index와 메세지 내의 '@' 문자의 순서가 일치한다.
               치환될 데이터는 [] 사이에 콤마를 구분자로 하여 기술하면 Array 로 인식된다.
 * @author : 박재현
 */
function cfAlertMsg(msgId, paramArray) {
    if (cfIsNull(msgId)) {
        cfAlertMsg(MSG_COM_ERR_003);
    }
    var obj_msg = document.getElementById("glv-msg"); 
    obj_msg.innerHTML = new coMessage().getMsg(msgId, paramArray);
    
    var obj = document.getElementById("glv-popup-alert"); 
    obj.style.width = document.body.scrollWidth + 'px'; 
    obj.style.height = document.body.scrollHeight + 'px';
    obj.style.display = "block";
//    cfAlertDialog(new coMessage().getMsg(msgId, paramArray));
}

function cfAlertMsgClose() {
    var obj = document.getElementById("glv-popup-alert");
    obj.style.display = "none";
}


var glv_is_confirm = false;
function cfConfirmMsg(msg) {
    document.getElementById("glv-confirm-msg").innerHTML = msg; 
    var obj = document.getElementById("glv-popup-confirm"); 
    obj.style.width = document.body.scrollWidth + 'px'; 
    obj.style.height = document.body.scrollHeight + 'px';
    obj.style.display = "block";
    glv_is_confirm = false;
}

function cfConfirmCancel() {
    var obj = document.getElementById("glv-popup-confirm");
    obj.style.display = "none";
    glv_is_confirm = false;
}

function cfConfirmOk() {
    var obj = document.getElementById("glv-popup-confirm");
    obj.style.display = "none";
    glv_is_confirm = true;
}

// 로그인 에러 메세지 function cfErrorMsg(rc, msg, taskClassName, taskMethodName, taskLine, daoMsg, daoCalssName, daoMethodName, daoLine) {
function cfErrorMsg(rtn) {
    
    alert(rtn.msg);
    alert(rtn.daoExceptionMsg);
    
    document.getElementById("glv-error-msg").innerHTML = rtn.msg; 
    document.getElementById("glv-error-code").innerHTML = rtn.rc; 
    
    document.getElementById("glv-error-taskclassname").innerHTML = rtn.taskClassName; 
    document.getElementById("glv-error-taskline").innerHTML = rtn.taskLine; 
    document.getElementById("glv-error-daomsg").innerHTML = rtn.daoMsg; 
    document.getElementById("glv-error-dao-exception-msg").innerHTML = rtn.daoExceptionMsg; 
    document.getElementById("glv-error-daoline").innerHTML = rtn.daoLine;

    var obj = document.getElementById("glv-popup-error"); 
    obj.style.width = document.body.scrollWidth + 'px'; 
    obj.style.height = document.body.scrollHeight + 'px';
    obj.style.display = "block";
}

function cfErrorMsgDtl() {
    var obj = document.getElementById("glv-popup-error-detail"); 
    obj.style.width = document.body.scrollWidth + 'px'; 
    obj.style.height = document.body.scrollHeight + 'px';
    obj.style.display = "block";
}


function cfErrorMsgClose() {
    alert('clase');
    var obj = document.getElementById("glv-popup-error"); 
    obj.style.display = "none";
}

function cfErrorMsgFwdClose() {
    var obj = document.getElementById("glv-popup-error"); 
    obj.style.display = "none";
    //document.location.href=MY_PAGE;
    
}

function cfErrorMsgDtlClose() {
    var obj = document.getElementById("glv-popup-error-detail"); 
    obj.style.display = "none";
    //document.location.href=MY_PAGE;
    
}



/**
 * @type   : function
 * @access : public
 * @desc   : 문자열로 전달된 msg를 Alert 다이얼로그에 출력한다.
 * @sig    : msg
 * @param  : msg  - Alert 다이얼로그에 출력될 문자열
 * @author : 박재현
 */
function cfAlertDialog(msg) {
    alert(msg);
//    showModalDialog(CTX_ROOT+'/shortMessgeInfo.do', msg, 'status:no;resizable:false;center:yes;dialogWidth:300px;dialogHeight:190px;scroll:no;enter:yes')
}

/**
 * @type   : function
 * @access : public
 * @desc   : message.js에 정의된 메세지를 alert box로 보여준 후 리턴한다. cfGetMsg 참조.
             cfAlertMsg보다 더 많은 msg 문자열을 기술할 필요가 있을 때 사용한다.(공백제외 한글 45자 이상)
 * @sig    : msgId[, paramArray]
 * @param  : msgId - required - common.js의 공통 메세지 영역에 선언된 메세지 ID
 * @param  : paramArray - optional - 메세지에서 '@' 문자와 치환될 데이터 Array.
             Array의 index와 메세지 내의 '@' 문자의 순서가 일치한다.
               치환될 데이터는 [] 사이에 콤마를 구분자로 하여 기술하면 Array 로 인식된다.
 * @author : 박재현
 */
function cfAlertLongMsg(msgId, paramArray) {
    if (cfIsNull(msgId)) {
        cfAlertMsg(MSG_COM_ERR_006);
    }
    cfAlertLongDialog(new coMessage().getMsg(msgId, paramArray));
}

///**
// * @type   : function
// * @access : public
// * @desc   : 문자열로 전달된 msg를 Alert 다이얼로그에 출력한다.
// * @sig    : msg
// * @param  : msg  - Alert 다이얼로그에 출력될 문자열
// * @author : 박재현
// */
//function cfAlertLongDialog(msg) {
//    showModalDialog(CTX_ROOT+'/longMessgeInfo.do', msg, 'status:no;resizable:false;center:yes;dialogWidth:390px;dialogHeight:230px;scroll:no;enter:yes')
//}

/**
 * @type   : function
 * @access : public
 * @desc   : message.js에 정의된 메세지를 alert box로 보여준 후 리턴한다. (2초 후에 자동으로 close한다.)cfGetMsg 참조.
 * @sig    : msgId[, paramArray]
 * @param  : msgId - required - common.js의 공통 메세지 영역에 선언된 메세지 ID
 * @param  : paramArray - optional - 메세지에서 '@' 문자와 치환될 데이터 Array.
             Array의 index와 메세지 내의 '@' 문자의 순서가 일치한다.
               치환될 데이터는 [] 사이에 콤마를 구분자로 하여 기술하면 Array 로 인식된다.
 * @author : 박재현
 */
//function cfAutoCloseAlertMsg(msgId, paramArray) {
//    if (cfIsNull(msgId)) {
//        cfAlertMsg(MSG_COM_ERR_006);
//    }
//    cfAutoCloseAlertDialog(new coMessage().getMsg(msgId, paramArray));
//}

///**
// * @type   : function
// * @access : public
// * @desc   : 문자열로 전달된 msg를 Alert 다이얼로그에 출력한다.
// * @sig    : msg
// * @param  : msg  - Alert 다이얼로그에 출력될 문자열
// * @author : 박재현
// */
//function cfAutoCloseAlertDialog(msg) {
//    showModalDialog(CTX_ROOT+'/autoCloseInformationPopup.do', msg, 'status:no;resizable:false;center:yes;dialogWidth:300px;dialogHeight:190px;scroll:no;enter:yes')
//}


/**
 * @type   : function
 * @access : public
 * @desc   : message.js에 정의된 메세지를 confirm box로 보여준 후 리턴한다. cfGetMsg 참조.
             cfConfirmDialog보다 더 많은 msg 문자열을 기술할 필요가 있을 때 사용한다.(공백제외 한글 45자 이상)
 * @sig    : msgId[, paramArray]
 * @param  : msgId  -     required abcommon.js의 공통 메세지 영역에 선언된 메세지 ID
 * @param  : paramArray - optional 메세지에서 '@' 문자와 치환될 스트링 Array. (Array의 index와
 *           메세지 내의 '@' 문자의 순서가 일치한다.)
 * @return : true/false
 * @author : 박재현
 */
//function cfConfirmLongMsg(msgId, paramArray) {
//    if (cfIsNull(msgId)) {
//        cfAlertMsg(MSG_COM_ERR_006);
//        return null;
//    }
//    return cfConfirmLongDialog(new coMessage().getMsg(msgId, paramArray));
//}

/**
 * @type   : function
 * @access : public
 * @desc   : 문자열로 전달된 msg를 Confirm 다이얼로그에 출력한다.
 * @sig    : msg
 * @param  : msg  - Confirm 다이얼로그에 출력될 문자열
 * @author : 박재현
 */
//function cfConfirmLongDialog(msg) {
//    return showModalDialog(CTX_ROOT+"/confirmMessage.do", msg, 'status:no;resizable:false;center:yes;dialogWidth:390px;dialogHeight:230px;scroll:no;enter:yes')
//}

/**
 * @type   : function
 * @access : public
 * @desc   : message.js에 정의된 메세지를 alert box로 보여준 후 리턴한다. cfGetMsg 참조.
 * @sig    : msgId[, paramArray]
 * @param  : msgId - required - common.js의 공통 메세지 영역에 선언된 메세지 ID
 * @param  : paramArray - optional - 메세지에서 '@' 문자와 치환될 데이터 Array.
             Array의 index와 메세지 내의 '@' 문자의 순서가 일치한다.
               치환될 데이터는 [] 사이에 콤마를 구분자로 하여 기술하면 Array 로 인식된다.
 * @author : 박재현
 */
//function cfErrorMsg(msgId, paramArray) {
//    if (cfIsNull(msgId)) {
//        cfAlertMsg(MSG_COM_ERR_006);
//    }
//    cfErrorDialog(new coMessage().getMsg(msgId, paramArray));
//}

/**
 * @type   : function
 * @access : public
 * @desc   : 문자열로 전달된 msg를 Error 다이얼로그에 출력한다.
 * @sig    : msg
 * @param  : msg  - Error 다이얼로그에 출력될 문자열
 * @author : 박재현
 */
//function cfErrorDialog(msg) {
//    showModalDialog(CTX_ROOT+"/confirmMessage.do", msg, 'status:no;resizable:false;center:yes;dialogWidth:300px;dialogHeight:190px;scroll:no;enter:yes')
//}

/**
 * @type   : function
 * @access : public
 * @desc   : 공통메세지에 정의된 메세지를 리턴한다.
 * <pre>
 * // 공통 메세지 영역
 * var MSG_NO_CHANGED        = "변경된 사항이 없습니다.";
 * var MSG_SUCCESS_LOGIN     = "@님 안녕하세요?";
 * ...
 * var message1 = cfGetMsg(MSG_NO_CHANGED);
 * var message2 = cfGetMsg(MSG_SUCCESS_LOGIN, ["홍길동"]);
 * </pre>
 * 위의 예에서 message2 의 값은 "홍길동님 안녕하세요?" 가 된다.
 * @sig    : msgId[, paramArray]
 * @param  : msgId      - required common.js의 공통 메세지 영역에 선언된 메세지 ID
 * @param  : paramArray - optional 메세지에서 '@' 문자와 치환될 데이터 Array. Array의 index와
 *           메세지 내의 '@' 문자의 순서가 일치한다. 치환될 데이터는 [] 사이에 콤마를 구분자로 하여 기술하면 Array 로 인식된다.
 * @return : 치환된 메세지 스트링
 * @author : 박재현
 */
function cfGetMsg(msgId, paramArray) {
    return new coMessage().getMsg(msgId, paramArray);
}

/**
 * @type   : object
 * @access : private
 * @desc   : 메세지를 관리하는 객체이다.
 * @author : 박재현
 */
function coMessage() {
    this.getMsg = coMessage_getMsg;
}

/**
 * @type   : method
 * @access : public
 * @object : coMessage
 * @desc   : 공통메세지에 정의된 메세지를 치환하여 알려준다.
 * @sig    : message[, paramArray]
 * @param  : message    - required common.js의 공통 메세지 영역에 선언된 메세지 ID
 * @param  : paramArray - optional 메세지에서 '@' 문자와 치환될 스트링 Array. (Array의 index와
 *           메세지 내의 '@' 문자의 순서가 일치한다.)
 * @return : 치환된 메세지 스트링
 */
function coMessage_getMsg(message, paramArray) {
    if (cfIsNull(message)) {
        return null;
    }

    var index = 0;
    var count = 0;

    if (paramArray == null) {
        return message;
    }

    while ( (index = message.indexOf("@", index)) != -1) {
        if (paramArray[count] == null) {
            paramArray[count] = "";
        }

        message = message.substr(0, index)
                + String(paramArray[count])
                + message.substring(index + 1);
        index = index + String(paramArray[count++]).length;
    }
    return message;
}

//포커스 이동
function cfFocus(obj) {
    obj.focus();
}
//-------------------------------------------------------------------
// 컨트롤 관련 함수
//-------------------------------------------------------------------

function cfSelectAll(controlName) {

    var controls     = document.getElementsByName(controlName);
    var checkBoxFlag = false;

    for( var i=0; i<controls.length; i++ ) {
        if( controls[i].checked == true ){
            checkBoxFlag = true;
            break;
        }//end if
    }//end

    for(var loop=0; loop<controls.length; loop++) {
        if( controls[loop].type == "checkbox" ){
            controls[loop].checked = checkBoxFlag?true==false:true;
        }
    }
}

//Disabled 된거 제외하고 전체 선택 박재현 추가
function cfExceptDisabledSelectAll(controlName) {

    var controls     = document.getElementsByName(controlName);
    var checkBoxFlag = false;

    for( var i=0; i<controls.length; i++ ) {
        if( controls[i].checked == true ){
            checkBoxFlag = true;
            break;
        }//end if
    }//end

    for(var loop=0; loop<controls.length; loop++) {
        if( controls[loop].type == "checkbox" && !controls[loop].disabled){
            controls[loop].checked = checkBoxFlag?true==false:true;
        }
    }
}

////체크박스 선택 확인
//function getCheckedOnly(inputs) {
//    var result = new Array();
//    if (inputs && inputs.tagName == "INPUT" && inputs.checked) result[0] = inputs;
//    else if (inputs && inputs.length && inputs.length > 0 ) {
//        var until = inputs.length;
//        for (var idx=0; idx<until; idx++) {
//            if (inputs[idx].tagName == "INPUT" && inputs[idx].checked) result[result.length] = inputs[idx];
//        }
//    }
//    return result;
//}


//-------------------------------------------------------------------
// Date 정보 관련 함수
//-------------------------------------------------------------------
var GLB_TODAY;
var GLB_YEAR;
var GLB_MONTH;
var GLB_DAY;
var GLB_HOUR;
var GLB_MINUTE;

/**
 * @type   : function
 * @access : public
 * @desc   : 현재 일자를 전역 변수에 설정한다.
 * @param  : today  - 전역변수 GLB_TODAY에  할당할 현재 일자
 * @param  : year   - 전역변수 GLB_YEAR에   할당할 현재 연도
 * @param  : month  - 전역변수 GLB_MONTH에  할당할 현재 월
 * @param  : day    - 전역변수 GLB_DAY에    할당할 현재 일
 * @param  : hour   - 전역변수 GLB_HOUR에   할당할 현재 시간
 * @param  : minute - 전역변수 GLB_MINUTE에 할당할 현재 시간
 * @return : N/A
 * @author : 박재현
 */
function cfSetDate(today, year, month, day, hour, minute) {
    GLB_TODAY   = today;
    GLB_YEAR    = year;
    GLB_MONTH   = month;
    GLB_DAY     = day;
    GLB_HOUR    = hour;
    GLB_MINUTE  = minute;
}

/**
 * @type   : function
 * @access : public
 * @desc   : 시스템의 오늘 날짜를 YYYYMMDD 포맷으로 반환
 * @return : 오늘날짜(년월일)
 * @author : 박재현
 */
function cfGetToday() {
    return GLB_TODAY;
}

/**
 * @type   : function
 * @access : public
 * @desc   : 시스템의 올해 연도를 반환
 * @return : 올해 연도
 * @author : 박재현
 */
function cfGetYear() {
    return GLB_YEAR;
}

/**
 * @type   : function
 * @access : public
 * @desc   : 시스템의 해당 월을 가져옴
 * @return : 월
 * @author : 박재현
 */
function cfGetMonth() {
      return GLB_MONTH;
}

/**
 * @type   : function
 * @access : public
 * @desc   : 시스템의 당일 날짜를 가져옴
 * @return : 날짜
 * @author : 박재현
 */
function cfGetDay() {
    return GLB_DAY;
}

/**
 * @type   : function
 * @access : public
 * @desc   : 시스템의 현재 시간를 가져옴
 * @return : 시간
 * @author : 박재현
 */
function cfGetHour() {
    return GLB_HOUR;
}

/**
 * @type   : function
 * @access : public
 * @desc   : 시스템의 현재시간을 가져옴
 * @return : 현재시간
 * @author : 박재현
 */
function cfGetMinute() {
    return GLB_MINUTE;
}
//-------------------------------------------------------------------
// 날짜 변환 함수
//-------------------------------------------------------------------

/**
 * @type   : function
 * @access : public
 * @desc   : 날짜 문자열 중간의 공백을 없애는 함수
 *           yy.mm.dd(yyyy.mm.dd, yyyymmdd, yymmdd) --> yyyymmdd 형식으로 바꿔줌
 * <pre>
 *     var resultStr = cfStringDateToNum("2003.12.22");
 * </pre>
 * 위의 예에서 resultStr 은 "20031222" 가 된다.
 * @sig    : str
 * @param  : str - 날짜 문자열
 * @return : 변환된 날짜 문자열(실패시 ""를 리턴)
 * @author : 박재현
 */
function cfStringDateToNum(str) {
    var str = cfSkipSpaces(str);
    var dateNum = "";

    for (var i=0; i<str.length;i++ ) {
        if ((str.substr(i,1) >= '0') && (str.substr(i,1) <= '9'))
            dateNum = dateNum + str.substr(i,1);
    }

    if (dateNum.length < 1) {
        return ""; //빈 값이면 00000101로 셋팅해준다. cfCheckTwoDateCtrl() 함수 1114라인
    }

    if ((dateNum.length != 6) && (dateNum.length != 8)) {
        g_XJOS_CHK = false;
    }

    if (dateNum.length == 6) {
        if (parseInt(dateNum.substr(0,2),10) >= 50 )
            dateNum = "19" + dateNum;
        else
            dateNum = "20" + dateNum;
    }
    return dateNum;
}

/**
 * @type   : function
 * @access : public
 * @desc   : 시간 문자열 중간의 공백을 없애는 함수
 *           HH:mm:ss --> HHmmss 형식으로 바꿔줌
 * <pre>
 *     var resultStr = cfStringTimeToNum("12:12:22");
 * </pre>
 * 위의 예에서 resultStr 은 "121222" 가 된다.
 * @sig    : str
 * @param  : str - 시간 문자열
 * @return : 변환된 시간 문자열(실패시 ""를 리턴)
 * @author : 박재현
 */
function cfStringTimeToNum(str) {
    var str = cfSkipSpaces(str);
    var timeNum = "";

    for (var i=0; i<str.length;i++ ) {
        if ((str.substr(i,1) >= '0') && (str.substr(i,1) <= '9'))
            timeNum = timeNum + str.substr(i,1);
    }

    if (timeNum.length < 1) {
        return "";
    }

    if ((timeNum.length != 6)) {
        return "";
    }
    return timeNum;
}

/**
 * @type   : function
 * @access : public
 * @desc   : 날짜 스트링을 입력 받아 해당하는 요일명이나 요일 코드를 리턴한다.
 * @sig    : dateStr, typeChr
 * @param  : dateStr - 날짜스트링으로 YYYYMMDD or YYYY/MM/DD
 * @param  : typeChr - "c" 이면 날짜코드를 리턴하고 "n" 이면 요일명을 리턴한다. 값이 없으면 디폴트는 "c"
 * @return : day     - 요일명이나 요일코드를 리턴
 * @author : 박재현
 */
function cfGetDayOfWeek(dateStr, typeChr){
    var orgDateStr = cfStringDateToNum(dateStr);

    var orgDate = new Date(orgDateStr.substr(0,4), orgDateStr.substr(4,2)-1, orgDateStr.substr(6,2));
    var day = orgDate.getDay();

    if(typeChr != null && typeChr == "n") {
      var week = new Array('일','월','화','수','목','금','토');
      return week[day];
    }
    return day;
}

/**
 * @type   : function
 * @access : public
 * @desc   : YYYYMMDD 형식의 String을 Date Object로 변환
 * <pre>
 *     var resultDate = cfGetDateObject("20031223");
 * </pre>
 * 위의 예에서 resultDate는 Date Object 가 된다.
 * @sig    : dateObj
 * @param  : dateObj     -  YYYYMMDD 형식의 지정일자
 * @return : Date Object
 * @author : 박재현
 */
function cfGetDateObject(dateStr) {
    dateStr = dateStr.replace(/[^0-9]/gi,"");
    var dateObj = new Date();
    
    dateObj.setYear(parseInt(dateStr.substr(0,4),10))
    dateObj.setMonth(parseInt(dateStr.substr(4,2),10)-1)
    dateObj.setDate(parseInt(dateStr.substr(6,2),10));
    
    return dateObj;
}


/**
 * @type   : function
 * @access : public
 * @desc   : Date Object를 YYYYMMDD 형식의 String으로 변환하여 리턴한다.
 * <pre>
 *     var resultDate = cfGetDateString(todayObj);
 * </pre>
 * 위의 예에서 resultDate는 "20031223" 이 된다.
 * @sig    : dateObj
 * @param  : dateObj      - YYYYMMDD 형식의 지정일자
 * @return : YYYYMMDD 형식의 String
 * @author : 박재현
 */
function cfGetDateString(dateObj) {
    var year, mon, day;

    year = dateObj.getFullYear().toString();
    mon = (dateObj.getMonth() + 1).toString();
    day = dateObj.getDate().toString();

    if (mon.length == 1) mon = '0'+mon;
    if (day.length == 1) day = '0'+day;

    return year+mon+day;
}
/**
 * @type   : function
 * @access : public
 * @desc   : 지정일자부터 지정일 이전의 날짜를 구한다.
 * <pre>
 *     var resultDate = cfGetPrevDayNum("20031225", 5);
 * </pre>
 * 위의 예에서 resultDate는 "20031220" 이 된다.
 * @sig    : date, days
 * @param  : date     - YYYYMMDD or YYYY/MM/DD 형식의 지정일자
 * @param  : days     - 계산할 기간
 * @return : YYYYMMDD 형식의 String
 * @author : 박재현
 */
function cfGetPrevDayNum(date, days) {
    if ( days == '0')
        return date;

    date = cfStringDateToNum(date);

    var dateObj = cfGetDateObject(date);

    dateObj.setDate(dateObj.getDate() - parseInt(days,10));

    return cfGetDateString(dateObj);
}


/**
 * @type   : function
 * @access : public
 * @desc   : 지정일자부터 지정일 이후의 날짜를 구한다.
 * <pre>
 *     var resultDate = cfGetNextDayNum("20031225", 5);
 * </pre>
 * 위의 예에서 resultDate는 "20031230" 이 된다.
 * @sig    : date, days
 * @param  : date    -  YYYYMMDD or YYYY/MM/DD 형식의 지정일자
 * @param  : days    -  계산할 기간
 * @return : YYYYMMDD 형식의 String
 * @author : 박재현
 */
function cfGetNextDayNum(date, days) {
    if ( days == '0') return date;
    date = cfStringDateToNum(date);
    if (date == "") return "";
    var dateObj = cfGetDateObject(date);
    dateObj.setDate(dateObj.getDate()+ parseInt(days,10));
    return cfGetDateString(dateObj);
}

/**
 * @type   : function
 * @access : public
 * @desc   : 지정일자부터 지정 월 이전의 날짜를 구한다.
 * <pre>
 *     var resultDate = cfGetPrevMonthNum("20040605", 2);
 * </pre>
 * 위의 예에서 resultDate는 "20040405" 이 된다.
 *
 * @sig    : date, months
 * @param  : date    -  YYYYMMDD or YYYY/MM/DD 형식의 지정일자
 * @param  : months    -  계산할 기간(월)
 * @return : YYYYMMDD 형식의 String
 * @author : 박재현
 */
function cfGetPrevMonthNum(date, months) {
    if ( months == '0') return date;
    date = cfStringDateToNum(date);

    var dateObj = cfGetDateObject(date);
    var oldDay = dateObj.getDate().toString();
    dateObj.setMonth(dateObj.getMonth() - months);

    return cfGetDateString(dateObj);
}

/**
 * @type   : function
 * @access : public
 * @desc   : 지정일자부터 지정 월 이후의 날짜를 구한다.
 * <pre>
 *     var resultDate = cfGetNextMonthNum("20040605", 2);
 * </pre>
 * 위의 예에서 resultDate는 "20040805" 이 된다.
 *
 * @sig    : date, months
 * @param  : date    -  YYYYMMDD or YYYY/MM/DD 형식의 지정일자
 * @param  : months    -  계산할 기간(월)
 * @return : YYYYMMDD 형식의 String
 * @author : 박재현
 */
function cfGetNextMonthNum(date, months) {
    if ( months == '0') return date;
    date = cfStringDateToNum(date);

    var dateObj = cfGetDateObject(date);
    var oldDay = dateObj.getDate().toString();
    dateObj.setMonth(dateObj.getMonth() + months);

    return cfGetDateString(dateObj);
}

/**
 * @type   : function
 * @access : public
 * @desc   : 기본적으로 입력받은 두 날짜값 (yyyymmdd 형태)을 비교하여
 *           첫번째 날짜 값이 두번째 날짜 값보다 큰 경우 메세지를 띄우고
 *           false를 반환한다.
 *           두 일시의 개월 차이가 차이가 기간제한값 보다 크면 메세지를 띄우고 false를 반환한다.
 *
 * @sig    : startDateStr, endDateStr[, limit]
 * @param  : startDateStr  - 날짜스트링으로 YYYYMMDD or YYYY/MM/DD
 * @param  : endDateStr    - 날짜스트링으로 YYYYMMDD or YYYY/MM/DD
 * @param  : limit         - 기간제한값(월) optional
 * @return :  true or false 리턴
 * @author : 박재현
 */
function cfCheckTwoMonthsStr(startDateStr, endDateStr, limit){
    var startStr = cfStringDateToNum(startDateStr);
    var endStr = cfStringDateToNum(endDateStr);

    if (startStr > endStr) {
          cfAlertMsg(MSG_COM_ERR_009);
          cfFocus(startStr);
          return false;
    }

    if (cfIsNull(limit) == false && limit != 0 ) {
    var validEndStr = cfGetNextMonthNum(startStr, limit);
        if ( endStr > validEndStr) {
            cfAlertMsg(MSG_COM_ERR_005, [limit]);
            return false;
        }
    }
    return true;
}

/**
 * @type   : function
 * @access : public
 * @desc   : 기본적으로 입력받은 두 날짜값 (yyyymmdd 형태)을 비교하여
 *           첫번째 날짜 값이 두번째 날짜 값보다 큰 경우 메세지를 띄우고
 *           false를 반환한다.
 *           만약, 기간제한값을 파라미터로 같이 넘기는 경우에는 두 일시의
 *           차이가 기간제한값 보다 크면 메세지를 띄우고 false를 반환한다.
 * @sig    : startDateCtrl, endDateCtrl
 * @param  : startDateStr  - 시작 일자를 가지고 있는 컨트롤
 * @param  : endDateStr    - 종료 일자를 가지고 있는 컨트롤
 * @return : true or false
 * @author : 박재현
 */
function cfCheckTwoDateCtrl(startDateCtrl, endDateCtrl) {

    var startStr = cfStringDateToNum(startDateCtrl.value);
    var endStr   = cfStringDateToNum(endDateCtrl.value);

    if(startStr.trim() == "" && endStr.trim() == "") {
        return true;
    }

    if (startStr.trim() == "") {
        startStr = startDateCtrl.value = "00000101";
    }

    if (endStr.trim() == "") {
        endStr = endDateCtrl.value = "99991231";
    }

    //startDateCtrl: 날짜입력이 잘못되었습니다.(월만 체크)
    if(startStr.substring(4,6) > 12 || startStr.substring(4,6) < 1){
        cfAlertMsg(MSG_COM_ERR_010);
        cfFocus(startDateCtrl);
        return false;
    }

    //endDateCtrl : 날짜입력이 잘못되었습니다.(월만 체크)
    if(endStr.substring(4,6) > 12 || endStr.substring(4,6) < 1){
        cfAlertMsg(MSG_COM_ERR_010);
        cfFocus(endDateCtrl);
        return false;
    }

    //시작 일자가 마지막 일자보다 클 수 없습니다.
    if (parseInt(startStr) > parseInt(endStr)) {
        cfAlertMsg(MSG_COM_ERR_009);
        cfFocus(startDateCtrl);
        return false;
    }
    return true;
}

/**
 * @type   : function
 * @access : public
 * @desc   : 입력된 두 날짜가 시스템의 오늘 날짜보다 과거인지 체크
 * @return : true/false flag
 * @author : 박재현
 */
    function cfChkTwoDateOverToday(startDateCtrl, endDateCtrl){
    var flag = true;
    var startStr = cfStringDateToNum(startDateCtrl.value);
    var endStr = cfStringDateToNum(endDateCtrl.value);
    var todayDate =  cfGetYear() + cfGetMonth() + cfGetDay();

    if(startStr < todayDate) {
        cfAlertMsg(MSG_COM_ERR_011);
        cfFocus(startDateCtrl);
        return false;
    }

    if(endStr < todayDate) {
        cfAlertMsg(MSG_COM_ERR_011);
        cfFocus(endDateCtrl);
        return false;
    }
    return true;
}

/**
 * @type   : function
 * @access : public
 * @desc   : 기본적으로 입력받은 두 날짜값 (yyyymmdd 형태)을 비교하여
 *           첫번째 날짜 값이 두번째 날짜 값보다 큰 경우 메세지를 띄우고
 *           false를 반환한다.
 *           만약, 기간제한값을 파라미터로 같이 넘기는 경우에는 두 일시의
 *           차이가 기간제한값 보다 크면 메세지를 띄우고 false를 반환한다.
 * @sig    : startDateStr, endDateStr[, limit]
 * @param  : startDateStr  - 날짜스트링으로 YYYYMMDD or YYYY/MM/DD required
 * @param  : endDateStr    - 날짜스트링으로 YYYYMMDD or YYYY/MM/DD required
 * @param  : limit         - 기간제한값(일) optional
 * @return :  true or false 리턴
 * @author : 박재현
 */
function cfCheckTwoDateStr(startDateStr, endDateStr, limit){
    var startStr = cfStringDateToNum(startDateStr);
    var endStr = cfStringDateToNum(endDateStr);

    if (startStr > endStr) {
        cfAlertMsg(MSG_COM_ERR_003);
        return false;
    }

    if (limit != null) {
        if ((endStr - startStr) > limit) {
            cfAlertMsg(MSG_COM_ERR_004, [limit]);
            return false;
        }
    }
    return true;
}
/**
 * @type   : function
 * @access : public
 * @desc   : 날짜(yyyyMMdd 형식)와 시간(HHmmss 형식)을 입력받을 경우
 *           두 컨트롤 값을 결합한 후 반환한다.
 * @param  : dateObj       - 날짜를 입력 받는 입력 컨트롤
 * @param  : timeObj       - 시간을 입력 받는 입력 컨트롤
 * @return : 날짜와 시간의 연결값
 * @author : 박재현
 */
function cfConcatDateTime(dateObj, timeObj) {
    return cfStringDateToNum(dateObj.value) + cfStringTimeToNum(timeObj.value);
}

/**
 * @type   : function
 * @access : public
 * @desc   : 파일업로드시 지정한 Policy에 따른 첨부파일 확장자를 체크한다.
 * @param  : fileNm   : 파일경로/명
             policy : 첨부를 허용할 확장자 목록
 * @return : 없음
 * @author : 박재현
 * @history:
 */
function cfChkFileExtFor(fileNm, policy){

    var valid = false; /* 지원하는 확장자 여부 */
    var idx = fileNm.lastIndexOf("\\");
    var filename = fileNm.substring(idx+1);
    var idx2 = filename.lastIndexOf(".");

    /* 확장자가 없는 파일일 경우 */
    if(idx2 == -1){
        return false;
    }else{
        var ext = filename.substring(idx2+1);
        var ext2 = ext.toLowerCase();
        var fileExtPattern = "";

        if(policy != "" && policy != undefined){
            if(policy){
                fileExtPattern = eval("UP_" + policy.toUpperCase() + "_PATTERN");
            }
        }else{
            /* DRM 제한 확장자 */
            fileExtPattern = "doc,xls,ppt,csv,hwp,gul,txt,pdf,bmp,jpg,gif,tif,tiff,rtf,jtd,pptx,xlsx,docx";
        }

        /* 체크할 확장자가 있는 Policy일 경우에만 */
        if(fileExtPattern != ""){
            if(eval("UP_" + policy.toUpperCase() + "_PATTERN_TYPE") == "allow"){
                valid = cfHasFileExtInPattern(fileExtPattern,ext2);
            }else if(eval("UP_" + policy.toUpperCase() + "_PATTERN_TYPE") == "deny"){
                valid = !cfHasFileExtInPattern(fileExtPattern,ext2);
            }
        }
    return valid;
    }
}

 /**
 * @type   : function
 * @access : public
 * @desc   : 첨부파일 확장자를 체크한다.
 * @param  : fileNm   : 파일경로/명
             ext : 첨부한 파일의 확장자
 * @return : 없음
 * @author : 박재현
 * @history:
 */
function cfHasFileExtInPattern(pattern,ext) {
    var found = false;
    if(pattern.indexOf(ext) < 0){
        found = false;
    }else{
        found = true;
    }
    return found;
}

/**
 * @type   : function
 * @access : public
 * @desc   : 첨부파일 파일명의 길이가 50자 초과하는지 체크한다.
 * @param  : fileNm   : 파일경로/명
 * @return : 없음
 * @author : 박재현
 * @history:
 */
function cfChkFileNmLength(fileNm){
    var flag = true; /* 파일 길이 초과 여부 */
    var idx = fileNm.lastIndexOf("\\");
    var filename = fileNm.substring(idx+1);

    if(cfGetByteLength(filename) > 50){
        flag = false;
    }else{
        flag = true;
    }
    return flag;
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 화면의 Key 입력에 대한 처리. Enter키 - 조회
 * @example : <html:body onKeyDown="cfEnterSearch('fncSearchList')">
 * @param   : searchFncName : 실행할 조회 펑션 이름
 * @return  : false
 * @author  : 박재현
 * @history :
 */
 function cfEnterSearch(searchFncName){
    var keyCode = window.event.keyCode;
    if(searchFncName == undefined || searchFncName == ""){
        return false;
    }else{
        if(keyCode == 13){
            eval(searchFncName+"()");
            return false;
        }
    }
    return true;
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 화면의 Key 입력에 대한 처리.
            : 수정화면에서 BackSpace 입력을 방지 함
 * @example : <html:body onKeyDown="cfBlockBackSpace()">
 * @param   : none
 * @return  : none
 * @author  : 박재현
 * @history :
 */
function cfBlockBackSpace(){
    var keyCode = window.event.keyCode;
    if(keyCode == 8){
        window.event.keyCode = 0;
        window.event.keyCode.cancelBubble = true;
        window.event.keyCode.returnValue = false;
    }
}

/**
 * @type    : function
 * @access  : public
 * @desc    : 콤마를 찍어주는 함수
 * @example : Comma(12345678)=12,345,678                Comma(-12345678)=-12,345,678
 *            Comma(12345678.12345)=12,345,678.12345    Comma(-12345678.12345)=-12,345,678.12345
 * @param   : input 콤마를 찍고자 하는 해당 객체
 * @return  : 콤마가 입력된 객체 value값
 * @author  : 박재현
 * @history :
 */
function cfAddComma(input) {

    var inputString = new String;
    var outputString = new String;
    var counter = 0;
    var decimalPoint = 0;
    var end = 0;
    
    if(!cfCheckDigit(input)) return "0";

    inputString=input.toString().replace(/,/gi,'');
    outputString='';
    decimalPoint = inputString.indexOf('.', 1);

    if (decimalPoint == -1) {
        end = inputString.length - (inputString.charAt(0)=='-' ? 1 : 0);
        for (counter=1; counter <= inputString.length; counter++) {
            outputString = (counter%3==0  && counter < end ? ',' : '') + inputString.charAt(inputString.length - counter) + outputString;
        }
    } else {
        end = decimalPoint  - (inputString.charAt(0)=='-' ? 1 : 0);
        for (counter=1; counter <=  decimalPoint; counter++)    {
            outputString = (counter%3==0  && counter < end ? ',' : '') + inputString.charAt(decimalPoint - counter) + outputString;
        }
        for (counter=decimalPoint; counter <inputString.length; counter++)              {
            outputString += inputString.charAt(counter);
        }
    }
    return (outputString);
}

var _intValue   = '0123456789';

//-------------------------------------------------------------------
//숫자로 구성된 문자열인가를 체크하는 함수
//-------------------------------------------------------------------
function cfCheckDigit(num){
    // 좌우 trim(공백제거)을 해준다.
      opt =1;
      num = String(num).replace(/^\s+|\s+$/g, "");
      if(typeof opt == "undefined" || opt == "1"){
        // 모든 10진수 (부호 선택, 자릿수구분기호 선택, 소수점 선택)
        var regex = /^[+\-]?(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+){1}(\.[0-9]+)?$/g;
      }else if(opt == "2"){
        // 부호 미사용, 자릿수구분기호 선택, 소수점 선택
        var regex = /^(([1-9][0-9]{0,2}(,[0-9]{3})*)|[0-9]+){1}(\.[0-9]+)?$/g;
      }else if(opt == "3"){
        // 부호 미사용, 자릿수구분기호 미사용, 소수점 선택
        var regex = /^[0-9]+(\.[0-9]+)?$/g;
      }else{
        // only 숫자만(부호 미사용, 자릿수구분기호 미사용, 소수점 미사용)
        var regex = /^[0-9]$/g;
      }
      if( regex.test(num) ){
        num = num.replace(/,/g, "");
        return isNaN(num) ? false : true;
      }else{ return false;  }
}

function cfCheckEngilsh(txt) {
    var cnt = 0;
    for(i=0; i<txt.length; i++) {
        if(!(txt.charCodeAt(i)>=0 && txt.charCodeAt(i)<=127)) {
            cnt++;
        }
    }
    if(cnt!=0) return false;
    return true;
}


//-------------------------------------------------------------------
//숫자인가를 체크하는 함수
//-------------------------------------------------------------------
function IsInt(value) {
    var   j;
    for(j=0;j<_intValue.length;j++){
        if(value == _intValue.charAt(j)) {
            return true;
        }
    }
    return false;
}

//-------------------------------------------------------------------
//입력된 문자가 실수인가를 체크하는 함수
//-------------------------------------------------------------------
function check_Num(comp){
    var   i,j;
    var str = new String(comp.value);

    if ((str == '')||(comp.value.length == 0)){
        return true;
    }
    for( j = 0; j < comp.value.length; j++){
        if( !IsInt(str.charAt(j)) && str.charAt(j) != '.' && str.charAt(j) !='-'){
            return false;
        }
    }
    return true;
}

function fncCalendar(frm, sDate, vDate) {
    var retVal;

    //var url = frm.cal_url.value;
    var url = CTX_ROOT+"/callCalPopup.do";

    var varParam = new Object();
    varParam.sDate = sDate.value;

    // IE
    var openParam = "dialogWidth:185px;dialogHeight:165px;scroll:no;status:no;center:yes;resizable:yes;";
    // FIREFOX
    //var openParam = "dialogWidth:320px;dialogHeight:220px;scroll:no;status:no;center:yes;resizable:yes;";
    retVal = window.showModalDialog(url, varParam, openParam);

    if(retVal) {
        if(fncCalendar.arguments.length == 2){
            sDate.value = retVal.vDate;
        }else{
            sDate.value = retVal.sDate;
            vDate.value = retVal.vDate;
        }
    }
}

//-------------------------------------------------------------------
// 팝업을 생성하는 함수
//-------------------------------------------------------------------
function cfPopUp(type, popUpName, formObj, url){

   switch (type){
       case 'L' :
           window.open("", popUpName,"scrollbars=no,resizable=yes,top=120,left=150,height=550,width=870" );
           break;
        case 'M1' :
           window.open("", popUpName,"scrollbars=no,resizable=yes,top=120,left=150,height=550,width=670");
           break;
        case 'M2' :
           window.open("", popUpName,"scrollbars=no,resizable=yes,top=120,left=150,height=350,width=670");
           break;
        case 'S' :
           window.open("", popUpName,"scrollbars=no,resizable=yes,top=120,left=150,height=500,width=650");
           break;
    }
    formObj.action = url;
    formObj.target = popUpName;
    formObj.submit();
}

//-------------------------------------------------------------------
// 팝업을 생성하는 함수(URL로 이동 )
//-------------------------------------------------------------------
function cfPopUpURL(type, popUpName, url){

    switch (type){
        case 'L' :
           window.open(url,popUpName,"scrollbars=no,resizable=no,top=120,left=150,height=550,width=870");
           break;
        case 'LS' :
            window.open(url,popUpName,"scrollbars=yes,resizable=no,top=120,left=150,height=550,width=870");
            break;
        case 'M1' :
           window.open(url,popUpName,"scrollbars=no,resizable=no,top=120,left=150,height=550,width=670");
           break;
        case 'M1S' :
            window.open(url,popUpName,"scrollbars=yes,resizable=no,top=120,left=150,height=550,width=670");
            break;
        case 'M2' :
           window.open(url,popUpName,"scrollbars=no,resizable=no,top=120,left=150,height=350,width=670");
           break;
        case 'S' :
           window.open(url,popUpName,"scrollbars=no,resizable=no,top=120,left=150,height=500,width=650");
           break;
    }
}

/**
 * @type   : function
 * @access : public
 * @desc   : (등록/수정용) ProgressBar를 띄워준다.
 * @param  : cfActSubmitValidate(document.mainForm, "<c:url value='/explnmanEmailSend.do'/>", "_self", false);
             oform : 폼명
             action : 액션명
             target : 타겟명(default: _self)
             bHideAnimation :  이미지 보여주려면 false, 안보여주려면 true
             submitDiv : 기존 사용한 전송 메소드가 submit이라면  "_submit" 을 써준다. 미입력시 default는 onsubmit();
 * @return : 없음
 * @author : 박재현
 */
function cfActSubmitSave(oform, action, target, bHideAnimation, submitDiv) {

    /*LoadingBar image setting*/
    oform.aniImage01.src = CTX_ROOT+'/images/crms/imgLoading_save.gif';
    var old_action = null;
    var old_target = null;

    if(target){
        old_target = oform.target;
        oform.target = target;
    }

    if(action){
        old_action = oform.action;
        oform.action = action;
    }

    if(!oform.method) oform.method = "post";

    var ret = true;

    if(submitDiv =="_submit"){ //submit사용시
        oform.submit();

    }else{  //onsubmit사용시

        if(oform.onsubmit){
            ret = oform.onsubmit();
        }else{
            oform.submit();
        }
    }

    oform.target = old_target;
    oform.action = old_action;

    if(ret || ret == null){
        if (bHideAnimation)
            cfShowLoadingImage(LoadingAnimation, aniImage01, false);
        else
            cfObjDisabledCtrl(true, "TEXT,PASSWORD,RADIO,CHECKBOX,FILE,BUTTON,RESET,SELECT,TD,IMG");
            cfVisibleObjCtrl(false, "SELECT");
            cfShowLoadingImage(LoadingAnimation, oform.aniImage01, true);
    }
}

/**
 * @type   : function
 * @access : public
 * @desc   : (등록/수정용) ProgressBar를 띄워준다.
 * @param  : cfActSubmitSearch(document.mainForm, "<c:url value='/findProgrmList.do'/>", "_self", false, "_submit");
             oform : 폼명
             action : 액션명
             target : 타겟명(default: _self)
             bHideAnimation :  이미지 보여주려면 false, 안보여주려면 true
             submitDiv : 기존 사용한 전송 메소드가 submit이라면  "_submit" 을 써준다. 미입력시 default는 onsubmit();
 * @return : 없음
 * @author : 박재현
 */
function cfActSubmitSearch(oform, action, target, bHideAnimation, submitDiv) {

    /*LoadingBar image setting*/
    oform.aniImage01.src = CTX_ROOT+'/images/crms/imgLoading.gif';

    var old_action = null;
    var old_target = null;

    if(target){
      old_target = oform.target;
        oform.target = target;
    }

    if(action){
        old_action = oform.action;
        oform.action = action;
    }

    if(!oform.method) oform.method = "post";

    var ret = true;

    if(submitDiv =="_submit"){ //submit사용시
        oform.submit();

    }else{  //onsubmit사용시

        if(oform.onsubmit){
            ret = oform.onsubmit();
        }else{
            oform.submit();
        }
    }

    oform.target = old_target;
    oform.action = old_action;

    if(ret || ret == null){
        if (bHideAnimation)
            cfShowLoadingImage(LoadingAnimation, aniImage01, false);
        else
            cfObjDisabledCtrl(true, "TEXT,PASSWORD,RADIO,CHECKBOX,FILE,BUTTON,RESET,SELECT,TD,IMG");
            cfVisibleObjCtrl(false, "SELECT");
            cfShowLoadingImage(LoadingAnimation, oform.aniImage01, true);
    }
}

/**
 * @type   : function
 * @access : public
 * @desc   : 진행Bar 이미지를 보여준다.
 * @param  : oDivID, oImageID, bShow, nOffsetLeft, nOffsetTop,  nLeftPos, nTopPos
 * @return : 없음
 * @author : 박재현
 */
function cfShowLoadingImage(oDivID, oImageID, bShow, nOffsetLeft, nOffsetTop,  nLeftPos, nTopPos ) {

    if (oDivID && oImageID){
        if (oDivID.tagName == null)
            oDivID = document.getElementById(oDivID);
        if (oImageID.tagName == null)
            //oImageID = document.getElementById(oImageId);
        if (!oDivID || !oImageID)
            return false;

        if (bShow == null || bShow == true){
            oDivID.style.visibility = "visible";
        }else{
            oDivID.style.visibility = "hidden";
        }
    }
}

/**
 * @type   : function
 * @access : public
 * @desc   : 저장/조회 실행시 화면의 INPUT,SELECT,TD,IMG Object를 disabled/enbaled 처리한다.
 * @param  : div, objCtrl
 * @return : 없음
 * @author : 박재현
 */
function cfObjDisabledCtrl(div, objCtrl){
    var htmlObj ="";
    var retVal = new Array();
        retVal =  objCtrl.split(",");

    for(var val=0; val<retVal.length; val++){
        objCtrl = retVal[val];
        htmlObj = document.getElementsByTagName(objCtrl);

        for(var i=0; i< htmlObj.length; i++){
            htmlObj[i].disabled = div;
        }
    }
}

/**
 * @type   : function
 * @access : public
 * @desc   : 파라미터로 넘겨준 Object의 visible/hidden을 컨트롤함.
 * @param  : div : true(visible) false(hidden) , objCtrl : 컨트롤해줄 object
 * @return : 없음
 * @author : 박재현
 */
function cfVisibleObjCtrl(div, objCtrl){
    var htmlObj ="";
    var retVal = new Array();
        retVal =  objCtrl.split(",");

    for(var val=0; val<retVal.length; val++){
        objCtrl = retVal[val];
        htmlObj = document.getElementsByTagName(objCtrl);

        var len = htmlObj.length;
        for(var i=0; i< len; i++){
            if(div == true){
                htmlObj[i].style.visibility = "visible";
            }else{
                htmlObj[i].style.visibility = "hidden";
            }
        }
    }
}

/**
 * @type   : function
 * @access : public
 * @desc   : 현재날짜 기준으로 전월 1일을 가져온다.
 * @param  : None
 * @return : YYYYMMDD
 * @author : 심영환
 */
function cfLastMonth() {
    //현재날짜 YYYYMMDD
    var objDate   = new Date();
    var strToday  = objDate.getFullYear() + "";
    strToday += cfRight("0" + (objDate.getMonth() + 1), 2);
    strToday += cfRight("0" + objDate.getDate(), 2);
    
    //전월구하기 
    var lYear = strToday.substring(0,4);
    var lMonth = strToday.substring(4,6);
    var lDay = "01";
    var tYear = parseInt(lYear);
    var tMonth = parseInt(lMonth);
    
    tMonth = tMonth - 1;
    
    if(tMonth < 1) {
        tYear--;
        tMonth = 12;
    }
    
    lYear = '' + tYear;
    if(tMonth < 10) {
        lMonth = '0' + tMonth;
    } else {
        lMonth = ''  + tMonth;
    }
 
    var rtnValue = lYear + "-" + lMonth + "-" + lDay;
    return rtnValue;
}    

/**
 * @type   : function
 * @access : public
 * @desc   : 문자열의 오른쪽부분을 지정한 길이만큼 Return 한다.
 * @param  : strString (오른부분을 얻어올 원본 문자열)
 *           nSize (얻어올 크기. [Default Value = 0])
 * @return : 오른쪽 부분이 얻어진 문자열.
 * @author : 심영환
 */
function cfRight(strString, nSize) {
    var nStart = String(strString).length;
    var nEnd = Number(nStart) - Number(nSize);
    var rtnValue = strString.substring(nStart, nEnd);
 
    return rtnValue;
}

/**
 * @type   : function
 * @access : public
 * @desc   : 해당월의 마지막 날짜를 숫자로 구하기
 * @param  : sDate : yyyyMMdd형태의 날짜 ( 예 : "20121122" )
 * @return : 마지막 날짜 숫자값 ( 예 : 30 )
 * @author : 심영환
 */
function cfLastDateNum(sDate) {
    var nMonth, nLastDate;
 
    nMonth = parseInt(sDate.substr(5,2), 10);
    if( nMonth == 1 || nMonth == 3 || nMonth == 5 || nMonth == 7  || nMonth == 8 || nMonth == 10 || nMonth == 12 ) {
        nLastDate = 31;
    } else if( nMonth == 2 ) {
        if( gfnIsLeapYear(sDate) == true ) {
           nLastDate = 29;
        } else {
            nLastDate = 28;
        }
    }  else {
        nLastDate = 30;
    }
    
    return nLastDate;
}
 
/**
 * @type   : function
 * @access : public
 * @desc   : 해당월의 마지막 날짜를 yyyyMMdd형태로 구하기 
 * @param  : sDate : yyyyMMdd형태의 날짜 ( 예 : "20121122" )
 * @return : yyyyMMdd형태의 마지막 날짜 ( 예 : "20121130" )
 * @author : 심영환
 */
function cfLastDate(sDate) {
 
    var nLastDate = cfLastDateNum(sDate);
 
    return sDate.substr(0,8) + nLastDate.toString();
}

/**
 * @type   : function
 * @access : public
 * @desc   : 감사자료 추출기간 입력 (전월 1일 ~ 전월 마지막날) 
 * @param  : none
 * @return : none
 * @author : 심영환
 */
function cfExtractionDate() {
    //사용자가 선택한 기간이 있는지 확인 
    var DtAt = document.mainForm.registDtFr.value;
    //없으면 전월1일~전월 마지막날 입력 
    if (DtAt == "") {
        //전월 1일 
        var lastDtFr = cfLastMonth();
        document.mainForm.registDtFr.value = lastDtFr;
        //전월 마지막날 
        var lastDtTo = cfLastDate(lastDtFr);
        document.mainForm.registDtTo.value = lastDtTo;
    }
}

/**
 * @type   : function
 * @access : public
 * @desc   : row 병합
 * @param  : tbl      : 병합할 대상 table object
             startRow : 병합 시작 row, title 한 줄일 경우 1
             cNum     : 병합 실시할 컬럼번호, 0부터 시작
             length   : 병합할 row의 길이, 보통 1
             add      : 비교할 기준에 추가할 컬럼번호
                        A | 1
                        B | 1
                        을 서로 구분하고 싶다면, add에 0번째
                        컬럼을 추가
 * @return : none
 * @author : 심영환
 */
function cfMergeCell(tbl, startRow, cNum, length, add) {
    //alert("!");
    var isAdd = false;
    if (tbl == null) {
        return;
    }
    
    if (startRow == null || startRow.length == 0) {
        startRow = 1;
    }
    
    if (cNum == null || cNum.length == 0) {
        return ;
    }
    
    if (add  == null || add.length == 0) {
        isAdd = false;
    } else {
        isAdd = true;
        add   = parseInt(add);
    }
    
    cNum   = parseInt(cNum);
    length = parseInt(length);
    // document.getElementById('myTableId').getElementsByTagName('tbody')[0].rows.length;
    
    rows   = tbl.getElementsByTagName('tbody')[0].rows;
    rowNum = rows.length;
    
    tempVal  = '';
    cnt      = 0;
    startRow = parseInt(startRow);

    for (i = startRow; i < rowNum; i++ ) {
        if (typeof(rows[i].cells[cNum]) == 'undefined' ) {
            break;
        }
        //alert("cnt1="+cnt);
        curVal = rows[i].cells[cNum].innerHTML;
        if (isAdd) {
            curVal += rows[i].cells[add].innerHTML;
        }
        if (curVal == tempVal) {
            if (cnt == 0) {
                cnt++;
                startRow = i - 1;
            }
            cnt++;
        } else if (cnt > 0) {
            cfMerge(tbl, startRow, cnt, cNum, length);
            startRow = endRow = 0;
            cnt = 0;
        } else { }
        tempVal = curVal;
    }
    if (cnt > 0) {
        cfMerge(tbl, startRow, cnt, cNum, length);
    }
}

/**
 * @type   : function
 * @access : public
 * @desc   : cfMergeCell에서 사용하는 함수
 * @param  : tbl      : 병합할 대상 table object
             startRow : 병합 시작 row, title 한 줄일 경우 1
             cnt      : 
             cellNum  : 
             length   : 병합할 row의 길이, 보통 1
 * @return : none
 * @author : 심영환
 */
function cfMerge(tbl, startRow, cnt, cellNum, length) {
    rows = tbl.getElementsByTagName('tbody')[0].rows;
    row  = rows[startRow];
    for (i = startRow + 1; i < startRow + cnt; i++ ) {
        for (j = 0; j < length; j++) {
            rows[i].deleteCell(cellNum);
        }
    }
    for (j = 0; j < length; j++) {
        row.cells[cellNum + j].rowSpan = cnt;
    }
}

/**
 * @type   : function
 * @access : public
 * @desc   : 두 날짜사이의 일수구하기
 * @param  : as_Date1 : 시작일
             as_Date2 : 종료일
 * @return : 두 날짜사이의 일수 (number)
 * @author : 심영환
 */
function cfSetDaysRange(as_Date1, as_Date2) {
    // 년도, 월, 일로 분리 
    var as_DT1 = as_Date1.split("-"); 
    var as_DT2 = as_Date2.split("-"); 

    // Number()를 이용하여 월을 2자리로 포맷
    as_DT1[1] = (Number(as_DT1[1]) - 1) + ""; 
    as_DT2[1] = (Number(as_DT2[1]) - 1) + ""; 

    var s_DT = new Date(as_DT1[0], as_DT1[1], as_DT1[2]); 
    var e_DT   = new Date(as_DT2[0], as_DT2[1], as_DT2[2]); 

    return (e_DT.getTime() - s_DT.getTime()) / 1000 / 60 / 60 / 24; 
}


/**
 * prototype Function
 * 작성자 : 엄재환
 * 작성일자 : 2014-08-22
 * 내용 : 날짜 포멧을 쉽게 변경하도록 도와줌
 *             Date.format("yyyy-MM-dd") => 2014-08-22
 */
Date.prototype.format = function(f) {    
    if (!this.valueOf()) return " ";     
    
    var weekName = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];    
    var d = this;         
    
    return f.replace(/(yyyy|yy|MM|dd|E|hh|mm|ss|a\/p)/gi, function($1) {        
        switch ($1) {            
           case "yyyy": return d.getFullYear();            
           case "yy": return (d.getFullYear() % 1000).zf(2);            
           case "MM": return (d.getMonth() + 1).zf(2);            
           case "dd": return d.getDate().zf(2);            
           case "E": return weekName[d.getDay()];            
           case "HH": return d.getHours().zf(2);            
           case "hh": return ((h = d.getHours() % 12) ? h : 12).zf(2);            
           case "mm": return d.getMinutes().zf(2);            
           case "ss": return d.getSeconds().zf(2);            
           case "a/p": return d.getHours() < 12 ? "오전" : "오후";            
           default: return $1;        
         }    
    });
};

String.prototype.string = function(len){var s = '', i = 0; while (i++ < len) { s += this; } return s;}; 
String.prototype.zf = function(len){return "0".string(len - this.length) + this;}; 
Number.prototype.zf = function(len){return this.toString().zf(len);};

xlsDownload = function(option){
    // url과 data를 입력받음
    data1 = option.inputParameter;
    data2 = option.makeMultiValue;

    //alert(data1);
    if( option.url && data1 ){ 
        // 파라미터를 form의  input으로 만든다.
        var inputs = '';
        jQuery.each(data1.split('&'), function(){
            inputAdd = true;
            var pair = this.split('=');
            jQuery.each(data2.split('&'), function(){ 
                var pair2 = this.split('=');
                if(pair[0] == pair2[0]){
                    inputAdd = false;
                }
            });
            if(inputAdd == true){
                pair[1] = pair[1].replace(/[+]/gi, ' ');
                inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ decodeURIComponent(pair[1]) +'" />';
            }
        });
        jQuery.each(data2.split('&'), function(){
            var pair = this.split('=');
            pair[1] = pair[1].replace(/[+]/gi, ' ');
            inputs+='<input type="hidden" name="'+ pair[0] +'" value="'+ decodeURIComponent(pair[1]) +'" />';
        });
        // request를 보낸다.
        jQuery('<form action="'+ option.url +'" method="post">'+inputs+'</form>')
        .appendTo('body').submit().remove();
    };
};

xlsDownloadUseJson = function(option){
    // url과 data를 입력받음
    data1 = option.inputParameter;
    data2 = option.makeMultiValue;
    
    // 파라미터를 form의  input으로 만든다.
    var inputs = '';
    
    if( option.url && data1 ){
        for(var key in data1) { 
            //console.log(key+':'+data1[key]);
            inputs+='<input type="hidden" name="'+ key +'" value="'+ decodeURIComponent(data1[key]) +'" />';
        }
        for(var key in data2) { 
            //console.log(key+':'+data1[key]);
            inputs+='<input type="hidden" name="'+ key +'" value="'+ decodeURIComponent(data2[key]) +'" />';
        }
        
        jQuery('<form action="'+ option.url +'" method="post">'+inputs+'</form>')
            .appendTo('body').submit().remove();
    }
};


function formData2QueryString(docForm) {

    var strSubmit       = '';
    var formElem;
    var strLastElemName = '';
    for (i = 0; i < docForm.elements.length; i++) {
            formElem = docForm.elements[i];
            switch (formElem.type) {
                    // Text, select, hidden, password, textarea elements
                    case 'text':
                     strSubmit += formElem.name + 
                     '=' + encodeURI(formElem.value) + '&'
                     break;
                    case 'select-one':
                     strSubmit += formElem.name + 
                     '=' + encodeURI(formElem.value) + '&'
                     break;
                    case 'hidden':
                     strSubmit += formElem.name + 
                     '=' + encodeURI(formElem.value) + '&'
                     break;
                    case 'password':
                     strSubmit += formElem.name + 
                     '=' + encodeURI(formElem.value) + '&'
                     break;
                    case 'textarea':
                            strSubmit += formElem.name + 
                            '=' + encodeURI(formElem.value) + '&'
                    break;
        }
    }
    return strSubmit;
}

function cfComFile(url, fileSeq, obj) {
    if( url.indexOf("download") > -1 ) {
        downloadFile(url + "?fileSeq=" + fileSeq);
    } else {
        cfConfirm("파일삭제 하시겠습니까?", function() {
            $.ajax({
                url : url,
                type: 'POST',
                data: {fileSeq: fileSeq},
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                context:this,
                dataType:'json',
                beforeSend:function(){
                    $("#layoutSidenav").loading();
                },
                success: function(data) {
                    $("#layoutSidenav").loadingClose();
                    if(!cfCheckRtnObj(data.rtn)) return;
                    cfAlert("삭제 되었습니다.");
                    $(obj).closest("li").remove();
                },
                error: function(request, status, error){
                    console.log("code = "+ request.status + " message = " + request.responseText + " error = " + error);
                    cfAlert('시스템 오류입니다.');
                },
                complete:function(){
                    $("#layoutSidenav").loadingClose();

                    if( typeof fn_fileDeleteCallback == "function" ) {
                        fn_fileDeleteCallback();
                    }
                }
            });
        });
    }
}