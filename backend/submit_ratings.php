<?php

  require "init.php";

  $ratingsList = $_POST['ratingsList']; 
  $ratingArray = json_decode($ratingsList, true);
  $query="";
  foreach ($ratingArray as $key => $value) {
    echo $value["email"] . ", " . $value["rating"] . "<br>";
    $query .= "UPDATE  Users SET rating=rating+'".$value["rating"]."', rating_modification_counter=rating_modification_counter + 1  WHERE email='".$value["email"]."';";
  }
  $result = mysqli_multi_query($con, $query);	

?>