package models;

import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class Interest {

    int id_interest;
    int id_customer;
    int id_category;
    int id_user_create;
    int id_user_update;

    public int getId_interest() {
        return id_interest;
    }

    public void setId_interest(int id_interest) {
        this.id_interest = id_interest;
    }

    public int getId_customer() {
        return id_customer;
    }

    public void setId_customer(int id_customer) {
        this.id_customer = id_customer;
    }

    public int getId_category() {
        return id_category;
    }

    public void setId_category(int id_category) {
        this.id_category = id_category;
    }

    public int getId_user_create() {
        return id_user_create;
    }

    public void setId_user_create(int id_user_create) {
        this.id_user_create = id_user_create;
    }

    public int getId_user_update() {
        return id_user_update;
    }

    public void setId_user_update(int id_user_update) {
        this.id_user_update = id_user_update;
    }

    public Interest(int id_interest, int id_customer, int id_category, int id_user_create, int id_user_update) {
        this.id_interest = id_interest;
        this.id_customer = id_customer;
        this.id_category = id_category;
        this.id_user_create = id_user_create;
        this.id_user_update = id_user_update;
    }

    public Interest() {
    }

    public void MostrarInteres(JTable paramInterest) {

        //Instancia de Conexion Con Mysql Tabla CUSTOMER
        ConectionMySql conexionInterest = new ConectionMySql();
        DefaultTableModel ModeloTablaInterest = new DefaultTableModel();

        // Ordenar Tabla 
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(ModeloTablaInterest);
        paramInterest.setRowSorter(OrdenarTabla);

        String ConsultaSql = "SELECT c.dni, c.name, GROUP_CONCAT(cat.name SEPARATOR ', ') AS interests\n"
                + "FROM customer c\n"
                + "LEFT JOIN interest i ON c.id_customer = i.id_customer\n"
                + "LEFT JOIN category cat ON i.id_category = cat.id_category\n"
                + "WHERE c.dni IS NOT NULL AND c.name IS NOT NULL AND cat.name IS NOT NULL\n"
                + "GROUP BY c.id_customer;";

        // Agrego las columnas a la tabla vacia
        ModeloTablaInterest.addColumn("DNI");
        ModeloTablaInterest.addColumn("Nombre Cliente");
        ModeloTablaInterest.addColumn("Nombre Categoria");

        //Agarro el parametro de la tabla y le agrego el modelo
        paramInterest.setModel(ModeloTablaInterest);

        // Guardo los datos de la tabla en un vector
        String[] DatosTablaInterest = new String[3];
        Statement st;

        try {
            st = conexionInterest.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(ConsultaSql);

            while (rs.next()) {
                DatosTablaInterest[0] = rs.getString(1);
                DatosTablaInterest[1] = rs.getString(2);
                DatosTablaInterest[2] = rs.getString(3);

                ModeloTablaInterest.addRow(DatosTablaInterest);
            }
            paramInterest.setModel(ModeloTablaInterest);
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "No se pudo mostrar a los clientes, error: " + e.toString());

        }

    }
    
    



}
