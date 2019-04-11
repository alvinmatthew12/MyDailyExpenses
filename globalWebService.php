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
 ?>