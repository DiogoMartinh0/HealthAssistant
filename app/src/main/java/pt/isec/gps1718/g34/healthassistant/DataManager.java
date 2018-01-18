package pt.isec.gps1718.g34.healthassistant;

import android.content.Context;
import android.os.Build;
import android.util.Log;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.function.Predicate;

import pt.isec.gps1718.g34.healthassistant.Base.Appointment;
import pt.isec.gps1718.g34.healthassistant.Base.Prescription;

public class DataManager implements Serializable {
    public static transient Context context;
    public static String PRESCRIPTIONS_FILENAME = "PrescriptionsFile";
    public static String APPOINTMENTS_FILENAME = "AppointmentsFile";
    public static Integer contadorPrescriptions = 0;
    public static Integer contadorAppointments = 0;

    public static ArrayList<Prescription> listPrescriptions = null;
    public static ArrayList<Appointment> listAppointments = null;


    public DataManager(Context ctx){
        this.context = ctx;
        listPrescriptions = new ArrayList<>();
        listAppointments = new ArrayList<>();

        listPrescriptions = GetPrescritionList();
        listAppointments = GetAppointmentList();
    }

    public static void SetContext(Context ctx){
        context = ctx;
    }

    private static int getIDForNewEvent(){
        return contadorPrescriptions + contadorAppointments + 1;
    }

    public static void LoadLists(){
        GetPrescritionList();
        GetAppointmentList();
    }

    public static ArrayList<Prescription> GetPrescritionList(){
        if (listPrescriptions != null) {
            return listPrescriptions;
        } else {
            listPrescriptions = new ArrayList<>();
            getPrescriptionsFromFile();

            contadorPrescriptions = listPrescriptions.size();
            return listPrescriptions;
        }
    }

    public static ArrayList<Appointment> GetAppointmentList(){
        if (listAppointments != null) {
            return listAppointments;
        } else {
            listAppointments = new ArrayList<>();
            getPrescriptionsFromFile();

            contadorAppointments = listAppointments.size();
            return listAppointments;
        }
    }

    public static void AddPrescription(Prescription newPrescription){
        newPrescription.setID(getIDForNewEvent());
        contadorPrescriptions++;
        listPrescriptions.add(newPrescription);
    }

    public static void AddAppointment(Appointment newAppointment){
        newAppointment.setID(getIDForNewEvent());
        contadorAppointments++;
        listAppointments.add(newAppointment);
    }

    public static void EditPrescription(int ID, Prescription newPrescription){
        for (int i = 0; i < listPrescriptions.size(); i++){
            Prescription toEdit = listPrescriptions.get(i);
            if (toEdit.getID() == ID) {
                toEdit.setNome(newPrescription.getNome());
                toEdit.setDosagem(newPrescription.getDosagem());
                toEdit.setdInicio(newPrescription.getdInicio());
                toEdit.settInterval(newPrescription.gettInterval());
                break;
            }
        }
    }

    public static void EditAppointment(int ID, Appointment newAppointment){
        for (int i = 0; i < listAppointments.size(); i++){
            Appointment toEdit = listAppointments.get(i);
            if (toEdit.getID() == ID) {
                toEdit.setNome(newAppointment.getNome());
                toEdit.setLocalizacao(newAppointment.getLocalizacao());
                toEdit.setMedico(newAppointment.getMedico());
                toEdit.setInformacaoAdicional(newAppointment.getInformacaoAdicional());
                toEdit.setdInicio(newAppointment.getdInicio());
                break;
            }
        }
    }

    public static void DeletePrescription(int ID){
        for (int i = 0; i < listPrescriptions.size(); i++){
            Prescription toDelete = listPrescriptions.get(i);
            if (toDelete.getID() == ID) {
                listPrescriptions.remove(i);
                break;
            }
        }
    }

    public static void DeleteAppointment(int ID){
        for (int i = 0; i < listAppointments.size(); i++){
            Appointment toDelete = listAppointments.get(i);
            if (toDelete.getID() == ID) {
                listAppointments.remove(i);
                break;
            }
        }
    }

    public static void RemovePrescription(final Prescription targetPrescription){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            listPrescriptions.removeIf(new Predicate<Prescription>() {
                @Override
                public boolean test(Prescription prescription) {
                    return prescription.getID() == targetPrescription.getID();
                }
            });
        }
    }

    public static void RemoveAppointment(final Appointment targetAppointment){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            listAppointments.removeIf(new Predicate<Appointment>() {
                @Override
                public boolean test(Appointment appointment) {
                    return appointment.getID() == targetAppointment.getID();
                }
            });
        }
    }



    private static ArrayList<Prescription> getPrescriptionsFromFile(){
        listPrescriptions.clear();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(PRESCRIPTIONS_FILENAME);
        } catch (FileNotFoundException e) {
            Log.e("DataManager", "Prescription File not found!");
            return null;
        }

        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ler objectos
        try {
            for (;;){
                listPrescriptions.add((Prescription)objectInputStream.readObject());
            }
        } catch (EOFException exc) {
            // não há mais objectos para ler
        } catch (IOException | ClassNotFoundException exc) {
            exc.printStackTrace();
        }

        return listPrescriptions;
    }

    private static ArrayList<Appointment> getAppointmentsFromFile(){
        listAppointments.clear();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = context.openFileInput(APPOINTMENTS_FILENAME);
        } catch (FileNotFoundException e) {
            Log.e("DataManager", "Prescription File not found!");
            return null;
        }

        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(fileInputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Ler objectos
        try {
            for (;;) {
                listAppointments.add((Appointment) objectInputStream.readObject());
            }
        } catch (EOFException exc)  {
            // não há mais objectos para ler
        } catch (IOException | ClassNotFoundException exc) {
            exc.printStackTrace(); // for example
        }

        return listAppointments;
    }

    public static void savePrescritionsToFile(){
        if (listPrescriptions.size() == 0){
            try{
                String dir = context.getFilesDir().getAbsolutePath();
                File filePrescriptions = new File(dir, PRESCRIPTIONS_FILENAME);
                filePrescriptions.delete();
            }catch(Exception e){

            }finally {
                return;
            }
        }

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

    public static void saveAppointmentsToFile(){
        if (listPrescriptions.size() == 0){
            try{
                String dir = context.getFilesDir().getAbsolutePath();
                File filePrescriptions = new File(dir, PRESCRIPTIONS_FILENAME);
                filePrescriptions.delete();
            }catch(Exception e){

            }finally {
                return;
            }
        }

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
