package com.example.easynote.pojos;

import android.icu.text.TimeZoneFormat;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.io.Serializable;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by SEVAK on 09.05.2017.
 */

public class Note implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    private String title;
    private String description;
    private Calendar createDate;
    private int color;
    private boolean important;
    private Calendar alarmDate;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Note() {
        createDate = Calendar.getInstance();
        alarmDate = Calendar.getInstance();

        TimeZone timeZone = createDate.getTimeZone();
        timeZone.setRawOffset(4);
        createDate.setTimeZone(timeZone);
        alarmDate.setTimeZone(timeZone);
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getCreateDate() {
        SimpleDateFormat dateFormat;
        if(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) > createDate.get(Calendar.DAY_OF_MONTH)) {
//            Log.d("testt", "Note -- today -- " + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + " --- " + createDate.get(Calendar.DAY_OF_MONTH));
            dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        }
        else
            dateFormat = new SimpleDateFormat("HH:mm");
        String string = dateFormat.format(createDate.getTime());
//        Log.d("testt", "Note -- " + string);
        return string;
    }

    public int getColor() {
        return color;
    }

    public boolean isImportant() {
        return important;
    }

    public Calendar getAlarmDate() {
        return alarmDate;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String getAlarmDateInString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return dateFormat.format(alarmDate.getTime());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void changeUpdateDate() {
        createDate = Calendar.getInstance();
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", createDate=" + createDate +
                ", color=" + color +
                ", important=" + important +
                '}';
    }
}
