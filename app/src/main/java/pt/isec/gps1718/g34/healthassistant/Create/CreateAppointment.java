package pt.isec.gps1718.g34.healthassistant.Create;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import pt.isec.gps1718.g34.healthassistant.DataManager;
import pt.isec.gps1718.g34.healthassistant.R;

public class CreateAppointment extends AppCompatActivity {

    Date tmpDate = null;
    Spinner dynamicSpinner = null;
    DataManager dm = null;

    EditText addAppointment_editText_Nome;
    EditText addAppointment_editText_Localizacao;
    EditText addAppointment_editText_Medico;
    EditText addAppointment_editText_AditionalInformation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_appointment);
        dm = (DataManager)getIntent().getSerializableExtra("DataManager");

        // Initialize
        final SwitchDateTimeDialogFragment dateTimeDialogFragment = SwitchDateTimeDialogFragment.newInstance(
                "Starting date for the prescription",
                "Ok",
                "Cancel"
        );

        addAppointment_editText_Nome = findViewById(R.id.addAppointment_editText_Nome);
        addAppointment_editText_Localizacao = findViewById(R.id.addAppointment_editText_Localizacao);
        addAppointment_editText_Medico = findViewById(R.id.addAppointment_editText_Medico);
        addAppointment_editText_AditionalInformation = findViewById(R.id.addAppointment_editText_AditionalInformation);

        Button addAppointment_btnOk = findViewById(R.id.addAppointment_btnOk);
        Button addAppointment_btnCancel = findViewById(R.id.addAppointment_btnCancel);

        addAppointment_btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        addAppointment_btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText addAppointment_editText_Nome = findViewById(R.id.addAppointment_editText_Nome);
                EditText addAppointment_editText_Localizacao = findViewById(R.id.addAppointment_editText_Localizacao);
                EditText addAppointment_editText_Medico = findViewById(R.id.addAppointment_editText_Medico);
                EditText addAppointment_editText_AditionalInformation = findViewById(R.id.addAppointment_editText_AditionalInformation);

                boolean erro = false;


                if (addAppointment_editText_Nome.getText().length() <= 2 || addAppointment_editText_Nome.getText().length() >= 15){
                    Toast.makeText(getApplicationContext(), "Invalid name", Toast.LENGTH_SHORT).show();
                    erro = true;
                }

                if (addAppointment_editText_Localizacao.getText().length() <= 2 || addAppointment_editText_Localizacao.getText().length() >= 15){
                    Toast.makeText(getApplicationContext(), "Invalid localtion", Toast.LENGTH_SHORT).show();
                    erro = true;
                }

                if (addAppointment_editText_Medico.getText().length() <= 2 || addAppointment_editText_Medico.getText().length() >= 15){
                    Toast.makeText(getApplicationContext(), "Invalid doctor", Toast.LENGTH_SHORT).show();
                    erro = true;
                }

                if (addAppointment_editText_AditionalInformation.getText().length() >= 300){
                    Toast.makeText(getApplicationContext(), "You wrote too much on your aditional information!", Toast.LENGTH_SHORT).show();
                    erro = true;
                }

                if (tmpDate == null){
                    Toast.makeText(getApplicationContext(), "Invalid date", Toast.LENGTH_SHORT).show();
                    erro = true;
                }

                if (!erro){
                    Appointment newAppointment = new Appointment(
                            -1,
                            addAppointment_editText_Nome.getText().toString(),
                            addAppointment_editText_Localizacao.getText().toString(),
                            addAppointment_editText_Medico.getText().toString(),
                            addAppointment_editText_AditionalInformation.getText().toString(),
                            tmpDate
                    );

                    DataManager.AddAppointment(newAppointment);
                    DataManager.savePrescritionsToFile();
                    finish();

                    // TODO: processar alarmes
                }
            }
        });


        findViewById(R.id.addAppointment_textView_DateTimePicker).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dateTimeDialogFragment.show(getSupportFragmentManager(), null);
            }
        });

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
                TextView textView_DateTimePicker = findViewById(R.id.addAppointment_textView_DateTimePicker);

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
