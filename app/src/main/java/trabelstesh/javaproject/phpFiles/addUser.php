<?php
try {
	require 'DB_Manage.php';

	if (isset($_REQUEST["_id"]))
		$id = $_REQUEST["_id"];
	else
		$id = 'NULL';

	$name = $_REQUEST["name"];
	$password = $_REQUEST["password"]

	$sql = "INSERT INTO `user_table`( `_id`, `name`, `password`) VALUES ('$id', '$name', '$password')";

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