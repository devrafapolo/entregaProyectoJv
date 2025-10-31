package dao;

import modelo.ErrorLog;
import java.sql.*;

public class ErrorDAO {
    public void insertarError(ErrorLog error){
        String sql = "INSERT INTO registro_erroresJava (nombre_archivo, usuario, tipo_error, mensaje, metodo, linea, fecha_hora) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.conectar();
        PreparedStatement ps = con.prepareStatement(sql)){

            ps.setString(1, error.getNombreArchivo());
            ps.setString(2, error.getUsuario());
            ps.setString(3, error.getTipoError());
            ps.setString(4, error.getMensaje());
            ps.setString(5, error.getMetodo());
            ps.setInt(6, error.getLinea());
            ps.setTimestamp(7, Timestamp.valueOf(error.getFechaHora()));

            ps.executeUpdate();
            System.out.println("Error registrado en BD");

            }catch (SQLException e){
                System.out.println("Error al insertar: " + e.getMessage());
            }

        }
    }
