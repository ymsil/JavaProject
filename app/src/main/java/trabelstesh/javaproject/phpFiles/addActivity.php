<?php
try {
	require 'DB_Manage.php';

	if (isset($_REQUEST["_id"]))
		$id = $_REQUEST["_id"];
	else
		$id = 'NULL';

	$description = $_REQUEST["description"];
	$country = $_REQUEST["country"]
	$start_date = $_REQUEST["start_date"];
	$end_date = $_REQUEST["end_date"];
	$cost = $_REQUEST["cost"];
	$short_description = $_REQUEST["short_description"];
	$activity_business_id = $_REQUEST["activity_business_id"];

	$sql = "INSERT INTO `activity_table`( `_id`, `description`, `country`, `start_date`, `end_date`, `cost` , `short_description`, `activity_business_id`)
	        VALUES ('$id', '$description', '$country', '$start_date', '$end_date', '$cost', '$short_description', '$activity_business_id')";

	if ($conn->query($sql) === TRUE) {
		$last_id = $conn->insert_id;
		echo $last_id;
	}
	else {
		echo "Error: " . $sql . "\n" . $conn->error;
	}
}

catch(Exception $e) {
	echo "Exception Error See Log....";
	error_log($e->getMessage() , 0);
}

$conn->close();
?>