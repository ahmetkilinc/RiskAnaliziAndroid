<?php

// array for JSON response
$response = array();

//kullanici emaili doğru bir şekilde gönderilebildi mi?
if(isset($_POST['kullanici_email'])){
	
	// include db connect class
	require_once __DIR__ . '/db_connect.php';
	 
	// connecting to db
	$db = new DB_CONNECT();
	
	$kullanici_email = $_POST['kullanici_email'];
	$kullanici_ad = $_POST['kullanici_ad'];
	$kullanici_foto = $_POST['kullanici_foto'];

	// get a analiz from analizler table
    $result = mysql_query("SELECT 'email' FROM 'user' WHERE 'email' = '$kullanici_email'");
	
	//$result = mysql_fetch_array($result);
	
	//if ($result[email] > 0){
	if (!empty($result)) {
	
		if (mysql_num_rows($result) > 0){
			
			$response["success"] = 2;
		}
		
		$response["success"] = 2;
	}
	else{
		
		$result = mysql_query("INSERT INTO user(name, email, photourl) VALUES('$kullanici_ad', '$kullanici_email', '$kullanici_foto')");

		if ($result) {
			
			// successfully inserted into database
			$response["success"] = 1;
			$response["message"] = "Yeni Kullanıcı Başarı ile Eklendi.";

			// echoing JSON response
			echo json_encode($response);
		} else {
			
			// failed to insert row
			$response["success"] = 0;
			$response["message"] = "Yeni Kullanıcı Eklenemedi.";

			// echoing JSON response
			echo json_encode($response);
		}
	}
}
else{
	
	// required field is missing
    $response["success"] = 0;
    $response["message"] = "Bazı Alanlar Eksik Gözüküyor.";

    // echoing JSON response
    echo json_encode($response);
}
?>