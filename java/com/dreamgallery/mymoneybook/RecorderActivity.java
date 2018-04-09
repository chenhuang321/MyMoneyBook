package com.dreamgallery.mymoneybook;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamgallery.mymoneybook.feature.MyDB;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RecorderActivity extends Activity implements View.OnClickListener {

    private Spinner mspinner_type;
    private EditText  medittext_fee, medittext_remarks;
    private TextView  medittext_time;
    private Calendar calendar;
    private String content_type, content_select_group;
    private ArrayList<String> Data = new ArrayList<>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recorder);
        Button mbutton_sure, mbutton_cancel;
        RadioGroup mRadiogroup;

        medittext_time    = findViewById(R.id.edit_text_time);
        medittext_fee     = findViewById(R.id.editText_fee);
        medittext_remarks = findViewById(R.id.editText_remarks);
        mbutton_sure      = findViewById(R.id.plan_sure);
        mbutton_cancel    = findViewById(R.id.button_cancel);
        mspinner_type     = findViewById(R.id.spinner_type);
        mRadiogroup       = findViewById(R.id.radioGroup);
        ImageButton imageButtonBack = findViewById(R.id.imageButtonBack);

        medittext_fee.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        //Set the adapter for next step
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.payment_type, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Bind the data and spinner together
        mspinner_type.setAdapter(adapter);

        medittext_time.setOnClickListener(this);
        imageButtonBack.setOnClickListener(this);
        mspinner_type.setOnItemSelectedListener(listener);
        mRadiogroup.setOnCheckedChangeListener(grouplistener);
        mbutton_sure.setOnClickListener(this);
        mbutton_cancel.setOnClickListener(this);
    }

    RadioGroup.OnCheckedChangeListener grouplistener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup arg0, int arg1) {
            int radioButtonId = arg0.getCheckedRadioButtonId();
            content_select_group = (((RadioButton) findViewById(radioButtonId)).getText()).toString();
        }
    };
    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            content_type = mspinner_type.getItemAtPosition(pos).toString();
        }

        public void onNothingSelected(AdapterView<?> parent) {
            // Another interface callback
        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.plan_sure:
                Data.clear();
                Data.add(content_type);
                Data.add(medittext_time.getText().toString());
                Data.add(medittext_fee.getText().toString());
                Data.add(medittext_remarks.getText().toString());
                Data.add(content_select_group);
                WriteData(Data);
                break;
            case R.id.button_cancel:
                medittext_time.setText(null);
                medittext_fee.setText(null);
                medittext_remarks.setText(null);
                break;
            case R.id.imageButtonBack:
                this.finish();
                break;
            case R.id.edit_text_time:
                calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(
                        this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                calendar.set(year, monthOfYear, dayOfMonth);
                                medittext_time.setText(DateFormat.format("yyy-MM-dd", calendar));
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                );
                dialog.show();
                break;
            default:
                break;
        }

    }

    /**
     *
     * @param data Waiting to be written
     */
    public void WriteData(ArrayList<String> data) {
        MyDB mDatabase = new MyDB(getApplicationContext());
        String type = data.get(0);
        String time = data.get(1);
        String fee  = data.get(2);
        String note = data.get(3);
        String budge = data.get(4);
        mDatabase.createRecords(type, time, fee, note, budge);
        final String regExp = "[0-9]+([.][0-9]{1,2})?";
        final Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(fee);
        if(type == null || time == null || note == null || budge == null) {
            Toast.makeText(
                    getApplicationContext(), R.string.error_incomplete_information, Toast.LENGTH_SHORT
            ).show();
        }
        else if (!matcher.matches()) {
            Toast.makeText(
                    getApplicationContext(), R.string.error_invalid_fee_format, Toast.LENGTH_SHORT
            ).show();
        }
        else {
            Toast.makeText(
                    getApplicationContext(), R.string.success_saved, Toast.LENGTH_SHORT
            ).show();
            Intent intentBackToAccount = new Intent(this, MainActivity.class);
            startActivity(intentBackToAccount);
        }
    }
}

