
package models;

import java.sql.PreparedStatement;
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

        String ConsultaSql = "SELECT i.id_interest, c.dni, c.name, GROUP_CONCAT(cat.name SEPARATOR ', ') AS interests FROM customer c INNER JOIN interest i ON c.id_customer = i.id_customer INNER JOIN category cat ON i.id_category = cat.id_category WHERE c.dni IS NOT NULL AND c.name IS NOT NULL GROUP BY c.id_customer;";

        // Agrego las columnas a la tabla vacia
        ModeloTablaInterest.addColumn("Codigo Interes");
        ModeloTablaInterest.addColumn("DNI");
        ModeloTablaInterest.addColumn("Nombre Cliente");
        ModeloTablaInterest.addColumn("Nombre Categoria");

        //Agarro el parametro de la tabla y le agrego el modelo
        paramInterest.setModel(ModeloTablaInterest);

        // Guardo los datos de la tabla en un vector
        String[] DatosTablaInterest = new String[4];
        Statement st;

        try {
            st = conexionInterest.estableceConexion().createStatement();
            ResultSet rs = st.executeQuery(ConsultaSql);

            while (rs.next()) {
                DatosTablaInterest[0] = rs.getString(1);
                DatosTablaInterest[1] = rs.getString(2);
                DatosTablaInterest[2] = rs.getString(3);
                DatosTablaInterest[3] = rs.getString(4);
                ModeloTablaInterest.addRow(DatosTablaInterest);
            }
            paramInterest.setModel(ModeloTablaInterest);
        } catch (Exception e) {

            JOptionPane.showMessageDialog(null, "No se pudo mostrar a los clientes, error: " + e.toString());

        }

    }
    
    public void AgregarInteres(JTextField paramIDCustomer, JTextField paramIdCategory){

  setId_customer(Integer.parseInt(paramIDCustomer.getText()));
  setId_category(Integer.parseInt(paramIdCategory.getText()));
  
  
  ConectionMySql objetoConexion = new ConectionMySql();
  
  String ConsultaSQL = "INSERT INTO interest (id_customer, id_category) VALUES (?, ?)";
  
  
    try {
        PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(ConsultaSQL);

// Usar setInt ya que son valores enteros
ps.setInt(1, getId_customer()); // Asigna el valor de id_customer
ps.setInt(2, getId_category());  // Asigna el valor de id_category

// Ejecutar la consulta
ps.executeUpdate(); // Ejecuta la inserción

JOptionPane.showMessageDialog(null , "Se Inserto Correctamente el Interes");
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null , "No se Inserto Correctamente el Interes" + e.toString());
    }
}


public void ModificarIntereses(JTextField paramIdInteres, JTextField paramIdCustomer, JTextField paramIdCategory) { 
    // Validación de entradas
    if (paramIdInteres.getText().isEmpty() || paramIdCustomer.getText().isEmpty() || paramIdCategory.getText().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
        return; // Termina el método si hay campos vacíos
    }

    try {
        // Obtener los valores de los campos
        int idInterest = Integer.parseInt(paramIdInteres.getText());
        int idCustomer = Integer.parseInt(paramIdCustomer.getText());
        int idCategory = Integer.parseInt(paramIdCategory.getText());

        ConectionMySql objetoConexion = new ConectionMySql();  
        
        // Consulta SQL para actualizar el registro
        String consultaSQL = "UPDATE interest SET id_customer = ?, id_category = ?, id_user_create = 31, id_user_update = 31, date_update = NOW() WHERE id_interest = ?;";

        try {
            PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consultaSQL);

            // Asignar los parámetros en el orden correcto
            ps.setInt(1, idCustomer);   // Asigna el valor de id_customer
            ps.setInt(2, idCategory);    // Asigna el valor de id_category
            ps.setInt(3, idInterest);     // Asigna el valor de id_interest

            // Ejecutar la consulta
            int rowsAffected = ps.executeUpdate(); // Ejecuta la actualización

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Se modificó correctamente el interés");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el interés para modificar");
            }
        } catch (Exception e) {
            e.printStackTrace(); // Para registrar la excepción
            JOptionPane.showMessageDialog(null, "Error al modificar el interés: " + e.toString());
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(null, "Por favor, ingrese valores numéricos válidos.");
    }
}

    
    
    public void EliminarInteres(JTextField paramIdInteres) { 
    if (paramIdInteres.getText().isEmpty()) {
        JOptionPane.showMessageDialog(null, "Por favor, ingrese el Codigo del interés a eliminar.");
        return; // Termina el método si el campo está vacío
    }

    int idInterest = Integer.parseInt(paramIdInteres.getText());
    ConectionMySql objetoConexion = new ConectionMySql();  
    
    // Consulta SQL para eliminar el registro
    String consultaSQL = "DELETE FROM interest WHERE id_interest = ?;";

    try {
        PreparedStatement ps = objetoConexion.estableceConexion().prepareStatement(consultaSQL);

        // Asignar el parámetro
        ps.setInt(1, idInterest); // Asigna el valor de id_interest

        // Ejecutar la consulta
        int rowsAffected = ps.executeUpdate(); // Ejecuta la eliminación

        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Se eliminó correctamente el interés");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró el interés para eliminar");
        }
    } catch (Exception e) {
        e.printStackTrace(); // Para registrar la excepción
        JOptionPane.showMessageDialog(null, "No se eliminó correctamente el interés: " + e.toString());
    }
}

    
    
    
    
    
}
