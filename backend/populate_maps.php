<?php
require "init.php";

//query should return every passenger looking for a route on the map
$sql= "SELECT Users.id, Users.first_name, Users.last_name, Users.email, Users.device_key, Users.gender,
	Users.smoker, Users.profile_picture,
	(Users.rating / IF(Users.rating_modification_counter,Users.rating_modification_counter,1)) as rating,
	passengers_searching.start_point, passengers_searching.end_point, passengers_searching.search_id,
	passengers_searching.wantsSmoker, passengers_searching.wantsBoy, passengers_searching.wantsGirl,
	passengers_searching.start_date_time, passengers_searching.route_name
	FROM Users RIGHT JOIN passengers_searching ON Users.id = passengers_searching.user_id 
	";
$result = mysqli_query($con, $sql);
$data = array();

while($row = mysqli_fetch_assoc($result)){
	$a = array(
		"id" => $row['id'],
		"first_name" => $row['first_name'],
		"last_name" => $row['last_name'],
		"gender" => $row['gender'],
		"smoker" => $row['smoker'],
		"profile_picture" => $row['profile_picture'],
		"email" => $row['email'],
		"device_key" => $row['device_key'],
		"rating" => $row['rating'],
		"start_point" => $row['start_point'],
		"end_point" => $row['end_point'],
		"search_id" => $row['search_id'],
		"wantsSmoker" => $row['wantsSmoker'],
		"wantsBoy" => $row['wantsBoy'],
		"wantsGirl" => $row['wantsGirl'],
		"start_date_time" => $row['start_date_time'],
		"route_name" => $row['route_name']
	);
	array_push($data, $a);
}

//Send back json data
echo json_encode($data);

?>