<?php
class db_generator {
    public function init () {

    }

    /**
     * db dump file import
     *
     * @param $file_path
     */
    public function add_structure_dump ($file_path) {
        try {
            $handle = fopen ($file_path, 'r');
            if (!$handle) {
                throw new \Exceptions\File ("Could not open the file!");
            }
        }
        catch (\Exceptions\File $e) {
            die ("Error (File: ".$e->getFile().", line ".
                $e->getLine()."): ".$e->getMessage());
        }
        $sql = fread($handle, filesize($file_path));
        fclose ($handle);
        //echo $sql;
        try {
            $result = \hlkiller_core::db ()->multi_query($sql);
            if ($result === false) {
                throw new \Exceptions\MySQLQuery ('Mysqli died.');
            }
        }
        catch (\Exceptions\MySQLQuery $e) {
            die ($e->getMessage());
        }
        echo 'Tables loaded';
        //exit ();
    }

	/**
	 * generate random data for base
	 */
	public function generate_fish () {
	    $config = \testing_config::$gen_fish;
        $start = microtime(TRUE);
        
        $tables=\config::getModel();

        $startPostsEach = $config['startPostsEach'];
        $endPostsEach = $config['endPostsEach'];
        $startCategoriesEach = $config['startCategoriesEach'];
        $endCategoriesEach = $config['endCategoriesEach'];
        $startCommentsEach = $config['startCommentsEach'];
        $endCommentsEach = $config['endCommentsEach'];
        $startLikesEach = $config['startLikesEach'];
        $endLikesEach = $config['endLikesEach'];
        $startFriendsEach = $config['startFriendsEach'];
        $endFriendsEach = $config['endFriendsEach'];

        $partCount = $config['partCount'];
        
        $categoriesCount = $config['categoriesCount'];

        $user_insert_values_arr = array();
        $following_insert_values_arr = array();
        $posts_insert_values_arr = array();
        $rel_categories_posts_insert_arr = array();
        $add_comments_rel_count = array();
        $rel_comments_posts_insert_arr = array();
        $rel_like_posts_insert_arr = array();

        $categories_insert_arr = array();
        $startUsers = 1;
        
        if(isset($_SESSION['exist']) && $_SESSION['exist']=='1') {
            $post_id = $_SESSION['post_id'];
            $startUsers = $_SESSION['user_start'];
            $categories_insert_arr = json_decode($_SESSION['categories_arr'], true);
            //var_dump($categories_insert_arr);
            //return ;
        } else {
            $post_id = 1;
            
            for ($i=1; $i<=$categoriesCount; $i++) {
                $categories_insert_arr[] = \annex::set_fields($tables['categories'],$i);
            }
            $_SESSION['categories_arr'] = json_encode($categories_insert_arr);

            \hlkiller_core::sql_gen('insert',array(
                'table'=>'categories',
                'values'=>$categories_insert_arr
            ));
        }
        $finalUsersCount = $startUsers+$partCount*$config['finalUsersCount']-1;
        

        for ($user_part_first_id=$startUsers; $user_part_first_id<=$finalUsersCount; $user_part_first_id+=$partCount) {

            $user_insert_values_arr = array();
            $users_temp_ids = array();
            $following_insert_values_arr = array();

            $user_part_last = $user_part_first_id+$partCount;
            // Add Users
            for($user_id=$user_part_first_id; $user_id < $user_part_last; $user_id++) {
                $user_insert_values_arr[] = \annex::set_fields($tables['users'],$user_id);
                $users_temp_ids[] = $user_id;
            }


            \hlkiller_core::sql_gen('insert',array(
                'table'=>'users',
                'values'=>$user_insert_values_arr
            ));
            $new_tenp_ids = $users_temp_ids;
            for($user_id=$user_part_first_id; $user_id<$user_part_last; $user_id++) {
                $add_friend_count= rand($startFriendsEach, $endFriendsEach);
                for($friendsEach=$startFriendsEach; $friendsEach<=$add_friend_count; $friendsEach++) {
                    $friedSupplier = get_primary_value('users_id',$user_insert_values_arr);
                    if(in_array($friedSupplier, $new_tenp_ids)) {
                        $following_insert_values_arr[] = \annex::set_fields($tables['rel_users_following'],$user_id,$friedSupplier);
                        if(($key = array_search($friedSupplier, $new_tenp_ids)) !== false) {
                            unset($new_tenp_ids[$key]);
                        }
                    }
                    
                }
                $new_tenp_ids = $users_temp_ids;
            }

            \hlkiller_core::sql_gen('insert',array(
                'table'=>'rel_users_following',
                'values'=>$following_insert_values_arr
            ));

            $counter = 1;
            $posts_insert_values_arr = array();
            $rel_categories_posts_insert_arr = array();
            $add_comments_rel_count = array();
            $rel_comments_posts_insert_arr = array();
            $rel_like_posts_insert_arr = array();
            // Add user posts
            for($user_id=$user_part_first_id; $user_id<$user_part_last; $user_id++) {


                $add_post_count = rand($startPostsEach, $endPostsEach);

                for($i=$startPostsEach;$i<=$add_post_count;$i++) {
                    // Add Post
                    $posts_insert_values_arr[] = \annex::set_fields($tables['posts'],$post_id,get_primary_value('users_id',$user_insert_values_arr));


                    // Add Categories Relations
                    $add_categories_rel_count = rand($startCategoriesEach, $endCategoriesEach);
                    for($rel_count=$startCategoriesEach; $rel_count<=$endCategoriesEach; $rel_count++) {
                        $rel_categories_posts_insert_arr[] = \annex::set_fields($tables['rel_posts_categories'],$post_id,get_primary_value('categories_id',$categories_insert_arr));
                        //Array(post_id, rand_from_array(category_id))
                    }

                    // Add Post Comments
                    $add_comments_rel_count = rand($startCommentsEach, $endCommentsEach);
                    for($rel_count=$startCategoriesEach; $rel_count<=$endCategoriesEach; $rel_count++) {
                        $rel_comments_posts_insert_arr[] = \annex::set_fields($tables['rel_users_posts_comments'],get_primary_value('users_id',$user_insert_values_arr),$post_id);
                        //Array(post_id, user_insert_values_arr[rand_this()], comment_text)
                    }

                    // Add Post Likes
                    $add_like_rel_count = rand($startLikesEach, $endLikesEach);
                    for($rel_count=$startCategoriesEach; $rel_count<=$endCategoriesEach; $rel_count++) {
                        $rel_like_posts_insert_arr[] = \annex::set_fields($tables['users_posts_like'],get_primary_value('users_id',$user_insert_values_arr),$post_id);
                        //Array(post_id, user_insert_values_arr[rand_this()], like_time)
                    }
                    $post_id++;
                }

                if($counter%25==0) {
                    //var_dump(count($posts_insert_values_arr));
                    //var_dump(count($rel_categories_posts_insert_arr));
                    //var_dump(count($rel_comments_posts_insert_arr));
                    //var_dump(count($rel_like_posts_insert_arr));
                    //echo '<br><br>';
                    \hlkiller_core::sql_gen('insert',array(
                        'table'=>'posts',
                        'delayed'=>TRUE,// DELAYED
                        'values'=>$posts_insert_values_arr
                    ),true);

                    \hlkiller_core::sql_gen('insert',array(
                        'table'=>'rel_posts_categories',
                        'values'=>$rel_categories_posts_insert_arr
                    ),true);

                    \hlkiller_core::sql_gen('insert',array(
                        'table'=>'rel_users_posts_comments',
                        'values'=>$rel_comments_posts_insert_arr
                    ),true);

                    \hlkiller_core::sql_gen('insert',array(
                        'table'=>'users_posts_like',
                        'values'=>$rel_like_posts_insert_arr
                    ),true);
                    $posts_insert_values_arr = array();
                    $rel_categories_posts_insert_arr = array();
                    $add_comments_rel_count = array();
                    $rel_comments_posts_insert_arr = array();
                    $rel_like_posts_insert_arr = array();
                    $counter=0;
                }
                $counter++;
            }
            if(!empty($posts_insert_values_arr)) {
                \hlkiller_core::sql_gen('insert',array(
                    'table'=>'posts',
                    'delayed'=>TRUE,// DELAYED
                    'values'=>$posts_insert_values_arr
                ),true);
            }
             if(!empty($rel_categories_posts_insert_arr)) {
                \hlkiller_core::sql_gen('insert',array(
                    'table'=>'rel_posts_categories',
                    'values'=>$rel_categories_posts_insert_arr
                ),true);
             }
             if(!empty($rel_comments_posts_insert_arr)) {
                \hlkiller_core::sql_gen('insert',array(
                    'table'=>'rel_users_posts_comments',
                    'values'=>$rel_comments_posts_insert_arr
                ),true);
             }
             if(!empty($rel_like_posts_insert_arr)) {
                \hlkiller_core::sql_gen('insert',array(
                    'table'=>'users_posts_like',
                    'values'=>$rel_like_posts_insert_arr
                ),true);
             }

            $posts_insert_values_arr = array();
            $rel_categories_posts_insert_arr = array();
            $add_comments_rel_count = array();
            $rel_comments_posts_insert_arr = array();
            $rel_like_posts_insert_arr = array();
            $counter=0;
        }
        $_SESSION['post_id'] = $post_id;
        $_SESSION['user_start'] = $finalUsersCount+1;
        $_SESSION['exist'] = '1';
        
        $finish = microtime(TRUE);
        $totaltime = $finish - $start;
        echo json_encode(array(
            'script'=>$totaltime,
            'userID'=>$finalUsersCount+1,
            'post_id'=>$post_id
        ));
       
    }

	/**
	 * clear data in database
	 */
	public function clear_db () {
        
        $action = $_GET['action'];
        $_SESSION['exist'] = '0';
        foreach(\config::getTables() as $table) {
            try {
                $sql = "DROP TABLE IF EXISTS `".$table['TABLE_NAME']."`";
                $result = \hlkiller_core::db ()->query($sql);
                if ($result === false) {
                    echo $sql.';';
                    throw new \Exceptions\MySQLQuery ('Mysqli died.');
                }

            }
            catch (\Exceptions\MySQLQuery $e) {
                die ($e->getMessage());
            }
        }
        switch ($action) {
            case 1:
                $this->add_structure_dump(PATH.'test_dump1.sql');
                break;
            case 2:
                $this->add_structure_dump(PATH.'test_dump2.sql');
                break;
        }
        
    }
}