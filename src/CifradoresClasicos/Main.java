package CifradoresClasicos;


import java.io.*;
import java.util.*;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author Luis Cuevas
 */

class Main {
  
    public static  ArrayList<Character> KV = new ArrayList<Character>(Arrays.asList('a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'));

     
    public static void cifrarVig (String name, String key) throws Exception{   
        try {
            Scanner leer = new Scanner(new File (name));            
            key=key.toLowerCase();
            if (key.indexOf(241)!=-1){ //241 ascii de ñ
                JOptionPane.showMessageDialog(null,"Clave Inválida, no debe tener 'ñ'","Inválido",JOptionPane.WARNING_MESSAGE);
            }
            else {
                
                String C = ""; //El texto cifrado se irá almacenando aqui
                int cnt =0;
                
                int p,k,c;
                char ca;
                do {
                if (cnt>0){
                  C+=(char)37;
                }
                String texto = leer.nextLine(); //Obtención de la primer línea del plain text
                texto=texto.toLowerCase();
                int j=0;
                for (int i=0;i<texto.length();i++){ //Ir recorriendo cada línea del plaint text caracter por caracter
                    char caracterp=texto.charAt(i); //Obtenemos un caracter para ir cifrándolo
                    
                    if ((int)caracterp==32){ //Si hay un espacio en el texto plano
                        ca=(char)35;
                        C+=ca;//añadir un #
                        //System.out.println("Para "+caracterp+" k=32"+"=' '");
                    }
                    else{
                        
                       k=KV.indexOf(key.charAt((j)%key.length()));
                       
                        //System.out.println("Para "+caracterp+" k="+k+"="+KV.get(k) );
                        
                        //De esa manera cada que el key acabe, inicie de nuevo                        
                        p=KV.indexOf(caracterp);            
                        c=(p+k)%26; //C= P + K mod 26
                        ca=KV.get(c);
                        C+=ca;    
                        j+=1;
                        
                    }
                 cnt+=1;
                }
              }while (leer.hasNextLine());
                leer.close();
                String newName=name.substring(0,name.length()-4)+"_Cif.vig"; //Creamos un nuevo nombre para el archivo cifrado
                //Tendra la extension .vig pues fue cifrado con Vigenere
                BufferedWriter fos = new BufferedWriter(new FileWriter(newName));
                fos.write(C);
                fos.close(); 
                JOptionPane.showMessageDialog(null,"Texto Cifrado con éxito\n"+newName+" creado","Completo",JOptionPane.INFORMATION_MESSAGE);
            
                //System.out.println(C);
            }
        } catch (IOException e) {
           JOptionPane.showMessageDialog(null,name+" no encontrado!","Inválido",JOptionPane.WARNING_MESSAGE);
        }
    }
    public static void descifrarVig (String name, String key) throws Exception{
        
        try {
            Scanner leer = new Scanner(new File (name));            
            key=key.toLowerCase();
            if (key.indexOf(241)!=-1){ //241 ascii de ñ
                JOptionPane.showMessageDialog(null,"Clave Inválida, no debe tener 'ñ'","Inválido",JOptionPane.WARNING_MESSAGE);
            }
            else {
                //Inversos de k
                ArrayList<Character> inversoK =new ArrayList<Character>();
                for (byte i=0;i<key.length();i++){
                    inversoK.add(KV.get((26-KV.indexOf(key.charAt(i)))%26));
                };
                //System.out.println(inversoK);
                String P = ""; //El texto descifrado se irá almacenando aqui
                int j = 0;
                int c,k,p;
                do {
                    String texto = leer.nextLine(); //Obtención de la primer línea del plain text
                    texto=texto.toLowerCase();
                    for (int i=0;i<texto.length();i++){ //Ir recorriendo cada línea del plaint text caracter por caracter
                        char caracterc=texto.charAt(i); //Obtenemos un caracter para ir cifrándolo
                        if ((int)caracterc==37){ //Si hay un % en el texto plano
                            P+="\n";//añadir un salto de linea     
                            //System.out.println("Para "+caracterc+" k=salto");
                            j=0;
                        }
                        else if ((int)caracterc==35){ //Si hay un # en el texto plano
                            P+=(char)32;//añadir un espacio      
                            //System.out.println("Para "+caracterc+" k=32"+"=' '");
                        }
                        else{
                           k=KV.indexOf(inversoK.get(j%key.length()));
                           
                             //El módulo es usado por si el texto es mayor a la key
                            //System.out.println("Para "+caracterc+" k="+k+"="+KV.get(k) );
                            c=KV.indexOf(caracterc);
                            
                            //De esa manera cada que el key acabe, inicie de nuevo
                            p=(c+k)%26; //P= C + (-K) mod 26
                            P+=KV.get(p);  
                            j+=1;
                        }
                     }
                } while (leer.hasNextLine());
                leer.close();
                String newName=name.substring(0,name.length()-8)+"_Des.vig"; //Creamos un nuevo nombre para el archivo Descifrado
                //Tendra la extension .vig pues fue cifrado con Vigenere
                BufferedWriter fos = new BufferedWriter(new FileWriter(newName));
                fos.write(P);
                fos.close(); 
                JOptionPane.showMessageDialog(null,"Texto Descifrado con éxito\n"+newName+" creado","Completo",JOptionPane.INFORMATION_MESSAGE);
                //System.out.println(P);
            }
        } catch (IOException e) {
           JOptionPane.showMessageDialog(null,name+" no encontrado!","Inválido",JOptionPane.WARNING_MESSAGE);
        }
               
    }
    //codigo en
    //https://www.makingcode.dev/2015/06/implementacion-de-euclides-extendido-en.html
    public static int[] validarK(int a,int n) {
        int[] resp = new int[3];
        int x=0,y=0,d=0,at=a,nt=n;
        if(n==0) {
            resp[0] = a; resp[1] = 1; resp[2] = 0;
            JOptionPane.showMessageDialog(null,"n debe ser mayor a 0","Error en en",JOptionPane.WARNING_MESSAGE);
            return resp;
        } 
        else {
            int x2 = 1, x1 = 0, y2 = 0, y1 = 1;
            int q = 0, r = 0;
            while(n>0) {
                q = (a/n);
                r = a - q*n;
                x = x2-q*x1;
                y = y2 - q*y1;
                a = n;
                n = r;
                x2 = x1;
                x1 = x;
                y2 = y1;
                y1 = y;
            }
            resp[0] = a; //mcd(a,n)
            resp[1] = x2; //x de ax+ny
            resp[2] = y2; //y de ax+ny

            if(resp[1]<0){ //Si el inverso es negativo habrá que sacar su complemento
             resp[1]=nt+resp[1];
            }
            //System.out.println(resp[1]+"& ,mcd="+resp[0]);
            return resp;
        }
    } 
    public static void cifrarAfin (int a, int b, int n) throws Exception{ 
        if (n<=256 && n>0){
            int valido[]=validarK(a,n);
            if (valido[0]==1){
                String name = JOptionPane.showInputDialog("Ingresa el nombre del archivo a cifrar (ej. Mensaje.txt");
                if (existe(name)){
                    Scanner leer= new Scanner(new File(name));
                    String C = ""; //El texto cifrado se irá almacenando aqui
                    int j = 0;
                    int p;
                    do{
                        if (j>0)
                           C+="\n";
                        String texto=leer.nextLine();
                        for (int i=0;i<texto.length();i++){ //Ir recorriendo cada línea del plaint text caracter por caracter
                            char caracterp=texto.charAt(i);
                            p=((int)caracterp) %n; //Obtenemos el valor ascii del caracter p y aplicamos módulo n
                            p=(p*a)%n; //Multiplicamos p*alfa y volvemos aplicar modulo 26
                            p=p+b; //sumamos p*alfa + beta
                            p=p%n ;//a la suma anterior aplicamos modulo n
                            C+=(char)p; //Obtenemos el caracter que hay en la posición p y lo añadimos al texto cifrado
                        }
                        j+=1;
                    } while (leer.hasNextLine());
                    //System.out.println(C);
                    String newName=name.substring(0,name.length()-4)+"_Cif.aff"; //Creamos un nuevo nombre para el archivo cifrado
                    //Tendra la extension .aff pues fue cifrado con Vigenere
                    BufferedWriter fos = new BufferedWriter(new FileWriter(newName));
                    fos.write(C);
                    fos.close(); 
                    JOptionPane.showMessageDialog(null,"Texto Cifrado con éxito\n"+newName+" creado","Completo",JOptionPane.INFORMATION_MESSAGE);
                    
                }
                else
                   JOptionPane.showMessageDialog(null,name+" no encontrado!\nIntenta con otro valor","Inválido",JOptionPane.WARNING_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(null,"alfa y n no son compatibles (coprimos)\nIntenta con otros valores","Error en alfa y n",JOptionPane.WARNING_MESSAGE);
        }
        else
            JOptionPane.showMessageDialog(null,"n debe ir desde 0<n<=256\nIntenta con otro valor","Error en n",JOptionPane.WARNING_MESSAGE);
      }

    public static void descifrarAfin(int a, int b, int n)throws Exception{
        if (n<=256 && n>0){
            int valido[]=validarK(a,n);
            if (valido[0]==1){
                 String name = JOptionPane.showInputDialog("Ingresa el nombre del archivo a descifrar (ej. Mensaje.txt");
                if (existe(name)){
                    Scanner leer= new Scanner(new File(name));
                    String P = ""; //El texto descifrado se irá almacenando aqui
                    int inva = valido[1]; //asignamos el inverso de alfa
                    int invb=(n-b)%n; //Calculamos el inverso de beta
                    int c,j=0;
                    do{
                        if (j>0)
                            P+="\n";
                        String texto=leer.nextLine();
                        for (int i=0;i<texto.length();i++){ //Ir recorriendo cada línea del plaint text caracter por caracter
                            char caracterc=texto.charAt(i);
                            c=((int)caracterc) %n; //Obtenemos el valor ascii del caracter c y aplicamos módulo n
                            c=(c+invb)%n; //sumanos c + el inverso de beta y aplicamos modulo n
                            c=c*inva; //multiplicamos c*inverso de alfa
                            c=c%n ;//a la multiplicación anterior aplicamos modulo n
                            P+=(char)c; //Obtenemos el caracter que hay en la posición p y lo añadimos al texto descifrado
                        }
                        j+=1;
                    } while (leer.hasNextLine());
                    String newName=name.substring(0,name.length()-8)+"_Des.aff"; //Creamos un nuevo nombre para el archivo cifrado
                    //Tendra la extension .aff pues fue cifrado con Vigenere
                    BufferedWriter fos = new BufferedWriter(new FileWriter(newName));
                    fos.write(P);
                    fos.close(); 
                    JOptionPane.showMessageDialog(null,"Texto Descifrado con éxito\n"+newName+" creado","Completo",JOptionPane.INFORMATION_MESSAGE);
                }
                else
                   JOptionPane.showMessageDialog(null,name+" no encontrado!","Inválido",JOptionPane.WARNING_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(null,"alfa y n no son compatibles (coprimos)","Error en alfa y n",JOptionPane.WARNING_MESSAGE);
        }
        else
           JOptionPane.showMessageDialog(null,"n debe ir desde 0<n<=256","Error en n",JOptionPane.WARNING_MESSAGE); 
    }
        public static boolean existe (String name) throws Exception{
        try{
            Scanner leer = new Scanner(new File (name));  
            return true;
        } catch (IOException e){
            return false;
        }
    }
}
