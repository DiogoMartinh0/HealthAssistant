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
import android.widget.Toast;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import pt.isec.gps1718.g34.healthassistant.Base.Prescription;
import pt.isec.gps1718.g34.healthassistant.DataManager;
import pt.isec.gps1718.g34.healthassistant.R;

public class CreatePrescription extends AppCompatActivity {

    Date tmpDate = null;
    Spinner dynamicSpinner = null;
    DataManager dm = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_prescription);
        dm = (DataManager)getIntent().getSerializableExtra("DataManager");

        // Initialize
        final SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                "Starting date for the prescription",
                "Ok",
                "Cancel"
        );

        EditText editText_Nome = findViewById(R.id.addPrescription_editText_Nome);
        EditText editText_Dosagem = findViewById(R.id.addPrescription_editText_Dosagem);
        editText_Nome.setText("Medicamento");
        editText_Dosagem.setText("Dosagem");


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
                TextView textView_DateTimePicker = findViewById(R.id.addPrescription_textView_DateTimePicker);
                Spinner spinner_TimeInterval = findViewById(R.id.addPrescription_dropDownList_TimeInterval);

                boolean erro = false;


                if (editText_Nome.getText().length() <= 2 || editText_Nome.getText().length() >= 15){
                    Toast.makeText(getApplicationContext(), "Invalid name", Toast.LENGTH_SHORT).show();
                    erro = true;
                }

                if (editText_Dosagem.getText().length() <= 2 || editText_Dosagem.getText().length() >= 10){
                    Toast.makeText(getApplicationContext(), "Invalid dosage", Toast.LENGTH_SHORT).show();
                    erro = true;
                }

                if (tmpDate == null){
                    Toast.makeText(getApplicationContext(), "Invalid date", Toast.LENGTH_SHORT).show();
                    erro = true;
                }

                if (!erro){
                    Prescription newPrescription = new Prescription(
                            -1,
                            editText_Nome.getText().toString(),
                            editText_Dosagem.getText().toString(),
                            false,
                            false,
                            tmpDate,
                            spinner_TimeInterval.getSelectedItem().toString()
                    );

                    dm.AddPrescription(newPrescription);
                    dm.savePrescritionsToFile();
                    finish();

                    // TODO: processar alarmes
                }
            }
        });


        findViewById(R.id.addPrescription_textView_DateTimePicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTimeDialogFragment.show(getSupportFragmentManager(), null);
            }
        });


        dynamicSpinner = findViewById(R.id.addPrescription_dropDownList_TimeInterval);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                new String[]{
                        "Every 4 Hours",
                        "Every 8 Hours",
                        "Every 12 Hours",
                        "Every 16 Hours",
                        "Every 24 Hours",
                        "Every 48 Hours",
                        "Every 72 Hours"
                }
                );

        dynamicSpinner.setAdapter(adapter);

        // Assign values
        dateTimeDialogFragment.startAtCalendarView();
        dateTimeDialogFragment.set24HoursMode(true);
        dateTimeDialogFragment.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
        dateTimeDialogFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());


        // Define new day and month format
        try {
            dateTimeDialogFragment
                    .setSimpleDateMonthAndDayFormat(new SimpleDateFormat("dd MM", Locale.getDefault()));
        } catch (SwitchDateTimeDialogFragment.SimpleDateMonthAndDayFormatException e) {
            Log.e("HealthAssistant", e.getMessage());
        }

        // Set listener
        dateTimeDialogFragment.setOnButtonClickListener(new SwitchDateTimeDialogFragment.OnButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Date date) {
                tmpDate = date;
                TextView textView_DateTimePicker = findViewById(R.id.addPrescription_textView_DateTimePicker);

                tmpDate.setSeconds(00);

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateFormatada = sdf.format(date);

                textView_DateTimePicker.setText(dateFormatada);
            }

            @Override
            public void onNegativeButtonClick(Date date) {
                // Ignorar?
            }
        });
    }
}
