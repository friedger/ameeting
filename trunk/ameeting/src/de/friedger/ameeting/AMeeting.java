package de.friedger.ameeting;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.Contacts.People;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TimePicker;

public class AMeeting extends ListActivity {
	private Button _startTimeButton;
	private Button _endTimeButton;
	private Button _dateButton;

	private int _year;
	private int _month;
	private int _day;
	private int _startHour;
	private int _startMinute;
	private int _endHour;
	private int _endMinute;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);

		_dateButton = (Button) findViewById(R.id.date);
		_startTimeButton = (Button) findViewById(R.id.startTime);
		_endTimeButton = (Button) findViewById(R.id.endTime);

		Calendar c = Calendar.getInstance();
		_year = c.get(Calendar.YEAR);
		_month = c.get(Calendar.MONTH);
		_day = c.get(Calendar.DAY_OF_MONTH);
		_startHour = c.get(Calendar.HOUR);
		_startMinute = c.get(Calendar.MINUTE);
		_endHour = _startHour + 1;
		_endMinute = _startMinute;

		_dateButton.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				new DatePickerDialog(AMeeting.this, _dateSetListener, _year,
						_month, _day, Calendar.SUNDAY).show();
			}
		});

		_startTimeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				new TimePickerDialog(AMeeting.this, _startTimeSetListener,
						"start time", _startHour, _startMinute, false).show();

			}
		});

		_endTimeButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				new TimePickerDialog(AMeeting.this, _endTimeSetListener,
						"end time", _startHour, _startMinute, false).show();

			}
		});

		fillData();
	}

	private void fillData() {
		// Get a cursor with all people
		Cursor c = getContentResolver().query(People.CONTENT_URI,
				new String[] { People._ID, People.NAME },
				"people._id IN (1,2,3)", null,
				Contacts.People.DEFAULT_SORT_ORDER);
		startManagingCursor(c);

		ListAdapter adapter = new SimpleCursorAdapter(this,
		// Use a template that displays a text view
				R.layout.guest_row,
				// Give the cursor to the list adapter
				c,
				// Map the NAME column in the people database to...
				new String[] { People.NAME },
				// The "text1" view defined in the XML template
				new int[] { R.id.guest });
		setListAdapter(adapter);
	}

	private void updateButtonTexts() {
		_dateButton.setText(new StringBuilder().append(_month).append("-")
				.append(_day).append("-").append(_year).append(" "));
		_startTimeButton.setText(new StringBuilder().append(pad(_startHour))
				.append(":").append(pad(_startMinute)));
		_endTimeButton.setText(new StringBuilder().append(pad(_endHour))
				.append(":").append(pad(_endMinute)));
	}

	private DatePicker.OnDateSetListener _dateSetListener = new DatePicker.OnDateSetListener() {

		public void dateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			_year = year;
			_month = monthOfYear;
			_day = dayOfMonth;
			updateButtonTexts();
		}
	};

	private TimePicker.OnTimeSetListener _startTimeSetListener = new TimePicker.OnTimeSetListener() {

		public void timeSet(TimePicker view, int hourOfDay, int minute) {
			_startHour = hourOfDay;
			_startMinute = minute;
			updateButtonTexts();
		}
	};

	private TimePicker.OnTimeSetListener _endTimeSetListener = new TimePicker.OnTimeSetListener() {

		public void timeSet(TimePicker view, int hourOfDay, int minute) {
			_endHour = hourOfDay;
			_endMinute = minute;
			updateButtonTexts();
		}
	};

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

}