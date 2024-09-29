(function($) {
	//초기값 & 이벤트설정
	$(function(){
		/**
		**********************************************************************
								로딩 이미지 Start
		**********************************************************************
		**/
		$.fn.loading = function(opt) {
			
			// 로딩이미지가 이미 존재하는 경우
			if($(this).find("div.spinner").length > 0){ return false; }
			
			// http://fgnass.github.io/spin.js/
			var options = {
					 lines: 13, // The number of lines to draw
					  length: 16, // The length of each line
					  width: 7, // The line thickness
					  radius: 23, // The radius of the inner circle
					  corners: 1, // Corner roundness (0..1)
					  rotate: 44, // The rotation offset
					  direction: 1, // 1: clockwise, -1: counterclockwise
					  color: '#000', // #rgb or #rrggbb or array of colors
					  speed: 2, // Rounds per second
					  trail: 70, // Afterglow percentage
					  shadow: true, // Whether to render a shadow
					  hwaccel: false, // Whether to use hardware acceleration
					  className: 'spinner', // The CSS class to assign to the spinner
					  zIndex: 2e9, // The z-index (defaults to 2000000000)
					  top: '50%', // Top position relative to parent
					  left: '50%' // Left position relative to parent
			};
			
			if(opt){
				$.extend(options, opt);
			}
			
			// $(this).empty();
			
			this.each(function() {
				if (!Spinner) return;
				var target = document.createElement('div');
				var	spin = new Spinner(options).spin(this);
				$(this).append(target);
			});
			return false;
		};

		// 스핀 종료 (spin 자동종료 안될때 사용)
		$.fn.loadingClose = function(){
			$(this).find("div.spinner").remove();
		};
		/**
		 **********************************************************************
								로딩 이미지 End
		 **********************************************************************
		 **/
	
		// 공통 이벤트
		$(document).ready(function() {
			
			// 테이블 내용 새로 그릴때 말줄임 말풍선효과
			$(document).on('click', 'tbody', function (e) {
				console.log("span title setting");
				// 동적 생성 테이블용
				setTimeout(function() {
				    $("tbody > tr > td[class='text-ellipsis'] > span").each(function() {
						if( !this.hasAttribute("title") ) {
							$(this).attr("title", this.textContent);
						}
				    });
				}, 1000);
			});
			
			// 테이블 내용 새로 그릴때 말줄임 말풍선효과
			$("tbody > tr > td[class='text-ellipsis'] > span").each(function() {
				if( !this.hasAttribute("title") ) {
					$(this).attr("title", this.textContent);
				}
		    });

		});
		
	});
})(jQuery);