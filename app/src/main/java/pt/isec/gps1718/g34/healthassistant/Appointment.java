package pt.isec.gps1718.g34.healthassistant;

import java.util.Date;


enum NotificationIntervalAppointment { T10, T15, T30, T60, T120 } // Minutos
public class Appointment {
    private String Nome;

    private Date dInicio;

    private NotificationIntervalAppointment tAvisoAntecedencia;

    private String Localizacao;
    private String Medico;
    private String InformacaoAdicional;

    public Appointment(int ID, String Nome, Date dInicio, NotificationIntervalAppointment tAvisoAntecedencia,
                        String Localizacao, String Medico, String InformacaoAdicional){

        this.Nome = Nome;
        this.dInicio = dInicio;
        this.tAvisoAntecedencia = tAvisoAntecedencia;
        this.Localizacao = Localizacao;
        this.Medico = Medico;
        this.InformacaoAdicional = InformacaoAdicional;
    }


    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public Date getdInicio() {
        return dInicio;
    }

    public void setdInicio(Date dInicio) {
        this.dInicio = dInicio;
    }

    public NotificationIntervalAppointment gettAvisoAntecedencia() {
        return tAvisoAntecedencia;
    }

    public void settAvisoAntecedencia(NotificationIntervalAppointment tAvisoAntecedencia) {
        this.tAvisoAntecedencia = tAvisoAntecedencia;
    }

    public String getLocalizacao() {
        return Localizacao;
    }

    public void setLocalizacao(String localizacao) {
        Localizacao = localizacao;
    }

    public String getMedico() {
        return Medico;
    }

    public void setMedico(String medico) {
        Medico = medico;
    }

    public String getInformacaoAdicional() {
        return InformacaoAdicional;
    }

    public void setInformacaoAdicional(String informacaoAdicional) {
        InformacaoAdicional = informacaoAdicional;
    }
}
