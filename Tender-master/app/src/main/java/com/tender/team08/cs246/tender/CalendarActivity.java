package com.tender.team08.cs246.tender;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.TimeZone;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.provider.CalendarContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class CalendarActivity extends AppCompatActivity {

    private int _year;
    private int _month;
    private int _dayOfMonth;
    private String _name;
    private String _address;
    private static final int REQUEST_CALENDAR = 1;

    //setting the time zone to CST
    private TimeZone cst = TimeZone.getTimeZone("America/Chicago");
    //creating a calendar to reference the current date and time
    private GregorianCalendar currentDate = new GregorianCalendar(cst);

    private String calendarIds;

    private CalendarView calendar;
    private Button query;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        calendar = (CalendarView) findViewById(R.id.calendar);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                _year = year;
                _month = month;
                _dayOfMonth = dayOfMonth;
            }
        });

        _name = getIntent().getStringExtra("first") + " " + getIntent().getStringExtra("last");
        _address = getIntent().getStringExtra("street") + " " + getIntent().getStringExtra("city")
                + ", " + getIntent().getStringExtra("state") + " " + getIntent().getStringExtra("zip");
    }

    private View.OnClickListener queryOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            queryCalendar(v);
        }
    };

    public void queryCalendar(View view) {
        // Check if the Calendar permission is available.
        if (checkSelfPermission(Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            CalendarService.readCalendar(CalendarActivity.this, 7, 5);
        } else {
            // Permission not granted
            // Showing the user why we need access.
            if (shouldShowRequestPermissionRationale(Manifest.permission.READ_CALENDAR)) {
                Toast.makeText(this, "Calendar permission is needed to read events from the calendar.", Toast.LENGTH_SHORT).show();
            }
            //Request the Permission
            requestPermissions(new String[]{Manifest.permission.READ_CALENDAR}, REQUEST_CALENDAR);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CALENDAR) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                CalendarService.readCalendar(CalendarActivity.this, 7, 5);
            } else {
                Toast.makeText(this, "Permission was not granted", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    public void onEventClick(View view) {

        //creates a new calendar based of the date selected in the calendarView, with the current date time of day
        GregorianCalendar calendar = new GregorianCalendar(_year, _month, _dayOfMonth, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE));
        Log.d("onEventClick", "Create new calendar: Day: " + calendar.get(Calendar.DAY_OF_MONTH) + " Month: " + calendar.get(Calendar.MONTH) + " Year: " + calendar.get(Calendar.YEAR));

        //If no date was selected set the vars to the current day
        if(_year == 0 && _month == 0 && _dayOfMonth == 0) {
            calendar = currentDate;
            Log.d("onEventClick", "Setting CurrentDate: Day: " + calendar.get(Calendar.DAY_OF_MONTH) + " Month: " + calendar.get(Calendar.MONTH) + " Year: " + calendar.get(Calendar.YEAR));
        }


        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType("vnd.android.cursor.item/event");
        intent.putExtra(CalendarContract.Events.TITLE, _name + "'s Appointment");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION, _address);
        intent.putExtra(CalendarContract.Events.DESCRIPTION, "Massage for " + _name);

        //TODO Tony: get Client's email address to send appointment invite, don't think this is an option.

        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, calendar.getTimeInMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, (calendar.getTimeInMillis() + 60 * 60 * 1000)); //Sets end time to 1 hour ahead of start time.

        intent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);

        //intent.putExtra(CalendarContract.Events.RRULE, "FREQ=WEEKLY;COUNT=11;WKST=SU;BYDAY=TU,TH"); //recurring event

        intent.putExtra(CalendarContract.Events.ACCESS_LEVEL, CalendarContract.Events.ACCESS_PRIVATE);
        intent.putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

        startActivity(intent);

    }


    public void editListener(View view) {

    }

    public void addListener(View view) {
        
    }
}
