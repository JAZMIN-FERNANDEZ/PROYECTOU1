/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author jazmi
 */
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
        String id = vista.getIdCliente().getText();
        String nombre = vista.getNombre().getText();
        String apellido = vista.getApellido().getText();
        String telefono = vista.getTelefono().getText();

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
        JOptionPane.showMessageDialog(vista,
                "Cliente registrado correctamente "
                + listaClientes.size());
        limpiar();
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