import java.util.ArrayList;
import javax.swing.JOptionPane;

public class clienteControlador {
    private Vista vista;
    private ArrayList<Cliente> listaClientes;

    public clienteControlador(Vista vista) {
        this.vista = vista;
        listaClientes = new ArrayList<>();
    }

    public void JButton_RegistroCliente() {
        String id = vista.getIdCliente().getText().trim();
        String nombre = vista.getNombre().getText().trim();
        String apellido = vista.getApellido().getText().trim();
        String telefono = vista.getTelefono().getText().trim();

        if (id.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el ID del cliente");
            return;
        }
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el nombre");
            return;
        }
        if (apellido.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el apellido");
            return;
        }
        if (telefono.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Ingrese el teléfono");
            return;
        }
        if (!id.matches("\\d+")) {
            JOptionPane.showMessageDialog(vista, "El ID solo debe contener números");
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
        vista.habilitarTab(1);
        vista.getControladorAuto().setClienteActivo(idCliente, nombre + " " + apellido);

        limpiar();
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

    // Obtener autos y servicios
    java.util.ArrayList<Auto> autos = vista.getControladorAuto().getListaAutos();
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

    for (Auto auto : autos) {
        java.util.ArrayList<String> servicios = serviciosPorAuto.get(auto.getId_auto());
        if (servicios == null || servicios.isEmpty()) continue;

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