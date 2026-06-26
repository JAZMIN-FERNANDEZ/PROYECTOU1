/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */

import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class autoControlador {
    private Vista vista;
    // Todos los autos de todos los clientes
    private ArrayList<Auto> todosLosAutos;
    // Autos del cliente activo (pendientes de guardar)
    private ArrayList<Auto> autosClienteActivo;
    private int idClienteActivo;
    private String nombreClienteActivo;
    private int contadorAuto = 1;

    public autoControlador(Vista vista) {
        this.vista = vista;
        this.todosLosAutos = new ArrayList<>();
        this.autosClienteActivo = new ArrayList<>();
        vista.inicializarTablaAutos();
    }

    public void setClienteActivo(int idCliente, String nombreCompleto) {
    this.idClienteActivo = idCliente;
    this.nombreClienteActivo = nombreCompleto;
    vista.setClienteActivo(nombreCompleto);
    
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

        // Quitar autos previos de este cliente (re-guardado)
        todosLosAutos.removeIf(a -> a.getId_cliente() == idClienteActivo);
        autosClienteActivo = new ArrayList<>();

        for (int i = 0; i < filas; i++) {
            // Columnas: 0=ID Auto, 1=Cliente(display), 2=Modelo, 3=Color, 4=Tipo, 5=Obs, 6=Cita, 7=Hora
            String idStr       = String.valueOf(modelo.getValueAt(i, 0)).trim();
            String modelo_auto = String.valueOf(modelo.getValueAt(i, 2)).trim();
            String color       = String.valueOf(modelo.getValueAt(i, 3)).trim();
            String tipo        = String.valueOf(modelo.getValueAt(i, 4)).trim();
            String obs         = String.valueOf(modelo.getValueAt(i, 5)).trim();
            String citaStr     = String.valueOf(modelo.getValueAt(i, 6)).trim().toLowerCase();
            String horaStr     = String.valueOf(modelo.getValueAt(i, 7)).trim();

            if (idStr.isEmpty() || modelo_auto.isEmpty() || color.isEmpty() || tipo.isEmpty()) {
                JOptionPane.showMessageDialog(vista, "Fila " + (i+1) + ": completa ID, Modelo, Color y Tipo.");
                return;
            }

            int idAuto;
            try {
                idAuto = Integer.parseInt(idStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(vista, "Fila " + (i+1) + ": el ID debe ser un número.");
                return;
            }

            boolean cita = citaStr.equals("s");

            LocalTime hora = null;
            if (!horaStr.isEmpty() && !horaStr.equals("null")) {
                try {
                    hora = LocalTime.parse(horaStr);
                } catch (DateTimeParseException e) {
                    JOptionPane.showMessageDialog(vista, "Fila " + (i+1) + ": hora inválida, usa formato HH:mm");
                    return;
                }
            }

            Auto auto = new Auto(idAuto, modelo_auto, color, tipo, obs, cita, hora);
            auto.setId_cliente(idClienteActivo);
            autosClienteActivo.add(auto);
            todosLosAutos.add(auto);
        }

        JOptionPane.showMessageDialog(vista, "Autos guardados: " + autosClienteActivo.size());
        vista.habilitarTab(2);
        vista.getControladorServicios().cargarAutos(autosClienteActivo, nombreClienteActivo);
    }

    /** Autos del cliente activo (para la pantalla de servicios) */
    public ArrayList<Auto> getListaAutos() {
        return autosClienteActivo;
    }

    /** Todos los autos de todos los clientes (para el ticket) */
    public ArrayList<Auto> getTodosLosAutos() {
        return todosLosAutos;
    }
}