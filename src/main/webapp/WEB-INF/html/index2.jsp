<html>
<!-- ibsheet css -->
<link rel="stylesheet" href="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/css/default/main.css"/>
<!--  ibsheet íì js -->
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/ibleaders.js"></script>
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/ibsheet.js"></script>
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/locale/ko.js"></script>

<!--  ibsheet ì í/ì¶ê° js -->
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/plugins/ibsheet-common.js"></script>
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/plugins/ibsheet-dialog.js"></script>
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/ibsheet/plugins/ibsheet-excel.js"></script>

<!--  외부 라이브러리 추가 -->

<Script>
    //ì±ê³µ : beforeSend -> success -> complete
    //ì¤í¨ : beforeSend -> error -> complete
    function reqList() {
        //ajax í¸ì¶
        $.ajax({
            //ìì²­ ìë¹ì¤ url ì§ì 
            url : "/contact/list.do",
            //ë©ìë íì ì§ì 
            type : "GET",
            //ìì²­ ì ìë²ë¡ ì ë¬í  ë°ì´í° ì§ì 
            data : { pageno : 1, pagesize : 5 },
            //ìì²­ ì ì¡ ì ì ì¤íë  ì½ë°± í¨ì ì§ì 
            beforeSend : function(xhr, settings) {
                console.log("before Send");
            },
            //ìì²­ ì±ê³µ ì ëìí  ì½ë°± í¨ì ì§ì 
            success : function(data) {
                console.log("success");
                //pììì ìë²ë¡ë¶í° ìëµë°ì ë°ì´í° íì¶
                $("#resp").html(data);
            },
            //ìì²­ ì¤í¨ ì ì¤íë  ì½ë°± í¨ì ì§ì 
            error : function(xhr, status, error) {
                console.log("error : " + error + ", status : " + status);
            },
            //ìì²­ ìë£ ì ì¤íë  ì½ë°± í¨ì ì§ì 
            //ìì²­ ì±ê³µ/ì¤í¨ ì¬ë¶ì ê´ê³ìì´ ë¬´ì¡°ê±´ ì¤í
            complete : function(xhr, status) {
                console.log("complete");
            }
        });
    }

    function delList() {
        //pìì ì´ê¸°í
        $("#resp").html("");
    }
    
    function initSheet() {
        //ìí¸ì ì´ê¸°í ìì± ì¤ì 
//         var OPT = {
//              //íê³ ì  ì¢ì¸¡ ì»¬ë¼ ì¤ì 
//            Cfg:{
//            Menu: {
//                   Items: [
//                       { Name: "title", Text: "ë°ë¡ê°ê¸°", Value:"0" },
//                       { Name: "title1", Text: "ë°ë¡ê°ê¸°1", Value:"1" },
//                       { Name: "title2", Text: "ë°ë¡ê°ê¸°2", Value:"2" },
//                       { Name: "title3", Text: "ë°ë¡ê°ê¸°3", Value:"3" },
//                       { Name: "title4", Text: "ë°ë¡ê°ê¸°4", Value:"4" }
//                   ]
//               }
//            },Events:{
//               onSelectMenu:function(evtParam){
//                   // ë©ë´ìì ì íë ê°ì¼ë¡ ì ê°ì ë³ê²½íê¸°
//                   //evtParam.sheet.setValue({row: evtParam.row, col: evtParam.col, val: evtParam.result, render: 1});
//                   //evtParam.sheet.refreshRow(evtParam.row);

//       //          alert(1);

//                   //example1 í¤ëíì íì´íë¥¼ ìì 
//                   var hrow = sheet.getRowById("Header"); //í¤ëí ê°ì²´ ì»ê¸°
//                   hrow["colName1"] = "ìì í  í¤ëëª";
//                   sheet.refreshCell(hrow, "colName1");
//               }
//            },
//             "LeftCols": [
//               {"Type": "Int","Width": 50,"Align": "Center","Name": "SEQ"}
//             ],
//               //ê° ì´ì ëí ì ì (ì´ì ì´ë¦, ì í(Type), í¬ë§·(Format)ë±ì ì¤ì )
//               //ì´ì "Type"ê³¼ "Name" ìì±ì ë°ëì ì¤ì ëì´ì¼ í©ëë¤.
//               Cols:[
//                   {Header: "ì»¬ë¼1", Name: "colName1", Type: "Text"},
//                   {Header: "ì»¬ë¼2", Name: "colName2", Type: "Text", Align: "center"}
//               ]
//           };
        
        //ì´ê¸° ë°ì´í° ì¤ì 
        var DATA = [{"colName1": "a",    "colName2": "01"},
        {"colName1": "ì¥",    "colName2": "02"},
        {"colName1": "ì¥ì",    "colName2": "03"},
        {"colName1": "ì¥ìì°",    "colName2": "04"},];

        IBSheet.create({
            id: "sheet",        // ìí¸ ê°ì²´ ID
            el: "sheetDiv",     // ìí¸ë¥¼ ìì±í  DIVê°ì²´ ID
            options: OPT,       // ì´ê¸°í êµ¬ë¬¸ ë³ì
            data: DATA          // ì´ê¸° ë¡ë© ë°ì´í°
        });
    }

    function getRowIndex() {
        alert(1);
        var sheet = document.getElementById("sheet");
        //sheet.getRowByIndex(3);
        var tRow = IBSheet["sheet"].getTotalRowCount();
        alert(tRow);
    }
</Script>
<body onload="initSheet()">
  <div class="btnCls">
     <button type="button" class="mainBtnB" onclick="getRowIndex();">ì¡°í</button>
     <button type="button" class="mainBtnB">ì ê·</button>
  </div>
  <hr>
  <!-- ìí¸ê° ë  DIV ê°ì²´ -->
  <div id="sheetDiv" style="width:100%; height:500px;"></div>
</body>
</html>