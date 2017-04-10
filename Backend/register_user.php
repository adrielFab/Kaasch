<?php
require "init.php";

//a ratings value could be set as default instead of null being used in db.
$email= $_POST["loggedInUserEmail"];
$lastName=$_POST["loggedInUserLastName"];
$firstName=$_POST["loggedInUserFirstName"];
//$displayName=$_POST["loggedInUserDisplayName"];
$deviceId=$_POST["loggedInUserDeviceId"];
$profilPicUrl=$_POST["loggedInUserProfileUrl"];
$gender = $_POST['loggedInUserGender'];
$smoker = $_POST['loggedInUserSmokes'];

//Check if users exists, if not register user in database.
$sql = "SELECT * FROM Users WHERE email LIKE '".$email."'";
$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) >= 1){
	echo "User already exists.";
} else {
	$sql = "INSERT INTO Users 
	(first_name, last_name, email, rating, profile_picture, device_key, rating_modification_counter, gender, smoker) 
	VALUES 
	('".$firstName."', '".$lastName."', '".$email."',0,'".$profilPicUrl."','".$deviceId."',0,'".$gender."',".$smoker.")";
	//echo $sql;die;
	if(!$result = mysqli_query($con,$sql)){
		echo mysli_error($con);
		echo "Query failed";
	}else{
    		echo "User added succesfully";
	}
}
?>
