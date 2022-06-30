$(document).ready(
	function() {
		var multipleCancelButton = new Choices(
			'#choices-multiple-remove-button', {
			removeItemButton: true,
			maxItemCount: 5,
			searchResultLimit: 5,
			renderChoiceLimit: 10
		});

		$('#fileUpload').change(function() {
			showImgThumbnail(this);
		});

		var valImg = $('#post-image').val();
		if (valImg != '') {
			$('#post_img').attr('class', 'fix-image');
		}
	});

function showImgThumbnail(fileInput) {
	file = fileInput.files[0];
	reader = new FileReader();
	reader.onload = function(e) {
		$('#post_img').attr('src', e.target.result);
		$('#post_img').attr('class', 'fix-image');
	};
	reader.readAsDataURL(file);
}
function showImage() {
	if (this.files && this.files[0]) {
		var obj = new FileReader();
		obj.onload = function(data) {
			document.getElementById("imageData").value = data.target.result;
		}
		obj.readAsDataURL(this.files[0]);
	}
}