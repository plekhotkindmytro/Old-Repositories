<?php
class machine_gun {

	/**
	 * return first query
	 *
	 * @return string
	 */
	public function make_select_attack () {
		$current_user_id = (int) \testing_config::$select ['current_user_id'];
		$count           = (int) \testing_config::$select ['count'];

		$fields = '
			`posts`.`posts_id`,
			`posts_created_time`,
			`posts_title`,
			`posts_html`,

			`users_username`,
			`users_password`,

			`cats_names_string`,
			`cats_id_string`,

			`last_comments_users_id`,
			`last_comments_username`,

			`likes_count`
		';

		$JOINs = "

			LEFT JOIN `users` ON `users`.`users_id`=`posts`.`users_id`
			LEFT JOIN (
					SELECT
						`rel_posts_categories`.`posts_id`,
						GROUP_CONCAT(`categories_name` ORDER BY `categories_name` ASC SEPARATOR '[;]') as `cats_names_string`,
						GROUP_CONCAT(`rel_posts_categories`.`categories_id`   ORDER BY `categories_name` ASC SEPARATOR '[;]') as `cats_id_string`
					FROM
						`categories`
					LEFT JOIN
						`rel_posts_categories` ON
						`categories`.`categories_id`=`rel_posts_categories`.`categories_id`
					GROUP BY
						`rel_posts_categories`.`posts_id`
				) `posts_cats_strings` ON `posts_cats_strings`.`posts_id`=`posts`.`posts_id`
			LEFT JOIN `rel_users_following` ON `rel_users_following`.`users_supplier_id`=`users`.`users_id`
			LEFT JOIN (
					SELECT
						`rel_users_posts_comments`.`posts_id`,
						`users`.`users_id` as `last_comments_users_id`,
						`users`.`users_username` as `last_comments_username`,
						MAX(`rel_users_posts_comments_id`),
						`comments_text`
					FROM
						`rel_users_posts_comments`
					LEFT JOIN
						`users` ON
						`users`.`users_id`=`rel_users_posts_comments`.`users_id`
					GROUP BY
						`rel_users_posts_comments`.`posts_id`
				) `last_comments` ON `last_comments`.`posts_id`=`posts`.`posts_id`
			LEFT JOIN (
					SELECT
						`users_posts_like`.`posts_id`,
						COUNT(`rel_users_posts_like_id`) as `likes_count`
					FROM
						`users_posts_like`
					GROUP BY
						`users_posts_like`.`posts_id`
				) `likes` ON `likes`.`posts_id`=`posts`.`posts_id`

		";
		$tablename = 'posts';
		$conditions = "

			`rel_users_following`.`users_follower_id`='$current_user_id'

		";

		$query_text = "

			SELECT
				$fields
			FROM
				`$tablename`
			$JOINs
			WHERE
				$conditions
			ORDER BY
				`posts`.`posts_created_time` DESC
			LIMIT
				0, $count
		";
		return $query_text;
	}

	/**
	 * return new query
	 *
	 * @return string
	 */
	public function make_select_attack2 () {
		$current_user_id = (int) \testing_config::$select ['current_user_id'];
		$count           = (int) \testing_config::$select ['count'];

		$fields = '
			`posts`.`posts_id`,
			`posts_created_time`,
			`posts_title`,
			`posts_html`,

			`users_username`,
			`users_password`
		';

		$JOINs = "

			LEFT JOIN `users` ON `users`.`users_id`=`posts`.`users_id`
			LEFT JOIN `rel_users_following` ON `rel_users_following`.`users_supplier_id`=`users`.`users_id`

		";
		$tablename = 'posts';
		$conditions = "

			`rel_users_following`.`users_follower_id`='$current_user_id'

		";

		$query_text = "

			SELECT
				$fields
			FROM
				`$tablename`
			$JOINs
			WHERE
				1
			ORDER BY
				`posts`.`posts_created_time` ASC
			LIMIT
				0, $count
		";
		return $query_text;
	}

	/**
	 * ajax-function to execute random query from array [insert, update, delete]
	 */
	public function make_random_query () {
            $count = \testing_config::queries_array ();
            $times = 1;
            $rands = array();
            if (isset($_GET ['times'])) $times = $_GET ['times'];

            $start_time = microtime (2);
            for ($i = 0; $i < $times; $i ++) {
                    $id = rand (0, $count - 1);
                    $rands[] = array($id, \testing_config::queries_array ($id));
            }
            $end_time = microtime (2);
            echo json_encode(array(
                'diff'=>$end_time - $start_time,
                'rand_queries'=>$rands
            ));
	}

	/**
	 * in developing ...
	 */
	public function high_load_emulation () {

	}

	/**
	 * not used
	 */
	public function get_statistics () {}


	/**
	 * run select query by generated text query
	 *
	 * @param $query_text
	 */
	public function run_select ($query_text) {
		//$times = (int) \testing_config::$select ['times'];
		$times = $_GET ['times'];
		$db = \hlkiller_core::db ();
		$start_time = microtime (2);
		for ($i = 0; $i < $times; $i++){
			try {
					$result = $db->query ($query_text);
					if ($db->errno) throw new \Exceptions\MySQLQuery ('Mysqli died. '.$db->errno.' : '.$db->error);
					$result_array = array ();
					while ($tmp_array = $result->fetch_assoc ())
						$result_array [] = $tmp_array;
			}
			catch (\Exceptions\MySQLQuery $e) {
				die ($e->getMessage());
			}
		}
		$end_time = microtime (2);
		echo $end_time - $start_time, '<br>';
		\annex::showArray($result_array);
	}
}