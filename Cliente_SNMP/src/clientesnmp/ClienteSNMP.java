package clientesnmp;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

public class ClienteSNMP {
	 public static final String COMMUNITY = "public"; //comunidade
     public static final String OID_Temperatura="1.3.6.1.4.1.5.2"; //OID da temperatura
     public static final String OID_Umidade="1.3.6.1.4.1.5.1"; // OID da umidade
     public static final String OID_Fumaca="1.3.6.1.4.1.5.3"; // OId da fumaça
     public static final String ID_datacenter="1"; //ID datacenter
     public static final String ID_usuario="1";// ID usuario
     public static final String nome_datacenter="\"TCC\""; //Nome datacenter
    // public static final String ID_Fumaca="1.3.6.1.4.1.5.3";
     public static final long TEMPO = (1000);
     public static void main(String[] args)
    
     {
    	Timer timer = null;
        if (timer == null) {
        	timer = new Timer();
        	TimerTask tarefa = new TimerTask() {
        public void run() {
     try

     {
    	 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-mm-dd");
    	 SimpleDateFormat simpleHourFormat = new SimpleDateFormat("hh:mm:ss");
    	 Calendar c = Calendar.getInstance(); //retorna um calendar com a hora do sistema
    	 String data = simpleDateFormat.format(c.getTime());
    	 String hora= simpleHourFormat.format(c.getTime());
    	 String strIPAddress = "192.168.0.58"; //Ip de destino para a requisição SNMP

    	 Req_SNMP Requisicao_SNMP = new Req_SNMP(); // Inicia o Client SNMP
    	 Mysql env_sql = new Mysql(); //Metodo do Mysql
    	 String SNMP =Requisicao_SNMP.snmpGet(strIPAddress,COMMUNITY,OID_Temperatura, OID_Umidade, OID_Fumaca);// Chama o metodo de requisiçao SNMP
    	 String sql_SNMP = (ID_datacenter+","+ID_usuario+","+nome_datacenter+","+SNMP+",\""+data+"\",\""+hora+"\""); //Dados para insert no banco de dados
    	 env_sql.MysqlConnection(sql_SNMP);
     }
    
     catch (Exception e)
     
     {

     e.printStackTrace();

     }
     
        }
        	};
        	timer.scheduleAtFixedRate(tarefa, TEMPO, TEMPO);
}
}
}