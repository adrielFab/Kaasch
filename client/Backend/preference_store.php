<?php
  include('conn_android.php');

  //Preferences
  $wantsPet = $_POST["wantsPet"] == "true"?1:0;
  $wantsSmoking = $_POST["wantsSmoking"] == "true"?1:0;
  $wantsFemale = $_POST["wantsFemale"] == "true"?1:0;
  $wantsMale = $_POST["wantsMale"] == "true"?1:0;
  $email = $_POST["email"];
  
  //Verify if user already has preferences set.
  $check_existence = "SELECT * FROM Preferences WHERE email LIKE '$email'";
  $result = $dbC->query($check_existence);
  
  if ($result->num_rows > 0) {
    $mysqli_query =
    "UPDATE Preferences SET wantsPet = '$wantsPet',
                            wantsSmoking ='$wantsSmoking',
                            wantsFemale = '$wantsFemale',
                            wantsMale = '$wantsMale'
      WHERE Email = '$email';";
  }else{
    $mysqli_query = 
    "INSERT INTO Preferences (Email, wantsPet, wantsSmoking, wantsFemale, wantsMale) VALUES ('$email','$wantsPet', '$wantsSmoking', '$wantsFemale', '$wantsMale');";
  }
  
  echo "Your query is : " . PHP_EOL .$mysqli_query;
  

  if($dbC->query($mysqli_query) === TRUE){
    echo "Insertation Successful!";
  } else {
    echo "Failed to insert" . PHP_EOL . mysqli_error($dbC);
  }
  $dbC->close();
 ?>
