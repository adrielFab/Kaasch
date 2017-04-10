<?php
require "init.php";

$email = $_POST['loggedInUserEmail'];
echo "the email is: ";
echo $email;

//Check if users exists.
$sql = "SELECT * FROM Users WHERE email LIKE '".$email."'";

$result = mysqli_query($con,$sql);

if(mysqli_num_rows($result) >= 1){
        echo "True";
} else {
	echo "False";
}
?>
