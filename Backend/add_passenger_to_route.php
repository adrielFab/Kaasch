<?php
require "init.php";

$driver_email= $_POST["driverEmail"];
$passenger_email=$_POST["passengerEmail"];

//Retreive the route name of the driver from the database.
$result = mysqli_query($con, "SELECT drive_route_name FROM notifications WHERE user_id = (SELECT id FROM Users WHERE email = '".$passenger_email."') AND source = '".$driver_email."'");
$drive_route_name = mysqli_fetch_row($result)[0];

//Multi query to associate the accepting passenger to the driver route.
$sql = "
	SET @rName := (SELECT drive_route_name FROM notifications WHERE user_id = (SELECT id FROM Users WHERE email = '".$passenger_email."') AND source = '".$driver_email."' );
	SET @rID := (SELECT route_id FROM Routes_Users_Association WHERE userRouteName = @rName);
	SET @sID := (SELECT search_id FROM notifications WHERE user_id = (SELECT id FROM Users WHERE email = '".$passenger_email."') AND source = '".$driver_email."' );
	INSERT INTO Routes_Users_Association (route_id, user_id, userType, userRouteName) 
		VALUES (
			@rID,
			(SELECT id FROM Users WHERE email LIKE '".$passenger_email."'),
			'PASSENGER',
			(SELECT route_name FROM passengers_searching WHERE search_id = @sID)
			)";

/* execute multi query */
if (mysqli_multi_query($con, $sql)) {
    do {
        if ($result = mysqli_store_result($con)) {
            mysqli_free_result($result);
        }
        if (!mysqli_more_results($con)) {
            break;
	}
    } while (mysqli_next_result($con));
}

//Delete notification for this accepted invitation.
$sql = "DELETE FROM notifications WHERE user_id = (SELECT id FROM Users WHERE email = '".$passenger_email."') AND source = '".$driver_email."'";
$result = mysqli_query($con,$sql);
if(!$result){echo "Failed to delete notification \r\n"; echo mysqli_error($con);die;}

//if more people stil pending on route then switch status to PENDING (YELLOW)
$sql = "SELECT COUNT(*) FROM notifications WHERE drive_route_name = '".$drive_route_name."' AND  source = '".$driver_email."'";
$result = mysqli_query($con,$sql);
if(mysqli_fetch_row($result)[0]){
	$sql = "UPDATE Routes SET status = 'PENDING' WHERE id = (SELECT route_id FROM Routes_Users_Association WHERE user_id = (SELECT id FROM Users WHERE email = '".$driver_email."') AND userRouteName = '".$drive_route_name."' AND userType = 'DRIVER')";
	$result = mysqli_query($con, $sql);
	if(!$result){echo "Failed to update route status to PENDING ";die;}
}else{
	//if no people left to invite then switch route status to MATCHED (GREEN)
	$sql = "UPDATE Routes SET status = 'MATCHED' 
		WHERE id = (SELECT route_id FROM Routes_Users_Association WHERE userRouteName = '".$drive_route_name."' AND user_id = (SELECT id FROM Users WHERE email = '".$driver_email."') AND userType = 'DRIVER')";
	$result = mysqli_query($con, $sql);

	if($result){echo "Success";}else{echo "Failed to change route status to MATCHED"; echo mysqli_error($con);}
}
echo "Success";
mysqli_close($con);
?>
