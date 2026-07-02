/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 * Controlador único de la aplicación (fusiona lo que antes eran
 * clienteControlador, autoControlador y serviciosControlador).
 *
 * Se unifica en una sola clase para respetar mejor el patrón MVC:
 * la Vista ya no necesita conocer tres controladores distintos ni
 * exponer getters cruzados entre ellos (getControladorAuto(),
 * getControladorServicios()), sino que solo conversa con un único
 * Controlador.
 *
 * @author Usuario
 */

import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class Controlador {

    private final Vista vista;

    // ---------- Estado: Clientes ----------
    private int contadorCliente = 1;
    private ArrayList<Cliente> listaClientes;

    // ---------- Estado: Autos ----------
    private ArrayList<Auto> todosLosAutos;          // todos los autos de todos los clientes
    private ArrayList<Auto> autosClienteActivo;      // autos del cliente activo (pendientes de guardar)
    private int idClienteActivo;
    private String nombreClienteActivo;
    private int contadorAuto = 1;
    private ArrayList<Integer> idsClientes = new ArrayList<>();
    private ArrayList<String> nombresClientes = new ArrayList<>();

    // ---------- Estado: Servicios ----------
    private ArrayList<Auto> listaAutosServicio;
    private HashMap<Integer, ArrayList<String>> serviciosPorAuto;

    public Controlador(Vista vista) {
        this.vista = vista;
        this.listaClientes = new ArrayList<>();
        this.todosLosAutos = new ArrayList<>();
        this.autosClienteActivo = new ArrayList<>();
        this.serviciosPorAuto = new HashMap<>();

        generarIdCliente();
        vista.inicializarTablaAutos();
    }

    // =========================================================
    //  CLIENTES
    // =========================================================

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
        setClienteActivo(idCliente, nombre + " " + apellido);

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
        LocalDate fecha = LocalDate.now();

        // Autos de este cliente (ya no hace falta pasar por la vista)
        ArrayList<Auto> autos = getTodosLosAutos();

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

        for (Auto auto : autos) {
            // Solo autos de este cliente
            if (auto.getId_cliente() != c.getId_cliente()) continue;

            ArrayList<String> servicios = serviciosPorAuto.get(auto.getId_auto());
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
                    case "Sedán": return 150;
                    case "SUV": return 200;
                    case "Pickup": return 230;
                    case "Moto": return 100;
                }
            case "Lavado Interior":
                switch (tipo) {
                    case "Sedán": return 120;
                    case "SUV": return 180;
                    case "Pickup": return 200;
                }
            case "Encerado":
                switch (tipo) {
                    case "Sedán": return 200;
                    case "SUV": return 320;
                    case "Pickup": return 380;
                    case "Moto": return 70;
                }
            case "Pulido de Faros":
                switch (tipo) {
                    case "Sedán": return 100;
                    case "SUV": return 100;
                    case "Pickup": return 100;
                    case "Moto": return 50;
                }
            default:
                return 0;
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

    // =========================================================
    //  AUTOS
    // =========================================================

    public void setClienteActivo(int idCliente, String nombreCompleto) {
        idsClientes.add(idCliente);
        nombresClientes.add(nombreCompleto);

        // Puebla el combo y lo deja seleccionado en el recién registrado
        vista.agregarClienteAlCombo(idCliente, nombreCompleto);

        this.idClienteActivo = idCliente;
        this.nombreClienteActivo = nombreCompleto;
    }

    public void onComboClienteChanged() {
        int idx = vista.getComboClientes().getSelectedIndex();
        if (idx < 0 || idx >= idsClientes.size()) return;

        idClienteActivo = idsClientes.get(idx);
        nombreClienteActivo = nombresClientes.get(idx);

        // Actualiza el label debajo del combo
        vista.setClienteActivo(nombreClienteActivo);
    }

    public int getSiguienteIdAuto() {
        return contadorAuto++;
    }

    public void guardarAutos() {
        DefaultTableModel modelo = (DefaultTableModel) vista.getTablaAutos().getModel();
        int filas = modelo.getRowCount();

        if (filas == 0) {
            JOptionPane.showMessageDialog(vista, "Agrega al menos un auto en la tabla.");
            return;
        }

        // Limpiar toda la lista para evitar duplicados en cada guardado
        todosLosAutos.clear();
        autosClienteActivo = new ArrayList<>();

        for (int i = 0; i < filas; i++) {
            String idStr = String.valueOf(modelo.getValueAt(i, 0)).trim();
            String clienteNom = String.valueOf(modelo.getValueAt(i, 1)).trim();
            String modelo_auto = String.valueOf(modelo.getValueAt(i, 2)).trim();
            String color = String.valueOf(modelo.getValueAt(i, 3)).trim();
            String tipo = String.valueOf(modelo.getValueAt(i, 4)).trim();
            String obs = String.valueOf(modelo.getValueAt(i, 5)).trim();
            String citaStr = String.valueOf(modelo.getValueAt(i, 6)).trim().toLowerCase();
            String horaStr = String.valueOf(modelo.getValueAt(i, 7)).trim();
            String fechaStr = String.valueOf(modelo.getValueAt(i, 8)).trim();

            if (idStr.isEmpty() || modelo_auto.isEmpty() || color.isEmpty() || tipo.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Fila " + (i + 1) + ": completa ID, Modelo, Color y Tipo.");
                return;
            }

            int idAuto;
            try {
                idAuto = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista, "Fila " + (i + 1) + ": el ID debe ser un número.");
                return;
            }

            // Buscar el id_cliente que corresponde al nombre en la columna Cliente
            int idCliente = -1;
            for (int j = 0; j < nombresClientes.size(); j++) {
                if (nombresClientes.get(j).equals(clienteNom)) {
                    idCliente = idsClientes.get(j);
                    break;
                }
            }
            if (idCliente == -1) {
                JOptionPane.showMessageDialog(vista, "Fila " + (i + 1) + ": cliente \"" + clienteNom + "\" no reconocido.");
                return;
            }

            boolean cita = citaStr.equals("s");
            LocalTime hora = null;
            if (!horaStr.isEmpty() && !horaStr.equals("null")) {
                try {
                    hora = LocalTime.parse(horaStr);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(vista, "Fila " + (i + 1) + ": hora inválida, usa HH:mm");
                    return;
                }
            }

            LocalDate fecha = LocalDate.now();
            if (!fechaStr.isEmpty() && !fechaStr.equals("null")) {
                try {
                    fecha = LocalDate.parse(fechaStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(vista, "Fila " + (i + 1) + ": fecha inválida, usa dd/MM/yyyy");
                    return;
                }
            }

            Auto auto = new Auto(idAuto, modelo_auto, color, tipo, obs, cita, hora);
            auto.setId_cliente(idCliente);
            auto.setFecha(fecha);
            todosLosAutos.add(auto);

            if (idCliente == idClienteActivo) {
                autosClienteActivo.add(auto);
            }
        }

        JOptionPane.showMessageDialog(vista, "Autos guardados: " + todosLosAutos.size());
        vista.habilitarTab(2);
        cargarTodosLosAutos(todosLosAutos);
    }

    /** Autos del cliente activo (para la pantalla de servicios) */
    public ArrayList<Auto> getListaAutos() {
        return autosClienteActivo;
    }

    /** Devuelve el nombre completo del cliente dado su id */
    public String getNombreCliente(int idCliente) {
        for (int i = 0; i < idsClientes.size(); i++) {
            if (idsClientes.get(i) == idCliente) {
                return nombresClientes.get(i);
            }
        }
        return "Desconocido";
    }

    /** Todos los autos de todos los clientes (para el ticket) */
    public ArrayList<Auto> getTodosLosAutos() {
        return todosLosAutos;
    }

    // =========================================================
    //  SERVICIOS
    // =========================================================

    public void cargarAutos(ArrayList<Auto> autos, String nombreCliente) {
        this.listaAutosServicio = autos;
        vista.agregarAutosEnServicios(autos, nombreCliente);
    }

    public void cargarTodosLosAutos(ArrayList<Auto> todos) {
        javax.swing.table.DefaultTableModel modelo =
                (javax.swing.table.DefaultTableModel) vista.getTablaAutosServicios().getModel();

        modelo.setRowCount(0); // limpia para evitar duplicados

        for (Auto a : todos) {
            String nombreCliente = getNombreCliente(a.getId_cliente());
            modelo.addRow(new Object[]{
                nombreCliente,
                a.getId_auto(),
                a.getModelo(),
                a.getColor(),
                a.getTipo(),
                obtenerTextoServicios(a.getId_auto())
            });
        }
    }

    /** Devuelve los servicios asignados a un auto como texto para la tabla */
    private String obtenerTextoServicios(int idAuto) {
        ArrayList<String> servicios = serviciosPorAuto.get(idAuto);
        if (servicios == null || servicios.isEmpty()) {
            return "Sin asignar";
        }
        return String.join(", ", servicios);
    }

    public void asignarServicio() {
        int fila = vista.getTablaAutosServicios().getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Selecciona un auto de la lista.");
            return;
        }

        int idAuto = (int) vista.getTablaAutosServicios().getValueAt(fila, 1);
        String tipoAuto = (String) vista.getTablaAutosServicios().getValueAt(fila, 4);

        ArrayList<String> servicios = new ArrayList<>();
        if (vista.getCheckExterior().isSelected()) servicios.add("Lavado Exterior");
        if (vista.getCheckInterior().isSelected()) servicios.add("Lavado Interior");
        if (vista.getCheckEncerado().isSelected()) servicios.add("Encerado");
        if (vista.getCheckPulido().isSelected()) servicios.add("Pulido de Faros");

        if (tipoAuto.equalsIgnoreCase("Moto")) {
            servicios.remove("Lavado Interior");
            vista.getCheckInterior().setSelected(false);
            vista.getCheckInterior().setEnabled(false);
        }

        if (servicios.isEmpty()) {
            JOptionPane.showMessageDialog(vista, "Selecciona al menos un servicio.");
            return;
        }

        serviciosPorAuto.put(idAuto, servicios);

        // Refleja de inmediato los servicios asignados en la columna "Servicios"
        javax.swing.table.DefaultTableModel modelo =
                (javax.swing.table.DefaultTableModel) vista.getTablaAutosServicios().getModel();
        modelo.setValueAt(obtenerTextoServicios(idAuto), fila, 5);

        vista.getCheckExterior().setSelected(false);
        vista.getCheckInterior().setSelected(false);
        vista.getCheckEncerado().setSelected(false);
        vista.getCheckPulido().setSelected(false);

        JOptionPane.showMessageDialog(vista, "Servicios asignados al auto " + idAuto + ": " + servicios);
    }

    public HashMap<Integer, ArrayList<String>> getServiciosPorAuto() {
        return serviciosPorAuto;
    }

    public void cargarServiciosDelAuto() {
        int fila = vista.getTablaAutosServicios().getSelectedRow();
        if (fila == -1) {
            vista.getCheckInterior().setEnabled(true);
            return;
        }

        String tipoAuto = (String) vista.getTablaAutosServicios().getValueAt(fila, 4);
        boolean esMoto = tipoAuto != null && tipoAuto.equalsIgnoreCase("Moto");
        vista.getCheckInterior().setEnabled(!esMoto);
        if (esMoto) vista.getCheckInterior().setSelected(false);
    }
}
