import java.time.LocalDate;


public class Auto {

    private int id_auto;
    private int id_cliente;   // cliente propietario
    private String modelo;
    private String color;
    private String tipo;
    private String observaciones;
    private boolean cita;
    private java.time.LocalTime hora;
     private LocalDate fecha;

    public Auto() {
    }

    public Auto(int id_auto, String modelo, String color, String tipo,
                String observaciones, boolean cita, java.time.LocalTime hora) {
        this.id_auto = id_auto;
        this.modelo = modelo;
        this.color = color;
        this.tipo = tipo;
        this.observaciones = observaciones;
        this.cita = cita;
        this.hora = hora;
    }

    public int getId_cliente() { return id_cliente; }
    public void setId_cliente(int id_cliente) { this.id_cliente = id_cliente; }

    public int getId_auto() {
        return id_auto;
    }

    public void setId_auto(int id_auto) {
        this.id_auto = id_auto;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isCita() {
        return cita;
    }

    public void setCita(boolean cita) {
        this.cita = cita;
    }

    public java.time.LocalTime getHora() {
        return hora;
    }

    public void setHora(java.time.LocalTime hora) {
        this.hora = hora;
    }
    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    @Override
    public String toString() {
        return "Auto{" +
                "id_auto=" + id_auto +
                ", modelo='" + modelo + '\'' +
                ", color='" + color + '\'' +
                ", tipo='" + tipo + '\'' +
                ", observaciones='" + observaciones + '\'' +
                ", cita=" + cita +
                ", hora=" + hora +
                '}';
    }
}