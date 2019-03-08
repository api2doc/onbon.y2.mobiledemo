package onbon.y2.mobiledemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.awt.Color;

import onbon.y2.Y2Screen;
import onbon.y2.play.ProgramPlayFile;
import onbon.y2.play.TextArea;

public class MainActivity extends AppCompatActivity {

    private Y2Screen screen;

    private EditText outputText;

    private Button connButton;

    private Button disconnButton;

    private ToggleButton screenButton;

    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.outputText = (EditText)findViewById(R.id.outputText);
        this.connButton = (Button)findViewById(R.id.connButton);
        this.connButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        connect();
                    }
                }).start();
            }
        });

        this.disconnButton = (Button)findViewById(R.id.disconnButton);
        this.disconnButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        disconnect();
                    }
                }).start();
            }
        });

        this.screenButton = (ToggleButton)findViewById(R.id.screenButton);
        this.screenButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        turnOnOff();
                    }
                }).start();
            }
        });

        this.sendButton = (Button)findViewById(R.id.sendButton);
        this.sendButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        writeProgram();
                    }
                }).start();
            }
        });

        this.screen = new Y2Screen("192.168.140");
    }

    private void connect() {
        final TextView addrText = (TextView)findViewById(R.id.addrText);
        final TextView portText = (TextView)findViewById(R.id.portText);
        this.screen = new Y2Screen("http://" + addrText.getText());
        try {
            if (this.screen.login("guest", "guest")) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        MainActivity.this.connButton.setEnabled(false);
                        MainActivity.this.disconnButton.setEnabled(true);
                        MainActivity.this.sendButton.setEnabled(true);
                        MainActivity.this.screenButton.setEnabled(true);
                        MainActivity.this.outputText.setText("CONN success");
                    }
                });
            } else {
                runOnUiThread(new Runnable() {
                    public void run() {
                        MainActivity.this.outputText.setText("CONN failure");
                    }
                });
            }
        }
        catch(Exception ex) {

        }
    }

    private void disconnect() {
    }

    private void turnOnOff(){
        try {
            this.screen.turnOn();
        }
        catch(Exception ex) {

        }
    }

    private void writeProgram() {
        try {
            TextArea area = new TextArea(0, 0, 400, 200);
            area.addTextSection("！!大家好 Everyone")
                    .bgColor(Color.black)
                    .fgColor(Color.yellow)
                    .getFont().bold().underline().strikethrough();

            ProgramPlayFile file = new ProgramPlayFile(1);
            file.getAreas().add(area);

            String list = this.screen.writePlaylist(file);
            this.screen.play(list);
        }
        catch(Exception ex) {

        }
    }
}
