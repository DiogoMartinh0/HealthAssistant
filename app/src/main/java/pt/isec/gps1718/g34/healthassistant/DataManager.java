package pt.isec.gps1718.g34.healthassistant;

import android.content.Context;
import android.os.Build;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.function.Predicate;

public class DataManager{

    private Context context;
    private String PRESCRIPTIONS_FILENAME = "PrescriptionsFile";
    private String APPOINTMENTS_FILENAME = "AppointmentsFile";

    ArrayList<Prescription> listaPrescriptions;
    ArrayList<Appointment> listaAppointments;


    public DataManager(Context ctx){
        this.context = ctx;
        listaPrescriptions = new ArrayList<>();
        listaAppointments = new ArrayList<>();
    }



    public ArrayList<Prescription> GetPrescritionList(){
        return getPrescriptionsFromFile();
    }

    public ArrayList<Appointment> GetAppointmentList(){
        return getAppointmentsFromFile();
    }



    public void AddPrescription(Prescription newPrescription){
        listaPrescriptions.add(newPrescription);
    }

    public void AddAppointment(Appointment newAppointment){
        listaAppointments.add(newAppointment);
    }

    public void RemovePrescription(final Prescription targetPrescription){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            listaPrescriptions.removeIf(new Predicate<Prescription>() {
                @Override
                public boolean test(Prescription prescription) {
                    return prescription.getID() == targetPrescription.getID();
                }
            });
        }
    }

    public void RemoveAppointment(final Appointment targetAppointment){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            listaAppointments.removeIf(new Predicate<Appointment>() {
                @Override
                public boolean test(Appointment appointment) {
                    return appointment.getID() == targetAppointment.getID();
                }
            });
        }
    }

    private ArrayList<Prescription> getPrescriptionsFromFile(){
        if (!listaPrescriptions.isEmpty())
            return listaPrescriptions;

        listaPrescriptions.clear();

        FileInputStream fi = null;
        try {
            fi = context.openFileInput(PRESCRIPTIONS_FILENAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectInputStream oi = null;
        try {
            oi = new ObjectInputStream(fi);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ler objectos
        try
        {
            for (;;)
            {
                listaPrescriptions.add((Prescription)oi.readObject());
            }
        }
        catch (EOFException exc)
        {
            // não há mais objectos para ler
        }
        catch (IOException exc)
        {
            exc.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return listaPrescriptions;
    }

    private ArrayList<Appointment> getAppointmentsFromFile(){
        if (!listaAppointments.isEmpty())
            return listaAppointments;

        listaAppointments.clear();

        FileInputStream fi = null;
        try {
            fi = context.openFileInput(APPOINTMENTS_FILENAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectInputStream oi = null;
        try {
            oi = new ObjectInputStream(fi);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ler objectos
        try
        {
            for (;;)
            {
                listaAppointments.add((Appointment) oi.readObject());
            }
        }
        catch (EOFException exc)
        {
            // não há mais objectos para ler
        }
        catch (IOException exc)
        {
            exc.printStackTrace(); // for example
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return listaAppointments;
    }

    public void savePrescritionsToFile(){
        String string = "Hello world!";
        FileOutputStream streamFicheiro;

        try {
            streamFicheiro = context.openFileOutput(PRESCRIPTIONS_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(streamFicheiro);

            for (Prescription t : listaPrescriptions)
                oos.writeObject(t);

            streamFicheiro.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAppointmentsToFile(){
        String string = "Hello world!";
        FileOutputStream streamFicheiro;

        try {
            streamFicheiro = context.openFileOutput(APPOINTMENTS_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(streamFicheiro);

            for (Appointment t : listaAppointments)
                oos.writeObject(t);

            streamFicheiro.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
