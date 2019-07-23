package rashmi.umkc.edu.samplecalendarapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.displaycalendareventintent.R;

import java.util.Calendar;

public class CalendarActivity extends AppCompatActivity {
    DatePicker picker;
    TextView dateView;
    String dateText;
    int month;
    int day;
    int year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        dateView = (TextView)findViewById(R.id.date);

        Calendar calendar = Calendar.getInstance();
        month = (calendar.get(Calendar.MONTH) + 1);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        year = calendar.get(Calendar.YEAR);
        dateText = month + "/" + day + "/" + year;
        dateView.setText(dateText);

        picker = (DatePicker) findViewById(R.id.picker);
        picker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int pickerYear, int monthOfYear, int dayOfMonth) {
                month = monthOfYear + 1;
                day = dayOfMonth;
                year = pickerYear;
                dateText = month + "/" + day + "/" + year;
                dateView.setText(dateText);
            }
        });

        Button insert = (Button) findViewById(R.id.insert);
        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_INSERT,
                        CalendarContract.Events.CONTENT_URI);
                // Add the calendar event details
                intent.putExtra(CalendarContract.Events.TITLE, "Launch!");
                intent.putExtra(CalendarContract.Events.DESCRIPTION,
                        "Learn Java Android Coding");
                intent.putExtra(CalendarContract.Events.EVENT_LOCATION,
                        "UMKC.com");
                Calendar startTime = Calendar.getInstance();
                startTime.set(year, (month - 1), day);
                intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        startTime.getTimeInMillis());
                intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
                // Use the Calendar app to add the new event.
                startActivity(intent);
            }
        });

    }
}
