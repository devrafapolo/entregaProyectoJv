package modelo;

import java.time.LocalDateTime;

public class ErrorLog {
    private int id;
    private int archivoId;
    private String nombreArchivo;
    private String usuario;
    private String tipoError;
    private String mensaje;
    private String metodo;
    private int linea;
    private LocalDateTime fechaHora;

    public ErrorLog(String nombreArchivo, String usuario,
            String tipoError, String mensaje, String metodo, int linea){
        this.nombreArchivo = nombreArchivo;
        this.usuario = usuario;
        this.tipoError = tipoError;
        this.mensaje = mensaje;
        this.metodo = metodo;
        this.linea = linea;
        this.fechaHora = LocalDateTime.now();
    }

    public int getArchivoId() { return archivoId; }
    public void setArchivoId(int archivoId) {this.archivoId = archivoId; }

    public String getNombreArchivo(){ return nombreArchivo; }
    public String getUsuario(){ return usuario; }
    public String getTipoError(){ return tipoError; }
    public String getMensaje(){ return mensaje; }
    public String getMetodo(){ return metodo; }
    public int getLinea(){ return linea; }
    public LocalDateTime getFechaHora(){ return fechaHora; }
}
