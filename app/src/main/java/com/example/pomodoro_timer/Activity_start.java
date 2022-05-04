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

public class Activity_start extends AppCompatActivity {


    private static final long START_TIME_IN_MILES = 6000;

    private TextView mTextViewCountDown;
    private Button mButtonStartPause;

    private CountDownTimer mcountDownTimer;

    private boolean mTimerRunning;

    private long mTimeLeftInMiles = START_TIME_IN_MILES;

    private static final int NOTIFY_ID = 101;
    private static String CHANNEL_ID = "Cat channel";

    private Button butOtd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        mButtonStartPause = findViewById(R.id.button_start_pause);
        butOtd = findViewById(R.id.button2);
        butOtd.setVisibility(View.INVISIBLE);



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
                        butOtd.setVisibility(View.VISIBLE);
                        mTimerRunning = false;
                        mButtonStartPause.setText("СТАРТ");
                        mButtonStartPause.setVisibility(View.INVISIBLE);

                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(Activity_start.this, CHANNEL_ID)
                                        .setSmallIcon(R.drawable.my)
                                        .setContentTitle("Напоминание")
                                        .setContentText("Пора отдхонуть")
                                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        NotificationManagerCompat notificationManager =
                                NotificationManagerCompat.from(Activity_start.this);
                        notificationManager.notify(NOTIFY_ID, builder.build());
                    }
                }.start();
                updateCountDownText();
                mTimerRunning = true;
                mButtonStartPause.setText("ПАУЗА");

            }

            private void pauseTimer(){
                mcountDownTimer.cancel();
                mTimerRunning=false;
                mButtonStartPause.setText("СТАРТ");

            }

            private void updateCountDownText(){
                int minutes = (int) (mTimeLeftInMiles/1000) / 60;
                int seconds = (int) (mTimeLeftInMiles/1000) % 60;

                String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d", minutes, seconds);

                mTextViewCountDown.setText(timeLeftFormatted);
            }
        });


    }

    public void onSendMessage2(View view){
        Intent intent = new Intent(this, activity_stop.class);
        startActivity(intent);
    }
    public void onListTask_my(View view){
        Intent intent = new Intent(this, my_check_list.class);
        startActivity(intent);
    }

}