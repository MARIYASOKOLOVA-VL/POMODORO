package com.example.pomodoro_timer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Locale;

public class activity_stop extends AppCompatActivity {

    private static final long START_TIME_IN_MILES = 6000;
    private TextView mTextViewCountDown;
    private Button mButtonStartPause;
    private CountDownTimer mcountDownTimer;
    private boolean mTimerRunning;
    private long mTimeLeftInMiles = START_TIME_IN_MILES;
    private static final int NOTIFY_ID = 101;
    private static String CHANNEL_ID = "Cat channel";
    private Button butrab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop);

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        butrab = findViewById(R.id.button2);
        butrab.setVisibility(View.INVISIBLE);

        mButtonStartPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTimerRunning) {
                    pauseTimer();
                } else {
                    startTimer();
                }
            }
            private void startTimer() {
                mcountDownTimer = new CountDownTimer(mTimeLeftInMiles, 1000) {
                    @Override
                    public void onTick(long millisUntiFinished) {
                        mTimeLeftInMiles = millisUntiFinished;
                        updateCountDownText();
                    }

                    @Override
                    public void onFinish() {
                        mTimerRunning = false;
                        butrab.setVisibility(View.VISIBLE);
                        mButtonStartPause.setText("??????????");
                        mButtonStartPause.setVisibility(View.INVISIBLE);
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(activity_stop.this, CHANNEL_ID)
                                        .setSmallIcon(R.drawable.my)
                                        .setContentTitle("??????????????????????")
                                        .setContentText("???????? ???????????????????? ?? ????????????")
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        NotificationManagerCompat notificationManager =
                                NotificationManagerCompat.from(activity_stop.this);
                        notificationManager.notify(NOTIFY_ID, builder.build());
                    }
                }.start();
                updateCountDownText();
                mTimerRunning = true;
                mButtonStartPause.setText("??????????");

            }

            private void pauseTimer(){
                mcountDownTimer.cancel();
                mTimerRunning=false;
                mButtonStartPause.setText("??????????");

            }

            private void updateCountDownText(){
                int minutes = (int) (mTimeLeftInMiles/1000) / 60;
                int seconds = (int) (mTimeLeftInMiles/1000) % 60;

                String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

                mTextViewCountDown.setText(timeLeftFormatted);
            }
        });

        final Button btnStart = findViewById(R.id.button3);
        final Button btnStop = findViewById(R.id.button4);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(activity_stop.this, PlayService.class));
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(new Intent(activity_stop.this, PlayService.class));
            }
        });
    }

    
    public void onSendMessage3(View view){
        Intent intent = new Intent(this, Activity_start.class);
        startActivity(intent);
    }
}