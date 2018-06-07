<?php
 
//array for JSON response
$response = array();
 
// include db connect class
require_once __DIR__ . '/db_connect.php';
 
// connecting to db
$db = new DB_CONNECT();
 
// check for post data
if (isset($_GET["a_id"])){
	
    $a_id = $_GET['a_id'];
 
    // get a analiz from analizler table
    $result = mysql_query("SELECT * FROM analizler WHERE a_id = $a_id");
 
    if (!empty($result)) {
        // check for empty result
        if (mysql_num_rows($result) > 0) {
 
            $result = mysql_fetch_array($result);
 
            $analiz = array();
            $analiz["a_id"] = $result["a_id"];
            $analiz["a_adi"] = $result["a_adi"];
            $analiz["dcr_can_kaybi"] = $result["dcr_can_kaybi"];
            $analiz["dcr_servis_kaybi"] = $result["dcr_servis_kaybi"];
            $analiz["dcr_kulturel_miras_kaybi"] = $result["dcr_kulturel_miras_kaybi"];
            $analiz["dcr_ekonomik_kayip"] = $result["dcr_ekonomik_kayip"];
			$analiz["dolayli_can_kaybi"] = $result["dolayli_can_kaybi"];
			$analiz["dolayli_servis_kaybi"] = $result["dolayli_servis_kaybi"];
			$analiz["dolayli_kulturel_miras_kaybi"] = $result["dolayli_kulturel_miras_kaybi"];
			$analiz["dolayli_ekonomik_kayip"] = $result["dolayli_ekonomik_kayip"];
			$analiz["tr_can_kaybi"] = $result["tr_can_kaybi"];
			$analiz["tr_servis_kaybi"] = $result["tr_servis_kaybi"];
			$analiz["tr_kulturel_miras_kaybi"] = $result["tr_kulturel_miras_kaybi"];
			$analiz["tr_ekonomik_kayip"] = $result["tr_ekonomik_kayip"];
			$analiz["created_at"] = $result["created_at"];
			$analiz["user_id"] = $result["user_id"];
            // success
            $response["success"] = 1;
 
            // user node
            $response["analiz"] = array();
 
            array_push($response["analiz"], $analiz);
 
            // echoing JSON response
            echo json_encode($response);
        } else {
            // no analiz found
            $response["success"] = 0;
            $response["message"] = "Analiz Bulunamadı.";
 
            // echo no users JSON
            echo json_encode($response);
        }
    } else {
        // no analiz found
        $response["success"] = 0;
        $response["message"] = "Analiz Bulunamadı.";
 
        // echo no users JSON
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Gerekli Alanlar Doldurulmadı.";
 
    // echoing JSON response
    echo json_encode($response);
}
?>