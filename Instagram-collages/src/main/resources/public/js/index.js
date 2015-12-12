var currentUrl = null,
	nextUrl = null;

var selectedImages = [];
function toggleArrayItem(a, v) {
	var i = a.indexOf(v);
	if (i === -1)
		a.push(v);
	else
		a.splice(i, 1);
}

function loadImages(newUrl) {
	$.ajax({
		type : 'POST',
		url : "/images",
		cache : false,
		contentType : "application/json; charset=utf-8",
		dataType : "json",
		data : JSON.stringify({"nextUrl":newUrl}),
		success : function(data) {
			var i, imagesLength, images = data.elements, img, prevUrl;
			$('#images').html("");
			for (imagesLength = images.length, i = 0; i < imagesLength; ++i) {
				img = $('<img />', {
					id : images[i].id,
					src : images[i].standardUrl,
					width: 200,
					height: 200
				});
				if($.inArray(img.attr("src"), selectedImages) != -1) {
					img.addClass("selected-image");
				}
				img.appendTo($('#images'));
			}
			
			if(data.nextUrl) {
				$("#next").show();
				nextUrl = data.nextUrl;
				$("#recent").hide();
			} else {
				$("#next").hide();
				nextUrl = null;
				$("#recent").show();
			}
			
		}
	});
}
$(document).ready(function() {

	$("#images").on("click", "img", function() {
		$(this).toggleClass("selected-image");
		toggleArrayItem(selectedImages, $(this).attr("src"));
	});

	loadImages(null);
	
	$("#recent").on("click", function(e) {
		e.preventDefault();
		loadImages(null);
	});
	
	$("#next").on("click", function(e) {
		e.preventDefault();
		loadImages(nextUrl);
	});

	$("#create").on("click", function() {
		$.ajax({
			type : 'POST',
			url : "/collage",
			cache : false,
			contentType : "application/json; charset=utf-8",
			dataType : "json",
			data : JSON.stringify({
				images : selectedImages,
				type: $("#collageType").val()
			}),
			success : function(data) {
				$('#collage').html('<h2>Result</h2><img src="' + data.image + '" />');
				alert("Collage created in the bottom of the page :)");
			}
		});
	});
});