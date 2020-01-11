package com.example.haoli.countdown.view;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.haoli.countdown.R;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

public class CustomizeDialog extends AlertDialog {

    private AlertDialog TimeDialog;
    private List list;
    private WheelView minutes;
    private WheelView seconds;
    private TextView time;
    private int ms;
    private onChangeCustomTime mListener;

    public CustomizeDialog(Context context) {
        super(context);
        AlertDialog.Builder Builder = new AlertDialog.Builder(context);
        TimeDialog = Builder.create();
        list = new ArrayList();
        for (int i = 0; i < 60; i++) {
            if (i < 10) {
                list.add("0" + i);
            } else {
                list.add(i + "");
            }
        }
        init(context);
    }

    public CustomizeDialog(Context context, int themeResId) {
        super(context, themeResId);
        AlertDialog.Builder Builder = new AlertDialog.Builder(context);
        TimeDialog = Builder.create();
        init(context);

    }

    private void init(Context context) {
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_customize, null);
        minutes = (WheelView) dialogView.findViewById(R.id.minutes);
        seconds = (WheelView) dialogView.findViewById(R.id.seconds);
        time = (TextView) dialogView.findViewById(R.id.time);
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("minutes", list.get(minutes.getCurrentPosition()) + "");
                Log.e("seconds", list.get(seconds.getCurrentPosition()) + "");
                ms = (minutes.getCurrentPosition() * 60 + seconds.getCurrentPosition()) * 1000;
                mListener.onChangTime(ms);
            }
        });
        minutes.setWheelAdapter(new ArrayWheelAdapter(context));
        seconds.setWheelAdapter(new ArrayWheelAdapter(context));

        minutes.setSkin(WheelView.Skin.Common);
        seconds.setSkin(WheelView.Skin.Common);

        minutes.setWheelData(list);
        seconds.setWheelData(list);
        TimeDialog.setView(dialogView);

    }

    public void showDialog() {
        TimeDialog.show();
    }
    public void hideDialog(){
        TimeDialog.hide();
    }

    public interface onChangeCustomTime {
        void onChangTime(int ms);
    }

    public void setOnCustomClickListener(onChangeCustomTime listener){
        this.mListener = listener;
    }
}
