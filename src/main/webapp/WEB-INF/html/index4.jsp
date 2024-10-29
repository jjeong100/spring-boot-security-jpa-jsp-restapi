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

<Script>
    //성공 : beforeSend -> success -> complete
    //실패 : beforeSend -> error -> complete
    function reqList() {
        //ajax 호출
        $.ajax({
            //요청 서비스 url 지정
            url : "/file/readFileList",
            //메소드 타입 지정
            type : "GET",
            //요청 시 서버로 전달할 데이터 지정
            data : { Paging : 1, Count : 200 , folder : "D:\\torrent\\"},
            //요청 전송 전에 실행될 콜백 함수 지정
            beforeSend : function(xhr, settings) {
                console.log("before Send");
            },
            //요청 성공 시 동작할 콜백 함수 지정
            success : function(data) {
                console.log("success");
                //p요소에 서버로부터 응답받은 데이터 표출
//                 console.log("■ data : "+JSON.stringify(data));


                IBSheet["sheet"].loadSearchData(data);
//                 IBSheet["sheet"].getRow(0)["fileExt"].CLSRowSpan =3;
// sheet.setMergeRange( sheet.getRowById("AR2"), "fileExt", sheet.getRowById("AR4"), "fileSize" );

var header = sheet.getHeaderRows()[0];
header["RowMerge"] = false;

              //특정행을 병합하지 않음.
//                 var row = sheet.getFirstVisibleRow();
//                 row["chk"] = false;
//                 sheet.setAutoMerge( {dataMerge: 1, headerMerge: 0, prevColumnMerge: 0} );
            },
            //요청 실패 시 실행될 콜백 함수 지정
            error : function(xhr, status, error) {
                console.log("error : " + error + ", status : " + status);
            },
            //요청 완료 시 실행될 콜백 함수 지정
            //요청 성공/실패 여부와 관계없이 무조건 실행
            complete : function(xhr, status) {
                console.log("complete");
            }
        });
    }
    
    function insData() {
        //ajax 호출
        $.ajax({
            //요청 서비스 url 지정
//             url : "/file/insertFileList",
            url : "/file/insertFileBatch",
            //메소드 타입 지정
            type : "POST",
            //요청 시 서버로 전달할 데이터 지정
            data : { pageno : 1, pagesize : 5 , folder : "D:\\torrent\\"},
            //요청 전송 전에 실행될 콜백 함수 지정
            beforeSend : function(xhr, settings) {
                console.log("before Send");
            },
            //요청 성공 시 동작할 콜백 함수 지정
            success : function(data) {
                console.log("success");
                //p요소에 서버로부터 응답받은 데이터 표출
//                 console.log("■ data : "+JSON.stringify(data));
                IBSheet["sheet"].loadSearchData(data);
            },
            //요청 실패 시 실행될 콜백 함수 지정
            error : function(xhr, status, error) {
                console.log("error : " + error + ", status : " + status);
            },
            //요청 완료 시 실행될 콜백 함수 지정
            //요청 성공/실패 여부와 관계없이 무조건 실행
            complete : function(xhr, status) {
                console.log("complete");
            }
        });
    }

    function delList() {
        //p요소 초기화
        $("#resp").html("");
    }
    
    function initSheet() {
        //시트의 초기화 속성 설정
        var OPT = {
             //틀고정 좌측 컬럼 설정
             Cfg:{
                 
                 dataMerge:1,
                 SearchMode:4
//              Menu: {
//                     Items: [
//                         { Name: "title", Text: "바로가기", Value:"0" },
//                         { Name: "title1", Text: "바로가기1", Value:"1" },
//                         { Name: "title2", Text: "바로가기2", Value:"2" },
//                         { Name: "title3", Text: "바로가기3", Value:"3" },
//                         { Name: "title4", Text: "바로가기4", Value:"4" }
//                     ]
//                 }
//              , "InfoRowConfig": {
//                      "Visible": true,
//                      "Layout": [
//                        "Paging",
//                        "Count"
//                      ]
//                  }
            , InfoRowConfig: {
                "Visible": true,
                "Layout": ["Paging", {Value:"별도 표시하는 내용을 입력할 수 있습니다.", TextColor:"#FF0000"}, "Count"], // default: ["Paging","Count"])
                "Space": "Bottom",  // "Top", "Bottom" 위치 설정
                "Format": "CHANGEROWS개 행이 수정되었습니다." // Count 영역에 들어갈 내용
            }
        }
        , Events:{
        	
        	 onMouseOver : function(evtParam) {
        		 //alert(" 셀의 값은"+evtParam.sheet.getValue({row :evtParam.orow, col: evtParam.ocol}+"입니다."));
        		if (evtParam.row && evtParam.col && evtParam.orow && evtParam.ocol) {
// 			            console.log("이전 셀 값은 "+ sheet.getValue({row:evtParam.orow, col:evtParam.ocol}));
// 			            console.log("현재 셀 값은 "+ sheet.getValue({row:evtParam.row, col:evtParam.col}));
        			    if(evtParam.col == "fileName") {
        			    	 console.log("현재 셀 값은 "+ sheet.getValue({row:evtParam.row, col:evtParam.col})+"|"+evtParam.row["filePath"]);
        			    	 
        			    	$("#showImage").show(); // 이미지 보여줄 레이이 보이기
//         			        var imgSrc = ""; // 이미지 소스 저장 변수
//         			        imgSrc = $(this).attr("src"); // attr()로 src get하기
//         			        imgSrc = imgSrc.substr(imgSrc.lastIndexOf("/") + 1); // 순수 파일명만 얻기
        			        //imgSrc = "<img src='../ProductImages/bigs/" + imgSrc + "' width='<?php echo $width?>'/>"; // 큰이미지 설정
//         			        var imgSrc = "<img src=\"<spring:url value='"+ evtParam.row["filePath"] +"'>\" width=\"200\" height=\"200\"/>";
//         			        var imgSrc = "<img src=\""+ evtParam.row["filePath"] +"\" width=\"200\" height=\"200\"/>";
        			        var imgSrc = evtParam.row["filePath"];
//         			        var imgSrc = "<resources mapping=\"/image/**\" location=\""+ evtParam.row["filePath"] +"\">";
//         			        var imgSrc = "<img src='D:\\torrent\\20241001\\1\\性感反差小姐姐『iiiiiknfap』户外大胆露出 紧张又刺激 演唱会归来有内裤还是无内裤\\F35tffIbIAAZBRH.jpg' width=\"200\" height=\"200\"/>";
        			        $("#showImage").html(imgSrc); // 레이어에 HTML 추가
        			    }
			        }
        	    }
        	 , onBlur:function(evtParam){
        	        alert("시트가 포커스를 잃었습니다. 이전 포커스된 셀의 값은"+evtParam.sheet.getValue({row :evtParam.orow, col: evtParam.ocol}+"입니다."));
        	    }
                , onSelectMenu:function(evtParam){
                }
                , onclick:function (evtParam){
//                       console.log(evtParam);
//                     //현재 포커스가 위치한 페이지
//                      var page = evtParam.sheet.getFocusedPage();
//                      //페이지 인덱스 확인
//                      var pidx = evtParam.sheet.getPageIndex(page);
                     
//                      switch(evtParam.cellType){
//                      case "Button":
//                          if(evtParam.col == "PagerNext"){
// //                          console.log("■ page : "+JSON.stringify(page));
//                             console.log("■ pidx  : "+pidx);
                            
// 							console.log("■ page keys : "+Object.keys(page));
// 							console.log("■ page values : "+Object.values(page));
							
// 							for(var i=0;i<Object.values(page).length;i++){// Object.values(page)) {
// 							    console.log("["+Object.keys(page)[i]+"] : "+Object.values(page)[i]);
// 							}
//                          }
//                          break;
//                      }
                 }
             },
              "LeftCols": [
                {"Type": "Int","Width": 50,"Align": "Center","Name": "SEQ"}
              ],
                //각 열에 대한 정의 (열의 이름, 유형(Type), 포맷(Format)등을 설정)
                //열의 "Type"과 "Name" 속성은 반드시 설정되어야 합니다.
                 "total":201,  //Paging 조회시에만 필요(최초 조회때 한번만 필요)
                Cols:[
                    {Header: "체크",  Name: "chk",           Type: "Bool"}
                    , {Header: "아이디",  Name: "id",           Type: "Text"}
//                     , {Header: "temp", Name: "temp",    Type: "Text"}
                    , {Header: "파일이름", Name: "fileName",    Type: "Text"}
                    , {Header: "확장자",  Name: "fileExt",     Type: "Text"}
                    , {Header: "사이즈",  Name: "fileSize",    Type: "Text"}
                    , {Header: "위치",   Name: "directory",   Type: "Text"}
                    , {Header: "타입",   Name: "fileType",    Type: "Text"}
                    , {Header: "삭제",   Name: "delYn",       Type: "Text"}
                    , {Header: "사용",   Name: "actionYn",    Type: "Text"}
                    , {Header: "수정일",  Name: "updateDt",    Type: "Text"}
                    , {Header: "내용",   Name: "fileComment", Type: "Text"}//Align: "center"
                    , {Header: "파일경로",   Name: "filePath", Type: "Text"}
//                     , {Header: "이미지",   Name: "tagImg"}
                ]
            };
//         //초기 데이터 설정
//         var DATA = [{"id": "a","fileName": "01", "fileExt":"테스트"}];
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
        /// 수정된 데이터가 있는지 판별
        if(mySheet.hasChangedData()) {
            var saveData = mySheet.getSaveString();
            // 필수값 확인
            if(saveData.indexOf("RequiredError")==-1){
                var addRowCnt = mySheet.getRowsByStatus("Added,!Deleted").length;
                // 수정행
                var chgRowCnt = mySheet.getRowsByStatus("Changed,!Added,!Deleted").length;
                // 삭제행
                var delRowCnt = mySheet.getRowsByStatus("Deleted").length;
//              if(confirm("신규 : ${addRowCnt}건\n수정 : ${chgRowCnt}건\n삭제 : ${delRowCnt}건\n을 저장하시겠습니까?")){
                if(confirm("신규 : "+addRowCnt+"건\n수정 : "+chgRowCnt+"건\n삭제 : "+delRowCnt+"건\n을 저장하시겠습니까?")){
//                  mySheet.ajax({
//                      url : "./save.jsp",
//                      param : saveData,
//                      method : "post",
//                      callback: function(res, data, resXml, response){
//                          // 저장 완료 처리
//                          if( data && JSON.parse(data)?.IO?.Result > -1){
//                              mySheet.acceptChangedData();
//                          }
//                      }
//                  });
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
<!--   <img src="showImage" style="border:1px solid #dddddd;width:200px;height:200px;overflow:hidden;"/> -->
   <div id="showImage" style="border:1px solid #dddddd;width:200px;height:200px;overflow:hidden;"></div>
<!--    <input type="file" id="showImage" style="border:1px solid #dddddd;width:200px;height:200px;overflow:hidden;"> -->
</body>
</html>