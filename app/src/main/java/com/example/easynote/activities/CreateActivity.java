package com.example.easynote.activities;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.easynote.App;
import com.example.easynote.R;
import com.example.easynote.fragments.NoteDetailsFragment;
import com.example.easynote.pojos.Note;
import com.example.easynote.utils.AlarmReceiver;
import com.example.easynote.utils.note.FileNoteStorage;
import com.example.easynote.utils.note.NoteStorage;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import petrov.kristiyan.colorpicker.ColorPicker;

public class CreateActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_TITLE = "com.example.easynote.activities.CreateActivity_note";
    public static final String ALARM_KEY_TITLE = "com.example.easynote.activities.CreateActivity_Alarm";
    private static final int DEFAULT_COLOR = Color.GREEN;

    private EditText title;
    private EditText description;
    private CheckBox isImportant;
    private View note_color;
    private Button save;
    private Note note;
    private NoteStorage noteStorage;
    private TextView alarmDate;
    private Button setAlarm;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        title = (EditText) findViewById(R.id.createNote_Title);
        description = (EditText) findViewById(R.id.createNote_Description);
        isImportant = (CheckBox) findViewById(R.id.createNote_isImportant);
        note_color = findViewById(R.id.createNote_ColorPicker);
        alarmDate = (TextView) findViewById(R.id.createNote_alarmDate);
        setAlarm = (Button) findViewById(R.id.createNote_setAlarm);
        save = (Button) findViewById(R.id.createNote_save_btn);
        note_color.setBackgroundColor(DEFAULT_COLOR);
        note_color.setOnClickListener(this);
        setAlarm.setOnClickListener(this);
        save.setOnClickListener(this);

        noteStorage = new FileNoteStorage();

        setNote();
    }

//    Set Note
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setNote(){
        Note note = (Note) getIntent().getSerializableExtra(NoteDetailsFragment.KEY_TITLE);
        if(note != null) {
            title.setText(note.getTitle());
            description.setText(note.getDescription());
            isImportant.setChecked(note.isImportant());
            note_color.setBackgroundColor(note.getColor());
            if(note.getAlarmDate().getTimeInMillis() > System.currentTimeMillis()) {
                alarmDate.setText(note.getAlarmDateInString());
            }
            this.note = note;
        } else this.note = new Note();
    }

//    ONClick
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createNote_save_btn:
                if(note.getId() == 0) {
                    createNote();
                } else {
                    updateNote();
                }
                createAlarm();
                break;
            case R.id.createNote_ColorPicker:
                openColorPicker();
                break;
            case R.id.createNote_setAlarm:
                openDatePickerDialog();
        }
    }

//    Create Note
    private void createNote() {
        if(saveNote()) {
            noteStorage.createNote(note, new NoteStorage.NoteListener() {
                @Override
                public void onNote(Note note) {
                    Intent intent = new Intent();
                    intent.putExtra(KEY_TITLE, note);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }

//    Update Note
    private void updateNote() {
        if(saveNote()) {
            noteStorage.updateNote(note, new NoteStorage.NoteListener() {
                @Override
                public void onNote(Note note) {
                    Intent intent = new Intent();
                    intent.putExtra(KEY_TITLE, note);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            });
        }
    }

//    Save Note
    private boolean saveNote() {
        if(title.getText().length() == 0) {
            Toast.makeText(App.getInstance(), "Title can not be empty", Toast.LENGTH_LONG).show();
            return false;
        }
        if(description.getText().length() == 0) {
            Toast.makeText(App.getInstance(), "Description can not be empty", Toast.LENGTH_LONG).show();
            return false;
        }
        note.setTitle(title.getText().toString());
        note.setDescription(description.getText().toString());
        note.setImportant(isImportant.isChecked());
        if(note.getColor() == 0) note.setColor(DEFAULT_COLOR);
        return true;
    }


//    ColorPicker
    private void openColorPicker() {
        ColorPicker colorPicker = new ColorPicker(this);
        colorPicker.show();
        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position, int color) {
                note.setColor(color);
                note_color.setBackgroundColor(color);
            }
            @Override
            public void onCancel() {};
        });
    }

//    DatePickerDialog
    @RequiresApi(api = Build.VERSION_CODES.N)
    void openDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                note.getAlarmDate().set(Calendar.YEAR, year);
                note.getAlarmDate().set(Calendar.MONTH, month);
                note.getAlarmDate().set(Calendar.DAY_OF_MONTH, dayOfMonth);
                openTimePickerDialog();
            }
        }, year, month, dayOfMonth);
        dialog.show();
    }

//    TimePickerDialog
    @RequiresApi(api = Build.VERSION_CODES.N)
    void openTimePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                note.getAlarmDate().set(Calendar.HOUR_OF_DAY, hourOfDay);
                note.getAlarmDate().set(Calendar.MINUTE, minute);
                note.getAlarmDate().set(Calendar.SECOND, 0);
                alarmDate.setText(note.getAlarmDateInString());
            }
        }, hourOfDay, minute, true);
        dialog.show();
    }

//    Alarm
    @RequiresApi(api = Build.VERSION_CODES.N)
    void createAlarm() {
        if(note.getAlarmDate().getTimeInMillis() > System.currentTimeMillis()) {
            Intent intent = new Intent(App.getInstance(), AlarmReceiver.class);
            intent.putExtra(ALARM_KEY_TITLE, note.getTitle());
            AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
            manager.set(AlarmManager.RTC_WAKEUP,
                    note.getAlarmDate().getTimeInMillis(),
                    PendingIntent.getBroadcast(App.getInstance(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT));
        }
    }
}
