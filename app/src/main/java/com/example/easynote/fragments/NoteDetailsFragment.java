package com.example.easynote.fragments;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.easynote.App;
import com.example.easynote.R;
import com.example.easynote.activities.CreateActivity;
import com.example.easynote.pojos.Note;

import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoteDetailsFragment extends Fragment implements View.OnClickListener {
    public static final String KEY_TITLE = "com.example.easynote.fragments.NoteDetailsFragment_Note";
    public static final int REQUEST_CODE = 143;
    private static final String KEY_NOTE_EXTRA = "NoteDetailsFragment_note";

    private TextView title;
    private TextView description;
    private View color;
    private View isImportant;
    private TextView alarmDate;
    private TextView date;
    private View edit;
    private Note note;

    public static NoteDetailsFragment newInstance(Note note){
        NoteDetailsFragment fragment = new NoteDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_NOTE_EXTRA, note);
        fragment.setArguments(bundle);

        return fragment;
    }

    public NoteDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        note = (Note) getArguments().getSerializable(KEY_NOTE_EXTRA);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_note_details, container, false);

        title = (TextView) view.findViewById(R.id.noteDetailFragment_title);
        description = (TextView) view.findViewById(R.id.noteDetailFragment_description);
        color = view.findViewById(R.id.noteDetailFragment_color);
        isImportant = view.findViewById(R.id.noteDetailFragment_isImportant);
        date = (TextView) view.findViewById(R.id.noteDetailFragment_date);
        edit = view.findViewById(R.id.noteDetailFragment_edit);
        alarmDate = (TextView) view.findViewById(R.id.noteDetailFragment_alarmDate);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setData();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setData() {
        title.setText(note.getTitle());
        description.setText(note.getDescription());
        color.setBackgroundColor(note.getColor());
        if(note.isImportant()) isImportant.setVisibility(View.VISIBLE);
        else isImportant.setVisibility(View.GONE);
        date.setText(note.getCreateDate());
        edit.setOnClickListener(this);
        if(note.getAlarmDate().getTimeInMillis() > System.currentTimeMillis()) {
            alarmDate.setVisibility(View.VISIBLE);
            alarmDate.setText("Alarm worked in " + note.getAlarmDateInString());
        }
        else alarmDate.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.noteDetailFragment_edit:
                Intent intent = new Intent(App.getInstance(), CreateActivity.class);
                intent.putExtra(KEY_TITLE, note);
                startActivityForResult(intent, REQUEST_CODE);
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if((requestCode == REQUEST_CODE) && (resultCode == getActivity().RESULT_OK)) {
            note = (Note) data.getSerializableExtra(CreateActivity.KEY_TITLE);
            setData();
        }
    }

    public Note getNote() {
        return note;
    }
}