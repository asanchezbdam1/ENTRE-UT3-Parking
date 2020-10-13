/**
 * La clase representa a un parking de una ciudad europea
 * que dispone de dos tarifas de aparcamiento para los clientes
 * que lo usen: la tarifa regular (que incluye una tarifa plana para
 * entradas "tempranas") y la tarifa comercial para clientes que trabajan
 * cerca del parking, aparcan un nº elevado de horas y se benefician de esta 
 * tarifa más económica
 * (leer enunciado)
 * Asier Sánchez Barberena
 * DAM1 ProyectoParking
 */
public class Parking
{
    private final char REGULAR = 'R';
    private final char COMERCIAL = 'C';
    
    private final double PRECIO_BASE_REGULAR = 2.0;
    private final double PRECIO_MEDIA_REGULAR_HASTA11 = 3.0;
    private final double PRECIO_MEDIA_REGULAR_DESPUES11 = 5.0;
    
    private final int HORA_INICIO_ENTRADA_TEMPRANA = 6 * 60;
    private final int HORA_FIN_ENTRADA_TEMPRANA = 8 * 60 + 30;
    private final int HORA_INICIO_SALIDA_TEMPRANA = 15 * 60;
    private final int HORA_FIN_SALIDA_TEMPRANA = 18 * 60;
    
    private final double PRECIO_TARIFA_PLANA_REGULAR = 15.0;
    private final double PRECIO_PRIMERAS3_COMERCIAL = 5.0;
    private final double PRECIO_MEDIA_COMERCIAL = 3.0;
    
    private String nombre;
    
    private int cliente, regular, comercial, clientesLunes, clientesSabado,
    clientesDomingo, clienteMaximoComercial;
    
    private double importeTotal, importeMaximoComercial;
    /**
     * Inicializa el parking con el nombre indicada por el parámetro.
     * El resto de atributos se inicializan a 0 
     */
    public Parking(String queNombre) {
        nombre = queNombre;
        cliente = 0;
        regular = 0;
        comercial = 0;
        clientesLunes = 0;
        clientesSabado = 0;
        clientesDomingo = 0;
        clienteMaximoComercial = 0;
        importeTotal = 0;
        importeMaximoComercial = 0;

    }

    /**
     * accesor para el nombre del parking
     *  
     */
    public String getNombre() {
         return nombre;
    }
    
    /**
     * mutador para el nombre del parking
     *  
     */
    public void setNombre(String queNombre) {
        nombre = queNombre;
    }

