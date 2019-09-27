package tw.com.bte.keytest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tvMsg;
    private InputStream mInputStream;
    private OutputStream mOutputStream;
    private ReadThread mReadThread;
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

    private Button s1, s2, s3, s4, s5, s6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Uart_Init();
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
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_SHIFT_LEFT:
                b_lshift.setBackgroundResource(R.color.colorButtonClick);
                break;
            case KeyEvent.KEYCODE_C:
                b_c.setBackgroundResource(R.color.colorButtonClick);
                break;
            case KeyEvent.KEYCODE_O:
                b_o.setBackgroundResource(R.color.colorButtonClick);
                break;
            case KeyEvent.KEYCODE_P:
                b_p.setBackgroundResource(R.color.colorButtonClick);
                break;
            case KeyEvent.KEYCODE_SHIFT_RIGHT:
                b_rshift.setBackgroundResource(R.color.colorButtonClick);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                b_left.setBackgroundResource(R.color.colorButtonClick);
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                b_up.setBackgroundResource(R.color.colorButtonClick);
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                b_down.setBackgroundResource(R.color.colorButtonClick);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                b_right.setBackgroundResource(R.color.colorButtonClick);
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                b_volup.setBackgroundResource(R.color.colorButtonClick);
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                b_voldown.setBackgroundResource(R.color.colorButtonClick);
                break;
            case KeyEvent.KEYCODE_ENTER:
                b_enter.setBackgroundResource(R.color.colorButtonClick);
                return true;
            case KeyEvent.KEYCODE_BACK:
                b_esc.setBackgroundResource(R.color.colorButtonClick);
                return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_SHIFT_LEFT:
                b_lshift.setBackgroundResource(R.color.colorButton);
                break;
            case KeyEvent.KEYCODE_C:
                b_c.setBackgroundResource(R.color.colorButton);
                break;
            case KeyEvent.KEYCODE_O:
                b_o.setBackgroundResource(R.color.colorButton);
                break;
            case KeyEvent.KEYCODE_P:
                b_p.setBackgroundResource(R.color.colorButton);
                break;
            case KeyEvent.KEYCODE_SHIFT_RIGHT:
                b_rshift.setBackgroundResource(R.color.colorButton);
                break;
            case KeyEvent.KEYCODE_DPAD_LEFT:
                b_left.setBackgroundResource(R.color.colorButton);
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                b_up.setBackgroundResource(R.color.colorButton);
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                b_down.setBackgroundResource(R.color.colorButton);
                break;
            case KeyEvent.KEYCODE_DPAD_RIGHT:
                b_right.setBackgroundResource(R.color.colorButton);
                break;
            case KeyEvent.KEYCODE_VOLUME_UP:
                b_volup.setBackgroundResource(R.color.colorButton);
                break;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                b_voldown.setBackgroundResource(R.color.colorButton);
                break;
            case KeyEvent.KEYCODE_ENTER:
                b_enter.setBackgroundResource(R.color.colorButton);
                break;
            case KeyEvent.KEYCODE_BACK:
                b_esc.setBackgroundResource(R.color.colorButton);
                return true;
        }
        return super.onKeyUp(keyCode, event);
    }


    private void Uart_Init(){
        try {
            SerialPort serialPort = new SerialPort(new File("/dev/ttyS3"), 115200, 0);
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
                    byte[] buffer = new byte[64];
                    if (mInputStream == null) return;
                    size = mInputStream.read(buffer);
                    if (size > 0) {
                        Log.d("UART_R", String.format("%02X %02X %02X %02X %02X %02X %02X %02X %02X", buffer[0], buffer[1], buffer[2], buffer[3], buffer[4], buffer[5], buffer[6], buffer[7], buffer[8]));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }
}
