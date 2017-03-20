<?php

$host = "192.175.117.181";
$db_user="dbuser1";
$db_password="4DRxBBXfdv";
$db_name="db1";
$port = 3306;

$con = mysqli_connect($host,$db_user,$db_password,$db_name,$port);

if (mysqli_connect_errno()) {
    printf("Connect failed: %s\n", mysqli_connect_error(), mysqli_connect_errno());
    exit();
}

?>
