package Data;
import java.sql.ResultSet;


import java.sql.Connection;
import java.sql.PreparedStatement;



public class User {
    private String userName;
    private String password;
    private int roleID;

    
    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    

    DatabaseConnection con = new DatabaseConnection();

    Connection conexion = con.conectar();
	
	PreparedStatement stmt;
   
    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    

    public boolean login(String enteredUserName, String enteredPassword) {
        String sql = "SELECT * FROM users WHERE userName = ? AND password = ?";
        
        try {
            stmt = conexion.prepareStatement(sql);
            
            stmt.setString(1, enteredUserName);
            stmt.setString(2, enteredPassword);
            
            ResultSet resultSet = stmt.executeQuery();
            
            if (resultSet.next()) {
                
                int roleID = resultSet.getInt("roleID");
    
              
                this.setRoleID(roleID);
                System.out.println(roleID);
                
                return true;
            } else {
                return false;
            }
            
        } catch (Exception e) {
            System.out.println("Error al buscar usuario: " + e.getMessage());
            return false;
        }
    }
    
}


