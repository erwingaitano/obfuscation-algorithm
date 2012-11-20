import java.io.*;

import java.util.ArrayList;

public class ObfuscationAlgorithm {
    private File arch;
    private FileReader fr;
    private BufferedReader br;
    private String todosDatos, datosOriginales, datosOfuscados, linea;
    private ArrayList<ArrayList> datosPosicion, datosPosicionOfuscados, limiteSup, limiteInf, limiteIzq, limiteDer, distanciaMenor;
    private double radio;
    
    public ObfuscationAlgorithm(String ruta) throws IOException {
        
        arch = new File(ruta);        
        fr = new FileReader(arch);
        br = new BufferedReader(fr); 
        datosPosicion = new ArrayList();
        datosPosicionOfuscados = new ArrayList();
        limiteSup = new ArrayList();
        limiteDer = new ArrayList();
        limiteInf = new ArrayList();
        limiteIzq = new ArrayList();
        distanciaMenor = new ArrayList();
        datosOriginales = ""; linea= "";
        todosDatos = "";
        radio = 5; //Las unidades son proporcionales a 11.1 mts. aproximandamente. Es decir, r=5 significa 55.5mts.
        setDatos();
        ejecutarAlgoritmo();
        generarAreaSemiCircular();
        setDatosOfuscados();
        generarReporte();
   }
    
    public void setDatos() throws IOException {
        datosOriginales = "<h1>Original Data</h1><hr>"
                + "<table style=\"width:100%;\">\n"
                + "<thead>\n"
                    + "<tr style=\"font-weight:bold;\">\n"
                        + "<td style=\"margin-right:15px;\">Latitude</td>\n"
                        + "<td style=\"margin-right:15px;\">Longitude</td>\n"
                        + "<td style=\"margin-right:15px;\">Code</td>\n"
                        + "<td style=\"margin-right:15px;\">Altitude (Feet)</td>\n"
                        + "<td style=\"margin-right:15px;\">Date</td>\n"
                        + "<td style=\"margin-right:15px;\">Date (Standard)</td>\n"
                        + "<td style=\"margin-right:15px;\">Time</td>\n"
                    + "</tr>\n"
                + "</thead>\n";
        String tableBody = "";
        while((linea=br.readLine())!=null){
            String[] parts = linea.split(",");
            ArrayList temporal = new ArrayList();
            tableBody = tableBody + "<tr>\n";
            for(int i=0; i<7;i++){
                tableBody = tableBody + "<td>" + parts[i] + "</td>\n";
                if(i==0 || i==1){
                    temporal.add(Double.parseDouble(parts[i]));
                }else{
                    temporal.add(parts[i]);
                }
            }            
            tableBody = tableBody + "</tr>\n";
            datosPosicion.add(temporal);
        }
        
        //System.out.println(datosPosicion);
        tableBody = "<tbody>\n"
                + tableBody +"\n"
                + "</tbody>\n"
                + "</table>\n";
                       
        datosOriginales = datosOriginales + tableBody;
    }
    
    
    public void setDatosOfuscados() throws IOException {
        datosOfuscados = "<h1 style=\"margin-bottom:0px\">Ofuscated Data</h1>"
                + "<p style=\"margin-top:0px;\">Obfuscation Radius: "+ radio*10000*11.1 +" mts. </p>"
                + "<hr>"
                + "<table style=\"width:100%;\">\n"
                + "<thead>\n"
                    + "<tr style=\"font-weight:bold;\">\n"
                        + "<td style=\"margin-right:15px;\">Latitude</td>\n"
                        + "<td style=\"margin-right:15px;\">Longitude</td>\n"
                        + "<td style=\"margin-right:15px;\">Code</td>\n"
                        + "<td style=\"margin-right:15px;\">Altitude (Feet)</td>\n"
                        + "<td style=\"margin-right:15px;\">Date</td>\n"
                        + "<td style=\"margin-right:15px;\">Date (Standard)</td>\n"
                        + "<td style=\"margin-right:15px;\">Time</td>\n"
                    + "</tr>\n"
                + "</thead>\n";
        String tableBody = "";
        int size = datosPosicionOfuscados.size();
                
        for(int i=0; i<size; i++){
            ArrayList temporal = new ArrayList();
            tableBody = tableBody + "<tr>\n";
            for(int j=0; j<7;j++){
                tableBody = tableBody + "<td>" + datosPosicionOfuscados.get(i).get(j) + "</td>\n";
            }            
            tableBody = tableBody + "</tr>\n";
        }
        
        //System.out.println(datosPosicion);
        //System.out.println(datosPosicionOfuscados);
        tableBody = "<tbody>\n"
                + tableBody +"\n"
                + "</tbody>\n"
                + "</table>\n";
                       
        datosOfuscados = datosOfuscados + tableBody;
        todosDatos = datosOriginales + datosOfuscados;
    }
    
