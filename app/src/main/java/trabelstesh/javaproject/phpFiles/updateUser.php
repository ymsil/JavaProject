<?php
try {
	require 'DB_Manage.php';

	if (isset($_REQUEST["_id"]))
		$id = $_REQUEST["_id"];
	else
		$id = 'NULL';

	$name = $_REQUEST["name"];
	$password = $_REQUEST["password"];

	$sql = "UPDATE `user_table`
	        SET `name`='$name', `password`='$password'
	        WHERE `_id`='$id' ";

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