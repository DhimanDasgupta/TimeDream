package com.dhiman.timedream;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.service.dreams.DreamService;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


/**
 * This class is a sample implementation of a DreamService. When activated, it
 * displays the current time
 * <p/>
 * Daydreams are only available on devices running API v17+.
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class TimeDreamService extends DreamService {
    private static Calendar sCalendar;
    private static SimpleDateFormat sSimpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");

    private LinearLayout mLinearLayout;

    private TextView mTextViewHour;
    private TextView mTextViewColonOne;
    private TextView mTextViewMinute;
    private TextView mTextViewColonTwo;
    private TextView mTextViewSecond;

    private TextView mTextViewDate;

    private final Handler mHandlerUpdate = new Handler();
    private final Runnable mRunnableUpdate = new Runnable() {
        public void run() {
            updateTime();

            mHandlerUpdate.postDelayed(mRunnableUpdate, 1000);
        }
    };

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        setFullscreen(true);
        setInteractive(false);
        setScreenBright(false);

        setContentView(R.layout.time_dream);

        mLinearLayout = (LinearLayout) findViewById(R.id.dream_service_linear_layout);

        mTextViewHour = (TextView) findViewById(R.id.dream_service_time_hour);
        mTextViewColonOne = (TextView) findViewById(R.id.dream_service_colon_one);
        mTextViewMinute = (TextView) findViewById(R.id.dream_service_time_minute);
        mTextViewColonTwo = (TextView) findViewById(R.id.dream_service_colon_two);
        mTextViewSecond = (TextView) findViewById(R.id.dream_service_time_second);
        mTextViewDate = (TextView) findViewById(R.id.dream_service_date);
    }

    @Override
    public void onDreamingStarted() {
        super.onDreamingStarted();

        mHandlerUpdate.post(mRunnableUpdate);
    }

    @Override
    public void onDreamingStopped() {
        super.onDreamingStopped();

        mHandlerUpdate.removeCallbacks(mRunnableUpdate);
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();


    }

    private void updateTime() {
        sCalendar = Calendar.getInstance();

        if (mTextViewHour != null) {
            mTextViewHour
                    .setText(sCalendar.get(Calendar.HOUR_OF_DAY) < 10 ? "0"
                            + String.valueOf(sCalendar
                            .get(Calendar.HOUR_OF_DAY)) : String
                            .valueOf(sCalendar.get(Calendar.HOUR_OF_DAY)));
        }

        if (mTextViewColonOne != null && mTextViewColonTwo != null) {
            if (sCalendar.get(Calendar.SECOND) % 2 == 0) {
                if (mTextViewColonOne != null) {
                    mTextViewColonOne.setVisibility(View.VISIBLE);
                    mTextViewColonTwo.setVisibility(View.VISIBLE);
                }
            } else {
                if (mTextViewColonOne != null) {
                    mTextViewColonOne.setVisibility(View.INVISIBLE);
                    mTextViewColonTwo.setVisibility(View.INVISIBLE);
                }
            }
        }

        if (mTextViewMinute != null) {
            mTextViewMinute.setText(sCalendar.get(Calendar.MINUTE) < 10 ? "0"
                    + String.valueOf(sCalendar.get(Calendar.MINUTE)) : String
                    .valueOf(sCalendar.get(Calendar.MINUTE)));
        }

        if (mTextViewSecond != null) {
            mTextViewSecond.setText(sCalendar.get(Calendar.SECOND) < 10 ? "0"
                    + String.valueOf(sCalendar.get(Calendar.SECOND)) : String
                    .valueOf(sCalendar.get(Calendar.SECOND)));
        }

        if (mTextViewDate != null) {
            mTextViewDate.setText(sSimpleDateFormat.format(sCalendar.getTime()));
        }
    }
}
