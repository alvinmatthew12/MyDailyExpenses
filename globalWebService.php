<?php 
	if (isset($_REQUEST['selectFn']) && $_REQUEST['selectFn']=='fnGetDateTime')  
	{
		$currentDate= date("Y-n-j", time()+ 3600*8);
		$currentTime= date("H:i:s", time()+ 3600*7);
		$response["currDate"] = $currentDate;
		$response["currTime"] = $currentTime;
		$json=json_encode($response);
		echo $json;
	}
	else if (isset($_POST['selectFn']) && $_REQUEST['selectFn']=='fnAddExpense') {
		$hostname = "127.0.0.1";
		$username = "root";
		$password = "";
		$database = "database";

		$db = new PDO("mysql:host=$hostname;dbname=$database", $username, $password );

		$varExpName = $_REQUEST['varExpName'];
		$varExpPrice = $_REQUEST['varExpPrice'];
		$varMobileDate = $_REQUEST['varMobileDate'];
		$varMobileTime = $_REQUEST['varMobileTime'];
		try
		{
			$stmt = $db->prepare("INSERT INTO expenses (exp_name, exp_price, exp_date, exp_time, date_time_server) VALUES (:exp_name,:exp_price,:exp_date,:exp_time, now())");
			$stmt->execute(array(':exp_name' => $varExpName, ':exp_price' => $varExpPrice, ':exp_date' => $varMobileDate, ':exp_time' => $varMobileTime));

			$response["respond"] = "Information Saved";
			echo json_encode($response);
		} catch (Exception $ee)
		{
			$response["respond"] = "Cannot Save!";
			echo json_encode($response);
		}
	}
 ?>