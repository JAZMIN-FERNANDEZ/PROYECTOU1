import javax.swing.*;
import java.awt.*;
import java.time.Duration;
import java.time.LocalTime;

public class ContadorServicioPanel extends JPanel {

    private LocalTime horaEntrada;
    private LocalTime horaSalida;

    private final JLabel lblTiempo;
    private final JLabel lblTitulo;
    private final JLabel lblEstado;
    private final Timer timer;

    public ContadorServicioPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(240, 95));
        setLayout(new BorderLayout());

        lblTitulo = new JLabel("TIEMPO EN SERVICIO", SwingConstants.CENTER);
        lblTitulo.setForeground(new Color(225, 225, 230));
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 11));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        lblTiempo = new JLabel("00:00:00", SwingConstants.CENTER);
        lblTiempo.setForeground(Color.WHITE);
        lblTiempo.setFont(new Font("Consolas", Font.BOLD, 32));

        lblEstado = new JLabel("● Detenido", SwingConstants.CENTER);
        lblEstado.setForeground(new Color(200, 200, 200));
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblEstado.setBorder(BorderFactory.createEmptyBorder(0, 0, 8, 0));

        add(lblTitulo, BorderLayout.NORTH);
        add(lblTiempo, BorderLayout.CENTER);
        add(lblEstado, BorderLayout.SOUTH);

        // Se refresca cada segundo mientras el vehículo sigue en servicio
        timer = new Timer(1000, e -> actualizarTexto());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        GradientPaint gp = new GradientPaint(0, 0, new Color(32, 32, 44), 0, getHeight(), new Color(58, 58, 80));
        g2.setPaint(gp);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);

        g2.setColor(new Color(95, 95, 115));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 18, 18);
        g2.dispose();
    }

    /** Inicia (o reanuda) el conteo a partir de la hora de entrada indicada. */
    public void iniciar(LocalTime desde) {
        this.horaEntrada = desde;
        this.horaSalida = null;
        lblEstado.setForeground(new Color(95, 220, 125));
        lblEstado.setText("● En servicio");
        actualizarTexto();
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    /** Detiene el conteo fijando la hora de salida y muestra la duración final. */
    public void detener(LocalTime hasta) {
        this.horaSalida = hasta;
        timer.stop();
        lblEstado.setForeground(new Color(235, 95, 95));
        lblEstado.setText("● Finalizado");
        actualizarTexto();
    }

    /** Deja el contador en su estado inicial (sin vehículo seleccionado). */
    public void reiniciar() {
        timer.stop();
        horaEntrada = null;
        horaSalida = null;
        lblTiempo.setText("00:00:00");
        lblEstado.setForeground(new Color(200, 200, 200));
        lblEstado.setText("● Detenido");
    }

    private void actualizarTexto() {
        if (horaEntrada == null) {
            lblTiempo.setText("00:00:00");
            return;
        }
        LocalTime fin = (horaSalida != null) ? horaSalida : LocalTime.now();
        Duration d = Duration.between(horaEntrada, fin);
        if (d.isNegative()) {
            d = d.plusHours(24); // el servicio cruzó medianoche
        }
        long h = d.toHours();
        long m = d.toMinutesPart();
        long s = d.toSecondsPart();
        lblTiempo.setText(String.format("%02d:%02d:%02d", h, m, s));
    }

    public String getTiempoFormateado() {
        return lblTiempo.getText();
    }
}
