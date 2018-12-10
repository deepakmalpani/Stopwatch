package com.example.admin.stopwatch;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    SeekBar seekBar;
    Button button;
    CountDownTimer ctimer;
    boolean isactive=false;
    long timeleft;
    public void updatetimer(int timer){

        int minutes =(int) timer/60;
        int seconds=timer-minutes*60;
        String string2;
        if(seconds<=9){
            string2="0"+Integer.toString(seconds);
        }
        else{
            string2=Integer.toString(seconds);
        }
        textView.setText(Integer.toString(minutes)+":"+string2);
    }
    public void controltimer(View view){
        button=(Button) findViewById(R.id.button);
        if(isactive==false) {
            isactive=true;
            seekBar.setEnabled(false);
            button.setText("Stop");
            ctimer=new CountDownTimer(seekBar.getProgress() * 1000 + 100, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeleft=millisUntilFinished;
                    updatetimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    textView.setText("0:00");
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.alarm_beep);
                    mplayer.start();
                }
            }.start();
        }else{
            seekBar.setEnabled(true);
            isactive=false;
            button.setText("Start");
            updatetimer((int)timeleft/1000);
            seekBar.setProgress((int)timeleft/1000);
            ctimer.cancel();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.textView);
        seekBar=(SeekBar)findViewById(R.id.seekBar);
        seekBar.setMax(600);
        seekBar.setProgress(10);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int min=1;
                int timer;
                if(progress<min){
                    timer=min;
                    seekBar.setProgress(min);
                }
                else{
                    timer=progress;
                }
                updatetimer(timer);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
