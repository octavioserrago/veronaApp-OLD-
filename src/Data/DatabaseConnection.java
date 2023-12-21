package Data;
import java.sql.DriverManager;
import java.sql.Connection; 
import javax.swing.JOptionPane;

public class DatabaseConnection {
    Connection con; 

    public Connection conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/MarmoleriaVerona", "root", "");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al conectarse");
        }
        return con;
    }
    public boolean isConnected() {
        return (con != null);
    }
    
	
}
