package clientesnmp;

import org.snmp4j.CommunityTarget;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.TransportMapping;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.mp.SnmpConstants;
import org.snmp4j.smi.*;
import org.snmp4j.transport.DefaultUdpTransportMapping;

public class ClienteSNMP {
	 public static final String COMMUNITY = "public"; //comunidade
     public static final String OID_Temperatura="1.3.6.1.4.1.5.2"; //OID da temperatura
     public static final String OID_Umidade="1.3.6.1.4.1.5.1"; // OID da umidade
     public static final String OID_Fumaca="1.3.6.1.4.1.5.3"; // OId da fumaça
     public static void main(String[] args)

     {

     try

     {

     String strIPAddress = "172.0.0.1"; //Ip de destino para a requisição SNMP

     ClienteSNMP objSNMP = new ClienteSNMP(); // Inicia o Client

     String teste =objSNMP.snmpGet(strIPAddress,COMMUNITY,OID_Temperatura, OID_Umidade, OID_Fumaca); // Chama o metodo de requisiçao
     
     System.out.print(teste);
     }
    
     catch (Exception e)
     
     {

     e.printStackTrace();

     }
	
     
     

}


public String snmpGet(String strAddress, String community, String strOID, String strOID2, String strOID3)

{
	
String str = "";
String Temp="";
String Umid = "";
String Fum = "";
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

int len = str.indexOf(",");
//System.out.print(str);
Umid=str.substring(41,43);
Fum=str.substring(63,64);
Temp=str.substring(19,21);

}

}

}

else

{

System.out.println("Erro: TimeOut");

}

snmp.close();

} catch(Exception e) { e.printStackTrace(); }

return ("Temp= "+Temp+" Umid= "+ Umid + " Fum= " +Fum);

}

}