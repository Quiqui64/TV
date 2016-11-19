// can be used to send comands to Panasonic TV
<?php
$action = $_GET['action'];
$input = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n";
$input .= "<s:Envelope xmlns:s=\"http://schemas.xmlsoap.org/soap/envelope/\" s:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">\n";
$input .= "<s:Body>\n";
$input .= "<u:X_SendKey xmlns:u=\"urn:panasonic-com:service:p00NetworkControl:1\">\n";
$input .= "<X_KeyEvent>";
$input .= $action;
$input .= "</X_KeyEvent>\n";
$input .= "</u:X_SendKey>\n";
$input .= "</s:Body>\n";
$input .= "</s:Envelope>\n\n";
$header = array(
"Content-type: text/xml;charset=\"utf-8\"",
"Accept: text/xml",
"Cache-Control: no-cache",
"Pragma: no-cache",
"SOAPACTION: \"urn:panasonic-com:service:p00NetworkControl:1#X_SendKey\"",
"Content-Length: ".strlen($input),
);
$curl = curl_init();
curl_setopt($curl, CURLOPT_URL, 'http://192.168.1.101:55000/nrc/control_0');
curl_setopt($curl, CURLOPT_POST, 1);
curl_setopt($curl, CURLOPT_HTTPHEADER, $header); 
curl_setopt($curl, CURLOPT_POSTFIELDS, $input);
curl_setopt($curl, CURLOPT_RETURNTRANSFER, 1);
  if(curl_exec($curl) === false) {
    $err = 'Curl error: ' . curl_error($curl);
    curl_close($curl);
    print $err;
  } else {
    curl_close($curl);
    print 'Operation completed without any errors';
  }
?>
