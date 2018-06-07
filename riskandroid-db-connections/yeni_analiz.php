<?php
 
/*
 * Following code will create a new product row
 * All product details are read from HTTP Post Request
 */
 
// array for JSON response
$response = array();
 
// check for required fields
if (isset($_POST['a_adi']) && isset($_POST['dcr_can_kaybi']) && isset($_POST['dcr_servis_kaybi']) && isset($_POST['dcr_kulturel_miras_kaybi']) && isset($_POST['dcr_ekonomik_kayip']) && isset($_POST['dolayli_can_kaybi']) && isset($_POST['dolayli_servis_kaybi']) && isset($_POST['dolayli_kulturel_miras_kaybi']) && isset($_POST['dolayli_ekonomik_kayip']) && isset($_POST['tr_can_kaybi']) && isset($_POST['tr_servis_kaybi']) && isset($_POST['tr_kulturel_miras_kaybi']) && isset($_POST['tr_ekonomik_kayip'])) {
 
	$a_adi = $_POST['a_adi'];
	$dcr_can_kaybi = $_POST['dcr_can_kaybi'];
	$dcr_servis_kaybi = $_POST['dcr_servis_kaybi'];
	$dcr_kulturel_miras_kaybi = $_POST['dcr_kulturel_miras_kaybi'];
	$dcr_ekonomik_kayip = $_POST['dcr_ekonomik_kayip'];
	$dolayli_can_kaybi = $_POST['dolayli_can_kaybi'];
	$dolayli_servis_kaybi = $_POST['dolayli_servis_kaybi'];
	$dolayli_kulturel_miras_kaybi = $_POST['dolayli_kulturel_miras_kaybi'];
	$dolayli_ekonomik_kayip = $_POST['dolayli_ekonomik_kayip'];
	$tr_can_kaybi = $_POST['tr_can_kaybi'];
	$tr_servis_kaybi = $_POST['tr_servis_kaybi'];
	$tr_kulturel_miras_kaybi = $_POST['tr_kulturel_miras_kaybi'];
	$tr_ekonomik_kayip = $_POST['tr_ekonomik_kayip'];
 
    // include db connect class
    require_once __DIR__ . '/db_connect.php';
 
    // connecting to db
    $db = new DB_CONNECT();
 
    // mysql inserting a new row
    $result = mysql_query("INSERT INTO analizler(a_adi, dcr_can_kaybi, dcr_servis_kaybi, dcr_kulturel_miras_kaybi, dcr_ekonomik_kayip, dolayli_can_kaybi, dolayli_servis_kaybi, dolayli_kulturel_miras_kaybi, dolayli_ekonomik_kayip, tr_can_kaybi, tr_servis_kaybi, tr_kulturel_miras_kaybi, tr_ekonomik_kayip) VALUES('$a_adi', '$dcr_can_kaybi', '$dcr_servis_kaybi', '$dcr_kulturel_miras_kaybi', '$dcr_ekonomik_kayip', '$dolayli_can_kaybi', '$dolayli_servis_kaybi', '$dolayli_kulturel_miras_kaybi', '$dolayli_ekonomik_kayip', '$tr_can_kaybi', '$tr_servis_kaybi', '$tr_kulturel_miras_kaybi', '$tr_ekonomik_kayip')");
 
    // check if row inserted or not
    if ($result) {
        // successfully inserted into database
        $response["success"] = 1;
        $response["message"] = "Yeni Analiz Başarı ile Eklendi.";
 
        // echoing JSON response
        echo json_encode($response);
    } else {
        // failed to insert row
        $response["success"] = 0;
        $response["message"] = "Yeni Analiz Eklenemedi.";
 
        // echoing JSON response
        echo json_encode($response);
    }
} else {
    // required field is missing
    $response["success"] = 0;
    $response["message"] = "Bazı Alanlar Eksik Gözüküyor.";
 
    // echoing JSON response
    echo json_encode($response);
}
?>