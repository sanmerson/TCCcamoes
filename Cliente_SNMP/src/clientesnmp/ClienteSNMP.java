package clientesnmp;


public class ClienteSNMP {
	 public static final String COMMUNITY = "public"; //comunidade
     public static final String OID_Temperatura="1.3.6.1.4.1.5.2"; //OID da temperatura
     public static final String OID_Umidade="1.3.6.1.4.1.5.1"; // OID da umidade
     public static final String OID_Fumaca="1.3.6.1.4.1.5.3"; // OId da fumaça
     public static void main(String[] args)

     {
        int i=0;
         while(i<1){
     try

     {

     String strIPAddress = "192.168.0.58"; //Ip de destino para a requisição SNMP

    // ClienteSNMP objSNMP = new ClienteSNMP(); // Inicia o Client
     Req_SNMP Requisicao_SNMP = new Req_SNMP();
     String SNMP =Requisicao_SNMP.snmpGet(strIPAddress,COMMUNITY,OID_Temperatura, OID_Umidade, OID_Fumaca);
    // String teste =objSNMP.snmpGet(strIPAddress,COMMUNITY,OID_Temperatura, OID_Umidade, OID_Fumaca); // Chama o metodo de requisiçao
     
     System.out.print(SNMP);
     }
    
     catch (Exception e)
     
     {

     e.printStackTrace();

     }
	
}
}
}