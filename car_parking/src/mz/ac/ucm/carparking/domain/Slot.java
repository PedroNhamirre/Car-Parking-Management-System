package mz.ac.ucm.carparking.domain;


public class Slot {

    private int idSlot;
    private String status;
    private String carroMatricula;
    private Carro carro;

    public int getIdSlot() {
        return idSlot;
    }

    public void setIdSlot(int idSlot) {
        this.idSlot = idSlot;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCarroMatricula() {
        return carroMatricula;
    }

    public void setCarroMatricula(String carroMatricula) {
        this.carroMatricula = carroMatricula;
    }

    public Carro getCarro() {
        return carro;
    }

    public void setCarro(Carro carro) {
        this.carro = carro;
    }

    

}
