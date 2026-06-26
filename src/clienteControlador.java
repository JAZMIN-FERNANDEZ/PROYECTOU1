import java.util.ArrayList;
import javax.swing.JOptionPane;

public class clienteControlador {
    private Vista vista;
    private int contadorCliente = 1;
    private ArrayList<Cliente> listaClientes;

    public clienteControlador(Vista vista) {
        this.vista = vista;
        listaClientes = new ArrayList<>();
        generarIdCliente();
    }

    public void generarIdCliente() {
        String idFormateado = String.format("%04d", contadorCliente);
        vista.getIdCliente().setText(idFormateado);
        vista.getIdCliente().setEditable(false);
    }

    public void JButton_RegistroCliente() {
        String id = vista.getIdCliente().getText().trim();
        String nombre = vista.getNombre().getText().trim();
        String apellido = vista.getApellido().getText().trim();
        String telefono = vista.getTelefono().getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el nombre");
            return;
        }
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+")) {
            JOptionPane.showMessageDialog(vista, "El nombre solo debe contener letras");
            return;
        }
        if (apellido.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el apellido");
            return;
        }
        if (!apellido.matches("[a-zA-ZáéíóúÁÉÍÓÚüÜñÑ ]+")) {
            JOptionPane.showMessageDialog(vista, "El apellido solo debe contener letras");
            return;
        }
        if (telefono.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el teléfono");
            return;
        }
        if (!telefono.matches("\\d{10}")) {
            JOptionPane.showMessageDialog(vista, "El teléfono debe tener exactamente 10 dígitos");
            return;
        }
        if (!telefono.matches("\\d+")) {
            JOptionPane.showMessageDialog(vista, "El teléfono solo debe contener números");
            return;
        }

        int idCliente = Integer.parseInt(id);

        for (Cliente c : listaClientes) {
            if (c.getId_cliente() == idCliente) {
                JOptionPane.showMessageDialog(vista, "Ya existe un cliente con ese ID");
                return;
            }
        }

        Cliente cliente = new Cliente();
        cliente.setId_cliente(idCliente);
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setTelefono(telefono);
        listaClientes.add(cliente);

        // Agregar a la tabla
        vista.getModeloTabla().addRow(new Object[]{idCliente, nombre, apellido, telefono});

        JOptionPane.showMessageDialog(vista, "Cliente registrado correctamente. Total: " + listaClientes.size());
        contadorCliente++;
        vista.habilitarTab(1);
        vista.getControladorAuto().setClienteActivo(idCliente, nombre + " " + apellido);

        limpiar();
        generarIdCliente();
    }

  public void mostrarTicket(int filaSeleccionada) {
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(vista,
            "Selecciona un cliente de la tabla primero.",
            "Sin selección",
            JOptionPane.WARNING_MESSAGE);
        return;
    }

    Cliente c = listaClientes.get(filaSeleccionada);
    java.time.LocalDate fecha = java.time.LocalDate.now();

    // Obtener TODOS los autos y filtrar solo los de este cliente
    java.util.ArrayList<Auto> todosAutos = vista.getControladorAuto().getTodosLosAutos();
    java.util.HashMap<Integer, java.util.ArrayList<String>> serviciosPorAuto = 
        vista.getControladorServicios().getServiciosPorAuto();

    StringBuilder ticket = new StringBuilder();
    ticket.append("=====================================\n");
    ticket.append("        LAVAUTOS EL PANTERA          \n");
    ticket.append("=====================================\n");
    ticket.append("CLIENTE:   ").append(c.getNombre()).append(" ").append(c.getApellido()).append("\n");
    ticket.append("TELÉFONO:  ").append(c.getTelefono()).append("\n");
    ticket.append("FECHA:     ").append(fecha).append("\n");
    ticket.append("-------------------------------------\n");

    double total = 0;
    boolean tieneServicios = false;

    for (Auto auto : todosAutos) {
        // Solo autos de este cliente
        if (auto.getId_cliente() != c.getId_cliente()) continue;

        java.util.ArrayList<String> servicios = serviciosPorAuto.get(auto.getId_auto());
        if (servicios == null || servicios.isEmpty()) continue;

        tieneServicios = true;
        ticket.append("\nVEHÍCULO:  ").append(auto.getModelo())
              .append(" (").append(auto.getTipo()).append(") - ")
              .append(auto.getColor()).append("\n");

        for (String servicio : servicios) {
            double precio = getPrecio(servicio, auto.getTipo());
            ticket.append("  • ").append(servicio)
                  .append(" ............. $").append(String.format("%.2f", precio)).append("\n");
            total += precio;
        }
    }

    if (!tieneServicios) {
        ticket.append("\n  (Sin servicios asignados)\n");
    }

    ticket.append("\n=====================================\n");
    ticket.append("  TOTAL:          $").append(String.format("%.2f", total)).append("\n");
    ticket.append("=====================================\n");

    javax.swing.JTextArea texto = new javax.swing.JTextArea(ticket.toString());
    texto.setFont(new java.awt.Font("Monospaced", 0, 13));
    texto.setEditable(false);

    JOptionPane.showMessageDialog(vista, texto, "Ticket", JOptionPane.INFORMATION_MESSAGE);
}

private double getPrecio(String servicio, String tipo) {
    switch (servicio) {
        case "Lavado Exterior":
            switch (tipo) {
                case "Sedán":   return 150;
                case "SUV":     return 200;
                case "Pickup":  return 230;
                case "Moto":    return 100;
            }
        case "Lavado Interior":
            switch (tipo) {
                case "Sedán":   return 120;
                case "SUV":     return 180;
                case "Pickup":  return 200;
            }
        case "Encerado":
            switch (tipo) {
                case "Sedán":   return 200;
                case "SUV":     return 320;
                case "Pickup":  return 380;
                case "Moto":    return 70;
            }
        case "Pulido de Faros":
            switch (tipo) {
                case "Sedán":   return 100;
                case "SUV":     return 100;
                case "Pickup":  return 100;
                case "Moto":    return 50;
            }
        default: return 0;
    }
}

    
    private void limpiar() {
        vista.getIdCliente().setText("");
        vista.getNombre().setText("");
        vista.getApellido().setText("");
        vista.getTelefono().setText("");
    }

    public ArrayList<Cliente> getListaClientes() {
        return listaClientes;
    }
}