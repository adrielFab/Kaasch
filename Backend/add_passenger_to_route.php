<?php
require "init.php";

$driver_email= $_POST["driverEmail"];
$passenger_email=$_POST["passengerEmail"];

$sql = "INSERT INTO Routes_Users_Association (route_id, user_id) 
		VALUES (
			(SELECT route_id FROM (SELECT * FROM Routes_Users_Association) AS copy WHERE user_id LIKE (SELECT id FROM Users WHERE email like '".$driver_email."')),
			(SELECT id FROM Users WHERE email LIKE '".$passenger_email."')
			)";

$result = mysqli_query($con, $sql);

if(!$result){
	echo $sql;
	echo mysqli_error($con);
	echo "Failed to add passenger to route.";
}else{
	echo "success";
}
?>
