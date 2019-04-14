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
	else if (isset($_POST['selectFn']) && $_REQUEST['selectFn']=='fnAddExpenses') {
		$hostname = "127.0.0.1";
		$username = "utemwebi_dailyexpenses_user";
		$password = "dailyexpenses_user";
		$database = "utemwebi_dailyexpenses";

		// $db = new PDO("mysql:host=$hostname;dbname=$database", $username, $password);
		$connect = new Mysqli($hostname, $username, $password, $database);

		$varExpName = $_REQUEST['varExpName'];
		$varExpPrice = $_REQUEST['varExpPrice'];
		$varMobileDate = $_REQUEST['varMobileDate'];
		$varMobileTime = $_REQUEST['varMobileTime'];

		
		
		try
		{
			$sql = "INSERT INTO expenses (exp_name, exp_price, exp_date, exp_time, date_time_server) VALUES ('$varExpName', '$varExpPrice', '$varMobileDate', '$varMobileTime', now())";
			$query = $connect->query($sql);
			if($query === TRUE) {
				
				$response["respond"] = "Information Saved";
				echo json_encode($response);
			} else {
				$response["respond"] = "Cannot Save";
				echo json_encode($response);
			}

			$connect->close();
			// $stmt = $db->prepare("INSERT INTO expenses (exp_name, exp_price, exp_date, exp_time, date_time_server) VALUES (:exp_name,:exp_price,:exp_date,:exp_time, now())");
			// $stmt->execute(array(':exp_name' => $varExpName, ':exp_price' => $varExpPrice, ':exp_date' => $varMobileDate, ':exp_time' => $varMobileTime));

			return;
		} catch (Exception $e)
		{
			$response["respond"] = "Cannot Save!";
			echo json_encode($response);
		}
	}
	else if (isset($_POST['updateFn']) && $_REQUEST['updateFn'] == 'fnUpdateExpenses') {
		$hostname = "127.0.0.1";
		$username = "utemwebi_dailyexpenses_user";
		$password = "dailyexpenses_user";
		$database = "utemwebi_dailyexpenses";

		$connect = new Mysqli($hostname, $username, $password, $database);

		$varExpId = $_REQUEST['varExpId'];
		$varExpName = $_REQUEST['varExpName'];
		$varExpPrice = $_REQUEST['varExpPrice'];
		$varMobileDate = $_REQUEST['varMobileDate'];
		$varMobileTime = $_REQUEST['varMobileTime'];

		try
		{
			$sql = "UPDATE expenses SET exp_name='$varExpName', exp_price='$varExpPrice', exp_date='$varMobileDate', exp_time='$varMobileTime' WHERE exp_id='$varExpId'";
			$query = $connect->query($sql);
			if($query === TRUE) {
				
				$response["respond"] = "Information Updated";
				echo json_encode($response);
			} else {
				$response["respond"] = "Cannot Update!";
				echo json_encode($response);
			}

			$connect->close();
		} catch (Exception $e)
		{
			$response["respond"] = "QUERY ERROR: Cannot Update!";
			echo json_encode($response);
		}
	}
	$response["respond"] = "Non of the function got caught!";
	echo json_encode($response);
 ?>