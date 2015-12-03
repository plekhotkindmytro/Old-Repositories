<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bootstrap 101 Template</title>

<!-- Bootstrap -->
<link href="resources/css/bootstrap.min.css" rel="stylesheet">

<!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
<script type="text/javascript"
	src="https://www.google.com/jsapi?autoload={'modules':[{'name':'visualization','version':'1','packages':['geochart']}]}"></script>
<script type="text/javascript">
	google.setOnLoadCallback(drawRegionsMap);
	var data = google.visualization.arrayToDataTable([ [ 'Region', 'Tests' ],
			[ 'UA-07', 0 ], [ 'UA-56', 1 ], [ 'UA-18', 2 ], [ 'UA-32', 3 ],
			[ 'UA-30', 4 ], [ 'UA-74', 5 ], [ 'UA-59', 6 ], [ 'UA-53', 7 ],
			[ 'UA-63', 8 ], [ 'UA-12', 9 ], [ 'UA-14', 10 ], [ 'UA-09', 11 ],
			[ 'UA-23', 12 ], [ 'UA-48', 13 ], [ 'UA-51', 14 ], [ 'UA-77', 15 ],
			[ 'UA-26', 16 ], [ 'UA-46', 17 ], [ 'UA-21', 18 ], [ 'UA-65', 19 ],
			[ 'UA-43 ', 20 ], [ 'UA-61 ', 21 ], [ 'UA-68 ', 22 ],
			[ 'UA-05 ', 23 ], [ 'UA-71 ', 24 ], [ 'UA-35 ', 25 ], ]);
	var options = {
		region : 'UA',
		displayMode : 'regions',
		resolution : 'provinces',
		datalessRegionColor : '#ffffff',
		legend : 'none',
		keepAspectRatio : true,
		width : '100%',
		colorAxis : {
			minValue : 0,
			maxValue : 100,
			colors : [ '#ffffff', '#4374e0' ]
		},
		enableRegionInteractivity : true,
		backgroundColor : {
			fill : "#fff",

		}
	};

	function drawRegionsMap() {

		chart = new google.visualization.GeoChart(document
				.getElementById('regions_div'));
		google.visualization.events.addListener(chart, 'regionClick', function(
				e) {

			$(".label").toggle("slow", function() {

			});

		});

		google.visualization.events.addListener(chart, 'select', function(e) {
			var selectedItem = chart.getSelection()[0];
			var newData = data;
			if (selectedItem) {
				var region = data.getValue(selectedItem.row, 0);

				data.setValue(selectedItem.row, 1, 70);
				chart.clearChart();
				chart.draw(data, options);
			}

		});
		chart.draw(data, options);
	}
</script>
</head>

