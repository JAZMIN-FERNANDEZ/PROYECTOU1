import javax.swing.*;
import java.awt.*;

public class TarjetaClientePanel extends JPanel {

    private String nombre = "-";
    private String telefono = "-";
    private String vehiculo = "-";

    public TarjetaClientePanel() {
        setOpaque(false);
        setPreferredSize(new Dimension(260, 92));
    }

    public void setDatos(String nombreCompleto, String telefono, String vehiculo) {
        this.nombre = (nombreCompleto == null || nombreCompleto.isBlank()) ? "-" : nombreCompleto;
        this.telefono = (telefono == null || telefono.isBlank()) ? "-" : telefono;
        this.vehiculo = (vehiculo == null || vehiculo.isBlank()) ? "-" : vehiculo;
        repaint();
    }

    private String iniciales() {
        String[] partes = nombre.trim().split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String p : partes) {
            if (!p.isEmpty() && sb.length() < 2) {
                sb.append(Character.toUpperCase(p.charAt(0)));
            }
        }
        return sb.length() == 0 ? "?" : sb.toString();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(new Color(45, 45, 62));
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);
        g2.setColor(new Color(90, 90, 112));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 16, 16);

        int diam = 56;
        int avatarY = (getHeight() - diam) / 2;
        g2.setColor(new Color(90, 150, 230));
        g2.fillOval(15, avatarY, diam, diam);
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
        FontMetrics fm = g2.getFontMetrics();
        String ini = iniciales();
        g2.drawString(ini, 15 + diam / 2 - fm.stringWidth(ini) / 2, getHeight() / 2 + fm.getAscent() / 2 - 4);

        int xText = 15 + diam + 16;
        g2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        g2.setColor(Color.WHITE);
        g2.drawString(nombre, xText, 30);

        g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        g2.setColor(new Color(210, 210, 220));
        g2.drawString("Tel: " + telefono, xText, 50);
        g2.drawString("Vehículo: " + vehiculo, xText, 68);

        g2.dispose();
    }
}
