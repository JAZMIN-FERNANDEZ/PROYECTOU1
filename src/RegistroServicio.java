import java.time.LocalTime;
import java.time.Duration;
import java.time.format.DateTimeFormatter;

public class RegistroServicio {

    private int id_auto;
    private LocalTime hora_entrada;
    private LocalTime hora_salida;
    private int id_servicio;
    private Duration duracion;          // duración real del servicio
    private String duracionFormateada;  //determinada por HH:mm:ss
    
    private static final DateTimeFormatter FORMATO_HORA =
            DateTimeFormatter.ofPattern("HH:mm:ss");
    
    public RegistroServicio() {
    }

    public RegistroServicio(int id_auto, LocalTime hora_entrada,
                            LocalTime hora_salida, int id_servicio) {
        this.id_auto = id_auto;
        this.hora_entrada = hora_entrada;
        this.hora_salida = hora_salida;
        this.id_servicio = id_servicio;
        
        if (hora_salida != null) {
            calcularDuracion();
        }
        
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
    
    public Duration getDuracion() {
        return duracion;
    }

    /** Duración ya persistida en formato HH:mm:ss (o "-" si aún no hay salida). */
    public String getDuracionFormateada() {
        return duracionFormateada == null ? "-" : duracionFormateada;
    }

    public String getHoraEntradaFormateada() {
        return hora_entrada == null ? "-" : hora_entrada.format(FORMATO_HORA);
    }

    public String getHoraSalidaFormateada() {
        return hora_salida == null ? "-" : hora_salida.format(FORMATO_HORA);
    }

    public boolean estaEnServicio() {
        return hora_entrada != null && hora_salida == null;
    }
    
    
     public void registrarEntrada(){
        this.hora_entrada = LocalTime.now();
        this.hora_salida = null;
        this.duracion = null;
        this.duracionFormateada = null;
    }//registra la entrada del vehiculo con la hora actual

     public void registrarSalida() {
        if (hora_entrada == null) {
            throw new IllegalStateException(
                "No se puede registrar la salida sin haber registrado la entrada.");
        }
        this.hora_salida = LocalTime.now();
        calcularDuracion();
    }//registra la salida y calcula la duracion 
     
     private void calcularDuracion() {
        this.duracion = Duration.between(hora_entrada, hora_salida);
        if (this.duracion.isNegative()) {
            // el servicio cruzó medianoche
            this.duracion = this.duracion.plusHours(24);
        }
        long h = duracion.toHours();
        long m = duracion.toMinutesPart();
        long s = duracion.toSecondsPart();
        this.duracionFormateada = String.format("%02d:%02d:%02d", h, m, s);
    }//calcula la duracion de entrada y salida 
     
     
     
    @Override
    public String toString() {
        return "RegistroServicio{" +"id_auto=" + id_auto +", hora_entrada=" + hora_entrada +", hora_salida=" + hora_salida +", id_servicio=" + id_servicio +", duracion=" + getDuracionFormateada() +'}';
    }
}