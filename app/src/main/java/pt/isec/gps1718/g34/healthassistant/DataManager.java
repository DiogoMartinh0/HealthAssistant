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

    ArrayList<Prescription> listPrescriptions;
    ArrayList<Appointment> listAppointments;


    public DataManager(Context ctx){
        this.context = ctx;
        listPrescriptions = new ArrayList<>();
        listAppointments = new ArrayList<>();
    }

    public ArrayList<Prescription> GetPrescritionList(){
        return getPrescriptionsFromFile();
    }

    public ArrayList<Appointment> GetAppointmentList(){
        return getAppointmentsFromFile();
    }

    public void AddPrescription(Prescription newPrescription){
        listPrescriptions.add(newPrescription);
    }

    public void AddAppointment(Appointment newAppointment){
        listAppointments.add(newAppointment);
    }

    public void RemovePrescription(final Prescription targetPrescription){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            listPrescriptions.removeIf(new Predicate<Prescription>() {
                @Override
                public boolean test(Prescription prescription) {
                    return prescription.getID() == targetPrescription.getID();
                }
            });
        }
    }

    public void RemoveAppointment(final Appointment targetAppointment){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            listAppointments.removeIf(new Predicate<Appointment>() {
                @Override
                public boolean test(Appointment appointment) {
                    return appointment.getID() == targetAppointment.getID();
                }
            });
        }
    }

    private ArrayList<Prescription> getPrescriptionsFromFile(){
        if (!listPrescriptions.isEmpty()){
            return listPrescriptions;
        }

        listPrescriptions.clear();

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(PRESCRIPTIONS_FILENAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectInputStream objInputStream = null;
        try {
            objInputStream = new ObjectInputStream(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ler objectos
        try{
            for (;;) { 
                listPrescriptions.add((Prescription)objInputStream.readObject());
            }
        } catch (EOFException exc) {
            // não há mais objectos para ler
        } catch (IOException exc) {
            exc.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return listPrescriptions;
    }

    private ArrayList<Appointment> getAppointmentsFromFile(){
        if (!listAppointments.isEmpty()){
            return listAppointments;
        }
        
        listAppointments.clear();

        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(APPOINTMENTS_FILENAME);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ObjectInputStream objInputStream = null;
        try {
            objInputStream = new ObjectInputStream(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ler objectos
        try{
            for (;;) { 
                listPrescriptions.add((Prescription)objInputStream.readObject());
            }
        } catch (EOFException exc) {
            // não há mais objectos para ler
        } catch (IOException exc) {
            exc.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return listAppointments;
    }

    public void savePrescritionsToFile(){
        FileOutputStream streamFicheiro;

        try {
            streamFicheiro = context.openFileOutput(PRESCRIPTIONS_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(streamFicheiro);

            for (Prescription t : listPrescriptions)
                oos.writeObject(t);

            streamFicheiro.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAppointmentsToFile(){
        FileOutputStream streamFicheiro;

        try {
            streamFicheiro = context.openFileOutput(APPOINTMENTS_FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(streamFicheiro);

            for (Appointment t : listAppointments)
                oos.writeObject(t);

            streamFicheiro.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
