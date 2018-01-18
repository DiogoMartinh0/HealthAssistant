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

import pt.isec.gps1718.g34.healthassistant.Base.Appointment;
import pt.isec.gps1718.g34.healthassistant.Base.Prescription;
import pt.isec.gps1718.g34.healthassistant.DataManager;
import pt.isec.gps1718.g34.healthassistant.R;


public class ViewAppointment extends AppCompatActivity {

    Appointment myAppointment = null;

    Button viewAppointment_btnEdit = null;
    Button viewAppointment_btnDelete = null;
    Button viewAppointment_btnSave = null;



    EditText viewAppointment_editText_Nome = null;
    TextView viewAppointment_textView_DateTimePicker = null;
    EditText viewAppointment_editText_Localizacao = null;
    EditText viewAppointment_editText_Medico = null;
    EditText viewAppointment_editText_AditionalInformation = null;

    Date tmpDate;
    DataManager dm;


    // Internal control
    Boolean isOnEdit = false;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_appointment);

        dm = (DataManager)getIntent().getSerializableExtra("DataManager");


        if (myAppointment == null)
            myAppointment = (Appointment) getIntent().getSerializableExtra("myAppointment");

        dm = (DataManager) getIntent().getSerializableExtra("DataManager");
        //Toast.makeText(this, "IDAppointment -> " + myAppointment.getID(), Toast.LENGTH_SHORT).show();

        viewAppointment_editText_Nome = findViewById(R.id.viewAppointment_editText_Nome);
        viewAppointment_textView_DateTimePicker = findViewById(R.id.viewAppointment_textView_DateTimePicker);
        viewAppointment_editText_Localizacao = findViewById(R.id.viewAppointment_editText_Localizacao);
        viewAppointment_editText_Medico = findViewById(R.id.viewAppointment_editText_Medico);
        viewAppointment_editText_AditionalInformation = findViewById(R.id.viewAppointment_editText_AditionalInformation);

        viewAppointment_btnEdit = findViewById(R.id.viewAppointment_btnEdit);
        viewAppointment_btnDelete = findViewById(R.id.viewAppointment_btnDelete);
        viewAppointment_btnSave = findViewById(R.id.viewAppointment_btnSave);



        LoadFields();
        LockFields();



        // Initialize
        final SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                "Starting date for the prescription",
                "Ok",
                "Cancel"
        );

        findViewById(R.id.viewAppointment_textView_DateTimePicker).setOnClickListener(new View.OnClickListener() {
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
        dateTimeDialogFragment.setDefaultDateTime(myAppointment.getdInicio());


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
                TextView textView_DateTimePicker = findViewById(R.id.viewAppointment_textView_DateTimePicker);

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


        viewAppointment_btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isOnEdit){
                    isOnEdit = true;
                    viewAppointment_btnEdit.setText("Cancel");
                    viewAppointment_btnEdit.setCompoundDrawables(
                            getDrawable(R.drawable.ic_cancel),
                            null,
                            null,
                            null);
                    UnlockFields();
                }else {
                    isOnEdit = false;
                    viewAppointment_btnEdit.setText("Edit");
                    LoadFields();
                    LockFields();
                }
            }
        });

        viewAppointment_btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DataManager.DeleteAppointment(myAppointment.getID());
                DataManager.saveAppointmentsToFile();
                finish();
            }
        });

        viewAppointment_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnEdit){
                    isOnEdit = false;
                    viewAppointment_btnEdit.setEnabled(true);
                    LockFields();
                    Toast.makeText(getApplicationContext(), "Your changes have been saved!", Toast.LENGTH_SHORT).show();
                    SaveAppointment();
                }else {
                    finish();
                }
            }
        });
    }

    private void LoadFields(){
        viewAppointment_editText_Nome.setText(myAppointment.getNome());
        viewAppointment_editText_Localizacao.setText(myAppointment.getLocalizacao());
        viewAppointment_editText_Medico.setText(myAppointment.getMedico());
        viewAppointment_editText_AditionalInformation.setText(myAppointment.getInformacaoAdicional());

        tmpDate = myAppointment.getdInicio();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFormatada = sdf.format( myAppointment.getdInicio());

        viewAppointment_textView_DateTimePicker.setText(dateFormatada);
    }

    private void SaveAppointment(){
        int IDToChange = myAppointment.getID();
        myAppointment = new Appointment(
                myAppointment.getID(),
                viewAppointment_editText_Nome.getText().toString(),
                viewAppointment_editText_Localizacao.getText().toString(),
                viewAppointment_editText_Medico.getText().toString(),
                viewAppointment_editText_AditionalInformation.getText().toString(),
                tmpDate
        );

        DataManager.EditAppointment(IDToChange, myAppointment);
        DataManager.saveAppointmentsToFile();
    }

    private void UnlockFields(){
        viewAppointment_editText_Nome.setEnabled(true);
        viewAppointment_editText_Localizacao.setEnabled(true);
        viewAppointment_editText_Medico.setEnabled(true);
        viewAppointment_editText_AditionalInformation.setEnabled(true);
        viewAppointment_textView_DateTimePicker.setEnabled(true);
    }

    private void LockFields(){
        viewAppointment_editText_Nome.setEnabled(false);
        viewAppointment_editText_Localizacao.setEnabled(false);
        viewAppointment_editText_Medico.setEnabled(false);
        viewAppointment_editText_AditionalInformation.setEnabled(false);
        viewAppointment_textView_DateTimePicker.setEnabled(false);
    }
}