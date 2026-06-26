/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Usuario
 */

import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

public class serviciosControlador {
    private Vista vista;
    private ArrayList<Auto> listaAutos;
    private HashMap<Integer, ArrayList<String>> serviciosPorAuto;

    public serviciosControlador(Vista vista) {
        this.vista = vista;
        this.serviciosPorAuto = new HashMap<>();
    }

    public void cargarAutos(ArrayList<Auto> autos, String nombreCliente) {
        this.listaAutos = autos;
        vista.cargarAutosEnServicios(autos, nombreCliente);
    }

    public void asignarServicio() {
    int fila = vista.getTablaAutosServicios().getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(vista, "Selecciona un auto de la lista.");
        return;
    }

    ArrayList<String> servicios = new ArrayList<>();
    if (vista.getCheckExterior().isSelected()) servicios.add("Lavado Exterior");
    if (vista.getCheckInterior().isSelected()) servicios.add("Lavado Interior");
    if (vista.getCheckEncerado().isSelected())  servicios.add("Encerado");
    if (vista.getCheckPulido().isSelected())    servicios.add("Pulido de Faros");
    
     Auto auto = listaAutos.get(fila);
    if (auto.getTipo().equalsIgnoreCase("Moto")) {
        servicios.remove("Lavado Interior");
        vista.getCheckInterior().setSelected(false);
        vista.getCheckInterior().setEnabled(false);
    }

    if (servicios.isEmpty()) {
        JOptionPane.showMessageDialog(vista, "Selecciona al menos un servicio.");
        return;
    }

    int idAuto = listaAutos.get(fila).getId_auto();
    serviciosPorAuto.put(idAuto, servicios);

    // Limpiar checkboxes
    vista.getCheckExterior().setSelected(false);
    vista.getCheckInterior().setSelected(false);
    vista.getCheckEncerado().setSelected(false);
    vista.getCheckPulido().setSelected(false);

    JOptionPane.showMessageDialog(vista, "Servicios asignados al auto " + idAuto + ": " + servicios);
    vista.habilitarTab(3);
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

    Auto auto = listaAutos.get(fila);
    boolean esMoto = auto.getTipo().equalsIgnoreCase("Moto");
    vista.getCheckInterior().setEnabled(!esMoto);
    if (esMoto) vista.getCheckInterior().setSelected(false);
}

}