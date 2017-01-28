<?php
//try {
header('Content-Type: text/html; charset=utf-8');
$servername = "localhost";
$username = "stesh";
$password = "28041991";
$dbname = "troubleDB";

// Create connection
$conn = new mysqli($servername, $username, $password, $dbname);

mysql_query("SET NAMES 'utf8'", $conn);

// Check connection
if ($conn->connect_error)
{
	die("Connection failed: " . $conn->connect_error);
}

$sql = "SELECT * FROM `business_table`";
$result = $conn->query($sql);
$data = array();
if ($result->num_rows > 0)
{
    // output data of each row
    while ($row = $result->fetch_assoc())
    {
        array_push($data, $row);
    }
    echo json_encode(array('businesses' => $data));
}
else
{
    echo "0 results";
}
//}
//catch(Exception $e) {
//	echo "Exception Error See Log....";
//	error_log($e->getMessage() , 0);
//}
$conn->close();
?>
