package com.example.wangqian.happinesshunter.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.wangqian.happinesshunter.R;

import java.util.Calendar;
import java.util.TimeZone;

public class AlarmActivity extends Activity {

	private static final String TAG = AlarmActivity.class.getSimpleName();
	
	private TimePicker mTimePicker;
	private Button button;

	private int mHour = -1;
	private int mMinute = -1;

	public static final long DAY = 1000L * 60 * 60 * 24;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alarm);

		// 获取当前时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
		if(mHour == -1 && mMinute == -1) {
			mHour = calendar.get(Calendar.HOUR_OF_DAY);
			mMinute = calendar.get(Calendar.MINUTE);
		}

		mTimePicker = (TimePicker)findViewById(R.id.timePicker);
		mTimePicker.setCurrentHour(mHour);
		mTimePicker.setCurrentMinute(mMinute);
		mTimePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
			
			@Override
			public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
				
				mHour = hourOfDay;
				mMinute = minute;
			}
		});

		button = (Button)findViewById(R.id.repeating_button);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent(AlarmActivity.this, AlarmReceiver.class);
				PendingIntent sender = PendingIntent.getBroadcast(AlarmActivity.this, 0, intent, 0);

	            long firstTime = SystemClock.elapsedRealtime();
	            long systemTime = System.currentTimeMillis();

	            Calendar calendar = Calendar.getInstance();
			 	calendar.setTimeInMillis(System.currentTimeMillis());
			 	calendar.setTimeZone(TimeZone.getTimeZone("GMT+8"));
			 	calendar.set(Calendar.MINUTE, mMinute);
			 	calendar.set(Calendar.HOUR_OF_DAY, mHour);
			 	calendar.set(Calendar.SECOND, 0);
			 	calendar.set(Calendar.MILLISECOND, 0);

			 	long selectTime = calendar.getTimeInMillis();	

			 	if(systemTime > selectTime) {
			 		Toast.makeText(AlarmActivity.this, "设置的时间小于当前时间", Toast.LENGTH_SHORT).show();
			 		calendar.add(Calendar.DAY_OF_MONTH, 1);
			 		selectTime = calendar.getTimeInMillis();
			 	}
			 	long time = selectTime - systemTime;
		 		firstTime += time;

	            AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
	            manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
	                            firstTime, (1000 * 60 * 60 * 24),  sender);
                Toast.makeText(AlarmActivity.this, "设置成功! ", Toast.LENGTH_LONG).show();
				finish();

			}
		});


	}


}
