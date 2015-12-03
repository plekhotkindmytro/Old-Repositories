<?php
session_start();
define ('DIRSEP', DIRECTORY_SEPARATOR);
define ('EXT', '.php');
define ('PATH', dirname (__FILE__).DIRSEP);

// class autoload
function __autoload ($classname) {
	$filename = PATH.'hlkiller'.DIRSEP.$classname.EXT;
	include_once ($filename);
}

function get_primary_value($field,array $array) {
    return $array[array_rand($array)][$field];
}


// function for routing ajax queries
function ajax_route ($request) {
	switch ($request) {
        case 'generate_fish' : {
	        if (isset ($_GET ['users_by_step']))
		        \testing_config::$gen_fish ['partCount'] = $_GET ['users_by_step'];
            $db_generator = new \db_generator ();
            $db_generator->generate_fish();
        }
            break;
		case 'clear_db' : {
			$db_generator = new \db_generator ();
			$db_generator->clear_db();
		}
			break;
		case 'add_structure_dump' : {
			$db_generator = new \db_generator ();
			$db_generator->add_structure_dump($_GET ['filename']);
		}
			break;
		case 'make_select_attack' : {
			$machine_gun = new \machine_gun ();
			$func = 'make_select_attack';
			if ($_GET ['action'] == '2') $func .= '2';
			$machine_gun->run_select($machine_gun->$func ());
		}
			break;
		case 'make_random_query' : {
			$machine_gun = new \machine_gun ();
			$machine_gun->make_random_query();
		}
			break;
		case 'hight_load_emulation' : {
			$machine_gun = new \machine_gun ();
			$machine_gun->high_load_emulation();
		}
			break;
		case 'get_statistics' : {
			$machine_gun = new \machine_gun ();
			$machine_gun->get_statistics();
		}
			break;
	}
}

// db connect (mysqli)
\hlkiller_core::connect();

	// route ajax queries
	if (isset ($_GET ['do'])) {
		ajax_route ($_GET ['do']);
	} else
		// print index view
		echo \ui::get_index_view();

//db disconnect
\hlkiller_core::disconnect();