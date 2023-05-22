<?php

$first_number= $_POST['first_number'];
$second_number= $_POST["second_number"];

$sum = json_decode($first_number) + json_decode($second_number);


$data = "This data is from PHP. Doing some complex calcuation that the result is: $sum";

echo json_encode($data);

?>