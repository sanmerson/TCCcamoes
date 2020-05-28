//================================Bibliotecas====================================
#include <DHT.h>
#include <WiFi.h>
#include <WiFiUdp.h>
#include <Arduino_SNMP.h>

//=================================Variaveis=====================================
#define DHTPIN 26 // pino que estamos conectado
#define DHTTYPE DHT11 // Define o tipo do sensor DHT 11
#define sensFum 25 //pino que esta conectado o sensor de fumaça
const char* ssid = "SSID"; //nome da rede wifi
const char* password = "SENHA1234"; //senha da rede wifi

int LocUmid = 10; // variavel da umidade
int LocTemp = 10; // variavel da temperatura
int LocFum = 0; // variavel da fumaça

//Callback das variaveis
ValueCallback* LocUmidOID;
ValueCallback* LocTempOID;
ValueCallback* LocFumOID;

//================================================================================

WiFiUDP udp; // inicia o o protocolo UDP
SNMPAgent snmp = SNMPAgent("public");  // inicia o serviço do agent snmp com a comunidade 'public'
DHT dht(DHTPIN, DHTTYPE); //envia o numero do pino e o tipo de sensor para a biblioteca DHT.H

void setup(){
    Serial.begin(115200); //inicia o serial
    pinMode (sensFum, INPUT); // define o pino do sensor de fumaça como entrada de dados;
    WiFi.begin(ssid, password); // inicia o wifi;
    dht.begin(); //inicia a biblioteca do DHT;
    Serial.println("");
//===============================Wifi=============================================
    // Aguarda a conexão wifi
    while (WiFi.status() != WL_CONNECTED) {
        delay(500);
        Serial.print(".");
    }
    Serial.println("");
    Serial.print("Connected to ");
    Serial.println(ssid);
    Serial.print("IP address: ");
    Serial.println(WiFi.localIP());
//=================================SNMP=============================================    
    // give snmp a pointer to the UDP object
    snmp.setUDP(&udp);
    snmp.begin();
    
    // add 'callback' for an OID - pointer to an integer
 //   changingNumberOID = snmp.addIntegerHandler(".1.3.6.1.4.1.5.0", &changingNumber);
     LocUmidOID = snmp.addIntegerHandler(".1.3.6.1.4.1.5.1", &LocUmid);
     LocTempOID = snmp.addIntegerHandler(".1.3.6.1.4.1.5.2", &LocTemp);
     LocFumOID = snmp.addIntegerHandler(".1.3.6.1.4.1.5.3", &LocFum);
    // usando a sua ferramenta favorita de snmp:
    // snmpget -v 1 -c public <IP> 1.3.6.1.4.1.5.0
    
}

void loop(){
    snmp.loop(); // Deve ser sempre chamado no inicio do loop
    int u = dht.readHumidity(); // armazena os dados de umidade na variavel u;
    int t = dht.readTemperature(); //armazena os dados de temperatura na variavel t;
    int fum = digitalRead(sensFum); //Armazena (1) - se tiver fumaça ou gas (0)- se não tiver fumaça nem gas;
 
    LocUmid = u; //envia os dados de umidade para a veriavel LocUmid que será lida pelo OID;
    LocTemp = t; //envia os dados de temperatura para a veriavel LocTemp que será lida pelo OID;
    LocFum = fum; //envia os dados de fumaça para a veriavel LocFum que será lida pelo OID;
}
