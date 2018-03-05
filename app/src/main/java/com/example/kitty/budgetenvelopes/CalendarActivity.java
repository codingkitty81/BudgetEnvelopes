package com.example.kitty.budgetenvelopes;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.CalendarView;

public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendar_view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        calendar_view = (CalendarView) findViewById(R.id.calendar_view);

        calendar_view.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                month += 1;
                String date = month + "/" + day + "/" + year;
                Intent intent = new Intent();
                intent.putExtra("date", date);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
