package cli;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {

    Context context;
    Connection connection;
    String url;

    public Main(){
        setContext(context);
    }
   // public static void main(String[] args){
        //Main method (
        /*Inicializar base de datos*/
     //   if(!new File("../../assets/FMiradas.sql").exists()) {
       //     indicatorsToSql();
        //}
    //}

    public static void indicatorsToSql(){
        try(BufferedReader br=new BufferedReader(new FileReader("../../assets/AutisticOrganization.csv"))){
            try(BufferedWriter bw=new BufferedWriter(new FileWriter("../../assets/FMiradas.sql",true))){
                String line = br.readLine();
                int current_indicator = 1;
                while (line != null) {
                    String[] aux = line.split(";");
                    bw.write("INSERT INTO indicators VALUES("+current_indicator+",'AUTISM','"+aux[8].split(": ")[1]+"',"+Integer.parseInt(aux[9])+");"+System.lineSeparator());
                    for (int i = 0; i < 4; i++) {
                        int current_evidence = i + 1;
                        //evidences.add(new Evidence(current_evidence, current_indicator, "AUTISM", aux[i + 4], Float.parseFloat(aux[i]))); //The first 4 elements of aux are the values of every incidence, the next 8 elements of aux are the description of every incidence
                        bw.write("INSERT INTO evidences VALUES("+current_evidence+","+current_indicator+",'AUTISM','"+aux[i+4]+"',"+Integer.parseInt(aux[i])+");"+System.lineSeparator());
                    }
                    line = br.readLine();
                    current_indicator++;
                }
            }
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR. FILE NOT FOUND");
            ex.printStackTrace();
        } catch (Exception ex) {
            System.out.println("Error de lectura del fichero");
            ex.printStackTrace();
        }
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

}
