# IoT + Android = Protocolo MQTT

Esse aplicativo tem como Objetivo utilizar o Protocolo MQTT, ou seja, se comunicar via Broker e tópicos, para envio e recebimento de mensagens

## Funcionalidades 🛠️

| Funcionalidade          | Descrição                                                                                       |
|-------------------------|-------------------------------------------------------------------------------------------------|
| Envio de Mensagens        | O usuário consegue enviar mensagens via Mobile ou pelo Web Client |
| Recebimento de Mensagens        | O usuário consegue visualizar mensagens via Mobile ou pelo Web Client |


## Tecnologias Utilizadas 🚀

- **HiveMq**: Provedor de plataformas MQTT
- **Android Studio**: IDE utilizada para desenvolvimento do Código
- **Java**: Linguagem de Programação utilizada

## Como Usar 📋

### Conexão com o Web Client(Broker)
1. Faça o download do código-fonte do projeto.
2. Importe o projeto para o Android Studio.
3. Entre no site ``https://www.hivemq.com``
4. Clique em ``Get HiveMQ``
5. Procure a Opção ``HiveMQ Cloud``
6. Crie sua conta
7. Procure a opção ``Cluster``
8. Clique nela e em ``Create New Cluster``
9. Selecione a Opção ``Serveless``
10. Selecione o ``AWS`` e em Your Selection clique em ``Create``
11. Clique em ``Manage Cluster`` no Cluster criado
12. Em ``Overview`` copie o valor que estiver em ``Cluster URL``
13. Em ``Acess management`` crie suas Credenciais, Username e Senha (Guarde elas pois usaremos na Aplicação)
14. Em ``Web Client`` coloque suas credenciais, em seguida clique em ``Connect Client``
15. Em ``Topic Subscriptions`` crie um novo Tópico (Guarde esse nome pois usaremos na aplicação)
16. Clique em Subscribe para ativar o Tópico

### Configurações na Aplicação
1. No arquivo ``MainActivity`` substitua as seguintes variaveis pelos valores guardados da etapa anterior

   
   - Em BROKER_URL, insira o valor copiado no Cluster URL
   - Em USERNAME, insira o valor colocado no Username criado em Acess Management
   - Em PASSWORD, insira o valor colocado no Password criado em Acess Management
   - Em TOPIC, inseria o valor colocado para o Tópico
  
     
![image](https://github.com/LarissaSL/MqttProtocolo/assets/112571317/adaf7ffb-967a-4149-b83e-4c18f230094d)



3. No arquivo ``AndroidManifest.xml`` acrescente essas duas permissões, para evitarmos o erro de conexão na Socket.
OBS.: Coloque fora da Tag Application e dentro da Tag Manifest

```xml
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
```

4. No arquivo ``build.gradle.kts nivel Module:APP`` acrescente as seguintes linhas de código


   OBS.: O packaging fica dentro de Android
```kts
   packaging {
        resources {
            excludes += "META-INF/*"
        }
    }
```
OBS.: As implementações ficam na aba de ``Dependencias``
```kts
implementation ("com.hivemq:hivemq-mqtt-client:1.2.2")
implementation(platform("com.hivemq:hivemq-mqtt-client-websocket:1.2.2"))
```

## Telas 📱
### Tela de Inicio
<p>Permite que o usuário envie mensagens e veja todo o histórico do Tópico</p>
<img src="https://github.com/LarissaSL/MqttProtocolo/assets/112571317/40636659-973a-4bb8-9ea0-d2c514dfab8c" width="300">

## Simulação 🎬

Aqui está uma simulação do aplicativo em funcionamento:

![Simulação do Aplicativo](https://github.com/LarissaSL/MqttProtocolo/assets/112571317/7e372ca7-8ffb-46de-88b9-a70cb3ddcd9c)


### Resultado no Web Client
<img src="https://github.com/LarissaSL/MqttProtocolo/assets/112571317/21b18a85-5def-41a5-9c21-97307ce68dc1">



## 🎥 Materiais de Apoio Utilizados

Aqui estão alguns vídeos que utilizei como apoio durante o desenvolvimento:

- [Projeto do Wesley](https://github.com/WesleyS08/MQTT/tree/master) - Repositório GitHub com o projeto do Wesley, utilizando o HiveMQ como WebClient.

- [Stack Overflow](https://stackoverflow.com/questions/56266801/java-net-socketexception-socket-failed-eperm-operation-not-permitted) - Usei este link para tirar dúvidas sobre permissões de acesso.


