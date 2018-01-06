package pt.isec.gps1718.g34.healthassistant.View;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import pt.isec.gps1718.g34.healthassistant.Base.NotificationIntervalPrescription;
import pt.isec.gps1718.g34.healthassistant.Base.Prescription;
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



    // Internal control
    Boolean isOnEdit = false;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_prescription);


        if (myPrescription == null)
            myPrescription = (Prescription) getIntent().getSerializableExtra("myPrescription");

        Toast.makeText(this, "IDPrescription -> " + myPrescription.getID(), Toast.LENGTH_SHORT).show();


        editText_Nome = findViewById(R.id.viewPrescription_editText_Nome);
        editText_Dosagem = findViewById(R.id.viewPrescription_editText_Dosagem);
        isAlarmChecked = findViewById(R.id.viewPrescription_isAlarmChecked);
        isNotificationChecked = findViewById(R.id.viewPrescription_isNotificationChecked);
        textView_DateTimePicker = findViewById(R.id.viewPrescription_textView_DateTimePicker);
        spinner_TimeInterval = findViewById(R.id.viewPrescription_dropDownList_TimeInterval);

        viewPrescription_btnEdit = findViewById(R.id.viewPrescription_btnEdit);
        viewPrescription_btnDelete = findViewById(R.id.viewPrescription_btnDelete);
        viewPrescription_btnSave = findViewById(R.id.viewPrescription_btnSave);

        Integer pos = 0;
        for (int k = 0; k < NotificationIntervalPrescription.values().length; k++) {
            if (myPrescription.gettAvisoAntecedencia().toString().compareTo(NotificationIntervalPrescription.values()[k].toString()) == 0) {
                pos = k;
                break;
            }
        }

        spinner_TimeInterval.setSelection(pos);

        LoadFields();
        LockFields();


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

        viewPrescription_btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isOnEdit){
                    isOnEdit = false;
                    viewPrescription_btnEdit.setEnabled(true);
                    LockFields();
                    // TODO: update event
                    Toast.makeText(getApplicationContext(), "Your changes have been saved!", Toast.LENGTH_SHORT).show();
                }else {
                    SavePrescription();
                    finish();
                }
            }
        });
    }

    private void LoadFields(){
        editText_Nome.setText(myPrescription.getNome());
        editText_Dosagem.setText(myPrescription.getDosagem());
        isAlarmChecked.setChecked(myPrescription.isAlarmActive());
        isNotificationChecked.setChecked(myPrescription.isNotificationActive());
        textView_DateTimePicker.setText(myPrescription.getdInicio().toString());
    }

    private void SavePrescription(){
        // TODO: FINISH THIS, SAVE PRESCRIPTION TO LIST
        //
    }

    private void UnlockFields(){
        editText_Nome.setEnabled(true);
        editText_Dosagem.setEnabled(true);
        isAlarmChecked.setEnabled(true);
        isNotificationChecked.setEnabled(true);
        textView_DateTimePicker.setEnabled(true);
        spinner_TimeInterval.setEnabled(true);
    }

    private void LockFields(){
        editText_Nome.setEnabled(false);
        editText_Dosagem.setEnabled(false);
        isAlarmChecked.setEnabled(false);
        isNotificationChecked.setEnabled(false);
        textView_DateTimePicker.setEnabled(false);
        spinner_TimeInterval.setEnabled(false);
    }
}
