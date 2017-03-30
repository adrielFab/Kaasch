<?php

$passengerDeviceId = $_POST["passengerDeviceId"];
$loggedInUserEmail = $_POST["loggedInUserEmail"];
$logedInUserDisplayName = $_POST["logedInUserDisplayName"];
$logedInUserDeviceId = $_POST["logedInUserDeviceId"];
$loggedInUserLastName = $_POST["loggedInUserLastName"];
$loggedInUserFirstName = $_POST["loggedInUserFirstName"];


$url = 'https://fcm.googleapis.com/fcm/send';
$notification=  array('body' => "{$logedInUserDisplayName} has selected you for their route!",
                      'title' => "mRides");
$data= array('driverEmail' => $loggedInUserEmail, 'driverDisplayName' =>$logedInUserDisplayName,
           'driverDeviceID' => $logedInUserDeviceId, 'driverLastName' => $loggedInUserLastName,
            'driverFirstName' => $loggedInUserFirstName);
$notification = array('to' => $passengerDeviceId, 'notification' => $notification, 'data' => $data);


// use key 'http' even if you send the request to https://...
$options = array(
    'http' => array(
        'header'  => "Content-type: application/json\r\n"
            . "Authorization: key=AAAA_WBhOtk:APA91bFtOd8C-tyRReTu5IoPMal2yc4Kzf5LUyQFInzRbTSN9OBUXvz1CK4g8yBKscL-PEkOpSVsPmKrtZ_AGnwHEpIvJizrbj-fPUEw_JqoniZAmFBoAgi5_1_YZd0oIiVsKnKFSNMl\r\n",
        'method'  => 'POST',
        'content' => http_build_query($notification)
    )
);
$context  = stream_context_create($options);
$result = file_get_contents($url, false, $context);
if ($result === FALSE) { /* Handle error */ }

var_dump($result);

    
?>