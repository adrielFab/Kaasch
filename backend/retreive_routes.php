<?php
 require('init.php');
 $email = $_POST['loggedInUserEmail'];
 $data = array();
//$email = 'adriel.fab@gmail.com';
 
 $sql = "SELECT Routes_Users_Association.userType, Routes_Users_Association.userRouteName, Routes.status
 		FROM Routes_Users_Association
 			INNER JOIN Routes ON Routes.id = Routes_Users_Association.route_id 
 		WHERE (SELECT Users.id FROM Users where email='".$email."') = Routes_Users_Association.user_id";

 $result = mysqli_query($con, $sql); 

 while($row = mysqli_fetch_assoc($result)){
	$a = array(
 		"user_type" => $row['userType'],
 		"route_name" => $row['userRouteName'],
 		"route_status" => $row['status']
 	);
 	array_push($data, $a);
 	}

	echo $json_data = json_encode($data);
?>

