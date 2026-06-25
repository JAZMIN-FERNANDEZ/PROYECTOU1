public class Servicios {

    private int id_servicio;
    private String nombre_tipo;
    private double costo;
    private String extras;

    public Servicios() {
    }

    public Servicios(int id_servicio, String nombre_tipo, double costo, String extras) {
        this.id_servicio = id_servicio;
        this.nombre_tipo = nombre_tipo;
        this.costo = costo;
        this.extras = extras;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public String getNombre_tipo() {
        return nombre_tipo;
    }

    public void setNombre_tipo(String nombre_tipo) {
        this.nombre_tipo = nombre_tipo;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public String getExtras() {
        return extras;
    }

    public void setExtras(String extras) {
        this.extras = extras;
    }

    @Override
    public String toString() {
        return "Servicios{" +"id_servicio=" + id_servicio +", nombre_tipo='" + nombre_tipo + '\'' +", costo=" + costo +", extras='" + extras + '\'' +'}';
    }
}