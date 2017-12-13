package pt.isec.gps1718.g34.healthassistant;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(AndroidJUnit4.class)
public class DataManagerInstrumentedTest {
    @Test
    public void getPrescritionList() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DataManager dm = new DataManager(appContext);
        ArrayList<Prescription> lista = dm.GetPrescritionList();

        assertNotEquals(null, lista);
    }

    @Test
    public void getAppointmentList() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DataManager dm = new DataManager(appContext);
        ArrayList<Appointment> lista = dm.GetAppointmentList();

        assertNotEquals(null, lista);
    }

    @Test
    public void addPrescription() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DataManager dm = new DataManager(appContext);

        dm.AddPrescription(new Prescription(
                0,
                "Ibuprofeno",
                "600mg",
                true,
                true,
                new Date(),
                NotificationIntervalPrescription.T10
        ));

        assertEquals(dm.GetPrescritionList().size(), 1);
    }

    @Test
    public void addAppointment() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DataManager dm = new DataManager(appContext);

        dm.AddAppointment(new Appointment(
                1,
                "Consulta Ortopedia",
                "HUC",
                "Tiago Silva",
                "Levar exames realizados em Julho de 2017",
                new Date(),
                NotificationIntervalAppointment.T10
        ));

        assertEquals(dm.GetAppointmentList().size(), 1);
    }

    @Test
    public void removePrescription() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DataManager dm = new DataManager(appContext);

        Prescription toAdd = new Prescription(
                0,
                "Ibuprofeno",
                "600mg",
                true,
                true,
                new Date(),
                NotificationIntervalPrescription.T10
        );

        dm.AddPrescription(toAdd);
        dm.RemovePrescription(toAdd);
        assertEquals(dm.GetPrescritionList().size(), 0);
    }

    @Test
    public void removeAppointment() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DataManager dm = new DataManager(appContext);

        Appointment toAdd = new Appointment(
                1,
                "Consulta Ortopedia",
                "HUC",
                "Tiago Silva",
                "Levar exames realizados em Julho de 2017",
                new Date(),
                NotificationIntervalAppointment.T10
        );

        dm.AddAppointment(toAdd);
        dm.RemoveAppointment(toAdd);
        assertEquals(dm.GetAppointmentList().size(), 0);
    }

    @Test
    public void savePrescritionsToFile() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DataManager dm = new DataManager(appContext);

        Prescription toAdd = new Prescription(
                0,
                "Ibuprofeno",
                "600mg",
                true,
                true,
                new Date(),
                NotificationIntervalPrescription.T10
        );

        dm.AddPrescription(toAdd);

        dm.savePrescritionsToFile();
        ArrayList<Prescription> listPrescriptions = dm.GetPrescritionList();
        for(Prescription t : listPrescriptions){
            assertEquals(toAdd.getID(), t.getID());
            assertEquals(toAdd.getNome(), t.getNome());
            assertEquals(toAdd.getDosagem(), t.getDosagem());
            assertEquals(toAdd.isAlarmActive(), t.isAlarmActive());
            assertEquals(toAdd.isNotificationActive(), t.isNotificationActive());
            assertEquals(toAdd.getdInicio(), t.getdInicio());
            assertEquals(toAdd.gettAvisoAntecedencia(), t.gettAvisoAntecedencia());
        }
    }

    @Test
    public void saveAppointmentsToFile() throws Exception {
        Context appContext = InstrumentationRegistry.getTargetContext();
        DataManager dm = new DataManager(appContext);

        Appointment toAdd = new Appointment(
                1,
                "Consulta Ortopedia",
                "HUC",
                "Tiago Silva",
                "Levar exames realizados em Julho de 2017",
                new Date(),
                NotificationIntervalAppointment.T10
        );
        dm.AddAppointment(toAdd
        );

        dm.saveAppointmentsToFile();
        ArrayList<Appointment> listAppointments = dm.GetAppointmentList();

        for(Appointment t : listAppointments){
            assertEquals(toAdd.getID(), t.getID());
            assertEquals(toAdd.getNome(), t.getNome());
            assertEquals(toAdd.getMedico(), t.getMedico());
            assertEquals(toAdd.getLocalizacao(), t.getLocalizacao());
            assertEquals(toAdd.getInformacaoAdicional(), t.getInformacaoAdicional());
            assertEquals(toAdd.getdInicio(), t.getdInicio());
            assertEquals(toAdd.gettAvisoAntecedencia(), t.gettAvisoAntecedencia());
        }
    }
}
