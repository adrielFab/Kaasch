<?php

$passengerDeviceId =      "f4TBeBH3M7Q:APA91bHhiq-4egX6AOFbd-3tdL3d2OguCkbzevAAtWzshBTtIdLNzS0pQTOO408H3wx8oN0fZBdLa14jSGWQ4K_TajGeRfqZsd-_CrhPOovDqQL3NoeJ113vX2TpqKKLJaNpEZ9Gsi"
$loggedInUserEmail =      "asfd"
$logedInUserDisplayName = "asfd"
$logedInUserDeviceId =    "asfd"
$loggedInUserLastName =   "asfd"
$loggedInUserFirstName =  "asfd"


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
$r = new HttpRequest($url,HttpRequest::METH_POST);
$r->setOptions($options);
$r->addPostFields($notification);
try{
   echo $r->send()->getBody();
}  catch(HttpException $ex) {
    echo $ex;
}

?>
