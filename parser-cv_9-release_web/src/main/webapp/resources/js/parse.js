$().ready(function() {

	$("#parse-button").click(function(e){

		e.preventDefault();
		$.ajax({
			url:"/parse",
			type:"POST",
			success: function(result,status,xhr){
				window.locationre=result.url;
				//alert("Your sessID is "+xhr.getResponseHeader("HeadSessionID"));
				showInformationDialogParsed(result); //can add ,status,xhr
				//alert("I finished PARSED! You can see that!!");  // or any other indication if you want to show
			}
		});
	});

	function showInformationDialogParsed(serverResponse) {

		var responseContent = serverResponse.toString();
		//var sessID = xhr.getResponseHeader("HeadSessionID");

		// from the library bootstrap-dialog.min.js
		BootstrapDialog.show({
			title : '<b>Server Response on PARSE</b>',
			message : 'These files: \n'+responseContent
		});
	}
});