    /**
     *  Recibe cuatro parámetros que supondremos correctos:
     *    tipoTarifa - un carácter 'R' o 'C'
     *    entrada - hora de entrada al parking
     *    salida – hora de salida del parking
     *    dia – nº de día de la semana (un valor entre 1 y 7)
     *    
     *    A partir de estos parámetros el método debe calcular el importe
     *    a pagar por el cliente y mostrarlo en pantalla 
     *    y  actualizará adecuadamente el resto de atributos
     *    del parking para poder mostrar posteriormente (en otro método) las estadísticas
     *   
     *    Por simplicidad consideraremos que un cliente entra y sale en un mismo día
     *    
     *    (leer enunciado del ejercicio)
     */
    public void facturarCliente(char tipoTarifa, int entrada, int salida, int dia) {
        cliente++;
        double importe;
        
        int horaEntrada = entrada / 100;
        int horaSalida = salida / 100;
        int minutoEntrada = entrada % 100;
        int minutoSalida = salida % 100;
        
        int tiempoEntrada = horaEntrada * 60 + minutoEntrada;
        int tiempoSalida = horaSalida * 60 + minutoSalida;
        
        String minutosEntradaString;
        String minutosSalidaString;
        String tarifa;
        
        if (minutoEntrada < 10) {
            minutosEntradaString = "0" + minutoEntrada;
        }
        else {
            minutosEntradaString = "" + minutoEntrada;
        }
        
        if (minutoSalida < 10) {
            minutosSalidaString = "0" + minutoSalida;
        }
        else {
            minutosSalidaString = "" + minutoSalida;
        }
        
        
        switch (tipoTarifa) {
            case 'R':
            if (tiempoEntrada >= HORA_INICIO_ENTRADA_TEMPRANA &&
            tiempoEntrada <= HORA_FIN_ENTRADA_TEMPRANA &&
            tiempoSalida >= HORA_INICIO_SALIDA_TEMPRANA &&
            tiempoSalida <= HORA_FIN_SALIDA_TEMPRANA) {
                importe = PRECIO_TARIFA_PLANA_REGULAR;
                tarifa = "REGULAR y TEMPRANA";
            }
            else {
                importe = PRECIO_BASE_REGULAR;
                if (entrada < 1100) {
                    if (salida > 1100) {
                        importe += (11 * 60 - tiempoEntrada)
                        / 30 * PRECIO_MEDIA_REGULAR_HASTA11 +
                        (tiempoSalida - 11 * 60)
                        / 30 * PRECIO_MEDIA_REGULAR_DESPUES11;
                    }
                    else {
                        importe += (tiempoSalida - tiempoEntrada)
                        / 30 * PRECIO_MEDIA_REGULAR_HASTA11;
                    }
                }
                else {
                    importe += (tiempoSalida - tiempoEntrada)
                    / 30 * PRECIO_MEDIA_REGULAR_DESPUES11;
                }
                tarifa = "REGULAR";
            }
            regular++;
            break;
            default:
            importe = PRECIO_PRIMERAS3_COMERCIAL;
            if (tiempoSalida - tiempoEntrada > 180) {
                importe += (tiempoSalida - tiempoEntrada - 180)
                / 30 * PRECIO_MEDIA_COMERCIAL;
            }
            tarifa = "COMERCIAL";
            comercial++;
            break;
        }
        
        importeTotal += importe;
        System.out.println("**********************************");
        System.out.println("Cliente nº: " + cliente);
        System.out.println("Hora entrada: " + horaEntrada + ":" + minutosEntradaString);
        System.out.println("Hora salida: " + horaSalida + ":" + minutosSalidaString);
        System.out.println("Tarifa a aplicar: " + tarifa);
        System.out.println("Importe a pagar: " + importe + "€");
        System.out.println("**********************************");
        
        if (importeMaximoComercial < importe) {
            clienteMaximoComercial = cliente;
            importeMaximoComercial = importe;
        }
        
        switch (dia) {
            case 1: clientesLunes++; break;
            case 6: clientesSabado++; break;
            case 7: clientesDomingo++; break;
        }
    }

    /**
     * Muestra en pantalla las estadísticcas sobre el parking  
     *   
     * (leer enunciado)
     *  
     */
    public void printEstadísticas() {
        System.out.println("**********************************");
        System.out.println("Importe total entre todos los clientes: " + importeTotal + "€");
        System.out.println("Nº clientes tarifa regular: " + regular);
        System.out.println("Nº clientes tarifa comercial: " + comercial);
        System.out.println("Cliente tarifa COMERCIAL con favtura máxima fue el nº " +
        clienteMaximoComercial);
        System.out.println("y pagó " + importeMaximoComercial + "€");
        System.out.println("**********************************");
        System.out.println();
    }

    /**
     *  Calcula y devuelve un String que representa el nombre del día
     *  en el que más clientes han utilizado el parking - "SÁBADO"   "DOMINGO" o  "LUNES"
     */
    public String diaMayorNumeroClientes() {
        int maximo = 0;
        String dia = "";
        
        if (maximo < clientesLunes) {
        dia = "Lunes";
        maximo = clientesLunes;
        }
        
        if (maximo < clientesSabado) {
        dia = "Sábado";
        maximo = clientesLunes;
        }
        
        if (maximo < clientesDomingo) {
        dia = "Domingo";
        maximo = clientesLunes;
        }
        
        return dia;
    }

}
