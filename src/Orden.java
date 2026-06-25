public class Orden {

    private int id_orden;
    private String tipo_pago;
    private double costo_final;

    public Orden() {
    }

    public Orden(int id_orden, String tipo_pago, double costo_final) {
        this.id_orden = id_orden;
        this.tipo_pago = tipo_pago;
        this.costo_final = costo_final;
    }

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public String getTipo_pago() {
        return tipo_pago;
    }

    public void setTipo_pago(String tipo_pago) {
        this.tipo_pago = tipo_pago;
    }

    public double getCosto_final() {
        return costo_final;
    }

    public void setCosto_final(double costo_final) {
        this.costo_final = costo_final;
    }

    @Override
    public String toString() {
        return "Orden{" +"id_orden=" + id_orden +", tipo_pago='" + tipo_pago + '\'' +", costo_final=" + costo_final +'}';
    }
}