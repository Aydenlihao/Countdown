package com.example.haoli.countdown;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.haoli.countdown.view.CircleProgress;
import com.example.haoli.countdown.view.CustomizeDialog;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.circle_progress)
    CircleProgress mCircleProgress;
    @BindView(R.id.set_time)
    Button btn;
    @BindView(R.id.order)
    TextView order;
    private Random mRandom;
    private CustomizeDialog dialog;
    private long millisecond;
    private CountDownTimer timer;
    private String minute;
    private String second;
    private boolean isStrat = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRandom = new Random();
        dialog = new CustomizeDialog(this);
        mCircleProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("value", mRandom.nextFloat() + "");
                Log.e("MaxValue", mCircleProgress.getMaxValue() + "");
                mCircleProgress.setValue(mRandom.nextFloat() * mCircleProgress.getMaxValue());
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.showDialog();
            }
        });
        dialog.setOnCustomClickListener(new CustomizeDialog.onChangeCustomTime() {
            @Override
            public void onChangTime(int ms) {
                millisecond = ms;
                Log.e("ms", ms + "");
                dialog.hideDialog();
                OnStartTime();
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timer !=null){
                   if (isStrat){
                       OnStartTime();
                   }else {
                       timer.cancel();
                   }
                   isStrat =!isStrat;
                }
            }
        });

    }

    private void OnStartTime() {
        if (timer != null){
            timer.cancel();
            timer = null;
        }
        timer = new CountDownTimer(millisecond, 1) {

            @Override
            public void onTick(long millisUntilFinished) {
                millisecond = millisUntilFinished;
                int mm = (int) (millisUntilFinished / 1000 / 60 % 60);
                int ss = (int) millisUntilFinished / 1000 % 60;
                String ms = millisUntilFinished + "";
                char[] stringArr = ms.toCharArray();
                String mss = "";
                if (stringArr.length > 3) {
                    mss = stringArr[stringArr.length - 3] + stringArr[stringArr.length - 2] + "";
                } else if (stringArr.length == 2) {
                    mss = "0" + stringArr[stringArr.length - 2];
                } else {
                    mss = "00";
                }
                if (mm < 10){
                    minute = "0"+mm;
                }else {
                    minute = ""+mm;
                }
                if (ss < 10){
                    second = "0"+ss;
                }else {
                    second = ""+ss;
                }
                Log.e("millisUntilFinished",millisUntilFinished+"");
                order.setText(minute + ":" + second + ":" + mss);
            }

            @Override
            public void onFinish() {

            }
        };
        timer.start();
    }
}
