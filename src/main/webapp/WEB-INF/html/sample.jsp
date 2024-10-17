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
<script src="https://demo.ibsheet.com/ibsheet/v8/samples/customer-sample/assets/vendors/fontawesome.min.js"></script>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script>
/**
 * 
 */
function retrieve() {
    //영화진흥원 영화목록 openapi  (https://www.kobis.or.kr/kobisopenapi/homepg/apiservice/searchServiceInfo.do)
    const url = "http://kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json?key=82ca741a2844c5c180a208137bb92bd7&itemPerPage=100&prdtEndYear=2000";
    axios({
        url: url,
        method: 'get',
        responseType: 'json'
    })
    .then(function (response) {
        //리턴데이터 확인
        // console.log(response); 
        
        // 데이터 가공
        const data = {"data":response.data.movieListResult.movieList};
        mySheet.loadSearchData(data); //비동기 형식 주의
    }).catch(function (error) {
        console.log("데이터 조회 중 오류가 발생하였습니다.");
    });
}
</script>
<body>
<button type="button" onclick="retrieve()">조회</button>
<div id="sheetEl" style="width:100%;height:500px"/>
<script>
IBSheet.create({
  id: "mySheet",
  el: "sheetEl",
  options: {
    Cfg: {
    	"SearchMode": 4,
        "SectionCanResize": 1,
        "CanSort": false,
        "CanColMove": false,
        "CanColResize": false,
        "HeaderCheck": 0,
        "CanEdit": 1,
        "Hover": 0,
        "SelFocusColor": 1,
        "InfoRowConfig": {
          "Visible": true,
          "Layout": [
            "Paging",
            "Count"
          ]
        },
        "Export": {
          "Url": "https://dev.ibsheet.com/api/ibsheet/v8/"
        }
    },
    LeftCols:[
        {Header:"순번", Type:"Int", Name:"SEQ", Align:"center"}
    ],
    Cols:[
        {Header:"영화명", Type:"Text", Name:"movieNm"},
        {Header:"영화코드", Type:"Text", Name:"movieCd"},
        {Header:"제작국가", Type:"Text", Name:"nationAlt"},
        {Header:"장르", Type:"Text", Name:"genreAlt"},
        {Header:"개봉일", Type:"Date", Name:"openDt", DataFormat:"yyyyMMdd", Format: "yyyy-MM-dd" },
    ],
  }
});
</script>
</body>
</html>