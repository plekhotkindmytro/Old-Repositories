<?php
/**
 * Created by PhpStorm.
 * User: Денис
 * Date: 08.11.14
 * Time: 23:47
 */

abstract class testing_config {
	public static $gen_fish = array(
		'startPostsEach'        =>  5,
		'endPostsEach'          =>  15,
		'startCategoriesEach'   =>  1,
		'endCategoriesEach'     =>  5,
		'startCommentsEach'     =>  1,
		'endCommentsEach'       =>  4,
		'startLikesEach'        =>  10,
		'endLikesEach'          =>  20,

                'startFriendsEach'=>2,
                'endFriendsEach'=>5,
            
		'partCount'             =>  5, //Users by step
		'finalUsersCount'       =>  1,
		'categoriesCount'       =>  20
	);

	public static $select = array(
	//max select attacks
		'current_user_id'       =>  1,
				// user id
		'count'                 =>  6
				// query limits count
		//'times'                 =>  100
				// request times
	);
        public static function queries_array($index=null) {
            $arr =array(
                    function() {
                         $users_q = \hlkiller_core::db()->query("SELECT `users_id` FROM `users` ORDER BY RAND() LIMIT 1");
                         $users = $users_q->fetch_array(MYSQLI_ASSOC);
                         $posts_q = \hlkiller_core::db()->query("SELECT `posts_id` FROM `posts` ORDER BY RAND() LIMIT 1");
                         $posts = $posts_q->fetch_array(MYSQLI_ASSOC);
                         
                         
                         $likes_q = \hlkiller_core::db()->query("SELECT COUNT(*) FROM `users_posts_like` WHERE `users_id`='$users[users_id]' AND `posts_id`='$posts[posts_id]'");
                         $likes = $likes_q->fetch_array(MYSQLI_NUM);
                         if($likes[0]) {
                             $result = \hlkiller_core::db()->query("DELETE FROM `users_posts_like` WHERE `users_id`='$users[users_id]' AND `posts_id`='$posts[posts_id]'");
                         } else {
                             $result = \hlkiller_core::db()->query("INSERT INTO `users_posts_like` (`users_id`,`posts_id`,`rel_users_posts_like_time`) VALUES ('$users[users_id]','$posts[posts_id]',UNIX_TIMESTAMP())");
                         }
                         return $result;
                         
                    },
                    function() {
                        $users_q = \hlkiller_core::db()->query("SELECT `users_id` FROM `users` ORDER BY RAND() LIMIT 1");
                        $users = $users_q->fetch_array(MYSQLI_ASSOC);
                        $users2_q = \hlkiller_core::db()->query("SELECT `users_id` FROM `users` ORDER BY RAND() LIMIT 1");
                        $users2 = $users2_q->fetch_array(MYSQLI_ASSOC);
                        
                        $likes_q = \hlkiller_core::db()->query("SELECT `following_id` FROM `rel_users_following` WHERE `users_follower_id`='$users[users_id]' AND `users_supplier_id`='$users2[users_id]'");
                        $likes = $likes_q->fetch_array(MYSQLI_NUM);
                        if($likes[0]) {
                            $result = \hlkiller_core::db()->query("DELETE FROM `rel_users_following` WHERE `following_id`='$likes[0]'");
                        } else {
                            $result = \hlkiller_core::db()->query("INSERT INTO `rel_users_following` (`users_follower_id`,`users_supplier_id`,`following_time`) VALUES ('$users[users_id]','$users2[users_id]',UNIX_TIMESTAMP())");
                        }
                        return $result;
                    },
                    function() {
                        $users_q = \hlkiller_core::db()->query("SELECT `users_id` FROM `users` ORDER BY RAND() LIMIT 1");
                        $users = $users_q->fetch_array(MYSQLI_ASSOC);
                        $posts_q = \hlkiller_core::db()->query("SELECT `posts_id` FROM `posts` ORDER BY RAND() LIMIT 1");
                        $posts = $posts_q->fetch_array(MYSQLI_ASSOC);
                        
                        $text = \annex::gen_rnd_text();
                        $result = \hlkiller_core::db()->query("INSERT INTO `rel_users_posts_comments` (`users_id`,`posts_id`,`rel_users_posts_comments_time`,`comments_text`) VALUES ('$users[users_id]','$posts[posts_id]',UNIX_TIMESTAMP(),'$text')");
                        return $result;
                    },
                    function() {
                        $posts_q = \hlkiller_core::db()->query("SELECT `posts_id` FROM `posts` ORDER BY RAND() LIMIT 1");
                        $posts = $posts_q->fetch_array(MYSQLI_ASSOC);
                        $categories_q = \hlkiller_core::db()->query("SELECT `categories_id` FROM `categories` ORDER BY RAND() LIMIT 1");
                        $categories = $categories_q->fetch_array(MYSQLI_ASSOC);
                        
                        $pc_q = \hlkiller_core::db()->query("SELECT `posts_categories_id` FROM `rel_posts_categories` WHERE `posts_id`='$posts[posts_id]' AND `categories_id`='$categories[categories_id]'");
                        if($pc_q->num_rows) {
                            $result = \hlkiller_core::db()->query("DELETE FROM `rel_posts_categories` WHERE `posts_id`='$posts[posts_id]' AND `categories_id`='$categories[categories_id]'");
                        }  else {
                            $result = \hlkiller_core::db()->query("INSERT INTO `rel_posts_categories` (`posts_id`,`categories_id`) VALUES ('$posts[posts_id]','$categories[categories_id]')");
                        }
                        return $result;
                        
                    },
                    function() {
                        $name = \annex::gen_rnd_str(20);
                        $pass = \annex::gen_rnd_str(25);
                        $time = time();
                        $result = \hlkiller_core::db()->query("INSERT INTO `users` (`users_username`,`users_password`,`users_created_on`) VALUES ('$name','$pass','$time')");
                        return $result;
                    }
                ); 
        
            if(!is_null($index)) {
                return $arr[$index]();
            } else {
                return count($arr);
            }
            
        }
        
} 
