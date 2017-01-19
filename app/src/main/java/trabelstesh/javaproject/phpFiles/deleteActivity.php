<?php
try {
	require 'DB_Manage.php';

	if (isset($_REQUEST["_id"]))
		$id = $_REQUEST["_id"];
	else
		$id = 'NULL';

	$sql = "DELETE FROM `activity_table` WHERE `_id`='$id' ";

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