package io.github.juntrinh.callroulette;

import android.app.Activity;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;

public class MainActivity extends Activity implements View.OnClickListener{
    private Button callButton;
    private Chronometer callTime;
    //helper class to connect to Twilio
    private CallRoulette phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        callButton = (Button)this.findViewById(R.id.callButton);
        callTime = (Chronometer)this.findViewById(R.id.clock_view);
        callButton.setOnClickListener(this);
        phone = new CallRoulette(this);
    }

    protected void initView() {
        setContentView(R.layout.activity_main);
    }
    @Override
    public void onClick(View view) {
        //whenever a user taps the call button, connect/disconnect a phone line
        if (view.getId() == R.id.callButton && callButton.getText().toString().equals("Call Someone")) {

            phone.connect();
            //callsomeoneButton.setBackgroundResource(R.drawable.endcallgradient);
            callButton.setText("End Call");
            callTime.setVisibility(view.VISIBLE);
            callTime.setBase(SystemClock.elapsedRealtime());
            callTime.start();

        } else if(view.getId() == R.id.callButton && callButton.getText().toString().equals("End Call")) {

            phone.disconnect();
            callButton.setText("Call Someone");
            //callButton.setBackgroundResource(R.drawable.callgradient);
            callTime.setVisibility(view.INVISIBLE);
            callTime.stop();
            callTime.setBase(SystemClock.elapsedRealtime());
        }
    }
}
