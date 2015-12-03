<?php $front_end_folder = 'hlkiller/view/includes';
		#/HighloadKIller/php/hlkiller/view/includes
		#/hlkiller/hlkiller/view/includes
?>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <title>HighLoad Killer UI</title>
    <meta name="viewport" content="width=1280">
 

    <link href="<?=$front_end_folder?>/bootstrap/css/bootstrap.css" rel="stylesheet">
	<link href="<?=$front_end_folder?>/outside/css/reset.css" rel="stylesheet">
	<link href="<?=$front_end_folder?>/outside/css/core.css" rel="stylesheet">
	<link href="<?=$front_end_folder?>/outside/css/style.css" rel="stylesheet">
	
	<link rel="shortcut icon" href="<?=$front_end_folder?>/favicon.png">

  
  </head>

	<body>
	
		<div class="wrapper">
				
			<div class="content">
				<?php echo $index;?>
			</div>
			

			<footer class="black_bg">
				<div class="container">
					
				</div>
			</footer>
		</div> 		

		
		
		
		<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
		<script src="<?=$front_end_folder?>/inside/js/jquery.form.js"></script>
		<script src="<?=$front_end_folder?>/bootstrap/js/bootstrap.min.js"></script>	
		
		<?php		
		echo $footer;
		?>
		
  </body>
</html>
