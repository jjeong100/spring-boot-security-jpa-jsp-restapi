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
            data : { pageno : 1, pagesize : 40 , folder : "D:\\torrent\\"},
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
            	 SearchMode:1
//              Menu: {
//                     Items: [
//                         { Name: "title", Text: "바로가기", Value:"0" },
//                         { Name: "title1", Text: "바로가기1", Value:"1" },
//                         { Name: "title2", Text: "바로가기2", Value:"2" },
//                         { Name: "title3", Text: "바로가기3", Value:"3" },
//                         { Name: "title4", Text: "바로가기4", Value:"4" }
//                     ]
//                 }
             }
        , Events:{
                onSelectMenu:function(evtParam){
                    // 메뉴에서 선택된 값으로 셀 값을 변경하기
                    //evtParam.sheet.setValue({row: evtParam.row, col: evtParam.col, val: evtParam.result, render: 1});
                    //evtParam.sheet.refreshRow(evtParam.row);

                    //example1 헤더행의 타이틀를 수정
//                     var hrow = sheet.getRowById("Header"); //헤더행 객체 얻기
//                     hrow["colName1"] = "수정할 헤더명";
//                     sheet.refreshCell(hrow, "colName1");
                },
//                 onRenderFirstFinish:function (evtParam) {
//                     console.log(evtParam.eventName + ' 발생');
// //                     var URL = '/samples/customer/paging.jsp';
// //                     var URL = '/index.jsp';
//                     var URL = '/file/readFileList';
//                     var param = {};

//                     if (location.href.indexOf('localhost') > -1) {
// //                       URL = '../html' + URL;
// //                     	URL = '../html' + URL;
//                     } else {
//                       URL = 'https://api.ibsheet.com/ibsheet/v8' + URL;
//                     }
                    
// //                     alert(evtParam.sheet.SearchMode);

//                     // 시트 최초 생성시 발생 이벤트
//                     if (evtParam.sheet.SearchMode > 1) { // 서버모듈 조회
//                       param = {
//                         url: URL,
//                         param: 'data=50&total=15000&searchMode=' + evtParam.sheet.SearchMode,
// //                         method: 'POST',
//                         method: 'GET',
//                         callback: function (rtn) {
//                           var rtnData = JSON.parse(rtn.data);
//                           console.log("rtnData : "+rtnData);

//                           evtParam.sheet.showMessageTime('<span style=\'color:black\'>조회가 완료되었습니다.<br> 서버모듈 전체 데이터 건수 : ' + rtnData.Total + '</span>', 1000);
//                         }
//                       };

//                       evtParam.sheet.doSearchPaging(param);
//                     } else { // 클라이언트 조회
//                       param = {
//                         url: URL,
// //                         method: 'POST',
//                         method: 'GET',
//                         callback: function (rtn) {
//                           var rtnData = JSON.parse(rtn.data);

//                           evtParam.sheet.showMessageTime('<span style=\'color:black\'>조회가 완료되었습니다.<br> 클라이언트 전체 데이터 건수 : ' + rtnData.Total + '</span>', 1000);
//                         }
//                       };

//                       evtParam.sheet.doSearch(param);
//                     }
//                   },
//                   onBeforeDataLoad:function (evtParam) {
//                     // 조회 후 로딩 전 발생 이벤트
//                     var d = evtParam.data;
//                     var _data;

//                     console.log("evtParam d:"+d);
//                     try {
//                       // 서버에서 가져온 데이터를 "데이터" 텝에 넣어준다.
//                       if (d.length > 20) { // 최대 20개 행만 텝에 넣어 주자(많아지면 화면이 엄청 느려질 수 있음)
//                         _data = JSON.stringify(d.slice(0, 20), null, 2);
//                         _data += _data.substring(0, _data.length - 1) + '...\n]';
//                       } else {
//                         _data = JSON.stringify(d, null, 2);
//                       }
                      
//                       console.log("_data : "+_data);
//                       myTabs.contents.items(1).setContents('<pre><code class=\'language-json\'>' + _data + '</code></pre>');
//                       hljs.initHighlighting.called = false;
//                       hljs.initHighlighting();
//                     } catch (e) {
//                       console.log('IBTab 이 존재하지 않습니다.');
//                       console.log(e);
//                     }
//                   },
//                   onAfterGotoPage:function (evtParam) {
//                     var sheet = evtParam.sheet;
//                     var page = sheet.getPageIndex(sheet.getFocusedPage()) + 1;

//                     if (document.getElementById('chk1').checked && currentPage != -1 && currentPage != page) {
//                       samplePageObj.externalFunction.makePageIndex(page);
//                     }
//                   }
             },
              "LeftCols": [
                {"Type": "Int","Width": 50,"Align": "Center","Name": "SEQ"}
              ],
                //각 열에 대한 정의 (열의 이름, 유형(Type), 포맷(Format)등을 설정)
                //열의 "Type"과 "Name" 속성은 반드시 설정되어야 합니다.
                Cols:[
                	{Header: "체크",  Name: "chk",           Type: "Bool"}
                    , {Header: "아이디",  Name: "id",           Type: "Text"}
                    , {Header: "파일이름", Name: "fileName",    Type: "Text"}
                    , {Header: "확장자",  Name: "fileExt",     Type: "Text"}
                    , {Header: "사이즈",  Name: "fileSize",    Type: "Text"}
                    , {Header: "위치",   Name: "directory",   Type: "Text"}
                    , {Header: "타입",   Name: "fileType",    Type: "Text"}
                    , {Header: "삭제",   Name: "delYn",       Type: "Text"}
                    , {Header: "사용",   Name: "actionYn",    Type: "Text"}
                    , {Header: "수정일",  Name: "updateDt",    Type: "Text"}
                    , {Header: "내용",   Name: "fileComment", Type: "Text"}//Align: "center"
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
    
    var sampleBtn = function () {
        if (arguments[0].type === 'checkbox') { // 페이지 네비게이션 표시 선택
          if (samplePageObj.init.Cfg.SearchMode == 1 || samplePageObj.init.Cfg.SearchMode == 4) { // 페이징 조회에서만 페이지네비게이션 표시
            if (arguments[0].checked) { // 페이징 조회에서만 페이지네비게이션 표시
              sheet.InfoRow.Visible = 0; // 페이지네비게이션 표시 안함
              sheet.rerender();

              if (arguments[0].id == 'chk1') {
                document.getElementById('chk2').checked = 0;
                samplePageObj.externalFunction.makePageIndex(sheet.getPageIndex(sheet.getFocusedPage()) + 1); // 페이지 초기화
              } else {
                samplePageObj.externalFunction.jqueryPagination(sheet.getPageIndex(sheet.getFocusedPage()) + 1);
                document.getElementById('chk1').checked = 0;
              }
              document.getElementById('pageNavi').style.display = 'block';
            } else {
              sheet.InfoRow.Visible = 1; // 페이지네비게이션 표시
              sheet.rerender();

              document.getElementById('pageNavi').style.display = 'none';
            }
          }
        } else {
          document.getElementById('pageNavi').style.display = 'none';
          document.getElementById('chk1').checked = 0;
          document.getElementById('chk2').checked = 0;
          sheet.dispose(); //  시트 삭제
          var sMode = arguments[1]; // 1:클라이언트페이징 , 2:서버페이징 , 3:서버스크롤페이징

          samplePageObj.init.Cfg = {
            SearchMode: sMode,
            Alternate: 2,
            InfoRowConfig: ''
          };
          if (sMode === 1) {
            samplePageObj.init.Cfg.PageLength = 50;
          } else if (sMode === 4) {
            samplePageObj.init.Cfg.SortCurrentPage = 1;
            samplePageObj.init.Cfg.PageLength = 50;
          }
          samplePageObj.create(); // 시트 생성
        }
      }
    var makePageIndex = function (tp) {
          try {
            if (tp == null) {
              currentPage = -1;

              return;
            }

            if (document.getElementById('pageNavi').firstChild) {
              document.getElementById('pageNavi').removeChild(document.getElementById('pageNavi').firstChild);
            }

            currentPage = tp;
            // 디비전체 데이터 건수를 알아야 한다. 조회 데이터구성 할때 etc 데이터에 TotalRow로 전체 데이터 건수를 가져 온다.
            var sr = sheet.getTotalRowCount(); // 전체 조회 건수
            var page = sheet.PageLength; // 페이지당 레코드 수
            var thispage = tp; // 현재 페이지
            var pagecnt = 5; // 한번에 표시할 페이지 개수
            var lastpage = Math.ceil(sr / page); // 마지막 페이지 인덱스

            var li = null;
            var tt = null;
            var a = null;
            var ul = document.createElement('DIV');

            ul.style.width = sheet.MainTable.clientWidth + 'px';
            ul.style.textAlign = 'center';

            var k = 1;

            if (thispage != 1) {
              li = document.createElement('span');
              li.style.padding = '1px 4px 1px 4px';
              a = document.createElement('a');
              a.style.cursor = 'pointer';
              tt = document.createTextNode('<<');
              samplePageObj.externalFunction.addEventHandler(li, 'click', samplePageObj.externalFunction.pageMove, 1);
              a.appendChild(tt);
              li.appendChild(a);
              ul.appendChild(li);
              li = document.createElement('span');
              li.style.padding = '1px 4px 1px 4px';

              a = document.createElement('a');
              a.style.cursor = 'pointer';
              samplePageObj.externalFunction.addEventHandler(li, 'click', samplePageObj.externalFunction.pageMove, tp - 1);
              tt = document.createTextNode('<');
              a.appendChild(tt);
              li.appendChild(a);
              ul.appendChild(li);
            }

            var sr = (Math.ceil(tp / pagecnt) * pagecnt) - (pagecnt - 1);

            for (var i = sr; i < (sr + pagecnt); i++) {
              if (i <= lastpage) {
                li = document.createElement('span');
                li.style.padding = '1px 4px 1px 4px';
                a = document.createElement('a');

                a.style.cursor = 'pointer';
                tt = document.createTextNode(i);
                samplePageObj.externalFunction.addEventHandler(li, 'click', samplePageObj.externalFunction.pageMove, i);
                if (i == tp) {
                  li.style.fontWeight = 'bold';
                  li.style.textDecoration = 'underline';
                }
                a.appendChild(tt);
                li.appendChild(a);
                ul.appendChild(li);
              }
            }

            if (thispage < lastpage) {
              a = document.createElement('a');
              a.style.cursor = 'pointer';
              li = document.createElement('span');
              li.style.padding = '1px 4px 1px 4px';
              tt = document.createTextNode('>');
              samplePageObj.externalFunction.addEventHandler(li, 'click', samplePageObj.externalFunction.pageMove, tp + 1);
              a.appendChild(tt);
              li.appendChild(a);
              ul.appendChild(li);
              li = document.createElement('span');
              li.style.padding = '1px 4px 1px 4px';
              tt = document.createTextNode('>>');
              a = document.createElement('a');
              a.style.cursor = 'pointer';
              samplePageObj.externalFunction.addEventHandler(li, 'click', samplePageObj.externalFunction.pageMove, lastpage);
              a.appendChild(tt);
              li.appendChild(a);
              ul.appendChild(li);
            }

            ul.className = 'pageindex';

            document.getElementById('pageNavi').appendChild(ul);
          } catch (ex) {
            alert(ex.message);
          }
        }

    var jqueryPagination = function (move) {
          $('#pageNavi').html('');
          $('#pageNavi').removeData('twbs-pagination');

          // set pagination
          $('#pageNavi').twbsPagination({
            totalPages: Math.ceil(sheet.getTotalRowCount() / sheet.PageLength) || 1,
            visiblePages: 5,
            startPage: move || 1,
            initiateStartPageClick: false,
            first: '«',
            prev: '‹',
            next: '›',
            last: '»',
            paginationClass: 'pagination pagination-sm',
            onPageClick: function (event, page) {
              sheet.goToPageByIndex(page);
            }
          });

          $('#pageNavi > ul').css('width', sheet.MainTable.clientWidth);
          $('#pageNavi > ul').css('text-align', 'center');
        }

    var pageMove = function (cPage) {
          sheet.goToPageByIndex(cPage);
        }

    var addEventHandler = function (obj, evtName, func, param) {
        if (obj.addEventListener) { // all browsers except IE before version 9
           obj.addEventListener(evtName, function () { func(param); }, false);
        } else if (obj.attachEvent) { // IE before version 9
           obj.attachEvent('on' + evtName, function () { func(param); });
        }
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
// 	            if(confirm("신규 : ${addRowCnt}건\n수정 : ${chgRowCnt}건\n삭제 : ${delRowCnt}건\n을 저장하시겠습니까?")){
	            if(confirm("신규 : "+addRowCnt+"건\n수정 : "+chgRowCnt+"건\n삭제 : "+delRowCnt+"건\n을 저장하시겠습니까?")){
// 	                mySheet.ajax({
// 	                    url : "./save.jsp",
// 	                    param : saveData,
// 	                    method : "post",
// 	                    callback: function(res, data, resXml, response){
// 	                        // 저장 완료 처리
// 	                        if( data && JSON.parse(data)?.IO?.Result > -1){
// 	                            mySheet.acceptChangedData();
// 	                        }
// 	                    }
// 	                });
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
<!--   <div id='ib-tab'></div> -->
<!-- <div id='ib-contents'></div> -->
<!--   <div id="resp">11111</div> -->
<!-- <div id="pageNavi"></div>  -->

</body>
</html>