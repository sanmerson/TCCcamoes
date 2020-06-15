package clientesnmp;

import java.util.regex.Pattern;


import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class Req_SNMP {
	public String snmpGet(String strAddress, String community, String strOID, String strOID2, String strOID3)

	{
		
	String str = "";
	String Temp = ""; 
	String Umid = "";
	String Fum = "";
	String Fum2 = ""; 
	int i =0;
	try
	{

	OctetString community1 = new OctetString(community); //comunidade

	strAddress= strAddress+"/" + 161; // ip destino

	Address targetaddress = new UdpAddress(strAddress); 

	TransportMapping transport = new DefaultUdpTransportMapping();

	transport.listen();

	CommunityTarget comtarget = new CommunityTarget();

	comtarget.setCommunity(community1);

	comtarget.setVersion(SnmpConstants.version1);

	comtarget.setAddress(targetaddress);

	comtarget.setRetries(2);

	comtarget.setTimeout(5000);

	PDU pdu = new PDU();

	ResponseEvent response;

	Snmp snmp;

	pdu.add(new VariableBinding(new OID(strOID)));
	pdu.add(new VariableBinding(new OID(strOID2)));
	pdu.add(new VariableBinding(new OID(strOID3)));

	pdu.setType(PDU.GET);

	snmp = new Snmp(transport);

	response = snmp.get(pdu, comtarget);

	if(response != null)

	{

	if(response.getResponse().getErrorStatusText().equalsIgnoreCase("Success"))

	{

	PDU pduresponse=response.getResponse();

	str=pduresponse.getVariableBindings().toString();

	if(str.contains(","))

	{
	i=0;
	while(i<3){
		String[] resultado = str.split(",");
		String[] resultado2 = resultado[i].split("=");
		
		//int len = str.indexOf(",");
		//System.out.print(str);
		if(i==0) {
			Temp=resultado2[1].replaceAll("\\D", "");
		}else if (i==1) {
			Umid=resultado2[1].replaceAll("\\D", "");
		}else if (i==2) {
			Fum=resultado2[1].replaceAll("\\D", "");
				if (Fum.equals("0")) {
					Fum="False";
				}else {
					Fum="True";
				}
		}
		
		i++;
	}

	}
	System.out.println("");
	}

	}

	else

	{

	System.out.println("Erro: TimeOut");

	}

	snmp.close();

	} catch(Exception e) { e.printStackTrace(); }
	
	//return new Req_SNMP(Temp, Umid, Fum);
	
	return(Temp+","+ Umid+","+ Fum);
	
	}
	
}
