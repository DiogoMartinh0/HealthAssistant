package pt.isec.gps1718.g34.healthassistant.Base;

import java.io.Serializable;
import java.util.Date;


public class Appointment implements Serializable {
    private int ID;
    private String Nome;

    private Date dInicio;

    private String Localizacao;
    private String Medico;
    private String InformacaoAdicional;

    public Appointment(int ID, String Nome,
                        String Localizacao, String Medico, String InformacaoAdicional,
                        Date dInicio){

        this.Nome = Nome;
        this.dInicio = dInicio;
        this.Localizacao = Localizacao;
        this.Medico = Medico;
        this.InformacaoAdicional = InformacaoAdicional;
    }

    public int getID () { return ID; }

    public void setID(int x) { this.ID = x; }


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
