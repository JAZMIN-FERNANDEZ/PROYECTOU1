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
    private ArrayList<Auto> listaAutos;
    private int idClienteActivo;
    private int contadorAuto = 1;

    public autoControlador(Vista vista) {
        this.vista = vista;
        this.listaAutos = new ArrayList<>();
        vista.inicializarTablaAutos();
    }

    public void setClienteActivo(int idCliente, String nombreCompleto) {
        this.idClienteActivo = idCliente;
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

        listaAutos.clear();

        for (int i = 0; i < filas; i++) {
            String idStr       = String.valueOf(modelo.getValueAt(i, 0)).trim();
            String modelo_auto = String.valueOf(modelo.getValueAt(i, 1)).trim();
            String color       = String.valueOf(modelo.getValueAt(i, 2)).trim();
            String tipo        = String.valueOf(modelo.getValueAt(i, 3)).trim();
            String obs         = String.valueOf(modelo.getValueAt(i, 4)).trim();
            String citaStr     = String.valueOf(modelo.getValueAt(i, 5)).trim().toLowerCase();
            String horaStr     = String.valueOf(modelo.getValueAt(i, 6)).trim();

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
            listaAutos.add(auto);
        }

        JOptionPane.showMessageDialog(vista, "Autos guardados: " + listaAutos.size());
        vista.habilitarTab(2); 
        vista.getControladorServicios().cargarAutos(listaAutos, vista.getClienteActivo());
    }

    public ArrayList<Auto> getListaAutos() {
        return listaAutos;
    }
}