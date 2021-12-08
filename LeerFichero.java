import java.io.BufferedReader; 
import java.io.FileNotFoundException; 
import java.io.FileReader; 
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.spi.CalendarNameProvider;
import java.util.Calendar;

public class LeerFichero { @Deprecated

    public static double binario (String num){
        int numero, exp, digito;
        double binario;                                             
        numero = Integer.parseInt(num);

        exp=0;
        binario=0;
        while(numero!=0){
                digito = numero % 2;           
                binario = binario + digito * Math.pow(10, exp);                                                   
                exp++;
                numero = numero/2;
        }

        return binario;
    }
	public static void muestraContenido(String archivo) throws FileNotFoundException, IOException { 
    	String cadena; 
        int inicio = 0;
        int fin = 0;
        String cadenaNueva = "";
        FileReader f = new FileReader(archivo); 
        BufferedReader b = new BufferedReader(f); 
        while((cadena = b.readLine())!=null) { 
        	//System.out.println(cadena); 
            for(int i=0; i<cadena.length(); i++){
                char c = cadena.charAt(i);
                if (c == '>'){
                    inicio = i;
                 }
                else if(c == '<'){
                    fin = i;
                }
                //System.out.println(cadena.substring(inicio, fin));
            }
            cadenaNueva = cadena.substring(inicio, fin);
            if(cadenaNueva.substring(1,2).equals("R")){
                if(cadena.substring(42, 44).equals("ID")){
                    basico(cadena);
                    id(cadena, true);
                    System.out.println("");  
                }else{
                    extendido(cadena);
                    id(cadena, false);
                    System.out.println("");  
                }
                
            }
            //System.out.println(cadenaNueva);
            //inicio = 0;
            //fin = 0;
        } 
        b.close(); 
	}
    public static void basico(String cadena){
        System.out.println("Indice calificador: Response");
    if(cadena.substring(2,4).equals("EV")){
        System.out.println("Indicador de tipo de mensaje: Event Message");
    }
    System.out.println("Indice de evento: " + cadena.substring(4,6));
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    Date f = null;
    try {
        f = sdf.parse("06-01-1980");
    } catch (ParseException e) {
        
    }
    sdf.applyPattern("EEE, d MMM yyyy HH:mm:ss");
   
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(f);
    calendar.add(calendar.WEEK_OF_YEAR, Integer.parseInt(cadena.substring(6, 10)));
    calendar.add(calendar.DAY_OF_YEAR, Integer.parseInt(cadena.substring(10, 11)));
    //sumo los segundos
    calendar.add(Calendar.SECOND, Integer.parseInt(cadena.substring(11, 16)));
    calendar.set(Calendar.HOUR, calendar.get(Calendar.HOUR)-6);
    f = calendar.getTime();

    String fecha = sdf.format(f);

    System.out.println("Fecha: "+ fecha); 

    float lat = Float.parseFloat(cadena.substring(17, 24)) / 100000;
    float lon = Float.parseFloat(cadena.substring(25, 33)) / 100000;
    System.out.println("Latitud: +"+lat); 
    System.out.println("Longitud: "+lon);
    
    double km = Double.parseDouble(cadena.substring(33, 36)) * 1.6093;
    System.out.printf("Velocidad: %.2f", km); 
    System.out.println("Km/h");
    
    System.out.println("Orientación: " + cadena.substring(36, 39));  
 
} 
public static void extendido(String cadena){
    basico(cadena);
    String io = cadena.substring(45, 48);
    System.out.println("IO: " +io);  

    String ign = Double.toString(binario(io.substring(0,1)));
    if(ign.substring(0,1).equals("1")){
        System.out.println("Ignicion: ACTIVO");
    }else{
        System.out.println("Ignicion: INACTIVO");
    }

    if(ign.substring(1,2).equals("1")){
        System.out.println("Fuente de alimentación: EXT-PWR");
    }else{
        System.out.println("Fuente de alimentación: BACKUP-BATTERY");
    }
   
    System.out.println(cadena.substring(49, 53));
    System.out.println(cadena.substring(54, 58));

    if(cadena.substring(59, 61).equals("VO")){
        System.out.println(cadena.substring(59, 67));

    }
}
public static void id(String cadena, boolean flag){
    if(flag){
        System.out.println(cadena.substring(42, 60));
    }else{
        System.out.println(cadena.substring(68, 85));
    }
    

}
    public static void main(String[] args) throws IOException {
    	muestraContenido("/C:/Users/tm194/Documents/Calculadora/ejemplo.txt"); 
    } 
}
