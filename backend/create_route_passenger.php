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
echo "smokin ";
echo $smoking;
$boy = ($_POST['boy'] ? 1 : 0);
echo "boy ";
echo $boy;
$girl = ($_POST['girl'] ? 1 : 0);
echo "girl: ";
echo $girl;

//create entry in table passengers_searching
$sql = "INSERT INTO passengers_searching (user_id, route_name, start_point, end_point, wantsSmoker, wantsBoy, wantsGirl, start_date_time) 
	VALUES ((SELECT id FROM Users WHERE email = '".$email."'),
		'".$route_name."','".$start."','".$end."',".$smoking.",".$boy.",".$girl.",'".$time."')";
$result = mysqli_query($con, $sql);

if($result){
	echo "Success";
}else{
	echo "Fail \r\n";
	echo $sql;
	echo mysqli_error($con);
}
?>
