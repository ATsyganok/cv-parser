$().ready(function() {

	$("#myModal").on("show.bs.modal",function(e){
		//e.preventDefault();
		var id = $(e.relatedTarget).data("id");
		//var data = id.data();
		$.ajax({
			url:"/contact",
			type:"POST",
			dataType:"text", //{contID:$("#contact-button").val()}
			data:{contID:id},//{contID:$("#contact-button").val()},//"123" is work
			success: function(result,status,xhr) {
				$(".modal-title").html("Contact's of "+xhr.getResponseHeader("HeadSessionFullName"));
				$(".modal-body-phone").text(".              Phone: "+result).end();
				$(".modal-body-email").html(".              Email: "+xhr.getResponseHeader("HeadSessionEmail"));
				$(".modal-body-region").html(".              Country/City: "+xhr.getResponseHeader("HeadSessionRegion"));
				//$(".modal .modal-title").html("HELLO!!!"); //rewrite header?
				//$(".modal").modal("show"); //twice showing
				//alert("res is = "+xhr.result);

				//window.locationre=result.url;
				//alert("Your data is (for CONTACT...) "+xhr.getResponseHeader("HeadSessionID"));
				//alert("Your data is (for CONTACT...) "+xhr.data.contID);
				//showInformationDialogContact(result); //can add ,status,xhr
				//alert("I finished PARSED! You can see that!!");  // or any other indication if you want to show
			}

	});
	});
});
