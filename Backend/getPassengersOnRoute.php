<?php
require "init.php";

$user_email= "anosirrah365@gmail.com";//$_POST["loggedInUserEmail"];
$routeName= "From Deez To Nutz";//$_POST["routeName"];

//get route id
$sql = "SELECT route_id FROM Routes_Users_Association WHERE user_id  = (SELECT id FROM Users WHERE email = '".$user_email."')
            AND userRouteName = '".$routeName."'";
    
$result = mysqli_query($con,$sql);


if(mysqli_num_rows($result) == 0){
    echo "Route does not exist";
    die;
 }
else if(mysqli_num_rows($result) ==1) { 
    while($row = mysqli_fetch_assoc($result)){
        $routeId = $row['route_id'];
    }
}


#querry to get all pending routes from user
$sql = "SELECT id, email, rating, first_name, last_name, profile_picture FROM Users
                        WHERE id IN  (SELECT user_id FROM Routes_Users_Association WHERE route_id = '".$routeId."')";
$result = mysqli_query($con, $sql);
$data = array();
while($row = mysqli_fetch_assoc($result)){
    if($row["email"] != $user_email){
        $a = array(
            "email" => $row['email'],
            "rating" => $row['rating'],
            "first_name" => $row['first_name'],
            "last_name" => $row['last_name'],
            "profile_picture" => $row['profile_picture']
        );
        array_push($data, $a);
    }
}
echo $json_data = json_encode($data);

?>
