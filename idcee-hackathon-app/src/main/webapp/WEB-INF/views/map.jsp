
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico">

<title>Cover Template for Bootstrap</title>

<!-- Bootstrap core CSS -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">

<!-- Custom styles for this template -->
<link href="resources/css/cover.css" rel="stylesheet">

<!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]-->
<script src="resources/assets/js/ie-emulation-modes-warning.js"></script>

<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<script type="text/javascript"
	src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1','packages':['geochart']}]}"></script>
<script type="text/javascript">
	google.setOnLoadCallback(drawRegionsMap);

	function drawRegionsMap() {

		var data = google.visualization.arrayToDataTable([
				[ 'Region', 'Tests' ], [ 'UA-07', 0 ], [ 'UA-56', 2 ],
				[ 'UA-18', 3 ], [ 'UA-32', 4 ], [ 'UA-30', 5 ], [ 'UA-74', 6 ],
				[ 'UA-59', 7 ], [ 'UA-53', 8 ], [ 'UA-63', 9 ],
				[ 'UA-12', 10 ], [ 'UA-14', 11 ], [ 'UA-09', 12 ],
				[ 'UA-23', 13 ], [ 'UA-48', 14 ], [ 'UA-51', 15 ],
				[ 'UA-77', 16 ], [ 'UA-26', 17 ], [ 'UA-46', 18 ],
				[ 'UA-21', 19 ], [ 'UA-65', 20 ], [ 'UA-43 ', 21 ],
				[ 'UA-61 ', 22 ], [ 'UA-68 ', 23 ], [ 'UA-05 ', 24 ],
				[ 'UA-71 ', 25 ], [ 'UA-35 ', 26 ], ]);

		var options = {
			region : 'UA',
			displayMode : 'regions',
			resolution : 'provinces',
			datalessRegionColor : '#ffffff',
			backgroundColor : {
				stroke : '#666',
				strokeWidth : 1
			},

			colorAxis : {
				colors : [ '#ffffff', '#4374e0' ]
			}
		};

		var chart = new google.visualization.GeoChart(document
				.getElementById('regions_div'));
		google.visualization.events
				.addListener(
						chart,
						'regionClick',
						function(e) {
							document.getElementsByClassName("label-default")[0].style.display = "none";
							document.getElementsByClassName("label-success")[0].style.display = "none";
						});
		chart.draw(data, options);
	}
</script>
</head>

<body>

	<div class="site-wrapper">

		<div class="site-wrapper-inner">

			<div class="cover-container">

				<div class="masthead clearfix">
					<div class="inner">
						<h3 class="masthead-brand">Cover</h3>
						<ul class="nav masthead-nav">
							<li class="active"><a href="#">Home</a></li>
							<li><a href="#">Features</a></li>
							<li><a href="#">Contact</a></li>
						</ul>
					</div>
				</div>

				<div class="inner cover">
					<div id="regions_div" style="width: 900px; height: 500px;"></div>
					<span class="label label-default">Default</span> <span
						class="label label-primary"><span
						class="glyphicon glyphicon-tag"></span> Primary</span> <span
						class="label label-success"><span
						class="glyphicon glyphicon-tags"></span>Success</span> <span
						class="label label-info">Info</span> <span
						class="label label-warning">Warning</span> <span
						class="label label-danger">Danger</span>
				</div>

				<div class="mastfoot">
					<div class="inner">
						<p>
							Cover template for <a href="http://getbootstrap.com">Bootstrap</a>,
							by <a href="https://twitter.com/mdo">@mdo</a>.
						</p>
					</div>
				</div>

			</div>

		</div>

	</div>

	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<script src="resources/js/bootstrap.min.js"></script>
	<script src="resources/assets/js/docs.min.js"></script>
	<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
	<script src="resources/assets/js/ie10-viewport-bug-workaround.js"></script>
</body>
</html>
