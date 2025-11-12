package util;

import java.io.*;
import java.nio.file.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date; 

public class LogFileManager {
    
    private static final String BASE_DIR = "logs";

    public static File generarArchivosLog() throws IOException{
        Calendar ahora = Calendar.getInstance();
        String year = String.valueOf(ahora.get(Calendar.YEAR));
        String month = String.format("%02d", ahora.get(Calendar.MONTH) + 1);
        String day = String.format("%02d", ahora.get(Calendar.DAY_OF_MONTH));

        Path dirPath = Paths.get(BASE_DIR, year, month, day);
        Files.createDirectories(dirPath);

        String fechaActual = new SimpleDateFormat("yyyyMMdd").format(new Date());
        int secuencial = 1;
        File archivo;

        do{
            String nombreArchivo = String.format("LogsError_%s_%d.txt", fechaActual, secuencial);
            archivo = dirPath.resolve(nombreArchivo).toFile();
            secuencial++;
        }while (archivo.exists());

        archivo.createNewFile();
        return archivo;
    }

    public static void registrarEnArchivo(File archivo, String contenido){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(archivo, true))){
            writer.write(contenido);
            writer.newLine();
            writer.write("-----------------------------------");
            writer.newLine();
        }catch(IOException e){
            System.out.println("Error escribiendo log: " + e.getMessage());
        }
    }
}
