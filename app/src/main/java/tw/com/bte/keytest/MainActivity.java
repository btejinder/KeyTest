package tw.com.bte.keytest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.InputDevice;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private  static final String TAG = "KEYTEST";
    TextView tvMsg;
    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private ReadThread mReadThread;
    private QueryKeyThread mQueryKeyThread;
    private Button b_lshift;
    private Button b_rshift;
    private Button b_o;
    private Button b_p;
    private Button b_c;
    private Button b_left;
    private Button b_up;
    private Button b_down;
    private Button b_right;
    private Button b_volup;
    private Button b_voldown;
    private Button b_enter;
    private Button b_esc;
    private Button b_value;

    private final static int SCANCODE_ENTER=28;
    private final static int SCANCODE_ESCAPE=1;
    private final static int SCANCODE_P=25;
    private final static int SCANCODE_C=46;
    private final static int SCANCODE_DPAD_LEFT=105;
    private final static int SCANCODE_DPAD_RIGHT=106;
    private final static int SCANCODE_DPAD_UP=103;
    private final static int SCANCODE_DPAD_DOWN=108;
    private final static int SCANCODE_VOLUME_DOWN=114;
    private final static int SCANCODE_VOLUME_UP=115;
    private final static int SCANCODE_B=305;
    private final static int SCANCODE_X=307;
    private final static int SCANCODE_THUMBR=318;

    private Button s1, s2, s3, s4, s5, s6;
    private TextView tvADValue;

    private ProgressBar plungerbar;
    private int nKeyRepeatCount = 0;

    private AlertDialog dialogQuit;

    private HandlerKeyEvent mHandler;
    private byte[] keybuffer = new byte[64];
    private byte[] KeyStatus = new byte[13];
    private byte[] KeySetStatus = new byte[13];
    private Button[] KeyArray;
    private byte cur_adc_value;
    private byte adc_value;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        plungerbar = (ProgressBar) findViewById(R.id.plungerbar);
        plungerbar.setProgress(100, true);

        Uart_Init();

        mHandler = new HandlerKeyEvent();
        mQueryKeyThread = new QueryKeyThread();
        mQueryKeyThread.start();

        //Button btn = (Button) findViewById(R.id.button);
        /*
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                byte[] data = new byte[]{(byte)0xa6, 0x01, 0x00};
                try{
                    mOutputStream.write(data);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        */
        initKey();

        KeyArray = new Button[]{b_lshift, b_rshift, b_up, b_down, b_left, b_right,
                b_c, b_esc, b_voldown, b_enter, b_o, b_p, b_volup};
    }

    @Override
    public void onClick(View v) {
        int action  = v.getId();
        byte[] data = new byte[]{(byte) 0xA6, 7, 6, 0, 0, 0, 0, 0, 0};
        switch (action){
            case R.id.s1:
                data[3] = 1;
                SendUart(data);
                break;
            case R.id.s2:
                data[4] = 1;
                SendUart(data);
                break;
            case R.id.s3:
                data[5] = 1;
                SendUart(data);
                break;
            case R.id.s4:
                data[6] = 1;
                SendUart(data);
                break;
            case R.id.s5:
                data[7] = 1;
                SendUart(data);
                break;
            case R.id.s6:
                data[8] = 1;
                SendUart(data);
                break;
        }
    }

    private void SendUart(byte[] data){
        /*

        try{
            mOutputStream.write(data);
        } catch (Exception e){
            e.printStackTrace();
        }
         */
    }

    private void initKey()
    {
        b_lshift = findViewById(R.id.b_lshift);
        b_rshift = findViewById(R.id.b_rshift);
        b_c = findViewById(R.id.b_c);
        b_o = findViewById(R.id.b_o);
        b_p = findViewById(R.id.b_p);
        b_left = findViewById(R.id.b_left);
        b_up = findViewById(R.id.b_up);
        b_down = findViewById(R.id.b_down);
        b_right = findViewById(R.id.b_right);
        b_volup = findViewById(R.id.b_volup);
        b_voldown = findViewById(R.id.b_voldown);
        b_enter = findViewById(R.id.b_enter);
        b_esc = findViewById(R.id.b_esc);

        s1 = findViewById(R.id.s1);
        s1.setOnClickListener(this);
        s2 = findViewById(R.id.s2);
        s2.setOnClickListener(this);
        s3 = findViewById(R.id.s3);
        s3.setOnClickListener(this);
        s4 = findViewById(R.id.s4);
        s4.setOnClickListener(this);
        s5 = findViewById(R.id.s5);
        s5.setOnClickListener(this);
        s6 = findViewById(R.id.s6);
        s6.setOnClickListener(this);
        tvADValue = findViewById(R.id.tvADValue);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Long tsLong = System.currentTimeMillis()/1000;
        Log.d("KEY", tsLong.toString() + " Keycode:" + keyCode + " event: " + event) ;
        switch (event.getScanCode()){
            case SCANCODE_X:
                b_lshift.setBackgroundResource(R.color.colorButtonClick);
                break;
            case SCANCODE_C:
                b_c.setBackgroundResource(R.color.colorButtonClick);
                break;
            case SCANCODE_THUMBR:
                b_o.setBackgroundResource(R.color.colorButtonClick);
                break;
            case SCANCODE_P:
                b_p.setBackgroundResource(R.color.colorButtonClick);
                break;
            case SCANCODE_B:
                b_rshift.setBackgroundResource(R.color.colorButtonClick);
                break;
            case SCANCODE_DPAD_LEFT:
                b_left.setBackgroundResource(R.color.colorButtonClick);
                break;
            case SCANCODE_DPAD_UP:
                b_up.setBackgroundResource(R.color.colorButtonClick);
                break;
            case SCANCODE_DPAD_DOWN:
                b_down.setBackgroundResource(R.color.colorButtonClick);
                break;
            case SCANCODE_DPAD_RIGHT:
                b_right.setBackgroundResource(R.color.colorButtonClick);
                break;
            case SCANCODE_VOLUME_UP:
                b_volup.setBackgroundResource(R.color.colorButtonClick);
                break;
            case SCANCODE_VOLUME_DOWN:
                b_voldown.setBackgroundResource(R.color.colorButtonClick);
                break;
            case SCANCODE_ENTER:
                b_enter.setBackgroundResource(R.color.colorButtonClick);
                return true;
            case SCANCODE_ESCAPE:
                b_esc.setBackgroundResource(R.color.colorButtonClick);
                nKeyRepeatCount = event.getRepeatCount();
                if(nKeyRepeatCount > 28){
                    ShowAlertFinish();
                }
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onGenericMotionEvent(MotionEvent event) {
        if ((event.getSource() & InputDevice.SOURCE_JOYSTICK) ==
                InputDevice.SOURCE_JOYSTICK &&
                event.getAction() == MotionEvent.ACTION_MOVE) {
            float value = event.getAxisValue(MotionEvent.AXIS_RY);

            //Log.d("KEY", "RY: " + value);
            tvADValue.setText(String.valueOf(value));

            if(value > 0.1){
                plungerbar.setProgress(100-(int)(value*100));
            } else {
                plungerbar.setProgress(100);
            }
        }

        return super.onGenericMotionEvent(event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        Log.d("KEY", "Keycode:" + keyCode + " Event:" + event);
        switch (event.getScanCode()){
            case SCANCODE_X:
                b_lshift.setBackgroundResource(R.color.colorButton);
                break;
            case SCANCODE_C:
                b_c.setBackgroundResource(R.color.colorButton);
                break;
            case SCANCODE_THUMBR:
                b_o.setBackgroundResource(R.color.colorButton);
                break;
            case SCANCODE_P:
                b_p.setBackgroundResource(R.color.colorButton);
                break;
            case SCANCODE_B:
                b_rshift.setBackgroundResource(R.color.colorButton);
                break;
            case SCANCODE_DPAD_LEFT:
                b_left.setBackgroundResource(R.color.colorButton);
                break;
            case SCANCODE_DPAD_UP:
                b_up.setBackgroundResource(R.color.colorButton);
                break;
            case SCANCODE_DPAD_DOWN:
                b_down.setBackgroundResource(R.color.colorButton);
                break;
            case SCANCODE_DPAD_RIGHT:
                b_right.setBackgroundResource(R.color.colorButton);
                break;
            case SCANCODE_VOLUME_UP:
                b_volup.setBackgroundResource(R.color.colorButton);
                break;
            case SCANCODE_VOLUME_DOWN:
                b_voldown.setBackgroundResource(R.color.colorButton);
                break;
            case SCANCODE_ENTER:
                b_enter.setBackgroundResource(R.color.colorButton);
                break;
            case SCANCODE_ESCAPE:
                b_esc.setBackgroundResource(R.color.colorButton);
                return true;
        }

        return super.onKeyUp(keyCode, event);
    }

    private void ShowAlertFinish(){
        AlertDialog.Builder  builder = new AlertDialog.Builder(this);
        final View dialogview = this.getLayoutInflater().inflate(R.layout.dialog_finish_app, null);
        builder.setView(dialogview);
        Button btnYes = (Button) dialogview.findViewById(R.id.btnYes);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogQuit.cancel();
                finish();
            }
        });

        Button btnNo = (Button) dialogview.findViewById(R.id.btnNo);
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogQuit.cancel();
            }
        });

        dialogQuit = builder.create();
        dialogQuit.show();

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    private void Uart_Init(){
        try {
            SerialPort serialPort = new SerialPort(new File("/dev/ttyS1"), 115200, 0);
            mInputStream = serialPort.getInputStream();
            mOutputStream = serialPort.getOutputStream();
            mReadThread = new ReadThread();
            mReadThread.start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private class ReadThread extends Thread{
        @Override
        public void run() {
            super.run();
            while(!isInterrupted()){
                int size;
                try {
                    if (mInputStream == null) return;
                    size = mInputStream.read(keybuffer);
                    if ((keybuffer[0] == (byte) 0xA7) && keybuffer[1] == 0x10) {
                        mHandler.sendEmptyMessage(0);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    private class QueryKeyThread extends Thread {
        public boolean bReadKey = true;
        private final byte[] data = new byte[]{(byte) 0xA6, 0x01, 0x00};
        @Override
        public void run() {
            while(bReadKey){
                try{
                    mOutputStream.write(data);
                    Thread.sleep(10);
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private class HandlerKeyEvent extends Handler{
        @Override
        public void handleMessage(Message msg) {
            //Log.d("KEY", String.format("%02X, %02X", keybuffer[4] & 0x20, keybuffer[9] & 0x20));
            KeyStatus[0] = (byte)((keybuffer[4] >> 5)& 0x01); //Flipper L
            KeyStatus[1] = (byte) ((keybuffer[9] >> 5) & 0x01); //Flipper R
            KeyStatus[2] = (byte)(keybuffer[5] & 0x01); // Key Up
            KeyStatus[3] = (byte) ((keybuffer[5] >> 1) & 0x01); //Key Down
            KeyStatus[4] = (byte) ((keybuffer[5] >> 2) & 0x01); //Key Left
            KeyStatus[5] = (byte) ((keybuffer[5] >> 3) & 0x01); //Key Right
            KeyStatus[6] = (byte) ((keybuffer[5] >> 4) & 0x01); //Key C
            KeyStatus[7] = (byte) ((keybuffer[5] >> 5) & 0x01); //Key ESC
            KeyStatus[8] = (byte) ((keybuffer[8] >> 5) & 0x01); //Key VolDown
            KeyStatus[9] = (byte) (keybuffer[9] & 0x01); //Key Enter
            KeyStatus[10] = (byte) ((keybuffer[9] >> 1) & 0x01); //Key Thumber
            KeyStatus[11] = (byte) ((keybuffer[9] >> 2) & 0x01); //Key P
            KeyStatus[12] = (byte) ((keybuffer[9] >> 3) & 0x01); //Key Volup

            if(keybuffer[11] == 0x05){
                cur_adc_value = keybuffer[12];
                if(keybuffer[13] != 0){ //new mcu code, and adc lv is 0-31
                    if(cur_adc_value != adc_value){
                        adc_value = cur_adc_value;
                        double mValue = adc_value/31.0;
                        int progress = (int)(mValue * 100);
                        plungerbar.setProgress(progress);
                    }
                } else { //old mcu code, and adc lv is 0-15
                    adc_value = cur_adc_value;
                    double mValue = adc_value/15;
                    int progress = (int)(mValue * 100);
                    plungerbar.setProgress(progress);
                }
            }

            for(int i =0; i < 13; i ++){
                if(KeyStatus[i] != KeySetStatus[i]){
                    //update key status
                    KeySetStatus[i] = KeyStatus[i];
                    if(KeyStatus[i] == 1){
                        KeyArray[i].setBackgroundResource(R.color.colorButtonClick);
                    } else {
                        KeyArray[i].setBackgroundResource(R.color.colorButton);
                    }
                }
            }
        }
    }
}
