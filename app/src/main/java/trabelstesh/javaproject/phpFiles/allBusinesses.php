<?php
try {
	require 'DB_Manage.php';

	$sql = "SELECT * FROM `business_table`";
	$result = $conn->query($sql);
	$data = array();
	if ($result->num_rows > 0) {

		// output data of each row

		while ($row = $result->fetch_assoc()) {
			array_push($data, $row);
		}

		echo json_encode(array('businesses' => $data));
	}
	else {
		echo "0 results";
	}
}
catch(Exception $e) {
	echo "Exception Error See Log....";
	error_log($e->getMessage() , 0);
}

$conn->close();
?>
