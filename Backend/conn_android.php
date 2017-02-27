<?php

//Connection Parameters
$dbHost = "mysql4.hostica.com";
$dbUser = "Success_Android";
$dbPass = "mRides";
$dbName = "Success_android";

$dbC = mysqli_connect($dbhost, $dbuser, $dbpass, $dbname);

if (!$dbC) {
    echo "Error: Unable to connect to MySQL." . PHP_EOL;
    echo "Debugging errno: " . mysqli_connect_errno() . PHP_EOL;
    echo "Debugging error: " . mysqli_connect_error() . PHP_EOL;
    exit;
}
?>
