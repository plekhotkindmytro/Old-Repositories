<?php

abstract class hlkiller_core
{
    private static $db;

    /**
     * db connect by mysqli
     */
    public static function connect()
    {
        //connect to db
        self::$db = new mysqli(
            \config::$db_server,
            \config::$db_username,
            \config::$db_userpass,
            \config::$db_name
        );

        // check connect
        if (self::$db->connect_errno)
            die ('MySQLi cann\'t connect with DataBase');
    }

    /**
     * db disconnect
     */
    public static function disconnect()
    {
        self::$db->close();
    }

    public static function db()
    {
        return self::$db;
    }

    public static function sql_gen($type, array $array, $dubug = false)
    {
        switch ($type) {
            case 'insert':
                $flag = FALSE;
                $q_arr = array();
                $fields = array();
                $table = $array['table'];

                foreach ($array['values'] as $row) {
                    if (!$flag) {
                        foreach ($row as $k => $val) {
                            $fields[] = '`' . $k . '`';
                        }
                        $flag = TRUE;
                    }
                    $insert = array();
                    foreach ($row as $val) {
                        $insert[] = "'" . $val . "'";
                    }

                    $q_arr[] = '(' . implode(', ', $insert) . ')';
                }
                $q = "INSERT INTO `$table` (" . implode(', ', $fields) . ") VALUES " . implode(', ', $q_arr);
                //echo $q."<br>";
                $result = self::db()->query($q);

//                    echo self::db()->affected_rows.' '.'<br><br>';
                if ($result === false) {
                    //echo $table . ' - >>>   ' . $q . '<br><br>';
                }

                break;

            case 'update':
                break;
        }
    }
}