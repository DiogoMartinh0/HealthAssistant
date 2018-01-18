package pt.isec.gps1718.g34.healthassistant.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kunzisoft.switchdatetime.SwitchDateTimeDialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import pt.isec.gps1718.g34.healthassistant.Base.Prescription;
import pt.isec.gps1718.g34.healthassistant.DataManager;
import pt.isec.gps1718.g34.healthassistant.R;


public class ViewPrescription extends AppCompatActivity {

    Prescription myPrescription = null;

    Button viewPrescription_btnEdit = null;
    Button viewPrescription_btnDelete = null;
    Button viewPrescription_btnSave = null;

    EditText editText_Nome = null;
    EditText editText_Dosagem = null;
    CheckBox isAlarmChecked = null;
    CheckBox isNotificationChecked = null;
    TextView textView_DateTimePicker = null;
    Spinner spinner_TimeInterval = null;

    Date tmpDate;
    DataManager dm;


    // Internal control
    Boolean isOnEdit = false;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_prescription);

        dm = (DataManager)getIntent().getSerializableExtra("DataManager");


        if (myPrescription == null)
            myPrescription = (Prescription) getIntent().getSerializableExtra("myPrescription");

        //Toast.makeText(this, "IDPrescription -> " + myPrescription.getID(), Toast.LENGTH_SHORT).show();

        editText_Nome = findViewById(R.id.viewPrescription_editText_Nome);
        editText_Dosagem = findViewById(R.id.viewPrescription_editText_Dosagem);
        textView_DateTimePicker = findViewById(R.id.viewPrescription_textView_DateTimePicker);
        spinner_TimeInterval = findViewById(R.id.viewPrescription_dropDownList_TimeInterval);

        viewPrescription_btnEdit = findViewById(R.id.viewPrescription_btnEdit);
        viewPrescription_btnDelete = findViewById(R.id.viewPrescription_btnDelete);
        viewPrescription_btnSave = findViewById(R.id.viewPrescription_btnSave);



        LoadFields();
        LockFields();



        // Initialize
        final SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                "Starting date for the prescription",
                "Ok",
                "Cancel"
        );

        findViewById(R.id.viewPrescription_textView_DateTimePicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTimeDialogFragment .show(getSupportFragmentManager(), null);
            }
        });


        // Assign values
        dateTimeDialogFragment.startAtCalendarView();
        dateTimeDialogFragment.set24HoursMode(true);
        dateTimeDialogFragment.setMinimumDateTime(new GregorianCalendar(2015, Calendar.JANUARY, 1).getTime());
        dateTimeDialogFragment.setMaximumDateTime(new GregorianCalendar(2025, Calendar.DECEMBER, 31).getTime());
        dateTimeDialogFragment.setDefaultDateTime(myPrescription.getdInicio());


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
                TextView textView_DateTimePicker = findViewById(R.id.viewPrescription_textView_DateTimePicker);

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


        viewPrescription_btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOnEdit){
                    isOnEdit = true;
                    viewPrescription_btnEdit.setText("Cancel");
                    viewPrescription_btnEdit.setCompoundDrawables(
                            getDrawable(R.drawable.ic_cancel),
                            null,
                            null,
                            null);
                    UnlockFields();
                }else {
                    isOnEdit = false;
                    viewPrescription_btnEdit.setText("Edit");
                    LoadFields();
                    LockFields();
                }
            }
        });

        viewPrescription_btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dm.DeletePrescription(myPrescription.getID());
                dm.savePrescritionsToFile();
                finish();
            }
        });

        viewPrescription_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnEdit){
                    isOnEdit = false;
                    viewPrescription_btnEdit.setEnabled(true);
                    LockFields();
                    Toast.makeText(getApplicationContext(), "Your changes have been saved!", Toast.LENGTH_SHORT).show();
                    SavePrescription();
                }else {
                    finish();
                }
            }
        });
    }

    private void LoadFields(){
        editText_Nome.setText(myPrescription.getNome());
        editText_Dosagem.setText(myPrescription.getDosagem());

        String Intervalos[] = new String[]{
                "Every 4 Hours",
                "Every 8 Hours",
                "Every 12 Hours",
                "Every 16 Hours",
                "Every 24 Hours",
                "Every 48 Hours",
                "Every 72 Hours"
        };

        spinner_TimeInterval.setAdapter(
                new ArrayAdapter<>(this,
                    android.R.layout.simple_spinner_dropdown_item,
                    Intervalos
                )
        );

        Integer pos = 0;
        for (int k = 0; k < Intervalos.length; k++) {
            if (myPrescription.gettInterval().compareTo(Intervalos[k]) == 0) {
                pos = k;
                break;
            }
        }

        spinner_TimeInterval.setSelection(pos);

        tmpDate = myPrescription.getdInicio();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFormatada = sdf.format(myPrescription.getdInicio());

        textView_DateTimePicker.setText(dateFormatada);
    }

    private void SavePrescription(){
        int IDToChange = myPrescription.getID();
        myPrescription = new Prescription(
                myPrescription.getID(),
                editText_Nome.getText().toString(),
                editText_Dosagem.getText().toString(),
                false,
                false,
                tmpDate,
                spinner_TimeInterval.getSelectedItem().toString()
        );

        dm.EditPrescription(IDToChange, myPrescription);
        dm.savePrescritionsToFile();
    }

    private void UnlockFields(){
        editText_Nome.setEnabled(true);
        editText_Dosagem.setEnabled(true);
        textView_DateTimePicker.setEnabled(true);
        spinner_TimeInterval.setEnabled(true);
    }

    private void LockFields(){
        editText_Nome.setEnabled(false);
        editText_Dosagem.setEnabled(false);
        textView_DateTimePicker.setEnabled(false);
        spinner_TimeInterval.setEnabled(false);
    }
}
