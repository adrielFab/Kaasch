<?php
require "init.php";

$email = $_POST['loggedInUserEmail'];
$route_name = $_POST['route_name'];
$start = $_POST['start'];
$end = $_POST['end'];
$duration = $_POST['duration'] or 0;
$distance = $_POST['distance'] or 0;
$time = date("Y-m-d h:m:s", strtotime($_POST['time'])); 
$smoking = ($_POST['smoking'] ? 1 : 0);
$boy = ($_POST['boy'] ? 1 : 0);
$girl = ($_POST['girl'] ? 1 : 0);


//create entry in table Routes
$sql = "INSERT INTO Routes (start_point,end_point,trip_duration_minutes,distance_km,status,start_date_time) 
	VALUES ('".$start."','".$end."','".$duration."','".$distance."','CREATED','".$time."')";
$result = mysqli_query($con, $sql);

if($result){
	echo "Success";
}else{
	echo "Fail";
	echo mysqli_error($con);
}

//associate driver and preferences to route
$sql = "INSERT INTO Routes_Users_Association (route_id, user_id, userType, userRouteName, wantsSmoker, wantsBoy, wantsGirl)
	VALUES ((SELECT MAX(id) FROM Routes), (SELECT id FROM Users WHERE email = '".$email."'), 'DRIVER', '".$route_name."',".$smoking.",".$boy.",".$girl.")";
$result = mysqli_query($con, $sql);
echo "the result :";
echo $result;
?>
