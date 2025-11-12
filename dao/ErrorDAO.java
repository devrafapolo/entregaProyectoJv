package dao;

import modelo.ErrorLog;
import java.sql.*;
import util.LogFileManager;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import dao.ConexionBD;

public class ErrorDAO {

    public int obtenerOInsertarArchivoActual() throws Exception{
        String fecha = new SimpleDateFormat("yyyyMMdd").format(new Date());
        String nombreArchivo = "LogsError_" + fecha;

        try (Connection conn = ConexionBD.conectar()){
            //Buscar si ya existe un archivo de hoy
            String sql = "SELECT id, nombre_archivo FROM log_archivos WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombreArchivo + "%");
            ResultSet rs = ps.executeQuery();

            if (rs.next()){
                return rs.getInt("id");
            }else {
                //Crear nuevo archivo físico
                File nuevoArchivo = LogFileManager.generarArchivosLog();
                //Insertar registro en log_archivos
                String insertSql = "INSERT INTO log_archivos (nombre_archivo) VALUES (?)";
                PreparedStatement psInsert = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
                psInsert.setString(1, nuevoArchivo.getName());
                psInsert.executeUpdate();
                ResultSet genKeys = psInsert.getGeneratedKeys();
                if (genKeys.next()){
                    return genKeys.getInt(1);
                }
            }
        }
        throw new Exception("No se pudo obtener o crear el archivo log.");
    }

    //Devuelve el archivo físico según su ID
    public File obtenerArchivoPorId(int archivoId)throws SQLException{
        try(Connection conn = ConexionBD.conectar()){
            String sql = "SELECT nombre_archivo FROM log_archivos WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, archivoId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                String nombreArchivo = rs.getString("nombreArchivo");
            
                //reconstruye la ruta(más simple: busca en subcarpetas del día)
                String fechaActual = new SimpleDateFormat("yyyy/MM/dd").format(new Date());
                String path = "logs/" + fechaActual + "/" + nombreArchivo;
                return new File(path);
            }
        }
        return null;
    }

    //Inserta error y actualiza contador del archivo

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

            //actualiza contador en logs


            }catch (SQLException e){
                System.out.println("Error al insertar: " + e.getMessage());
            }

        }
    }
