package pt.isec.gps1718.g34.healthassistant.View;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import pt.isec.gps1718.g34.healthassistant.R;


public class ViewAppointment extends AppCompatActivity {

    Integer IDAppointment = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_appointment);

        if (IDAppointment == null)
            IDAppointment = getIntent().getIntExtra("IDPrescription", -1);

        Toast.makeText(this, "IDAppointment -> " + IDAppointment, Toast.LENGTH_SHORT).show();
    }
}
