package dao;

import java.sql.Connection; 
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD{
    private static final String URL = "jdbc:mysql://localhost:3306/mecanicaCowkie";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection conectar(){
        Connection conexion = null;
        try{
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexión establecida con la BD");
        }catch (SQLException e){
            System.out.println("Error al conectar: " + e.getMessage());
        }
        return conexion;
    }
}