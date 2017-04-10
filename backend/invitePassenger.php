<?php
require "init.php";

$passengerEmail = $_POST["passengerEmail"];
$passengerDeviceId = $_POST["passengerDeviceId"];
$loggedInUserEmail = $_POST["loggedInUserEmail"];
$logedInUserDeviceId = $_POST["logedInUserDeviceId"];
$loggedInUserLastName = $_POST["loggedInUserLastName"];
$loggedInUserFirstName = $_POST["loggedInUserFirstName"];
$loggedInUserProfileUrl = $_POST["loggedInUserProfileUrl"];
$driver_route_name = $_POST['route_name'];
$search_id = $_POST['passengerSearchId'];

//url for the notification sending.
$url = 'https://fcm.googleapis.com/fcm/send';
$notification=  array('body' => " $loggedInUserEmail has selected you for their route!",
                      'title' => "mRides");
$jsonNotification = json_encode($notification);
$data= array('driverEmail' => $loggedInUserEmail,
           'driverDeviceID' => $loggedInUserDeviceId, 'driverLastName' => $loggedInUserLastName,
            'driverFirstName' => $loggedInUserFirstName, 'driverUserProfileUrl' => $loggedInUserProfileUrl,
		'passengerEmail' => $passengerEmail);
$jsonData = json_encode($data);
$jsonString = array('to' => $passengerDeviceId, 'notification' => $notification, 'data' => $data);

$ch = curl_init($url);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($jsonString));
curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json', 
'Authorization: key=AAAA_WBhOtk:APA91bFtOd8C-tyRReTu5IoPMal2yc4Kzf5LUyQFInzRbTSN9OBUXvz1CK4g8yBKscL-PEkOpSVsPmKrtZ_AGnwHEpIvJizrbj-fPUEw_JqoniZAmFBoAgi5_1_YZd0oIiVsKnKFSNMl'));    
$response = curl_exec($ch);
echo $response;
curl_close($ch);

//Create db entry in the notifications table
$sql = "INSERT INTO notifications (user_id, source, drive_route_name, search_id) 
	VALUES 
	((SELECT id FROM Users WHERE email LIKE '".$passengerEmail."'),
	'".$loggedInUserEmail."',
	'".$driver_route_name."',
	'".$search_id."')";
$result = mysqli_query($con, $sql);

if(!$result){echo "\r\nFailed to create entry in notifications \r\n"; echo mysqli_error($con);die;}

echo "Success";
?>
