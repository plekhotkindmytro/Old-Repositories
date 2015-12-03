
DROP TABLE IF EXISTS `users`;
CREATE TABLE IF NOT EXISTS `users` (
  `users_id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `users_username` varchar(100) NOT NULL,
  `users_password` varchar(80) NOT NULL,
  `users_email` varchar(100) NOT NULL,
  `users_created_on` int(11) unsigned NOT NULL,
  `users_last_login` int(11) unsigned DEFAULT NULL,
  `users_active` tinyint(1) unsigned DEFAULT NULL,
  `users_phone` varchar(20) DEFAULT NULL,
  `users_social_vk` varchar(128) NOT NULL,
  `users_social_fb` varchar(128) NOT NULL,
  `users_sex` int(1) NOT NULL,
  `users_birth_date` varchar(32) NOT NULL,
  `users_adv_info` varchar(1024) NOT NULL,
  `users_img` varchar(256) NOT NULL,
  `users_verify_phone` int(1) NOT NULL,
  `users_premium` int(1) NOT NULL,
  PRIMARY KEY (`users_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `categories`;
CREATE TABLE IF NOT EXISTS `categories` (
  `categories_id` int(9) unsigned NOT NULL AUTO_INCREMENT,
  `categories_pid` int(9) unsigned NOT NULL,
  `categories_name` varchar(256) NOT NULL,
  `categories_desc` text NOT NULL,
  PRIMARY KEY (`categories_id`),
  KEY `categories_users_id` (`categories_pid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `posts`;
CREATE TABLE IF NOT EXISTS `posts` (
  `posts_id` int(9) unsigned NOT NULL AUTO_INCREMENT,
  `posts_updated_time` int(10) unsigned NOT NULL DEFAULT '0',
  `users_id` int(9) unsigned NOT NULL,
  `posts_created_time` int(10) NOT NULL,
  `posts_title` varchar(256) NOT NULL,
  `posts_html` text NOT NULL,
  `posts_img` varchar(256) NOT NULL,
  `posts_active` smallint(1) unsigned NOT NULL DEFAULT '1',
  `posts_fast_likes` smallint(5) unsigned NOT NULL DEFAULT '0',
  `posts_fast_categories_arr` varchar(1024) NOT NULL,
  PRIMARY KEY (`posts_id`),
  KEY `posts_users_id_fk` (`users_id`),
  CONSTRAINT `posts_users_id_fk` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `rel_posts_categories`;
CREATE TABLE IF NOT EXISTS `rel_posts_categories` (
  `posts_categories_id` int(9) unsigned NOT NULL AUTO_INCREMENT,
  `posts_id` int(9) unsigned NOT NULL,
  `categories_id` int(9) unsigned NOT NULL,
  PRIMARY KEY (`posts_categories_id`),
  KEY `rel_posts_categories_posts_id` (`posts_id`),
  KEY `rel_posts_categories_id` (`categories_id`),
  CONSTRAINT `rel_posts_categories_categories_id` FOREIGN KEY (`categories_id`) REFERENCES `categories` (`categories_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rel_posts_categories_post_id` FOREIGN KEY (`posts_id`) REFERENCES `posts` (`posts_id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `rel_users_following`;
CREATE TABLE IF NOT EXISTS `rel_users_following` (
  `following_id` int(9) NOT NULL AUTO_INCREMENT,
  `users_follower_id` int(9) unsigned NOT NULL,
  `users_supplier_id` int(9) unsigned NOT NULL,
  `following_confirm` smallint(1) unsigned NOT NULL DEFAULT '0',
  `following_time` int(10) unsigned NOT NULL,
  PRIMARY KEY (`following_id`),
  KEY `rel_users_following_follower_id` (`users_follower_id`),
  KEY `rel_users_following_supplier_id` (`users_supplier_id`),
  CONSTRAINT `rel_users_following_follower_id` FOREIGN KEY (`users_follower_id`) REFERENCES `users` (`users_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rel_users_following_supplier_id` FOREIGN KEY (`users_supplier_id`) REFERENCES `users` (`users_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `rel_users_posts_comments`;
CREATE TABLE IF NOT EXISTS `rel_users_posts_comments` (
  `rel_users_posts_comments_id` int(9) unsigned NOT NULL AUTO_INCREMENT,
  `users_id` int(9) unsigned NOT NULL,
  `posts_id` int(9) unsigned NOT NULL,
  `comments_text` text NOT NULL,
  `rel_users_posts_comments_time` int(10) unsigned NOT NULL,
  PRIMARY KEY (`rel_users_posts_comments_id`),
  KEY `rel_users_posts_comments_posts_id` (`posts_id`),
  KEY `rel_users_posts_comments_users_id` (`users_id`),
  CONSTRAINT `rel_users_posts_comments_posts_id` FOREIGN KEY (`posts_id`) REFERENCES `posts` (`posts_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rel_users_posts_comments_users_id` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `users_posts_like`;
CREATE TABLE IF NOT EXISTS `users_posts_like` (
  `rel_users_posts_like_id` int(9) unsigned NOT NULL AUTO_INCREMENT,
  `users_id` int(9) unsigned NOT NULL,
  `posts_id` int(9) unsigned NOT NULL,
  `rel_users_posts_like_time` int(10) unsigned NOT NULL,
  PRIMARY KEY (`rel_users_posts_like_id`),
  KEY `rel_users_posts_like_posts_id` (`posts_id`),
  KEY `rel_users_posts_like_users_id` (`users_id`),
  CONSTRAINT `rel_users_posts_like_posts_id` FOREIGN KEY (`posts_id`) REFERENCES `posts` (`posts_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `rel_users_posts_like_users_id` FOREIGN KEY (`users_id`) REFERENCES `users` (`users_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
