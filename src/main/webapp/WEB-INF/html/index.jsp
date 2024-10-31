<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html>
<!-- ibsheet css -->
<link rel="stylesheet" href="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/css/default/main.css"/>

<!--  ibsheet 필수 js -->
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/ibleaders.js"></script>
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/ibsheet.js"></script>
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/locale/ko.js"></script>

<!--  ibsheet 선택/추가 js -->
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/plugins/ibsheet-common.js"></script>
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/plugins/ibsheet-dialog.js"></script>
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/plugins/ibsheet-excel.js"></script>

<!--  외부 라이브러리 추가 -->
<script  src="http://code.jquery.com/jquery-latest.min.js"></script>

<script type= text/javascript>
   //
    function reqList() {
        $.ajax({
            url : "/file/readFileList",
            type : "GET",
            data : { Paging : 1, Count : 200 , folder : "D:\\torrent\\"},
            beforeSend : function(xhr, settings) {
                console.log("before Send");
            },
            success : function(data) {
                console.log("success");
                IBSheet["sheet"].loadSearchData(data);
                var header = sheet.getHeaderRows()[0];
                header["RowMerge"] = false;
            },
            error : function(xhr, status, error) {
                console.log("error : " + error + ", status : " + status);
            },
            complete : function(xhr, status) {
                console.log("complete");
            }
        });
    }
    
    //
    function insData() {
        $.ajax({
            url : "/file/insertFileBatch",
            type : "POST",
            data : { pageno : 1, pagesize : 5 , folder : "D:\\torrent\\"},
            beforeSend : function(xhr, settings) {
                console.log("before Send");
            },
            success : function(data) {
                console.log("success");
                IBSheet["sheet"].loadSearchData(data);
            },
            error : function(xhr, status, error) {
                console.log("error : " + error + ", status : " + status);
            },
            complete : function(xhr, status) {
                console.log("complete");
            }
        });
    }
    
    //
    function loadUrl(filepath) {
        $.ajax({
            url : "/web/img",
            type : "GET",
            data : { "filepath" : filepath },
            beforeSend : function(xhr, settings) {
                console.log("before Send");
            },
            success : function(data) {
                console.log("success");
//                 $("#showImage").html("<img src='"+data+"'/>");
//                 var newImage = document.createElement('img');
//                 newImage.src = data;
// $("#showImage").html(data);
            $("#showVideo").html( data);
// $("#showImage").attr("src", "data:image/png;base64," + data);
            },
            error : function(xhr, status, error) {
                console.log("error : " + error + ", status : " + status);
            },
            complete : function(xhr, status) {
                console.log("complete");
            }
        });
    }

    function delList() {
        $("#resp").html("");
    }
    
    function initSheet() {
        var OPT = {
             Cfg:{
                 
                 dataMerge:1
                , SearchMode:4
                , InfoRowConfig: {
                    "Visible": true,
                    "Layout": ["Paging", {Value:"별도 표시하는 내용을 입력할 수 있습니다.", TextColor:"#FF0000"}, "Count"], // default: ["Paging","Count"])
                    "Space": "Bottom",  // "Top", "Bottom" 위치 설정
                    "Format": "CHANGEROWS개 행이 수정되었습니다." // Count 영역에 들어갈 내용
                }
          }
        , Events:{
             onMouseOver : function(evtParam) {
                if (evtParam.row && evtParam.col && evtParam.orow && evtParam.ocol) {
                        if(evtParam.col == "fileName") {
                             console.log("현재 셀 값은 "+ sheet.getValue({row:evtParam.row, col:evtParam.col})+"|"+evtParam.row["filePath"]);
                            
                            var imgSrc = evtParam.row["filePath"];
                            switch(getExtensionOfFilename(imgSrc)) {
                            case ".jpg":
//                                   $("#showImage").show(); // 이미지 보여줄 레이이 보이기
//                                   loadUrl(imgSrc);
                                  $("#imgView").attr("src", "/web/img?filepath="+imgSrc);
                                break;
                                default:
//                                 $("#showVideo").html("/web/img?filepath="+imgSrc);
//                                 loadUrl(imgSrc);
                                    break;
                            }
//                             const encFileName = encodeURIComponent(imgSrc);
//                             var imgHtml = "<img src='http://localhost:8080/web/img?filepath="+imgSrc+"'/>";
//                             var imgHtml = "<img src='http://localhost:8080/web/img?filepath="+imgSrc+"'/>";
//                             $("#showImage").html(imgSrc); // 레이어에 HTML 추가
//                             $("#showImage").html(imgHtml); // 레이어에 HTML 추가
//                             $("#showImage").append(imgHtml); // 레이어에 HTML 추가
                            
//                             <video  src="비디오파일URL"  controls>대체텍스트</video>
                        }
                    }
                }
                , onBlur:function(evtParam){
                    alert("시트가 포커스를 잃었습니다. 이전 포커스된 셀의 값은"+evtParam.sheet.getValue({row :evtParam.orow, col: evtParam.ocol}+"입니다."));
                }
                , onSelectMenu:function(evtParam){
                }
                , onclick:function (evtParam){
                }
             },
              "LeftCols": [
                {"Type": "Int","Width": 50,"Align": "Center","Name": "SEQ"}
              ],
                 "total":201,  //Paging 조회시에만 필요(최초 조회때 한번만 필요)
                Cols:[
                      {Header: "체크",    Name: "chk",         Type: "Bool"}
                    , {Header: "아이디",  Name: "id",           Type: "Text"}
                    , {Header: "파일이름", Name: "fileName",    Type: "Text"}
                    , {Header: "확장자",   Name: "fileExt",     Type: "Text"}
                    , {Header: "사이즈",   Name: "fileSize",    Type: "Text"}
                    , {Header: "위치",    Name: "directory",   Type: "Text"}
                    , {Header: "타입",    Name: "fileType",    Type: "Text"}
                    , {Header: "삭제",    Name: "delYn",       Type: "Text"}
                    , {Header: "사용",    Name: "actionYn",    Type: "Text"}
                    , {Header: "수정일",   Name: "updateDt",    Type: "Text"}
                    , {Header: "내용",    Name: "fileComment", Type: "Text"}//Align: "center"
                    , {Header: "파일경로", Name: "filePath",    Type: "Text"}
                ]
        };
        
        var DATA = [];

        IBSheet.create({
            id: "sheet",        // 시트 객체 ID
            el: "sheetDiv",     // 시트를 생성할 DIV객체 ID
            options: OPT,       // 초기화 구문 변수
            data: DATA          // 초기 로딩 데이터
        });
    }

    function getRowIndex() {
        var sheet = document.getElementById("sheet");
        //sheet.getRowByIndex(3);
        var tRow = IBSheet["sheet"].getTotalRowCount();
        alert(tRow);
    }
    
    /*
    **
    */
    function delData() {
        var mySheet = IBSheet["sheet"];
        if(mySheet.hasChangedData()) {
            var saveData = mySheet.getSaveString();
            if(saveData.indexOf("RequiredError")==-1){
                var addRowCnt = mySheet.getRowsByStatus("Added,!Deleted").length;
                // 수정행
                var chgRowCnt = mySheet.getRowsByStatus("Changed,!Added,!Deleted").length;
                // 삭제행
                var delRowCnt = mySheet.getRowsByStatus("Deleted").length;
                if(confirm("신규 : "+addRowCnt+"건\n수정 : "+chgRowCnt+"건\n삭제 : "+delRowCnt+"건\n을 저장하시겠습니까?")){
                }
            }else{
                var errCode = saveData.split("|");
                var hRow = mySheet.getHeaderRows();
                var colTitle = mySheet.getString(hRow[hRow.length - 1] , errCode[3]);
                alert("${colTitle}열은 필수 입력컬럼 입니다.");
                mySheet.focus(mySheet.getRowById(errCode[2]),errCode[3] );
                return;
            }
        }else{
            alert("수정 된 데이터가 없습니다.");
        }
    }
        
    function readTextFile(file)
    {
        var rawFile = new XMLHttpRequest();
        rawFile.open("GET", file, false);
        rawFile.onreadystatechange = function ()
        {
            if(rawFile.readyState === 4)
            {
                if(rawFile.status === 200 || rawFile.status == 0)
                {
                    var allText = rawFile.responseText;
                    alert(allText);
                }
            }
        }
        rawFile.send(null);
    }
    
    /** * 파일명에서 확장자명 추출 * @param filename   파일명 * @returns _fileExt 확장자명 */
    function getExtensionOfFilename(filename) {
        var _fileLen = filename.length;
        /**
        * lastIndexOf('.')
        * 뒤에서부터 '.'의 위치를 찾기위한 함수
        * 검색 문자의 위치를 반환한다.
        * 파일 이름에 '.'이 포함되는 경우가 있기 때문에 lastIndexOf() 사용
        */
        var _lastDot = filename.lastIndexOf('.'); // 확장자 명만 추출한 후 소문자로 변경
        var _fileExt = filename.substring(_lastDot, _fileLen).toLowerCase();
        
        return _fileExt;
    }
    
    //
    function noImage() {
        $("#imgView").attr("src", "");
    }
</Script>

<body onload="initSheet()">
  <div class="btnCls">
     <button type="button" class="mainBtnB" onclick="reqList();">조회</button>
     <button type="button" class="mainBtnB" onclick="sampleBtn();">신규</button>
     <button type="button" class="mainBtnB" onclick="insData();">저장</button>
     <button type="button" class="mainBtnB" onclick="delData();">삭제</button>
  </div>
  <hr>
  <!-- 시트가 될 DIV 객체 -->
  <div id="sheetDiv" style="width:100%; height:500px;"></div>
   <div id="showImage" style="border:1px solid #dddddd;width:400px;height:400px;overflow:hidden;float:left;">
<!--    <img src="/web/img?filepath=D:\torrent\down\APP_3.0 ).png" id="imgView"/> -->
   <img src="" id="imgView" style="width:400px;height:400px;" onclick="javascript:noImage();"/>
   </div>
   <div id="showVideo" style="border:1px solid #dddddd;width:400px;height:400px;overflow:hidden;float:left;"></div>
<!--    <button type="button" class="mainBtnB" onclick="noImage();">닫기</button> -->
</body>
</html>