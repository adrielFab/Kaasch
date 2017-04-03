<?php

$driverEmail = $_POST["driverEmail"]
$passengerEmail = $_POST["passengerEmail"];
$passengerDeviceId = $_POST["passengerDeviceId"];
$loggedInUserEmail = $_POST["loggedInUserEmail"];
$loggedInUserDisplayName = $_POST["logedInUserDisplayName"];
$logedInUserDeviceId = $_POST["logedInUserDeviceId"];
$loggedInUserLastName = $_POST["loggedInUserLastName"];
$loggedInUserFirstName = $_POST["loggedInUserFirstName"];
$loggedInUserProfileUrl = $_POST["loggedInUserProfileUrl"];


$url = 'https://fcm.googleapis.com/fcm/send';
$notification=  array('body' => " $loggedInUserEmail has selected you for their route!",
                      'title' => "mRides");
$jsonNotification = json_encode($notification);
$data= array('driverEmail' => $loggedInUserEmail, 'driverDisplayName' =>$loggedInUserDisplayName,
           'driverDeviceID' => $loggedInUserDeviceId, 'driverLastName' => $loggedInUserLastName,
            'driverFirstName' => $loggedInUserFirstName, 'driverUserProfileUrl' => $loggedInUserProfileUrl,
		'passengerEmail' => $passengerEmail);
$jsonData = json_encode($data);
$jsonString = array('to' => $passengerDeviceId, 'notification' => $notification
, 'data' => $data);


//add passenger to route association as pending
$sql = "INSERT INTO Routes_Users_Association (route_id, user_id,userType,user_confirm_route) 
		VALUES (
			(SELECT route_id FROM (SELECT * FROM Routes_Users_Association) AS copy WHERE user_id LIKE (SELECT id FROM Users WHERE email like '".$driver_email."')),
			(SELECT id FROM Users WHERE email LIKE '".$passenger_email."'),0,0)";
$result = mysqli_query($con, $sql);

$ch=curl_init($url);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($jsonString));
curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json', 
'Authorization: key=AAAA_WBhOtk:APA91bFtOd8C-tyRReTu5IoPMal2yc4Kzf5LUyQFInzRbTSN9OBUXvz1CK4g8yBKscL-PEkOpSVsPmKrtZ_AGnwHEpIvJizrbj-fPUEw_JqoniZAmFBoAgi5_1_YZd0oIiVsKnKFSNMl'));    
$response = curl_exec($ch);
echo $response;
curl_close($ch);

?>
