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

        String ticket =
            "=============================\n" +
            "       TICKET CLIENTE        \n" +
            "=============================\n" +
            "ID:        " + c.getId_cliente() + "\n" +
            "Nombre:    " + c.getNombre()     + "\n" +
            "Apellido:  " + c.getApellido()   + "\n" +
            "Teléfono:  " + c.getTelefono()   + "\n" +
            "=============================";

        javax.swing.JTextArea texto = new javax.swing.JTextArea(ticket);
        texto.setFont(new java.awt.Font("Monospaced", 0, 14));
        texto.setEditable(false);

        JOptionPane.showMessageDialog(vista, texto,
            "Ticket del Cliente",
            JOptionPane.INFORMATION_MESSAGE);
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