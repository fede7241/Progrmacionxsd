package models;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

/**
 *
 * @author federicoRodriguez
 */
public class ConectionMySql {
    Connection conectar = null;
    
    String usuario =  "ies9021_userdb";
    String contrasenia =  "Xsw23edc.127";
    String bd =  "ies9021_coco";
    String ip =  "ies9021.edu.ar";
    String puerto =  "3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    
    // Método para establecer la conexión
    public Connection estableceConexion() {
        try {
            // Cargar el driver
            Class.forName("com.mysql.jdbc.Driver");
            
            // Establecer la conexión
            conectar = DriverManager.getConnection(cadena, usuario, contrasenia);
            
            // Si la conexión es exitosa, mostrar un mensaje
          //  JOptionPane.showMessageDialog(null, "Conexión exitosa a la base de datos");
        } catch (Exception e) {
            // Mostrar mensaje si hay un error
            JOptionPane.showMessageDialog(null, "No se conectó a la BD correctamente: " + e.getMessage());
        }
        
        return conectar;
    }
    
    // Método para cerrar la conexión
    public void cerrarConexion() {
        try {
            if (conectar != null && !conectar.isClosed()) {
                conectar.close();
              //  JOptionPane.showMessageDialog(null, "Conexión cerrada correctamente");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo cerrar la conexión: " + e.getMessage());
        }
    }
}





    
