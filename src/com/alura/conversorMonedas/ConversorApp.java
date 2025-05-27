package com.alura.conversorMonedas;

import com.alura.conversorMonedas.service.Consulta;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

public class ConversorApp {
    private Scanner lector = new Scanner(System.in);
    private Consulta consulta = new Consulta();

    public void mensajePrincipal(){
        System.out.println
                ("**************************************************"+'\n'
                        +"Sea bienvenido/a al Conversor de Moneda :D"+'\n'
                        +"1)Dólar  =>> Peso Chileno"+'\n'
                        +"2)Peso chileno  =>> Dólar"+'\n'
                        +"3)Dólar  =>> Real brasileño"+'\n'
                        +"4)Real brasileño  =>> Dólar"+'\n'
                        +"5)Dólar  =>> Peso Colombiano"+'\n'
                        +"6)Peso Colombiano =>> Dólar"+'\n'
                        +"7)Salir\n"+
                        "Elija una opción válida: ");

    }
    private static final Map<Integer, String[]> CONVERSIONES = Map.of(
            1, new String[]{"USD", "CLP"},
            2, new String[]{"CLP", "USD"},
            3, new String[]{"USD", "BRL"},
            4, new String[]{"BRL", "USD"},
            5, new String[]{"USD", "COP"},
            6, new String[]{"COP", "USD"}
    );

    public void menuPrincipal(){
        while (true) {
            var opcion = 0;
            double monto = 0.0;
            double montoConvertido= 0.0;
            mensajePrincipal();
            try{
                opcion = Integer.valueOf(lector.nextLine());


                if (opcion == 7) {
                    System.out.println("Gracias por utilizar mi conversor de Monedas, hasta la próxima! :D");
                    break;
                }

                if (CONVERSIONES.containsKey(opcion)){
                    System.out.println("Ingrese el monto que desea convertir: ");
                    monto = Double.valueOf(lector.nextLine());

                    //obtenemos las monedas del MAP
                    String monedaOrigen = CONVERSIONES.get(opcion)[0];//Saca el 1er valor del array
                    String monedaDestino = CONVERSIONES.get(opcion)[1];//Saca el 2do valor del array

                    //Llamamos al metodo de consulta pasándole toda la información necesaria.
                    montoConvertido = consulta.consultarAPI(monedaOrigen,monedaDestino, monto);

                    System.out.println("El valor "+ monto + " ["+monedaOrigen+"] equivale a el valor ==> "+ montoConvertido + " ["+monedaDestino+"]");

                }else{
                    System.out.println("Opción no válida. Intente nuevamente");
                }

            } catch (NumberFormatException e){
                System.out.println("Ingrese una respuesta válida por favor.");

            } catch (Exception e){
                System.out.println("Disculpe se ha generado un error inesperado: "+e.getMessage());
            }
        }
    }
}
