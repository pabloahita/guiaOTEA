package otea.connection;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ConnectionClient {

    private static final String BASE_URL = "https://oteawebapp-prueba1.azurewebsites.net"; // Cambiar al enlace definitivo

    private OkHttpClient client;
    private static Retrofit retrofit;
    public ConnectionClient() {
        client=new OkHttpClient().newBuilder().build();
        retrofit=new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public Retrofit getRetrofit(){return retrofit;}






    /*public static void indicatorsToSql(){
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
    }*/


}
