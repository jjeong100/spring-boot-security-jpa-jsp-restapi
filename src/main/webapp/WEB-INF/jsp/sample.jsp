<!-- ibsheet css -->
<link rel="stylesheet" href="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/css/default/main.css"/><!--  ibsheet 필수 js -->
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/ibleaders.js"></script>
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/ibsheet.js"></script>
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/locale/ko.js"></script>

<!--  ibsheet 선택/추가 js -->
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/plugins/ibsheet-common.js"></script>
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/plugins/ibsheet-dialog.js"></script>
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/plugins/ibsheet-excel.js"></script>

<script src="https://www.ibsheet.com/v7/libs/pagination/jquery.twbsPagination.min.js"></script>

<div class='ibsheet-wrap' id='descFunc'><div><input type='checkbox' id='chk1' onclick='ib.sampleBtn(this)'> <label class='mgr10'>페이지네비게이션 표시</label><hr><input type='checkbox' id='chk2' onclick='ib.sampleBtn(this)'> <label class='mgr10'>JQuery 네비게이션 표시</label><hr><button class='mgr10' onclick='ib.sampleBtn(this, 1)'>클라이언트페이징</button><hr><button class='mgr10' onclick='ib.sampleBtn(this, 4)'>서버페이징</button><hr><button class='mgr10' onclick='ib.sampleBtn(this, 3)'>서버스크롤페이징</button></div></div>


<div style='height:calc(100% - 128px)'><div id='sheetDiv' style='width:100%;height:90%'></div><input type='hidden' name='pagenum' id='pagenum' value='1'><div id='pageNavi' style='width:100%;height:100px;align:center;display:none;'></div></div>