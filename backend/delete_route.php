<?php

//delete entry from route table and all associations.
require "init.php";

$route_name = $_POST['route_name'];


$sql=
"SET @rID := (SELECT route_id FROM Routes_Users_Association WHERE userRouteName = '".$route_name."');
DELETE FROM Routes_Users_Association WHERE route_id = @rID;
DELETE FROM Routes WHERE id = @rID;";

$result= mysqli_multi_query($con, $sql);
echo $result;
?>

