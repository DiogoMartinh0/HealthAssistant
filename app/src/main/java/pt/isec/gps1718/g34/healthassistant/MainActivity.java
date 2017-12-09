package pt.isec.gps1718.g34.healthassistant;


import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements PrescriptionsFragment.OnFragmentInteractionListener, AppointmentsFragment.OnFragmentInteractionListener {

    private DataManager dm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Prescriptions"));
        tabLayout.addTab(tabLayout.newTab().setText("Appointments"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        final PagerAdapter pagerAdapter = new PageAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) { viewPager.setCurrentItem(tab.getPosition()); }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) { }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });


        dm = new DataManager(this);
        dm.getPrescriptionsFromFile();
        dm.getAppointmentsFromFile();

        dm.AddPrescription(new Prescription(
                0,
                "Hey",
                "600mg",
                true,
                true,
                new Date(),
                NotificationIntervalPrescription.T10
        ));

        dm.AddAppointment(new Appointment(
                1,
                "Consulta qq cena",
                new Date(),
                NotificationIntervalAppointment.T10,
                "HUC",
                "Tiago Silva",
                "Levar exames realizados em Julho de 2017"
        ));
    }

    @Override
    public void onFragmentInteraction(Uri uri) { }

    @Override
    protected void onPause() {
        super.onPause();

        dm.savePrescritionsToFile();
        dm.saveAppointmentsToFile();
    }
}
