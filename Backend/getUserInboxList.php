<?php
require "init.php";

$passenger_email=$_POST["passengerEmail"];

$sql = "INSERT INTO Routes_Users_Association (route_id, user_id) 
		VALUES (
			(SELECT route_id FROM (SELECT * FROM Routes_Users_Association) AS copy WHERE user_id LIKE (SELECT id FROM Users WHERE email like '".$driver_email."')),
			(SELECT id FROM Users WHERE email LIKE '".$passenger_email."'))";

#querry to get all pending routes from user
$sql = "SELECT route_id FROM Routes_Users_Association WHERE user_id LIKE (SELECT id FROM Users WHERE email like '".$passenger_email."') AND user_confirm_route =0"

$result = mysqli_query($con, $sql);

$routeIds = array();
while($row = mysql_fetch_array($result))
{
    #querry to get all driver ids from routes. 
    $sql= "SELECT user_id FROM Routes_Users_Association WHERE route"

}
if(!$result){
	echo $sql;
	echo mysqli_error($con);
	echo "Failed to add passenger to route.";
}else{
	echo "success";
}
?>