<body role="document">

	<!-- Fixed navbar -->
	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target=".navbar-collapse">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">Bootstrap theme</a>
			</div>
			<div class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Home</a></li>
					<li><a href="#about">About</a></li>
					<li><a href="#contact">Contact</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown">Dropdown <span class="caret"></span></a>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">Action</a></li>
							<li><a href="#">Another action</a></li>
							<li><a href="#">Something else here</a></li>
							<li class="divider"></li>
							<li class="dropdown-header">Nav header</li>
							<li><a href="#">Separated link</a></li>
							<li><a href="#">One more separated link</a></li>
						</ul></li>
				</ul>
			</div>
			<!--/.nav-collapse -->
		</div>
	</div>

	<div class="container theme-showcase" role="main">

		<!-- Main jumbotron for a primary marketing message or call to action -->
		<div class="jumbotron">
			<h1>Hello, world!</h1>
			<p>This is a template for a simple marketing or informational
				website. It includes a large callout called a jumbotron and three
				supporting pieces of content. Use it as a starting point to create
				something more unique.</p>
			<p>
				<a href="#" class="btn btn-primary btn-lg" role="button">Learn
					more &raquo;</a>
			</p>
		</div>


		<div class="row">
			<div class="col-md-9">
				<div class="page-header">
					<h2>Location:</h2>
				</div>
				<div id="regions_div" style="width: 800px; height: 500px;"></div>
			</div>
			<div style="height: 500px;" class="col-md-3">
				<div class="page-header">
					<h2>Topic:</h2>
				</div>
				<h3>
					<p id="war">
						<span class="label label-danger"><span
							class="glyphicon glyphicon-screenshot"></span>&nbsp;War</span>
					</p>
					<p id="forest">
						<span class="label label-success"><span
							class="glyphicon glyphicon-tree-conifer"></span>&nbsp;Forest</span>
					</p>

					<p id="mountains">
						<span class="label label-default"><span
							class="glyphicon glyphicon-picture"></span>&nbsp;Mountains</span>
					</p>

					<p id="earthquake">
						<span class="label label-warning"><span
							class="glyphicon glyphicon-record"></span>&nbsp;Earthquake</span>
					</p>

					<p id="fire">
						<span class="label label-danger"><span
							class="glyphicon glyphicon-fire"></span>&nbsp;Fire</span>
					</p>


					<p id="water">
						<span class="label label-primary"><span
							class="glyphicon glyphicon-tint"></span>&nbsp;Water</span>
					</p>

					<p id="flash">
						<span class="label label-info"><span
							class="glyphicon glyphicon-flash"></span>&nbsp;Flash</span>
					</p>

				</h3>



			</div>
		</div>


		<div class="page-header">
			<h1>Buttons</h1>
		</div>
		<p>
			<button type="button" class="btn btn-lg btn-default">Default</button>
			<button type="button" class="btn btn-lg btn-primary">Primary</button>
			<button type="button" class="btn btn-lg btn-success">Success</button>
			<button type="button" class="btn btn-lg btn-info">Info</button>
			<button type="button" class="btn btn-lg btn-warning">Warning</button>
			<button type="button" class="btn btn-lg btn-danger">Danger</button>
			<button type="button" class="btn btn-lg btn-link">Link</button>
		</p>
		<p>
			<button type="button" class="btn btn-default">Default</button>
			<button type="button" class="btn btn-primary">Primary</button>
			<button type="button" class="btn btn-success">Success</button>
			<button type="button" class="btn btn-info">Info</button>
			<button type="button" class="btn btn-warning">Warning</button>
			<button type="button" class="btn btn-danger">Danger</button>
			<button type="button" class="btn btn-link">Link</button>
		</p>
		<p>
			<button type="button" class="btn btn-sm btn-default">Default</button>
			<button type="button" class="btn btn-sm btn-primary">Primary</button>
			<button type="button" class="btn btn-sm btn-success">Success</button>
			<button type="button" class="btn btn-sm btn-info">Info</button>
			<button type="button" class="btn btn-sm btn-warning">Warning</button>
			<button type="button" class="btn btn-sm btn-danger">Danger</button>
			<button type="button" class="btn btn-sm btn-link">Link</button>
		</p>
		<p>
			<button type="button" class="btn btn-xs btn-default">Default</button>
			<button type="button" class="btn btn-xs btn-primary">Primary</button>
			<button type="button" class="btn btn-xs btn-success">Success</button>
			<button type="button" class="btn btn-xs btn-info">Info</button>
			<button type="button" class="btn btn-xs btn-warning">Warning</button>
			<button type="button" class="btn btn-xs btn-danger">Danger</button>
			<button type="button" class="btn btn-xs btn-link">Link</button>
		</p>



		<div class="page-header">
			<h1>Tables</h1>
		</div>
		<div class="row">
			<div class="col-md-6">
				<table class="table">
					<thead>
						<tr>
							<th>#</th>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Username</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>Mark</td>
							<td>Otto</td>
							<td>@mdo</td>
						</tr>
						<tr>
							<td>2</td>
							<td>Jacob</td>
							<td>Thornton</td>
							<td>@fat</td>
						</tr>
						<tr>
							<td>3</td>
							<td>Larry</td>
							<td>the Bird</td>
							<td>@twitter</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-md-6">
				<table class="table table-striped">
					<thead>
						<tr>
							<th>#</th>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Username</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>Mark</td>
							<td>Otto</td>
							<td>@mdo</td>
						</tr>
						<tr>
							<td>2</td>
							<td>Jacob</td>
							<td>Thornton</td>
							<td>@fat</td>
						</tr>
						<tr>
							<td>3</td>
							<td>Larry</td>
							<td>the Bird</td>
							<td>@twitter</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>

		<div class="row">
			<div class="col-md-6">
				<table class="table table-bordered">
					<thead>
						<tr>
							<th>#</th>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Username</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td rowspan="2">1</td>
							<td>Mark</td>
							<td>Otto</td>
							<td>@mdo</td>
						</tr>
						<tr>
							<td>Mark</td>
							<td>Otto</td>
							<td>@TwBootstrap</td>
						</tr>
						<tr>
							<td>2</td>
							<td>Jacob</td>
							<td>Thornton</td>
							<td>@fat</td>
						</tr>
						<tr>
							<td>3</td>
							<td colspan="2">Larry the Bird</td>
							<td>@twitter</td>
						</tr>
					</tbody>
				</table>
			</div>
			<div class="col-md-6">
				<table class="table table-condensed">
					<thead>
						<tr>
							<th>#</th>
							<th>First Name</th>
							<th>Last Name</th>
							<th>Username</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>1</td>
							<td>Mark</td>
							<td>Otto</td>
							<td>@mdo</td>
						</tr>
						<tr>
							<td>2</td>
							<td>Jacob</td>
							<td>Thornton</td>
							<td>@fat</td>
						</tr>
						<tr>
							<td>3</td>
							<td colspan="2">Larry the Bird</td>
							<td>@twitter</td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>



		<div class="page-header">
			<h1>Thumbnails</h1>
		</div>
		<img data-src="holder.js/200x200" class="img-thumbnail"
			alt="A generic square placeholder image with a white border around it, making it resemble a photograph taken with an old instant camera">



		<div class="page-header">
			<h1>Labels</h1>
		</div>
		<h1>
			<span class="label label-default">Default</span> <span
				class="label label-primary">Primary</span> <span
				class="label label-success">Success</span> <span
				class="label label-info">Info</span> <span
				class="label label-warning">Warning</span> <span
				class="label label-danger">Danger</span>
		</h1>
		<h2>
			<span class="label label-default">Default</span> <span
				class="label label-primary">Primary</span> <span
				class="label label-success">Success</span> <span
				class="label label-info">Info</span> <span
				class="label label-warning">Warning</span> <span
				class="label label-danger">Danger</span>
		</h2>
		<h3>
			<span class="label label-default">Default</span> <span
				class="label label-primary">Primary</span> <span
				class="label label-success">Success</span> <span
				class="label label-info">Info</span> <span
				class="label label-warning">Warning</span> <span
				class="label label-danger">Danger</span>
		</h3>
		<h4>
			<span class="label label-default">Default</span> <span
				class="label label-primary">Primary</span> <span
				class="label label-success">Success</span> <span
				class="label label-info">Info</span> <span
				class="label label-warning">Warning</span> <span
				class="label label-danger">Danger</span>
		</h4>
		<h5>
			<span class="label label-default">Default</span> <span
				class="label label-primary">Primary</span> <span
				class="label label-success">Success</span> <span
				class="label label-info">Info</span> <span
				class="label label-warning">Warning</span> <span
				class="label label-danger">Danger</span>
		</h5>
		<h6>
			<span class="label label-default">Default</span> <span
				class="label label-primary">Primary</span> <span
				class="label label-success">Success</span> <span
				class="label label-info">Info</span> <span
				class="label label-warning">Warning</span> <span
				class="label label-danger">Danger</span>
		</h6>
		<p>
			<span class="label label-default">Default</span> <span
				class="label label-primary">Primary</span> <span
				class="label label-success">Success</span> <span
				class="label label-info">Info</span> <span
				class="label label-warning">Warning</span> <span
				class="label label-danger">Danger</span>
		</p>



		<div class="page-header">
			<h1>Badges</h1>
		</div>
		<p>
			<a href="#">Inbox <span class="badge">42</span></a>
		</p>
		<ul class="nav nav-pills">
			<li class="active"><a href="#">Home <span class="badge">42</span></a></li>
			<li><a href="#">Profile</a></li>
			<li><a href="#">Messages <span class="badge">3</span></a></li>
		</ul>


		<div class="page-header">
			<h1>Dropdown menus</h1>
		</div>
		<div class="dropdown theme-dropdown clearfix">
			<a id="dropdownMenu1" href="#" role="button"
				class="sr-only dropdown-toggle" data-toggle="dropdown">Dropdown
				<span class="caret"></span>
			</a>
			<ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu1">
				<li class="active" role="presentation"><a role="menuitem"
					tabindex="-1" href="#">Action</a></li>
				<li role="presentation"><a role="menuitem" tabindex="-1"
					href="#">Another action</a></li>
				<li role="presentation"><a role="menuitem" tabindex="-1"
					href="#">Something else here</a></li>
				<li role="presentation" class="divider"></li>
				<li role="presentation"><a role="menuitem" tabindex="-1"
					href="#">Separated link</a></li>
			</ul>
		</div>



		<div class="page-header">
			<h1>Navs</h1>
		</div>
		<ul class="nav nav-tabs" role="tablist">
			<li class="active"><a href="#">Home</a></li>
			<li><a href="#">Profile</a></li>
			<li><a href="#">Messages</a></li>
		</ul>
		<ul class="nav nav-pills">
			<li class="active"><a href="#">Home</a></li>
			<li><a href="#">Profile</a></li>
			<li><a href="#">Messages</a></li>
		</ul>



		<div class="page-header">
			<h1>Navbars</h1>
		</div>

		<div class="navbar navbar-default">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">Project name</a>
				</div>
				<div class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						<li class="active"><a href="#">Home</a></li>
						<li><a href="#about">About</a></li>
						<li><a href="#contact">Contact</a></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown">Dropdown <span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#">Action</a></li>
								<li><a href="#">Another action</a></li>
								<li><a href="#">Something else here</a></li>
								<li class="divider"></li>
								<li class="dropdown-header">Nav header</li>
								<li><a href="#">Separated link</a></li>
								<li><a href="#">One more separated link</a></li>
							</ul></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>

		<div class="navbar navbar-inverse">
			<div class="container">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle collapsed"
						data-toggle="collapse" data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span> <span
							class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand" href="#">Project name</a>
				</div>
				<div class="navbar-collapse collapse">
					<ul class="nav navbar-nav">
						<li class="active"><a href="#">Home</a></li>
						<li><a href="#about">About</a></li>
						<li><a href="#contact">Contact</a></li>
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown">Dropdown <span class="caret"></span></a>
							<ul class="dropdown-menu" role="menu">
								<li><a href="#">Action</a></li>
								<li><a href="#">Another action</a></li>
								<li><a href="#">Something else here</a></li>
								<li class="divider"></li>
								<li class="dropdown-header">Nav header</li>
								<li><a href="#">Separated link</a></li>
								<li><a href="#">One more separated link</a></li>
							</ul></li>
					</ul>
				</div>
				<!--/.nav-collapse -->
			</div>
		</div>



		<div class="page-header">
			<h1>Alerts</h1>
		</div>
		<div class="alert alert-success" role="alert">
			<strong>Well done!</strong> You successfully read this important
			alert message.
		</div>
		<div class="alert alert-info" role="alert">
			<strong>Heads up!</strong> This alert needs your attention, but it's
			not super important.
		</div>
		<div class="alert alert-warning" role="alert">
			<strong>Warning!</strong> Best check yo self, you're not looking too
			good.
		</div>
		<div class="alert alert-danger" role="alert">
			<strong>Oh snap!</strong> Change a few things up and try submitting
			again.
		</div>



		<div class="page-header">
			<h1>Progress bars</h1>
		</div>
		<div class="progress">
			<div class="progress-bar" role="progressbar" aria-valuenow="60"
				aria-valuemin="0" aria-valuemax="100" style="width: 60%;">
				<span class="sr-only">60% Complete</span>
			</div>
		</div>
		<div class="progress">
			<div class="progress-bar progress-bar-success" role="progressbar"
				aria-valuenow="40" aria-valuemin="0" aria-valuemax="100"
				style="width: 40%">
				<span class="sr-only">40% Complete (success)</span>
			</div>
		</div>
		<div class="progress">
			<div class="progress-bar progress-bar-info" role="progressbar"
				aria-valuenow="20" aria-valuemin="0" aria-valuemax="100"
				style="width: 20%">
				<span class="sr-only">20% Complete</span>
			</div>
		</div>
		<div class="progress">
			<div class="progress-bar progress-bar-warning" role="progressbar"
				aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
				style="width: 60%">
				<span class="sr-only">60% Complete (warning)</span>
			</div>
		</div>
		<div class="progress">
			<div class="progress-bar progress-bar-danger" role="progressbar"
				aria-valuenow="80" aria-valuemin="0" aria-valuemax="100"
				style="width: 80%">
				<span class="sr-only">80% Complete (danger)</span>
			</div>
		</div>
		<div class="progress">
			<div class="progress-bar progress-bar-striped" role="progressbar"
				aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
				style="width: 60%">
				<span class="sr-only">100% Complete</span>
			</div>
		</div>
		<div class="progress">
			<div class="progress-bar progress-bar-success" style="width: 35%">
				<span class="sr-only">35% Complete (success)</span>
			</div>
			<div class="progress-bar progress-bar-warning" style="width: 20%">
				<span class="sr-only">20% Complete (warning)</span>
			</div>
			<div class="progress-bar progress-bar-danger" style="width: 10%">
				<span class='sr-only'>10% Complete (danger)</span>
			</div>
		</div>



		<div class="page-header">
			<h1>List groups</h1>
		</div>
		<div class="row">
			<div class="col-sm-4">
				<ul class="list-group">
					<li class="list-group-item">Cras justo odio</li>
					<li class="list-group-item">Dapibus ac facilisis in</li>
					<li class="list-group-item">Morbi leo risus</li>
					<li class="list-group-item">Porta ac consectetur ac</li>
					<li class="list-group-item">Vestibulum at eros</li>
				</ul>
			</div>
			<!-- /.col-sm-4 -->
			<div class="col-sm-4">
				<div class="list-group">
					<a href="#" class="list-group-item active"> Cras justo odio </a> <a
						href="#" class="list-group-item">Dapibus ac facilisis in</a> <a
						href="#" class="list-group-item">Morbi leo risus</a> <a href="#"
						class="list-group-item">Porta ac consectetur ac</a> <a href="#"
						class="list-group-item">Vestibulum at eros</a>
				</div>
			</div>
			<!-- /.col-sm-4 -->
			<div class="col-sm-4">
				<div class="list-group">
					<a href="#" class="list-group-item active">
						<h4 class="list-group-item-heading">List group item heading</h4>
						<p class="list-group-item-text">Donec id elit non mi porta
							gravida at eget metus. Maecenas sed diam eget risus varius
							blandit.</p>
					</a> <a href="#" class="list-group-item">
						<h4 class="list-group-item-heading">List group item heading</h4>
						<p class="list-group-item-text">Donec id elit non mi porta
							gravida at eget metus. Maecenas sed diam eget risus varius
							blandit.</p>
					</a> <a href="#" class="list-group-item">
						<h4 class="list-group-item-heading">List group item heading</h4>
						<p class="list-group-item-text">Donec id elit non mi porta
							gravida at eget metus. Maecenas sed diam eget risus varius
							blandit.</p>
					</a>
				</div>
			</div>
			<!-- /.col-sm-4 -->
		</div>



		<div class="page-header">
			<h1>Panels</h1>
		</div>
		<div class="row">
			<div class="col-sm-4">
				<div class="panel panel-default">
					<div class="panel-heading">
						<h3 class="panel-title">Panel title</h3>
					</div>
					<div class="panel-body">Panel content</div>
				</div>
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h3 class="panel-title">Panel title</h3>
					</div>
					<div class="panel-body">Panel content</div>
				</div>
			</div>
			<!-- /.col-sm-4 -->
			<div class="col-sm-4">
				<div class="panel panel-success">
					<div class="panel-heading">
						<h3 class="panel-title">Panel title</h3>
					</div>
					<div class="panel-body">Panel content</div>
				</div>
				<div class="panel panel-info">
					<div class="panel-heading">
						<h3 class="panel-title">Panel title</h3>
					</div>
					<div class="panel-body">Panel content</div>
				</div>
			</div>
			<!-- /.col-sm-4 -->
			<div class="col-sm-4">
				<div class="panel panel-warning">
					<div class="panel-heading">
						<h3 class="panel-title">Panel title</h3>
					</div>
					<div class="panel-body">Panel content</div>
				</div>
				<div class="panel panel-danger">
					<div class="panel-heading">
						<h3 class="panel-title">Panel title</h3>
					</div>
					<div class="panel-body">Panel content</div>
				</div>
			</div>
			<!-- /.col-sm-4 -->
		</div>



		<div class="page-header">
			<h1>Wells</h1>
		</div>
		<div class="well">
			<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit.
				Maecenas sed diam eget risus varius blandit sit amet non magna.
				Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent
				commodo cursus magna, vel scelerisque nisl consectetur et. Cras
				mattis consectetur purus sit amet fermentum. Duis mollis, est non
				commodo luctus, nisi erat porttitor ligula, eget lacinia odio sem
				nec elit. Aenean lacinia bibendum nulla sed consectetur.</p>
		</div>



		<div class="page-header">
			<h1>Carousel</h1>
		</div>
		<div id="carousel-example-generic" class="carousel slide"
			data-ride="carousel">
			<ol class="carousel-indicators">
				<li data-target="#carousel-example-generic" data-slide-to="0"
					class="active"></li>
				<li data-target="#carousel-example-generic" data-slide-to="1"></li>
				<li data-target="#carousel-example-generic" data-slide-to="2"></li>
			</ol>
			<div class="carousel-inner">
				<div class="item active">
					<img data-src="holder.js/1140x500/auto/#777:#555/text:First slide"
						alt="First slide">
				</div>
				<div class="item">
					<img data-src="holder.js/1140x500/auto/#666:#444/text:Second slide"
						alt="Second slide">
				</div>
				<div class="item">
					<img data-src="holder.js/1140x500/auto/#555:#333/text:Third slide"
						alt="Third slide">
				</div>
			</div>
			<a class="left carousel-control" href="#carousel-example-generic"
				role="button" data-slide="prev"> <span
				class="glyphicon glyphicon-chevron-left"></span>
			</a> <a class="right carousel-control" href="#carousel-example-generic"
				role="button" data-slide="next"> <span
				class="glyphicon glyphicon-chevron-right"></span>
			</a>
		</div>


	</div>
	<!-- /container -->


	<!-- Bootstrap core JavaScript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="resources/js/bootstrap.min.js"></script>
	<script type="text/javascript">
		function showSelectedRegionsMap(selectedIds, color) {
			var newData = jQuery.extend(true, {}, data);
			var newOptions = jQuery.extend(true, {}, options);
			newOptions.colorAxis.colors = [ "#fff", "#ccc", color];
			var idsCount = selectedIds.length;
			for (var i = 0; i < idsCount; i++) {
				newData.setValue(selectedIds[i], 1, 100);

			}
			//	chart.clearChart();
			chart.draw(newData, newOptions);
		}

		$("#forest").hover(function() {
			showSelectedRegionsMap([ 1, 5, 10, 15, 23, 24 ], '#00ff00')
		});

		$("#water").hover(function() {
			showSelectedRegionsMap([ 2, 3, 4, 11, 12, 20 ], '#0000ff')
		});

		$("#war").hover(function() {
			showSelectedRegionsMap([ 19, 20, 12,10,11 ], '#ff0000');
		});
	</script>

</body>
</html>