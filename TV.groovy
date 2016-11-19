//import groovy.json.JsonSlurper

preferences {
  input("ip", "text", title: "IP Address", description: "ip", required: true)
  input("port", "text", title: "Port", description: "port", required: true)
  input("mac", "text", title: "MAC Addr", description: "mac")
}

metadata {
  definition (name: "TV", namespace: "Jason", author: "Jason Brown") {
    capability "Refresh"
    capability "Switch"
    capability "Polling"

	command "on"
	command "off"
  }

  // UI tile definitions
  tiles {
      
   standardTile("switch", "device.switch", width: 1.5, height: 1.5, canChangeIcon: true, canChangeBackground: true ) {
   			state "off", action:"on", label:"TV OFF",icon: "st.Electronics.electronics15", backgroundColor: '#ffffff'
            state "on", action:"off", label:"TV ON", icon: "st.Electronics.electronics15", backgroundColor: '#79b821'
            
    }

    standardTile("refresh", "device.refresh", inactiveLabel: false, decoration: "flat") {
      state "default", label:'', action:"refresh.refresh", icon:"st.secondary.refresh"
    }

        main "switch"
    details("switch","refresh")
  }
}

// Parse incoming device messages to generate events

def parse(String description) {
  def msg = parseLanMessage(description)
  def headerString = msg.header

  def result = []
  def bodyString = msg.body
  def value = "";
  if (bodyString) {
//    log.debug {bodyString}
    def json = msg.json;
//    log.debug "Jason name is ${json?.name}"
    if( json?.name == "tvOn") {
      log.debug "${json?.name} value ${value}"
      // the value need to stay "on" and "off" or siri will not work
      result << createEvent(name: "switch", value: "on") 
    }
    else if( json?.name == "tvOff") {
    log.debug "${json?.name} value ${value}"
      result << createEvent(name: "switch", value: "off")
	}
  }
  result
}

private Integer convertHexToInt(hex) {
  Integer.parseInt(hex,16)
}

private getHostAddress() {
  def ip = settings.ip
  def port = settings.port

  log.debug "Using ip: ${ip} and port: ${port} for device: ${device.id}"
  return ip + ":" + port
}

def refresh() {
    poll()
}

def poll() {
	sendHubCommand (new physicalgraph.device.HubAction(
      method: "GET",
      path: "/ping.php",
      headers: [ HOST: "${getHostAddress()}"]   
	))
	log.debug "Sending poll"
}
def on() {	
	sendHubCommand (new physicalgraph.device.HubAction(
        method: "GET",
		path: "/tvOn/",
		headers: [ HOST: "${getHostAddress()}"]
        ))
    runIn(4*5, poll) 
//    log.debug "Turning tv on"
}

def off() {	  
	sendHubCommand (new physicalgraph.device.HubAction(
		method: "GET",
        path: "/tvOff/",
        headers: [ HOST: "${getHostAddress()}"]   
	))
    runIn(3*5, poll)
//	log.debug "Turning tv off"
}
// Needed for recieving info from raspberry
private String convertIPtoHex(ipAddress) { 
    String hex = ipAddress.tokenize( '.' ).collect {  String.format( '%02x', it.toInteger() ) }.join()
    return hex
}
// Needed for recieving info from raspberry
private String convertPortToHex(port) {
  String hexport = port.toString().format( '%04x', port.toInteger() )
    return hexport
}
