package pt.isec.gps1718.g34.healthassistant;

import java.io.Serializable;
import java.util.Date;


enum NotificationIntervalPrescription { T10, T15, T30, T60, T120 } // Minutos

public class Prescription extends Event implements Serializable {

    private String Nome;
    private String Dosagem;

    private boolean isAlarmActive;
    private boolean isNotificationActive;

    private Date dInicio;

    private NotificationIntervalPrescription tAvisoAntecedencia;

    public Prescription(int ID, String Nome, String Dosagem,
                        boolean isAlarmActive, boolean isNotificationActive,
                        Date dInicio, NotificationIntervalPrescription tAvisoAntecedencia){

        this.Nome = Nome;
        this.Dosagem = Dosagem;
        this.isAlarmActive = isAlarmActive;
        this.isNotificationActive = isNotificationActive;
        this.dInicio = dInicio;
        this.tAvisoAntecedencia = tAvisoAntecedencia;
    }

    public Prescription() {}

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getDosagem() {
        return Dosagem;
    }

    public void setDosagem(String dosagem) {
        Dosagem = dosagem;
    }

    public boolean isAlarmActive() {
        return isAlarmActive;
    }

    public void setAlarmActive(boolean alarmActive) {
        isAlarmActive = alarmActive;
    }

    public boolean isNotificationActive() {
        return isNotificationActive;
    }

    public void setNotificationActive(boolean notificationActive) {
        isNotificationActive = notificationActive;
    }

    public Date getdInicio() {
        return dInicio;
    }

    public void setdInicio(Date dInicio) {
        this.dInicio = dInicio;
    }

    public NotificationIntervalPrescription gettAvisoAntecedencia() {
        return tAvisoAntecedencia;
    }

    public void settAvisoAntecedencia(NotificationIntervalPrescription tAvisoAntecedencia) {
        this.tAvisoAntecedencia = tAvisoAntecedencia;
    }
}
