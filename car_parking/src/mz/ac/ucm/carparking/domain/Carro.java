package mz.ac.ucm.carparking.domain;

public class Carro {

    private String matricula;
    private String cor;
    private String marca;
    private String modelo;
    private int ano;
    private int proprietario;
    private String amountPaid;
    private Cliente proprietarioCarro;
    private Slot slot;

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getProprietario() {
        return proprietario;
    }

    public void setProprietario(int proprietario) {
        this.proprietario = proprietario;
    }

    public String getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(String amountPaid) {
        this.amountPaid = amountPaid;
    }

    public Cliente getProprietarioCarro() {
        return proprietarioCarro;
    }

    public void setProprietarioCarro(Cliente proprietarioCarro) {
        this.proprietarioCarro = proprietarioCarro;
    }

    public Slot getSlot() {
        return slot;
    }

    public void setSlot(Slot slot) {
        this.slot = slot;
    }

    
}
