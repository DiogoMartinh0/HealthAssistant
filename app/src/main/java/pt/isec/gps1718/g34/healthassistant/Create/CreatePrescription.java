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
import android.widget.EditText;
import android.widget.Spinner;

import pt.isec.gps1718.g34.healthassistant.R;

public class CreatePrescription extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_prescription);

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

                Snackbar.make(view, "You pressed ok", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                // chamar data manager -> adicionar à lista
                // processar alarmes
            }
        });


        //get the spinner from the xml.
        Spinner dropdown = findViewById(R.id.addPrescription_dropDownList_TimeInterval);
        //create a list of items for the spinner.
        //String[] items = new String[]{"1", "2", "three"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        //ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        //dropdown.setAdapter(adapter);


        Spinner dynamicSpinner = (Spinner) findViewById(R.id.addPrescription_dropDownList_TimeInterval);

        String[] items = new String[] { "Chai Latte", "Green Tea", "Black Tea" };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, items);

        dynamicSpinner.setAdapter(adapter);

        dynamicSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.v("item", (String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub
            }
        });
    }
}