    public void ejecutarAlgoritmo(){
        int size = datosPosicion.size();
        radio = radio/10000;
        double suma, randomX, randomY, rangoXmin, rangoYmin, rangoXmax, rangoYmax;
        ArrayList temDistanciaMenor;
        ArrayList<Double> temLimiteSup, temLimiteInf, temLimiteIzq, temLimiteDer;
        randomX = 0;
        randomY= 0;
        for(int i=0; i<size;i++){
            temLimiteSup = new ArrayList();
            temLimiteDer = new ArrayList();
            temLimiteInf = new ArrayList();
            temLimiteIzq = new ArrayList();
            temDistanciaMenor = new ArrayList();
           
            suma = Double.parseDouble(datosPosicion.get(i).get(0).toString())*Double.parseDouble(datosPosicion.get(i).get(0).toString()) + Double.parseDouble(datosPosicion.get(i).get(1).toString())*Double.parseDouble(datosPosicion.get(i).get(1).toString());
            
            rangoXmin = Double.parseDouble(datosPosicion.get(i).get(0).toString()) - radio; 
            rangoYmin = Double.parseDouble(datosPosicion.get(i).get(1).toString()) - radio;
            rangoXmax = Double.parseDouble(datosPosicion.get(i).get(0).toString()) + radio; 
            rangoYmax = Double.parseDouble(datosPosicion.get(i).get(1).toString()) + radio;
            
            for(int j=0; j<10; j++){
                randomX = rangoXmin + (Math.random() * (rangoXmax - rangoXmin));
                randomY = rangoYmin + (Math.random() * (rangoYmax - rangoYmin));
                // System.out.println(randomY);
            }
            
            //Definiendo los limites del area cuadrada
            temLimiteSup.add(Double.parseDouble(datosPosicion.get(i).get(0).toString()));
            temLimiteSup.add(Double.parseDouble(datosPosicion.get(i).get(1).toString()) + radio);
            temLimiteDer.add(Double.parseDouble(datosPosicion.get(i).get(0).toString()) + radio);
            temLimiteDer.add(Double.parseDouble(datosPosicion.get(i).get(1).toString()));
            temLimiteInf.add(Double.parseDouble(datosPosicion.get(i).get(0).toString()));
            temLimiteInf.add(Double.parseDouble(datosPosicion.get(i).get(1).toString()) - radio);
            temLimiteIzq.add(Double.parseDouble(datosPosicion.get(i).get(0).toString()) - radio);
            temLimiteIzq.add(Double.parseDouble(datosPosicion.get(i).get(1).toString()));
            
            //Calcular la distancia menor respecto a los limites
            temDistanciaMenor.add(0, distanciaEntreDosPuntos(randomX, randomY, temLimiteSup.get(0), temLimiteSup.get(1)));
            
            //System.out.println(distanciaEntreDosPuntos(-2.0,-2.0,-4.0,-4.0));
            //System.out.println(randomX + ", "+ randomY + " y " +temLimiteSup.get(0) + ", "+ temLimiteSup.get(1));
            
            temDistanciaMenor.add(1, "limiteSup");
            
            if (distanciaEntreDosPuntos(randomX, randomY, temLimiteDer.get(0), temLimiteDer.get(1)) < Double.parseDouble(temDistanciaMenor.get(0).toString())){
                temDistanciaMenor.add(0, distanciaEntreDosPuntos(randomX, randomY, temLimiteDer.get(0), temLimiteDer.get(1)));
                temDistanciaMenor.add(1, "limiteDer");
            }else{
                if(distanciaEntreDosPuntos(randomX, randomY, temLimiteInf.get(0), temLimiteInf.get(1)) < Double.parseDouble(temDistanciaMenor.get(0).toString())){
                    temDistanciaMenor.add(0, distanciaEntreDosPuntos(randomX, randomY, temLimiteInf.get(0), temLimiteInf.get(1)));
                    temDistanciaMenor.add(1, "limiteInf");
                    
                }else{
                    if(distanciaEntreDosPuntos(randomX, randomY, temLimiteIzq.get(0), temLimiteIzq.get(1)) < Double.parseDouble(temDistanciaMenor.get(0).toString())){
                        temDistanciaMenor.add(0, distanciaEntreDosPuntos(randomX, randomY, temLimiteIzq.get(0), temLimiteIzq.get(1)));
                        temDistanciaMenor.add(1, "limiteIzq");
                    }
                }
            }
            distanciaMenor.add(temDistanciaMenor);
            
            limiteSup.add(temLimiteSup);
            limiteDer.add(temLimiteDer);
            limiteInf.add(temLimiteInf);
            limiteIzq.add(temLimiteIzq);
            
            //System.out.println(distanciaMenor.get(i).get(0) + ", " + distanciaMenor.get(i).get(1));
            //System.out.println(limiteSup.get(i).get(0) + ", " + limiteSup.get(i).get(1));
            //System.out.println(limiteDer.get(i).get(0) + ", " + limiteDer.get(i).get(1));
            //System.out.println(limiteInf.get(i).get(0) + ", " + limiteInf.get(i).get(1));
            //System.out.println(limiteIzq.get(i).get(0) + ", " + limiteIzq.get(i).get(1));
            
            //System.out.println("------------");
        }
    }
    
