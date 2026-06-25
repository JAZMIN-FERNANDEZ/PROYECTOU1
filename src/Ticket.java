import java.time.LocalDate;
import java.time.LocalTime;

public class Ticket {

    private int id_ticket;
    private int id_orden;
    private LocalDate fechaEmision;
    private LocalTime horaEmision;
    private String nombre_cliente;
    private String desglose;
    private double costo_total;
    private String tipo_pago;

    public Ticket() {
    }

    public Ticket(int id_ticket, int id_orden, LocalDate fechaEmision,
                  LocalTime horaEmision, String nombre_cliente,
                  String desglose, double costo_total, String tipo_pago) {

        this.id_ticket = id_ticket;
        this.id_orden = id_orden;
        this.fechaEmision = fechaEmision;
        this.horaEmision = horaEmision;
        this.nombre_cliente = nombre_cliente;
        this.desglose = desglose;
        this.costo_total = costo_total;
        this.tipo_pago = tipo_pago;
    }

    public int getId_ticket() {
        return id_ticket;
    }

    public void setId_ticket(int id_ticket) {
        this.id_ticket = id_ticket;
    }

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public void setFechaEmision(LocalDate fechaEmision) {
        this.fechaEmision = fechaEmision;
    }

    public LocalTime getHoraEmision() {
        return horaEmision;
    }

    public void setHoraEmision(LocalTime horaEmision) {
        this.horaEmision = horaEmision;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getDesglose() {
        return desglose;
    }

    public void setDesglose(String desglose) {
        this.desglose = desglose;
    }

    public double getCosto_total() {
        return costo_total;
    }

    public void setCosto_total(double costo_total) {
        this.costo_total = costo_total;
    }

    public String getTipo_pago() {
        return tipo_pago;
    }

    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago = tipo_pago;
    }

    @Override
    public String toString() {
        return "Ticket{" +"id_ticket=" + id_ticket +", id_orden=" + id_orden +", fechaEmision=" + fechaEmision +", horaEmision=" + horaEmision +", nombre_cliente='" + nombre_cliente + '\'' +", desglose='" + desglose + '\'' +", costo_total=" + costo_total +", tipo_pago='" + tipo_pago + '\'' +'}';
    }
}