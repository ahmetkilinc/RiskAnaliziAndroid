<?php
 
/*
 * Following code will list all the products
 */
 
// array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// get all analiz from analizler table
$result = mysql_query("SELECT * FROM analizler") or die(mysql_error());
 
// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // analizler node
    $response["analizler"] = array();
 
    while ($row = mysql_fetch_array($result)) {
        // temp user array
		
		$analiz = array();
        $analiz["a_id"] = $row["a_id"];
        $analiz["a_adi"] = $row["a_adi"];
        $analiz["dcr_can_kaybi"] = $row["dcr_can_kaybi"];
        $analiz["dcr_servis_kaybi"] = $row["dcr_servis_kaybi"];
        $analiz["dcr_kulturel_miras_kaybi"] = $row["dcr_kulturel_miras_kaybi"];
        $analiz["dcr_ekonomik_kayip"] = $row["dcr_ekonomik_kayip"];
		$analiz["dolayli_can_kaybi"] = $row["dolayli_can_kaybi"];
		$analiz["dolayli_servis_kaybi"] = $row["dolayli_servis_kaybi"];
		$analiz["dolayli_kulturel_miras_kaybi"] = $row["dolayli_kulturel_miras_kaybi"];
		$analiz["dolayli_ekonomik_kayip"] = $row["dolayli_ekonomik_kayip"];
		$analiz["tr_can_kaybi"] = $row["tr_can_kaybi"];
		$analiz["tr_servis_kaybi"] = $row["tr_servis_kaybi"];
		$analiz["tr_kulturel_miras_kaybi"] = $row["tr_kulturel_miras_kaybi"];
		$analiz["tr_ekonomik_kayip"] = $row["tr_ekonomik_kayip"];
		$analiz["created_at"] = $row["created_at"];
		$analiz["user_id"] = $row["user_id"];
		
        // push single product into final response array
        array_push($response["analizler"], $analiz);
    }
    // success
    $response["success"] = 1;
 
    // echoing JSON response
    echo json_encode($response);
} else {
    // no analizler found
    $response["success"] = 0;
    $response["message"] = "Analizler Bulunamadı.";
 
    // echo no users JSON
    echo json_encode($response);
}
?>