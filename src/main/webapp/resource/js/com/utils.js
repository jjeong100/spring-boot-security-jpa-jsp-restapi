function paging(blockNo, pageNo, sortCol, sortVal, viewId) {
	
	var url = "?pageNo=" + pageNo + "&blockNo=" + blockNo + 
				"&sortCol=" + sortCol + "&sortVal=" + sortVal + 
				"&view_id=" + viewId;

	var searchCol = $('#searchCol').val();
	var searchVal = $('#searchVal').val().trim();

	if (searchCol == '' || searchVal == '') {
		$('#searchCol').val('');
		$('#searchVal').val('');
	} else {
		url += "&searchCol=" + searchCol + "&searchVal=" + searchVal;
	}
	document.location.href = url;
}

function cDialog(par1, par2, callback) {
	if (callback != null) {
		$('<div id="tetets"></div>').appendTo('body').html(par2).dialog({
			modal : true,
			title : par1,
			zIndex : 10000,
			autoOpen : true,
			width : '316px',
			resizable : false,
			buttons : {
				확인 : function() {
					$(this).dialog("close");
					callback();
				}
			},
			close : function(event, ui) {
				$(this).remove();
			}
		});
	} else {
		$('<div id="tetets"></div>').appendTo('body').html(par2).dialog({
			modal : true,
			title : par1,
			zIndex : 10000,
			autoOpen : true,
			width : '316px',
			resizable : false,
			buttons : {
				확인 : function() {
					$(this).dialog("close");
				}
			},
			close : function(event, ui) {
				$(this).remove();
			}
		});
	}
}

function setFormsData(obj, form) {
	let keys = [];
	for (attr in obj) {
		keys.push(attr);
	}
	
	for (key in keys) {
		if (form[keys[key] + 'View']) {
			form[keys[key] + 'View'].value = obj[keys[key]];	
		}
	}
}


