    public double distanciaEntreDosPuntos(Double x1, Double y1, Double x2, Double y2){
        double distancia;
        distancia = Math.sqrt(Math.pow((x2-x1), 2) + Math.pow((y2-y1), 2));
        return distancia;
    }
    
    
    public void generarAreaSemiCircular(){
        int size = datosPosicion.size();
        double randomX, randomY, rangoXmin, rangoYmin, rangoXmax, rangoYmax;
        ArrayList parejaOfuscada;
        for(int i=0; i<size;i++){
            parejaOfuscada = new ArrayList();
            if(distanciaMenor.get(i).get(1).equals("limiteSup")){
                
                rangoXmin = Double.parseDouble(limiteSup.get(i).get(0).toString()) - radio; 
                rangoYmin = Double.parseDouble(limiteSup.get(i).get(1).toString());
                rangoXmax = Double.parseDouble(limiteSup.get(i).get(0).toString()) + radio; 
                rangoYmax = Double.parseDouble(limiteSup.get(i).get(1).toString()) + radio;
                
                randomX = rangoXmin + (Math.random() * (rangoXmax - rangoXmin));
                randomY = rangoYmin + (Math.random() * (rangoYmax - rangoYmin));
                parejaOfuscada.add(randomX);
                parejaOfuscada.add(randomY);
                parejaOfuscada.add(datosPosicion.get(i).get(2));
                parejaOfuscada.add(datosPosicion.get(i).get(3));
                parejaOfuscada.add(datosPosicion.get(i).get(4));
                parejaOfuscada.add(datosPosicion.get(i).get(5));
                parejaOfuscada.add(datosPosicion.get(i).get(6));
            }else{
                if(distanciaMenor.get(i).get(1).equals("limiteDer")){
                    rangoXmin = Double.parseDouble(limiteDer.get(i).get(0).toString()); 
                    rangoYmin = Double.parseDouble(limiteDer.get(i).get(1).toString()) - radio;
                    rangoXmax = Double.parseDouble(limiteDer.get(i).get(0).toString()) + radio; 
                    rangoYmax = Double.parseDouble(limiteDer.get(i).get(1).toString()) + radio;

                    randomX = rangoXmin + (Math.random() * (rangoXmax - rangoXmin));
                    randomY = rangoYmin + (Math.random() * (rangoYmax - rangoYmin));
                    parejaOfuscada.add(randomX);
                    parejaOfuscada.add(randomY);
                    parejaOfuscada.add(datosPosicion.get(i).get(2));
                    parejaOfuscada.add(datosPosicion.get(i).get(3));
                    parejaOfuscada.add(datosPosicion.get(i).get(4));
                    parejaOfuscada.add(datosPosicion.get(i).get(5));
                    parejaOfuscada.add(datosPosicion.get(i).get(6));
                }else{
                    if(distanciaMenor.get(i).get(1).equals("limiteInf")){
                        rangoXmin = Double.parseDouble(limiteInf.get(i).get(0).toString()); 
                        rangoYmin = Double.parseDouble(limiteInf.get(i).get(1).toString()) - radio;
                        rangoXmax = Double.parseDouble(limiteInf.get(i).get(0).toString()) + radio; 
                        rangoYmax = Double.parseDouble(limiteInf.get(i).get(1).toString()) + radio;

                        randomX = rangoXmin + (Math.random() * (rangoXmax - rangoXmin));
                        randomY = rangoYmin + (Math.random() * (rangoYmax - rangoYmin));
                        parejaOfuscada.add(randomX);
                        parejaOfuscada.add(randomY);
                        parejaOfuscada.add(datosPosicion.get(i).get(2));
                        parejaOfuscada.add(datosPosicion.get(i).get(3));
                        parejaOfuscada.add(datosPosicion.get(i).get(4));
                        parejaOfuscada.add(datosPosicion.get(i).get(5));
                        parejaOfuscada.add(datosPosicion.get(i).get(6));

                    }else{
                        rangoXmin = Double.parseDouble(limiteIzq.get(i).get(0).toString()); 
                        rangoYmin = Double.parseDouble(limiteIzq.get(i).get(1).toString()) - radio;
                        rangoXmax = Double.parseDouble(limiteIzq.get(i).get(0).toString()) + radio; 
                        rangoYmax = Double.parseDouble(limiteIzq.get(i).get(1).toString()) + radio;

                        randomX = rangoXmin + (Math.random() * (rangoXmax - rangoXmin));
                        randomY = rangoYmin + (Math.random() * (rangoYmax - rangoYmin));
                        parejaOfuscada.add(randomX);
                        parejaOfuscada.add(randomY);
                        parejaOfuscada.add(datosPosicion.get(i).get(2));
                        parejaOfuscada.add(datosPosicion.get(i).get(3));
                        parejaOfuscada.add(datosPosicion.get(i).get(4));
                        parejaOfuscada.add(datosPosicion.get(i).get(5));
                        parejaOfuscada.add(datosPosicion.get(i).get(6));
                    }
                }
            }
            datosPosicionOfuscados.add(parejaOfuscada);
        }
    }
    
    
    public void generarReporte(){
        FileWriter nuevoReporte = null;
        PrintWriter pw = null;
            try{
            nuevoReporte = new FileWriter("logs/log.txt");
            pw = new PrintWriter(nuevoReporte);
            int size = datosPosicion.size();
            pw.println("Original Data");
            pw.print("Latitude; Longitude; Code; Altitude (Feet); Date; Date (Standard); Time\n");
            for (int i=0; i<size; i++){
                for(int j=0; j<6;j++){
                    pw.print(datosPosicion.get(i).get(j) + "; ");
                }
                pw.print(datosPosicion.get(i).get(6) + "\n");  
            }
            
            pw.println("\nObfuscated Data");
            pw.print("Latitude; Longitude; Code; Altitude (Feet); Date; Date (Standard); Time\n");
            for (int i=0; i<size; i++){
                for(int j=0; j<6;j++){
                    pw.print(datosPosicionOfuscados.get(i).get(j) + "; ");
                }
                pw.print(datosPosicionOfuscados.get(i).get(6) + "\n");  
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para 
           // asegurarnos que se cierra el fichero.
           if (null != nuevoReporte)
              nuevoReporte.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
    
    public String getDatos(){
        return todosDatos;
    }
    
}//END
