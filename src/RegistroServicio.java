import java.time.LocalTime;

public class RegistroServicio {

    private int id_auto;
    private LocalTime hora_entrada;
    private LocalTime hora_salida;
    private int id_servicio;

    public RegistroServicio() {
    }

    public RegistroServicio(int id_auto, LocalTime hora_entrada,
                            LocalTime hora_salida, int id_servicio) {
        this.id_auto = id_auto;
        this.hora_entrada = hora_entrada;
        this.hora_salida = hora_salida;
        this.id_servicio = id_servicio;
    }

    public int getId_auto() {
        return id_auto;
    }

    public void setId_auto(int id_auto) {
        this.id_auto = id_auto;
    }

    public LocalTime getHora_entrada() {
        return hora_entrada;
    }

    public void setHora_entrada(LocalTime hora_entrada) {
        this.hora_entrada = hora_entrada;
    }

    public LocalTime getHora_salida() {
        return hora_salida;
    }

    public void setHora_salida(LocalTime hora_salida) {
        this.hora_salida = hora_salida;
    }

    public int getId_servicio() {
        return id_servicio;
    }

    public void setId_servicio(int id_servicio) {
        this.id_servicio = id_servicio;
    }

    @Override
    public String toString() {
        return "RegistroServicio{" +"id_auto=" + id_auto +", hora_entrada=" + hora_entrada +", hora_salida=" + hora_salida +", id_servicio=" + id_servicio +'}';
    }
}