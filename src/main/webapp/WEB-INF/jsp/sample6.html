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

<script>
function initSheet() {
    //시트의 초기화 속성 설정
    var OPT = {
		 //틀고정 좌측 컬럼 설정
		 Cfg:{
		 Menu: {
				Items: [
					{ Name: "title", Text: "바로가기", Value:"0" },
					{ Name: "title1", Text: "바로가기1", Value:"1" },
					{ Name: "title2", Text: "바로가기2", Value:"2" },
					{ Name: "title3", Text: "바로가기3", Value:"3" },
					{ Name: "title4", Text: "바로가기4", Value:"4" }
				]
			}
		 },Events:{
		 	onSelectMenu:function(evtParam){
				// 메뉴에서 선택된 값으로 셀 값을 변경하기
				//evtParam.sheet.setValue({row: evtParam.row, col: evtParam.col, val: evtParam.result, render: 1});
				//evtParam.sheet.refreshRow(evtParam.row);

	//			alert(1);

				//example1 헤더행의 타이틀를 수정
				var hrow = sheet.getRowById("Header"); //헤더행 객체 얻기
				hrow["colName1"] = "수정할 헤더명";
				sheet.refreshCell(hrow, "colName1");
			}
		 },
		  "LeftCols": [
			{"Type": "Int","Width": 50,"Align": "Center","Name": "SEQ"}
		  ],
            //각 열에 대한 정의 (열의 이름, 유형(Type), 포맷(Format)등을 설정)
            //열의 "Type"과 "Name" 속성은 반드시 설정되어야 합니다.
            Cols:[
                {Header: "컬럼1", Name: "colName1", Type: "Text"},
                {Header: "컬럼2", Name: "colName2", Type: "Text", Align: "center"}
            ]
        };
    //초기 데이터 설정
    var DATA = [{"colName1": "a",    "colName2": "01"},
	{"colName1": "장",    "colName2": "02"},
	{"colName1": "장순",    "colName2": "03"},
	{"colName1": "장순연",    "colName2": "04"},];

    IBSheet.create({
        id: "sheet",        // 시트 객체 ID
        el: "sheetDiv",     // 시트를 생성할 DIV객체 ID
        options: OPT,       // 초기화 구문 변수
        data: DATA          // 초기 로딩 데이터
    });
}

function getRowIndex() {
	alert(1);
	var sheet = document.getElementById("sheet");
	//sheet.getRowByIndex(3);
	var tRow = IBSheet["sheet"].getTotalRowCount();
	alert(tRow);
}

/*
IBSheet.onBeforeCreate = function(obj){
	alert(1);
	options.Cfg = {
	 // 기존에 설정된 메뉴
		Menu: {
			Items: [
				{ Name: "title", Text: "바로가기", Value:"0" },
				{ Name: "title1", Text: "바로가기1", Value:"1" },
				{ Name: "title2", Text: "바로가기2", Value:"2" },
				{ Name: "title3", Text: "바로가기3", Value:"3" },
				{ Name: "title4", Text: "바로가기4", Value:"4" }
			]
		}
	}

	options.Events = {
		onSelectMenu:function(evtParam){
			// 메뉴에서 선택된 값으로 셀 값을 변경하기
			//evtParam.sheet.setValue({row: evtParam.row, col: evtParam.col, val: evtParam.result, render: 1});
			//evtParam.sheet.refreshRow(evtParam.row);
		}
	}
};
*/
</script>
<body onload="initSheet()">
  <div class="btnCls">
     <button type="button" class="mainBtnB" onclick="getRowIndex();">조회</button>
     <button type="button" class="mainBtnB">신규</button>
  </div>
  <hr>
  <!-- 시트가 될 DIV 객체 -->
  <div id="sheetDiv" style="width:100%; height:500px;"></div>
</body>