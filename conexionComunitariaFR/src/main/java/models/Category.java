
package models;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Category {
    int id_category;
    int name_category;

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public int getName_category() {
        return name_category;
    }

    public void setName_category(int name_category) {
        this.name_category = name_category;
    }

    public Category(int id_category, int name_category) {
        this.id_category = id_category;
        this.name_category = name_category;
    }

    public Category() {
    }
    
    
    public void MostrarCategorias(JTable paramCategorias){
    
    // Instancia de Conexion Con Mysql Tabla CUSTOMER
        ConectionMySql conexionCategorias = new ConectionMySql();
        DefaultTableModel ModeloTablaCategorias = new DefaultTableModel();

        // Ordenar Tabla 
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(ModeloTablaCategorias);
        paramCategorias.setRowSorter(OrdenarTabla);

        String ConsultaSql = "SELECT id_category, name FROM category;";

        // Agrego las columnas a la tabla vacia
        ModeloTablaCategorias.addColumn("ID");
        ModeloTablaCategorias.addColumn("Nombre de Categoria");

        //Agarro el parametro de la tabla y le agrego el modelo
        paramCategorias.setModel(ModeloTablaCategorias);

        // Guardo los datos de la tabla en un vector
        String[] DatosTablaClientes = new String[2];
        Statement st;

        try {
            st = conexionCategorias.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(ConsultaSql);

            while (rs.next()) {
                DatosTablaClientes[0] = rs.getString(1);
                DatosTablaClientes[1] = rs.getString(2);

                ModeloTablaCategorias.addRow(DatosTablaClientes);
            }
            paramCategorias.setModel(ModeloTablaCategorias);
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "No se pudo mostrar a los clientes, error: "+ e.toString());
         
            
            
        }
    
    
    
    }
    
}
