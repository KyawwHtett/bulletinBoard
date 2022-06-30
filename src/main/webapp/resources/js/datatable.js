$(document).ready(function() {
	var table = $('#user-table').DataTable({
		"paging": true,
		"pageLength": 5,
		"bLengthChange": false,
		"bFilter": true,
		"bInfo": false,
		"bAutoWidth": false,
		"dom": 'rtp',
		buttons: [{
			extend: 'excel',
			text: 'Download',
			className: 'btn-excel-export',
			exportOptions: {
				columns: [0, 1, 2, 3, 4,]
			}
		}, 'colvis'],
	});
	$('#userTableSearch').keyup(function() {
		table.search($(this).val()).draw();
	});
	var categoryTable = $('#category-table').DataTable({
		"paging": true,
		"pageLength": 5,
		"bLengthChange": false,
		"bAutoWidth": false,
		"dom": 'rtp'
	});
	$('#categoryTableSearch').keyup(function() {
		categoryTable.search($(this).val()).draw();
	});
});

$('.delete-btn').click(
	function() {
		$id = $(this).attr('data-modal-id').substring(2);
		$url = $('#modal-delete-btn').attr('href') + "";
		console.log($url);
		$modal_url = $url.substring(0, $url.lastIndexOf('/') + 1)
			.concat($id);
		$('#modal-delete-btn').attr('href', $modal_url);
	});