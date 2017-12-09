package pt.isec.gps1718.g34.healthassistant;

import java.io.Serializable;
import java.util.Date;


public class Appointment extends Event implements Serializable {
    private String Nome;

    private Date dInicio;

    private NotificationIntervalAppointment tAvisoAntecedencia;

    private String Localizacao;
    private String Medico;
    private String InformacaoAdicional;

    public Appointment(int ID, String Nome,
                        String Localizacao, String Medico, String InformacaoAdicional,
                        Date dInicio, NotificationIntervalAppointment tAvisoAntecedencia){

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
