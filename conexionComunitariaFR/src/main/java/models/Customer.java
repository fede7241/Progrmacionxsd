package models;

import java.lang.invoke.MethodHandles;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.TableView.TableRow;


public class Customer {

    int id_customer;
    String name_customer;
    int dni_customer;

    public Customer() {
    }

    public Customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public Customer(int id_customer, String name_customer, int dni_customer) {
        this.id_customer = id_customer;
        this.name_customer = name_customer;
        this.dni_customer = dni_customer;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public String getName_customer() {
        return name_customer;
    }

    public void setName_customer(String name_customer) {
        this.name_customer = name_customer;
    }

    public int getDni_customer() {
        return dni_customer;
    }

    public void setDni_customer(int dni_customer) {
        this.dni_customer = dni_customer;
    }

    public void MostrarClientes(JTable paramClientes) {
        // Instancia de Conexion Con Mysql Tabla CUSTOMER
        ConectionMySql conexionClientes = new ConectionMySql();
        DefaultTableModel ModeloTablaClientes = new DefaultTableModel();

        // Ordenar Tabla 
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(ModeloTablaClientes);
        paramClientes.setRowSorter(OrdenarTabla);

        String ConsultaSql = "SELECT dni, name FROM customer WHERE dni IS NOT NULL AND name IS NOT NULL;";

        // Agrego las columnas a la tabla vacia
        ModeloTablaClientes.addColumn("DNI");
        ModeloTablaClientes.addColumn("Nombres");

        //Agarro el parametro de la tabla y le agrego el modelo
        paramClientes.setModel(ModeloTablaClientes);

        // Guardo los datos de la tabla en un vector
        String[] DatosTablaClientes = new String[2];
        Statement st;

        try {
            st = conexionClientes.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(ConsultaSql);

            while (rs.next()) {
                DatosTablaClientes[0] = rs.getString(1);
                DatosTablaClientes[1] = rs.getString(2);

                ModeloTablaClientes.addRow(DatosTablaClientes);
            }
            paramClientes.setModel(ModeloTablaClientes);
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "No se pudo mostrar a los clientes, error: "+ e.toString());
         
            
            
        }

    }
    
    public void SeleccionarClientes(JTable paramClientesSeleccionado, JTextField paramDNI, JTextField paramNombres){
        try {
            int fila = paramClientesSeleccionado.getSelectedRow();
            
            if (fila >=0) {
                paramDNI.setText(paramClientesSeleccionado.getValueAt(fila, 0).toString());
                paramNombres.setText(paramClientesSeleccionado.getValueAt(fila, 1).toString());
            }else{
                JOptionPane.showMessageDialog(null, "Fila no seccionada");
            
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error de seleccion, error: "+ e.toString());
        }
    
    
    }

}
