package vista;

import dao.ErrorDAO;
import modelo.ErrorLog;
import java.util.Scanner;

public class MenuInteractivo {
    
    public void iniciar(){
        Scanner sc = new Scanner(System.in);
        boolean continuar = true;

        while(continuar){
            System.out.println("\n=== MENÚ ===");
            System.out.println("1. Provocar error");
            System.out.println("2. Salir");
            System.out.println("Opción: ");
            String opcion = sc.nextLine();

            try{
                if (opcion.equals("1")){
                    int resultado = 10 / 0;
                } else if(opcion.equals("2")){
                    continuar = false;
                }else {
                    throw new IllegalArgumentException("Opción inválida.");
                }
            } catch (Exception e){
                System.out.println("Error: " + e.getMessage());
                registrarError(e);
            }
        }
        sc.close();
    }

    private void registrarError(Exception e){
        StackTraceElement elemento = e.getStackTrace()[0];
        ErrorLog log = new ErrorLog(
            elemento.getFileName(),
            System.getProperty("user.name"),
            e.getClass().getSimpleName(),
            e.getMessage(),
            elemento.getMethodName(),
            elemento.getLineNumber()
        );

        ErrorDAO dao = new ErrorDAO();
        dao.insertarError(log);
    }
}
