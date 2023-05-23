package android.example.mycountdowntimer2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tvTimer, tvDuration;
    private Button btnStart, btnPause;
    private CountDownTimer countDownTimer;
    private long totalTimeInMillis;
    private long timeLeftInMillis;
    private boolean timerRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTimer = findViewById(R.id.tv_timer);
        tvDuration = findViewById(R.id.tv_duration);
        btnStart = findViewById(R.id.btn_start);
        btnPause = findViewById(R.id.btn_pause);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pauseTimer();
            }
        });
    }

    private void startTimer() {
        if (!timerRunning) {
            String durationString = tvDuration.getText().toString().trim();
            if (durationString.isEmpty() || durationString.equals("Duration in seconds")) {
                Toast.makeText(this, "Please enter a duration", Toast.LENGTH_SHORT).show();
                return;
            }

            int durationInSeconds = Integer.parseInt(durationString);
            totalTimeInMillis = durationInSeconds * 1000;
            timeLeftInMillis = totalTimeInMillis;

            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateTimer();
                }

                @Override
                public void onFinish() {
                    timerRunning = false;
                    btnStart.setText("Start");
                    btnPause.setVisibility(View.INVISIBLE);
                }
            }.start();

            timerRunning = true;
            btnStart.setText("Stop");
            btnPause.setVisibility(View.VISIBLE);
        } else {
            countDownTimer.cancel();
            timerRunning = false;
            btnStart.setText("Start");
            btnPause.setVisibility(View.INVISIBLE);
        }
    }

    private void pauseTimer() {
        if (timerRunning) {
            countDownTimer.cancel();
            timerRunning = false;
            btnPause.setText("Resume");
        } else {
            countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    timeLeftInMillis = millisUntilFinished;
                    updateTimer();
                }

                @Override
                public void onFinish() {
                    timerRunning = false;
                    btnStart.setText("Start");
                    btnPause.setVisibility(View.INVISIBLE);
                }
            }.start();

            timerRunning = true;
            btnPause.setText("Pause");
        }
    }

    private void updateTimer() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        tvTimer.setText(timeLeftFormatted);
    }
}