package pt.isec.gps1718.g34.healthassistant.Create;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import pt.isec.gps1718.g34.healthassistant.Base.NotificationIntervalPrescription;
import pt.isec.gps1718.g34.healthassistant.Base.Prescription;
import pt.isec.gps1718.g34.healthassistant.R;

public class CreatePrescription extends AppCompatActivity {

    Date tmpDate = null;
    Spinner dynamicSpinner = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_prescription);


        // Initialize
        final SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                "Starting date for the prescription",
                "Ok",
                "Cancel"
        );

        Button addPrescription_Ok = findViewById(R.id.addPrescription_btnOk);
        Button addPrescription_Cancel = findViewById(R.id.addPrescription_btnCancel);

        addPrescription_Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addPrescription_Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText_Nome = findViewById(R.id.addPrescription_editText_Nome);
                EditText editText_Dosagem = findViewById(R.id.addPrescription_editText_Dosagem);

                CheckBox isAlarmChecked = findViewById(R.id.addPrescription_isAlarmChecked);
                CheckBox isNotificationChecked = findViewById(R.id.addPrescription_isNotificationChecked);

                TextView textView_DateTimePicker = findViewById(R.id.addPrescription_textView_DateTimePicker);
                Spinner spinner_TimeInterval = findViewById(R.id.addPrescription_dropDownList_TimeInterval);


                Prescription newPrescription = new Prescription(
                        -1,
                        editText_Nome.getText().toString(),
                        editText_Dosagem.getText().toString(),
                        isAlarmChecked.isChecked(),
                        isNotificationChecked.isChecked(),
                        tmpDate,
                        (NotificationIntervalPrescription)spinner_TimeInterval.getSelectedItem()
                );

                Snackbar.make(view,
                    "You pressed ok", Snackbar.LENGTH_LONG)
                    .setAction("Action", null)
                    .show();



                // TODO: chamar data manager -> adicionar à lista
                // TODO: processar alarmes
            }
        });


        findViewById(R.id.addPrescription_textView_DateTimePicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTimeDialogFragment.show(getSupportFragmentManager(), null);
            }
        });


        dynamicSpinner = findViewById(R.id.addPrescription_dropDownList_TimeInterval);

        ArrayList<String> IntervalosString = new ArrayList<>();
        for (int i = 0; i < NotificationIntervalPrescription.values().length; i++)
            IntervalosString.add(NotificationIntervalPrescription.values()[i].toString());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, IntervalosString);

        dynamicSpinner.setAdapter(adapter);
        /*dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });*/


        // Assign values
        dateTimeDialogFragment.startAtCalendarView();
        dateTimeDialogFragment.set24HoursMode(true);
        dateTimeDialogFragment.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
        dateTimeDialogFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());


        // Define new day and month format
        try {
            dateTimeDialogFragment
                    .setSimpleDateMonthAndDayFormat(new SimpleDateFormat("dd MMMM", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e("HealthAssistant", e.getMessage());
        }

        // Set listener
        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                tmpDate = date;
                TextView textView_DateTimePicker = findViewById(R.id.addPrescription_textView_DateTimePicker);
                textView_DateTimePicker.setText(date.toString());
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Ignorar?
            }
        });
    }
}
