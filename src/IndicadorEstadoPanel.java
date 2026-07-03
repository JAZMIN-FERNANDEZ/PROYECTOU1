import javax.swing.*;
import java.awt.*;

/**
 * Componente propio (Vista): indicador de estado del vehículo.
 *
 * Dibuja una "pastilla" con un punto de color y un texto que reflejan
 * si el vehículo está SIN_REGISTRAR, EN_SERVICIO o FINALIZADO. Cuando
 * está EN_SERVICIO, el punto pulsa (cambia de opacidad) para dar
 * retroalimentación visual de que el proceso sigue activo.
 */
public class IndicadorEstadoPanel extends JPanel {

    public enum Estado { SIN_REGISTRAR, EN_SERVICIO, FINALIZADO }

    private Estado estado = Estado.SIN_REGISTRAR;
    private final Timer pulso;
    private float fase = 0f;

    public IndicadorEstadoPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(220, 42));
        pulso = new Timer(80, e -> {
            fase += 0.18f;
            repaint();
        });
    }

    public void setEstado(Estado nuevo) {
        this.estado = nuevo;
        if (nuevo == Estado.EN_SERVICIO) {
            if (!pulso.isRunning()) pulso.start();
        } else {
            pulso.stop();
        }
        repaint();
    }

    public Estado getEstado() {
        return estado;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color base;
        String texto;
        switch (estado) {
            case EN_SERVICIO:
                base = new Color(255, 170, 40);
                texto = "EN SERVICIO";
                break;
            case FINALIZADO:
                base = new Color(70, 190, 110);
                texto = "FINALIZADO";
                break;
            default:
                base = new Color(150, 150, 150);
                texto = "SIN REGISTRAR";
        }

        // Fondo tipo "pastilla"
        g2.setColor(new Color(40, 40, 54));
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());
        g2.setColor(new Color(90, 90, 110));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, getHeight(), getHeight());

        int diam = 16;
        int cy = getHeight() / 2;
        float alpha = (estado == Estado.EN_SERVICIO)
                ? (float) (0.45 + 0.55 * Math.abs(Math.sin(fase)))
                : 1f;
        g2.setColor(new Color(base.getRed(), base.getGreen(), base.getBlue(), (int) (alpha * 255)));
        g2.fillOval(14, cy - diam / 2, diam, diam);
        g2.setColor(base.darker());
        g2.drawOval(14, cy - diam / 2, diam, diam);

        g2.setColor(new Color(240, 240, 245));
        g2.setFont(new Font("Segoe UI", Font.BOLD, 13));
        g2.drawString(texto, 14 + diam + 12, cy + 5);
        g2.dispose();
    }
}
