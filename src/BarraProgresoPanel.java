import javax.swing.*;
import java.awt.*;

public class BarraProgresoPanel extends JPanel {

    private int completados = 0;
    private int total = 4;
    private String etiqueta = "Servicios asignados";

    public BarraProgresoPanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(240, 58));
    }

    public void setProgreso(int completados, int total) {
        this.completados = Math.max(0, completados);
        this.total = Math.max(1, total);
        repaint();
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int barY = 26, barH = 18, barW = getWidth() - 10;

        g2.setColor(new Color(230, 230, 235));
        g2.setFont(new Font("Segoe UI", Font.BOLD, 12));
        int pct = (int) Math.round(100.0 * completados / total);
        g2.drawString(etiqueta + "  (" + completados + "/" + total + " · " + pct + "%)", 5, 16);

        // Riel de fondo
        g2.setColor(new Color(60, 60, 78));
        g2.fillRoundRect(5, barY, barW, barH, 12, 12);

        // Relleno proporcional
        double proporcion = (double) completados / total;
        int fillW = (int) Math.round(barW * proporcion);
        if (fillW > 0) {
            GradientPaint gp = new GradientPaint(5, 0, new Color(80, 180, 255), 5 + fillW, 0, new Color(95, 220, 150));
            g2.setPaint(gp);
            g2.fillRoundRect(5, barY, fillW, barH, 12, 12);
        }

        g2.setColor(new Color(25, 25, 34));
        g2.drawRoundRect(5, barY, barW, barH, 12, 12);
        g2.dispose();
    }
}