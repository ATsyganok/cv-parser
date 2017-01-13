$("#my-alert").bind("closeed",function(){

});
$(document).ready(function() {

	$(".file-dropzone").on('dragover', handleDragEnter);
	$(".file-dropzone").on('dragleave', handleDragLeave);
	$(".file-dropzone").on('drop', handleDragLeave);

	function handleDragEnter(e) {

		this.classList.add('drag-over');
	}

	function handleDragLeave(e) {

		this.classList.remove('drag-over');
	}

	$("#myLink").click(function(e){

		e.preventDefault();
		$.ajax({

			url:$(this).attr("href"),
		success: function(){
			alert("Value Added");  // or any other indication if you want to show
		}

		});

	});

	/*$("#parse-button").click(function(e){

		e.preventDefault();
		$.ajax({
			url:"/parse",
			type:"POST",
			success: function(result,status,xhr){
				window.locationre=result.url;
				alert("Your sessID is "+xhr.getResponseHeader("HeadSessionID"));
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
			message : responseContent
		});
	}*/

	// "dropzoneForm" is the camel-case version of the form id "dropzone-form"
	Dropzone.options.dropzoneForm = {

		url : "uploaded",
		autoProcessQueue : false,
		uploadMultiple : true,
		maxFilesize : 256, // MB
		parallelUploads : 100,
		maxFiles : 100,
		addRemoveLinks : true,
		previewsContainer : ".dropzone-previews",

		// The setting up of the dropzone
		init : function() {

			var myDropzone = this;

			// first set autoProcessQueue = false
			$('#upload-button123').on("click", function(e) {

				myDropzone.processQueue();
			});

			// customizing the default progress bar
			this.on("uploadprogress", function(file, progress) {

				progress = parseFloat(progress).toFixed(0);

				var progressBar = file.previewElement.getElementsByClassName("dz-upload")[0];
				progressBar.innerHTML = progress + "%";
			});

			// displaying the uploaded files information in a Bootstrap dialog
			this.on("successmultiple", function(files, serverResponse) {
				showInformationDialog(files, serverResponse);
			});
		}
	}

	function showInformationDialog(files, objectArray) {

		var responseContent = "";

		for (var i = 0; i < objectArray.length; i++) {

			var infoObject = objectArray[i];

			for ( var infoKey in infoObject) {
				if (infoObject.hasOwnProperty(infoKey)) {
					responseContent = responseContent + " " + infoKey + " -> " + infoObject[infoKey] + "<br>";
				}
			}
			responseContent = responseContent + "<hr>";
		}

		// from the library bootstrap-dialog.min.js
		BootstrapDialog.show({
			title : '<b>Server Response</b>',
			message : responseContent
		});
	}

});