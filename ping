// Used to ping the TV from the Raspberry.
// Need to fix to much code
 
 <html>
 <head>
 <meta name="viewport" content="width=device-width" />
 <title>TV Control</title>
 </head>
         <body>
         <?php
         function pingAddress($ip) {
 	   $pingresult = exec("/bin/ping -c1 $ip", $outcome, $status);  
  		if ($status == 0) {
		  $url = "192.168.1.170:39500";    
		  $data = array("name" => "tvOn");
		  $content = json_encode($data);
		  $curl = curl_init($url);
		  curl_setopt($curl, CURLOPT_HEADER, false);
		  curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
		  curl_setopt($curl, CURLOPT_HTTPHEADER,
		        array("Content-type: application/json"));
		  curl_setopt($curl, CURLOPT_POST, true);
		  curl_setopt($curl, CURLOPT_POSTFIELDS, $content);
		  $json_response = curl_exec($curl);
		  $status = curl_getinfo($curl, CURLINFO_HTTP_CODE);
		  if ( $status != 201 ) {die("Error: call to URL $url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl));
		  }
		  curl_close($curl);
		  $response = json_decode($json_response, true);
                } 
		else {
		  $url = "192.168.1.170:39500";    
		  $data = array("name" => "tvOff");
		  $content = json_encode($data);
		  $curl = curl_init($url);
		  curl_setopt($curl, CURLOPT_HEADER, false);
		  curl_setopt($curl, CURLOPT_RETURNTRANSFER, true);
		  curl_setopt($curl, CURLOPT_HTTPHEADER,
		        array("Content-type: application/json"));
		  curl_setopt($curl, CURLOPT_POST, true);
		  curl_setopt($curl, CURLOPT_POSTFIELDS, $content);
		  $json_response = curl_exec($curl);
		  $status = curl_getinfo($curl, CURLINFO_HTTP_CODE);
		  if ( $status != 201 ) {die("Error: call to URL $url failed with status $status, response $json_response, curl_error " . curl_error($curl) . ", curl_errno " . curl_errno($curl));
		  }
		  curl_close($curl);
		  $response = json_decode($json_response, true);
                }
	}
// Some IP Address
pingAddress("192.168.1.101");
         ?>
        </body>
 </html>
