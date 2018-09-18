package com.example.pablo.mqtt;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MainActivity extends AppCompatActivity {
    private static final String serverUri = "tcp://m10.cloudmqtt.com:12182";
    private static final String serverUri2 = "tcp://10.3.141.1:8989";
    private static final String userName = "wymecqjv";
    private static final String password = "PBnK_BZnwDZ3";
    private static final String publishMessage = "android";
    private static final String publishTopic = "soaexpo3";
    private Button button1;
    private MqttAndroidClient client;

    public void publishMessage(){

        try {
            MqttMessage message = new MqttMessage();
            message.setPayload(publishMessage.getBytes());
            client.publish(publishTopic, message);

        } catch (MqttException e) {
            System.err.println("Error Publishing: " + e.getMessage());
            e.printStackTrace();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.button1 = (Button) findViewById(R.id.buttonTest);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishMessage();
            }
        });
        String clientId = MqttClient.generateClientId();
        this.client =
                new MqttAndroidClient(this.getApplicationContext(), this.serverUri2,
                        clientId);

        try {
            MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();
            mqttConnectOptions.setCleanSession(false);
            //mqttConnectOptions.setUserName(userName);
            //mqttConnectOptions.setPassword(password.toCharArray());
            IMqttToken token = client.connect(mqttConnectOptions);
            token.setActionCallback(new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    // We are connected
                    //Log.d(TAG, "onSuccess");
                    Context context = getApplicationContext();
                    CharSequence text = "Conectado";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                }



                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                    // Something went wrong e.g. connection timeout or firewall problems
                    //Log.d(TAG, "onFailure");
                    Context context = getApplicationContext();
                    CharSequence text = "Error";
                    int duration = Toast.LENGTH_LONG;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();

                }
            });


        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
