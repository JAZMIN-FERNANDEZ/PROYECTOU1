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
    vista.agregarAutosEnServicios(autos, nombreCliente);
}
    
    public void cargarTodosLosAutos(ArrayList<Auto> todos) {
    // Obtener los nombres de cada cliente por su id
    ArrayList<Auto> autos = todos;
    
    javax.swing.table.DefaultTableModel modelo =
        (javax.swing.table.DefaultTableModel) vista.getTablaAutosServicios().getModel();
    
    modelo.setRowCount(0); // limpia para evitar duplicados

    for (Auto a : autos) {
        // Buscar el nombre del cliente en el controlador de autos
        String nombreCliente = vista.getControladorAuto().getNombreCliente(a.getId_cliente());
        modelo.addRow(new Object[]{
            nombreCliente,
            a.getId_auto(),
            a.getModelo(),
            a.getColor(),
            a.getTipo()
        });
    }
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
    if (vista.getCheckEncerado().isSelected())  servicios.add("Encerado");
    if (vista.getCheckPulido().isSelected())    servicios.add("Pulido de Faros");

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