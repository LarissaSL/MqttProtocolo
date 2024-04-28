package com.example.mqtt;

import static com.hivemq.client.mqtt.MqttGlobalPublishFilter.ALL;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.nio.charset.StandardCharsets;


public class MainActivity extends AppCompatActivity {

    final String BROKER_URL = "8775fe518eda47a58b88e46e7020692d.s1.eu.hivemq.cloud";
    final String USERNAME = "larissaMqtt";
    final String PASSWORD = "-SenhaTeste1";
    public static final String TOPIC = "topico2";

    private MqttHandler mqttHandler;
    private TextView received_msg;
    private EditText send_msg;
    private Button send_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mqttHandler = new MqttHandler(this,BROKER_URL, USERNAME, PASSWORD);

        received_msg = findViewById(R.id.received_msg);
        mqttHandler.setReceivedMessageTextView(received_msg);
        send_msg = findViewById(R.id.send_msg);
        send_button = findViewById(R.id.send_button);

        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = send_msg.getText().toString();
                if (!message.isEmpty()) {
                    mqttHandler.publish(TOPIC, message);
                }
            }
        });

        mqttHandler.subscribe(TOPIC);
        mqttHandler.setCallbackAndPublish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mqttHandler.disconnect();
    }
}