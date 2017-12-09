package pt.isec.gps1718.g34.healthassistant;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements PrescriptionsFragment.OnFragmentInteractionListener, AppointmentsFragment.OnFragmentInteractionListener {

    private DataManager dm;
    private TabLayout tabLayout;
    private FloatingActionButton myFab;

    View.OnClickListener onClickListener_AddPrescription = new View.OnClickListener() {
        public void onClick(View v) {
            Log.i("HealthAssistant", "Heyyyyyy, hello!!!!!!!");
            if (tabLayout.getSelectedTabPosition() == 0){
                Toast.makeText(getApplicationContext(), "Criar Prescription", Toast.LENGTH_LONG);
            }else if (tabLayout.getSelectedTabPosition() == 1){
                Toast.makeText(getApplicationContext(), "Criar Appointment", Toast.LENGTH_LONG);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dm = new DataManager(this);
        myFab = findViewById(R.id.fab_AdicionarEvento);
        setUpLists();


        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Prescriptions"));
        tabLayout.addTab(tabLayout.newTab().setText("Appointments"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = findViewById(R.id.viewPager);
        final PagerAdapter pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 0){
                    // Prescriptions

                    if (!dm.GetPrescritionList().isEmpty()){
                        ListView lvPrescription = (ListView) findViewById(R.id.listViewPrescriptions);
                        lvPrescription.setAdapter(new PrescriptionAdapter(getApplicationContext(), dm.GetPrescritionList()));
                    }
                }else if (tab.getPosition() == 1){
                    // Appointments

                    if (!dm.GetAppointmentList().isEmpty()){
                        ListView lvAppointments = (ListView) findViewById(R.id.listViewAppointments);
                        lvAppointments .setAdapter(new AppointmentAdapter(getApplicationContext(), dm.GetAppointmentList()));
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.i("HealthAssistant", "Clock botão flutuante");
                if (tabLayout.getSelectedTabPosition() == 0){
                    Toast.makeText(getApplicationContext(), "Criar Prescription", Toast.LENGTH_LONG);
                }else if (tabLayout.getSelectedTabPosition() == 1){
                    Toast.makeText(getApplicationContext(), "Criar Appointment", Toast.LENGTH_LONG);
                }
            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) { }

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
        /*
        ListView lvPrescription = (ListView) findViewById(R.id.listViewPrescriptions);
        lvPrescription.setAdapter(new PrescriptionAdapter(getApplicationContext(), dm.GetPrescritionList()));*/
    }

    private void setUpLists(){
        dm.AddPrescription(new Prescription(
                0,
                "Ibuprofeno",
                "600mg",
                true,
                true,
                new Date(),
                NotificationIntervalPrescription.T10
        ));

        dm.AddAppointment(new Appointment(
                1,
                "Consulta Ortopedia",
                "HUC",
                "Tiago Silva",
                "Levar exames realizados em Julho de 2017",
                new Date(),
                NotificationIntervalAppointment.T10
        ));
    }
}
