package pt.isec.gps1718.g34.healthassistant;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import pt.isec.gps1718.g34.healthassistant.Adapter.AppointmentAdapter;
import pt.isec.gps1718.g34.healthassistant.Adapter.PrescriptionAdapter;
import pt.isec.gps1718.g34.healthassistant.Base.Appointment;
import pt.isec.gps1718.g34.healthassistant.Base.NotificationIntervalAppointment;
import pt.isec.gps1718.g34.healthassistant.Base.NotificationIntervalPrescription;
import pt.isec.gps1718.g34.healthassistant.Base.Prescription;
import pt.isec.gps1718.g34.healthassistant.Create.CreateAppointment;
import pt.isec.gps1718.g34.healthassistant.Create.CreatePrescription;
import pt.isec.gps1718.g34.healthassistant.View.ViewAppointment;
import pt.isec.gps1718.g34.healthassistant.View.ViewPrescription;

public class MainActivity extends AppCompatActivity{

    private DataManager dm;
    private TabLayout tabLayout;
    private FloatingActionButton myFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dm = new DataManager(this);
        myFab = findViewById(R.id.fab_AdicionarEvento);
        setUpLists();

        myFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tabLayout.getSelectedTabPosition() == 0) {
                    Intent i1 = new Intent(MainActivity.this, CreatePrescription.class);
                    startActivity(i1);
                    //Toast.makeText(getApplicationContext(), "Criar Prescription", Toast.LENGTH_SHORT).show();
                } else if (tabLayout.getSelectedTabPosition() == 1) {
                    Intent i1 = new Intent(MainActivity.this, CreateAppointment.class);
                    startActivity(i1);
                    //Toast.makeText(getApplicationContext(), "Criar Appointment", Toast.LENGTH_SHORT).show();
                }
            }
        });


        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Prescriptions"));
        tabLayout.addTab(tabLayout.newTab().setText("Appointments"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        ListView listView_Global = findViewById(R.id.listView_Global);
        listView_Global.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long l) {
            if (tabLayout.getSelectedTabPosition() == 0) {
                Intent i1 = new Intent(MainActivity.this, ViewPrescription.class);
                //i1.putExtra("IDPrescription", dm.GetPrescritionList().get(posicao).getID());
                i1.putExtra("myPrescription", dm.GetPrescritionList().get(posicao));
                startActivity(i1);
            } else if (tabLayout.getSelectedTabPosition() == 1) {
                Intent i1 = new Intent(MainActivity.this, ViewAppointment.class);
                i1.putExtra("IDAppointment", dm.GetAppointmentList().get(posicao).getID());
                startActivity(i1);
            }

                //Snackbar.make(view, "On item click", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ListView listView_Global = findViewById(R.id.listView_Global);
                TextView textView_Global = findViewById(R.id.textView_Global);

                listView_Global.setVisibility(View.INVISIBLE); // Esconder a lista
                textView_Global.setVisibility(View.INVISIBLE); // Esconder texto de lista vazia

                if (tab.getPosition() == 0){
                    listView_Global.setAdapter(new PrescriptionAdapter(getApplicationContext(), dm.GetPrescritionList()));

                    if (dm.GetPrescritionList().size() == 0){
                        textView_Global.setText(R.string.there_are_no_items_on_the_prescription_list);
                        textView_Global.setVisibility(View.VISIBLE); // Mostrar texto da lista vazia
                    }else{
                        listView_Global.setVisibility(View.VISIBLE); // Mostrar a lista
                    }
                }else if (tab.getPosition() == 1){
                    listView_Global.setAdapter(new AppointmentAdapter(getApplicationContext(), dm.GetAppointmentList()));

                    if (dm.GetAppointmentList().size() == 0){
                        textView_Global.setText(R.string.there_are_no_items_on_the_appointment_list);
                        textView_Global.setVisibility(View.VISIBLE); // Mostrar texto da lista vazia
                    }else {
                        listView_Global.setVisibility(View.VISIBLE); // Mostrar a lista
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });


        startUpLists();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        dm.savePrescritionsToFile();
        dm.saveAppointmentsToFile();
    }

    @Override
    protected void onStop() {
        super.onStop();

        dm.savePrescritionsToFile();
        dm.saveAppointmentsToFile();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void startUpLists(){
        ListView listView_Global = findViewById(R.id.listView_Global);
        TextView textView_Global = findViewById(R.id.textView_Global);

        listView_Global.setVisibility(View.INVISIBLE); // Esconder a lista
        textView_Global.setVisibility(View.INVISIBLE); // Esconder texto de lista vazia

        listView_Global.setAdapter(new PrescriptionAdapter(getApplicationContext(), dm.GetPrescritionList()));

        if (dm.GetPrescritionList().size() == 0){
            textView_Global.setText("Sem items0");
            textView_Global.setVisibility(View.VISIBLE); // Mostrar texto da lista vazia
        }else{
            listView_Global.setVisibility(View.VISIBLE); // Mostrar a lista
        }
    }

    private void setUpLists(){
        dm.AddPrescription(new Prescription(
                0,
                "Ibuprofeno",
                "600mg",
                true,
                true,
                new Date(),
                NotificationIntervalPrescription.T30
        ));

        dm.AddAppointment(new Appointment(
                0,
                "Consulta Ortopedia",
                "HUC",
                "Tiago Silva",
                "Levar exames realizados em Julho de 2017",
                new Date(),
                NotificationIntervalAppointment.T10
        ));
    }
}
