package pt.isec.gps1718.g34.healthassistant.Base;

import java.io.Serializable;
import java.util.Date;

public class Prescription implements Serializable {

    private int ID;

    private String Nome;
    private String Dosagem;

    private boolean isAlarmActive;
    private boolean isNotificationActive;

    private Date dInicio;

    private String tInterval;

    public Prescription(int ID, String Nome, String Dosagem,
                        boolean isAlarmActive, boolean isNotificationActive,
                        Date dInicio, String tAvisoAntecedencia){
        this.Nome = Nome;
        this.Dosagem = Dosagem;
        this.isAlarmActive = isAlarmActive;
        this.isNotificationActive = isNotificationActive;
        this.dInicio = dInicio;
        this.tInterval = tAvisoAntecedencia;
    }

    public Prescription() {}

    public int getID () { return ID; }

    public void setID(int x) { this.ID = x; }

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

    public String gettInterval() {
        return tInterval;
    }

    public void settInterval(String tInterval) {
        this.tInterval = tInterval;
    }
}
