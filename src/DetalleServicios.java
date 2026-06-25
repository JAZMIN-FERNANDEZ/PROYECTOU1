public class DetalleServicios {

    private int id_servicio;
    private int id_auto;
    private int id_orden;
    private int id_cliente;

    public DetalleServicios() {
    }

    public DetalleServicios(int id_servicio, int id_auto,
                            int id_orden, int id_cliente) {
        this.id_servicio = id_servicio;
        this.id_auto = id_auto;
        this.id_orden = id_orden;
        this.id_cliente = id_cliente;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    public int getId_auto() {
        return id_auto;
    }

    public void setId_auto(int id_auto) {
        this.id_auto = id_auto;
    }

    public int getId_orden() {
        return id_orden;
    }

    public void setId_orden(int id_orden) {
        this.id_orden = id_orden;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    @Override
    public String toString() {
        return "DetalleServicios{" +"id_servicio=" + id_servicio +", id_auto=" + id_auto +", id_orden=" + id_orden +", id_cliente=" + id_cliente +'}';
    }
}