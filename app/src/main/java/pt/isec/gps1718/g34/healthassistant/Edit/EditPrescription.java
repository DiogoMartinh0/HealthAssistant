package pt.isec.gps1718.g34.healthassistant.Edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import pt.isec.gps1718.g34.healthassistant.Base.Prescription;
import pt.isec.gps1718.g34.healthassistant.R;

public class EditPrescription extends AppCompatActivity {

    Integer IDPrescription = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_prescription);

        if (IDPrescription == null)
            IDPrescription = getIntent().getIntExtra("IDPrescription", -1);

        Toast.makeText(this, "IDPrescription -> " + IDPrescription, Toast.LENGTH_SHORT).show();


        EditText editText_Nome = findViewById(R.id.editPrescription_editText_Nome);
        EditText editText_Dosagem = findViewById(R.id.editPrescription_editText_Dosagem);
        CheckBox isAlarmChecked = findViewById(R.id.editPrescription_isAlarmChecked);
        CheckBox isNotificationChecked = findViewById(R.id.editPrescription_isNotificationChecked);
        TextView textView_DateTimePicker = findViewById(R.id.editPrescription_textView_DateTimePicker);
        Spinner spinner_TimeInterval = findViewById(R.id.editPrescription_dropDownList_TimeInterval);
    }
}
