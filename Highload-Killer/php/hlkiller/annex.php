<?php
abstract class annex {
	/**
	 * @author Stephen Watkins
	 * @url http://stackoverflow.com/questions/4356289/php-random-string-generator
	 *
	 * @param int $length
	 *
	 * @return string
	 */
	public static function gen_rnd_str($length = 10, $special='none') {
            
            switch ($special) {
                case 'name':
                    $names = array('Dry', 'Hospitable', 'Skillful', 'Invincible', 'Reflective', 'Supreme', 'Divergent', 'Abject', 'Spiritual', 'Foregoing', 'Different', 'Messy', 'Tiny', 'Melodic', 'Snotty', 'Lackadaisical', 'Spiffy', 'Diligent', 'Tacky', 'Industrious', 
                        'Spiritual', 'Foregoing', 'Different', 'Messy', 'Tiny', 'Melodic', 'Snotty', 'Lackadaisical', 'Spiffy', 'Diligent', 'Tacky', 'Industrious', 'Wanting', 'Cautious', 'Acceptable', 'Sassy', 'Various', 'Plant', 'Plastic', 'Dysfunctional', 'Ashamed', 'Exciting', 'Sick', 'Second-hand', 'Evasive');
                    
                    
                    $random = $names[array_rand($names)].' '.$names[array_rand($names)];
                    return $random;
                    break;
                default :
                    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
                    $randomString = '';
                    for ($i = 0; $i < $length; $i++) {
                            $randomString .= $characters[rand(0, strlen($characters) - 1)];
                    }
                    return $randomString;
                    break;
            }
	}

	/**
	 * returned lorem ipsum text
	 *
	 * @return string
	 */
	public static function gen_rnd_text() {
        return 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec vestibulum ipsum a justo tempus molestie. '
        . 'Vestibulum condimentum sapien quis eros gravida, a mattis nibh posuere. Curabitur nec pretium dui. '
                . 'Suspendisse eget turpis nisl. Nunc lacus eros, dictum ut porta id, aliquet at velit. '
                . 'Praesent quis tempor velit. Nulla at erat fermentum, accumsan neque sit amet, volutpat metus.'
                . ' Nulla interdum, libero accumsan facilisis ullamcorper, risus dolor sagittis mi, et fermentum velit neque vitae est.'
                . ' Aenean sodales augue eget leo imperdiet, imperdiet gravida est porta.'
                . ' Aenean vitae tortor egestas, maximus ipsum nec, posuere nulla. Curabitur tempor id ligula a porta.';
    }

	/**
	 * set random value for fields by their parameters
	 *
	 * @param $fields
	 *
	 * @return array
	 */
	public static function set_fields($fields) {
        $params = func_get_args();
        $result = array();
        $i = 0;
        foreach ($fields as $key => $value) {
            if(isset($value['primary']) || isset($value['fk'])) {
                $result[$key] = $params[++$i];
            } else {
                switch ($value['type']) {
                    case 'int':
                        $result[$key] = rand(1, 100);
                        break;
                    case 'time':
                        $result[$key] = time()-  rand(1000, 864000); //@TODO: change
                        break;
                    case 'string':
                        $result[$key] = self::gen_rnd_str($value['length']);
                        break;
                    case 'text':
                        $result[$key] = self::gen_rnd_text();
                        break;
                }
            }
        }

        return $result;
    }

	public static function showArray ($array) {
		echo '<pre style="white-space: pre-wrap">';
		print_r($array);
		echo '</pre>';
	}
} 