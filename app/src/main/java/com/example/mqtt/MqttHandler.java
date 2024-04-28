package com.example.mqtt;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import static java.nio.charset.StandardCharsets.UTF_8;

public class MqttHandler {
    private static final String TAG = "MqttHandler";

    private final Mqtt5BlockingClient client;
    private Context mContext;
    private TextView received_msg;
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
    String horaAtual = dateFormat.format(calendar.getTime());

    public MqttHandler(Context context, String host, String username, String password) {
        mContext = context;
        client = MqttClient.builder()
                .useMqttVersion5()
                .serverHost(host)
                .serverPort(8883)
                .sslWithDefaultConfig()
                .buildBlocking();

        try {
            client.connectWith()
                    .simpleAuth()
                    .username(username)
                    .password(UTF_8.encode(password))
                    .applySimpleAuth()
                    .send();
            Log.d(TAG, "Conexão bem sucedida");
        } catch (Exception e) {
            Log.e(TAG, "Falha ao Conectar ao Broker", e);
        }
    }

    public void subscribe(String topic) {
        try {
            client.subscribeWith()
                    .topicFilter(topic)
                    .qos(MqttQos.EXACTLY_ONCE)
                    .send();

            Log.d(TAG, "Topico subscrito " + topic);
        } catch (Exception e) {
            Log.e(TAG, "Falha ao subscrever topico " + topic, e);
        }
    }

    public void setCallbackAndPublish() {
        client.toAsync().publishes(ALL, publish -> {
            String receivedMessage = UTF_8.decode(publish.getPayload().get()).toString();
            Log.d(TAG, "Mensagem recebida: " + publish.getTopic() + " -> " + receivedMessage);
            updateReceivedMessage(receivedMessage);
        });
    }

    public void setReceivedMessageTextView(TextView textView) {
        received_msg = textView;
    }

    private void updateReceivedMessage(String message) {
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (received_msg != null) {
                    received_msg.append("\n" + message + "\n" + "Horário: " + horaAtual + "\n");
                } else {
                    Log.e(TAG, "TextView received_msg não está inicializado");
                }
            }
        });
    }

    public void disconnect() {
        client.disconnect();
        Log.d(TAG, "Disconectado do MQTT broker");
    }

    public void publish(String topic, String message) {
        try {
            client.publishWith()
                    .topic(topic)
                    .payload(UTF_8.encode(message))
                    .qos(MqttQos.EXACTLY_ONCE)
                    .send();


            Log.d(TAG, "Mensagem Publicada no Topico " + topic + ": " + message);
        } catch (Exception e) {
            Log.e(TAG, "Falha ao publicar a mensagem no topico " + topic, e);
        }
    }
}
