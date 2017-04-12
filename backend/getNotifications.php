<?php
require "init.php";

$user_email = $_POST['loggedInUserEmail'];

$data =array();

$sql = "SELECT * FROM Users WHERE email LIKE '".$user_email."'";
$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) == 0){
    echo "User doesn't exist.";
    die;
 } else {
 	$sql = "SELECT email, rating, first_name, last_name, profile_picture FROM Users 
 			WHERE email IN (SELECT source FROM notifications WHERE user_id = 
				(SELECT id FROM Users WHERE email LIKE '".$user_email."'))";
 	$result = mysqli_query($con,$sql);
	
 	while($row = mysqli_fetch_assoc($result)){
		$a = array(
 			"email" => $row['email'],
 			"rating" => $row['rating'],
 			"first_name" => $row['first_name'],
 			"last_name" => $row['last_name'],
			"profile_picture" => $row['profile_picture']
 		);
 		array_push($data, $a);
 	}

 	//Send back the data
 	echo $json_data = json_encode($data);
 }
?>
