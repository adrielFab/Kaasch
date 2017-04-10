<?php

require 'init.php';

$userEmail = $_POST['loggedInUserEmail'];
$newDeviceId = $_POST['loggedInUserDeviceId'];

$sql = "UPDATE Users SET device_key = '".$newDeviceId."' WHERE email = '".$userEmail."'";
$result = mysqli_query($con, $sql);
echo "Device key updated";

?>