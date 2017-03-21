<?php

$passengerEmail = $_POST["passengerEmail"];
$passengerDeviceId = $_POST["passengerDeviceId"];
$loggedInUserEmail = $_POST["loggedInUserEmail"];
$loggedInUserDisplayName = $_POST["logedInUserDisplayName"];
$logedInUserDeviceId = $_POST["logedInUserDeviceId"];
$loggedInUserLastName = $_POST["loggedInUserLastName"];
$loggedInUserFirstName = $_POST["loggedInUserFirstName"];
$loggedInUserProfileUrl = $_POST["loggedInUserProfileUrl"];


$url = 'https://fcm.googleapis.com/fcm/send';
$notification=  array('body' => " $loggedInUserDisplayName has selected you for their route!",
                      'title' => "mRides");
$jsonNotification = json_encode($notification);
$data= array('driverEmail' => $loggedInUserEmail, 'driverDisplayName' =>$loggedInUserDisplayName,
           'driverDeviceID' => $loggedInUserDeviceId, 'driverLastName' => $loggedInUserLastName,
            'driverFirstName' => $loggedInUserFirstName, 'driverUserProfileUrl' => $loggedInUserProfileUrl,
		'passengerEmail' => $passengerEmail);
$jsonData = json_encode($data);
$jsonString = array('to' => $passengerDeviceId, 'notification' => $notification
, 'data' => $data);


// use key 'http' even if you send the request to https://...
$options = array(
    'http' => array(
        'header'  => "Content-type: application/json\r\n"
            . "Authorization: key=AAAA_WBhOtk:APA91bFtOd8C-tyRReTu5IoPMal2yc4Kzf5LUyQFInzRbTSN9OBUXvz1CK4g8yBKscL-PEkOpSVsPmKrtZ_AGnwHEpIvJizrbj-fPUEw_JqoniZAmFBoAgi5_1_YZd0oIiVsKnKFSNMl\r\n",
        'method'  => 'POST',
        'content' => http_build_query($notification)
    )
);
$ch=curl_init($url);
curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($jsonString));
curl_setopt($ch, CURLOPT_HTTPHEADER, array('Content-Type: application/json', 
'Authorization: key=AAAA_WBhOtk:APA91bFtOd8C-tyRReTu5IoPMal2yc4Kzf5LUyQFInzRbTSN9OBUXvz1CK4g8yBKscL-PEkOpSVsPmKrtZ_AGnwHEpIvJizrbj-fPUEw_JqoniZAmFBoAgi5_1_YZd0oIiVsKnKFSNMl'));    
$response = curl_exec($ch);
echo $response;
curl_close($ch);

//create db notification entry
$sql = "INSERT INTO notifications (user_id, content, source) VALUES ('(SELECT id FROM Users WHERE device_key = '".$passengerDeviceId."')','".$loggedInUserDisplayName." has selected you for their route!','".$loggedInUserEmail."')";
$result = mysqli_query($con, $sql);

?>
