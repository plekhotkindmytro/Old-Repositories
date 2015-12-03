<!--

    Copyright (c) 2014 Oracle and/or its affiliates. All rights reserved.

    You may not modify, use, reproduce, or distribute this software except in
    compliance with  the terms of the License at:
    http://java.net/projects/javaeetutorial/pages/BerkeleyLicense

-->
<!DOCTYPE html>
<html lang="en">
<head>
<title>High load Killer</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="resources/bootstrap/css/bootstrap.css" rel="stylesheet">
<link href="resources/outside/css/reset.css" rel="stylesheet">
<link href="resources/outside/css/core.css" rel="stylesheet">
<link href="resources/outside/css/style.css" rel="stylesheet">
</head>
<body>
	<div class="top_block">
		<div class="container">
			<div class="row top50">
				<div class="span6">
					<h1>High load killer</h1>
					<div class="main_slogan white">
						Test your server for high loads<span class="bold ml20">NOW</span>
					</div>
				</div>
				<div class="span6 right_side white">

					<h2>Upload DB structure (*sql)</h2>
					<form id="upload-form" method="POST" action="data"
						enctype="multipart/form-data">

						<div class="form-group">
							<input type="file" name="file" id="file" class="form-control" />

						</div>
						<button class="btn btn-large btn-success" type="submit"
							name="upload" id="upload">Generate test data</button>
					</form>
					<p id="upload-result"></p>
					<hr>
					<h2>Enter SQL-query</h2>
					<form id="sql-form" method="POST" action="measure">
						<div class="form-group">
							<textarea name="sql" id="sql" class="form-control"></textarea>
						</div>
						<br>
						<h2>Enter number of iterations</h2>
						<div class="form-group">
							<input type="number" name="iterations" id="iterations"
								class="form-control">
						</div>
						<button class="btn btn-large" type="submit" name="measure"
							id="measure">Measure</button>
					</form>
					<p id="measure-result"></p>
				</div>
				<div class="clearfix"></div>
				<br /> <br /> <br /> <br />
			</div>
		</div>
	</div>
	<div class="info_block">
		<div class="well result_btn">
			<button class="btn btn-large btn-block btn btn-success" type="button">See
				result</button>
		</div>
		<div class="container">
			<div class="main_slogan">Results:</div>
			<div id="result_div" class="result_div"></div>
		</div>
	</div>

	<script
		src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
	<script src="resources/inside/js/jquery.form.js"></script>
	<script src="resources/bootstrap/js/bootstrap.min.js"></script>

	<script type="text/javascript">
		$('#upload-form').submit(function(e) {
			e.preventDefault();

			var data = new FormData(this);
			$.ajax({
				url : 'data',
				data : data,
				cache : false,
				contentType : false,
				processData : false,
				type : 'POST',
				success : function(data) {
					$('#upload-result').html(data);
				}
			});
		});

		$('#sql-form').submit(function(e) {
			e.preventDefault();
			var sql = $('#sql').val();
			var iterations = $('#iterations').val();

			$.ajax({
				url : 'measure',
				data : {
					iterations : iterations,
					sql : sql
				},
				type : 'POST',
				success : function(data) {
					$('#measure-result').html(data);
				}
			});
		});
	</script>
</body>
</html>
