<?php
require "init.php";

//a ratings value could be set as default instead of null being used in db.
$email= $_POST["loggedInUserEmail"];
$lastName=$_POST["loggedInUserLastName"];
$firstName=$_POST["loggedInUserFirstName"];
$displayName=$_POST["loggedInUserDisplayName"];
$deviceId=$_POST["loggedInUserDeviceId"];
$profilPicUrl=$_POST["loggedInUserProfileUrl"];

//Check if users exists, if not register user in database.
$sql = "SELECT * FROM Users WHERE email LIKE '".$email."'";
//echo $sql;
$result = mysqli_query($con,$sql);
//echo "\r\n check if exists";
//var_dump($result);

if(mysqli_num_rows($result) >= 1){
	echo "User already exists.";
    
} else {
//POST sql
	$sql = "INSERT INTO Users (first_name, last_name, email, profile_picture, device_key) VALUES ('".$firstName."', '".$lastName."', '".$email."','".$profilPicUrl."','". $deviceId."')";
//	$sql = "INSERT INTO Users (first_name, last_name, email) VALUES ('".$first_name."','".$last_name."','".$email."')";
//	echo $sql;
    if(!$result = mysqli_query($con,$sql)){
	echo mysli_error($con);
	echo "query failed";
    }else{
    	echo "User added succesfully";
    }
}
?>
