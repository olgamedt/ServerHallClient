
import com.google.gson.Gson;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import java.util.Scanner;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;

public class ServerClient {

    private static ClientConfig config = new DefaultClientConfig();
    private static Client client = Client.create(config);
    private static WebResource service = client.resource(
            UriBuilder.fromUri("http://localhost:8080/ServerHall/").build());

    private static Scanner in = new Scanner(System.in);

    Gson gson = new Gson();

    public void listLatestTemp() {

        String jsonString = service
                .path("rest/ServerHallDBService/temperature/latest")
                .accept(MediaType.APPLICATION_JSON)
                .get(String.class);

        System.out.println(jsonString);

        TempTable[] tempArray = gson.fromJson(jsonString, TempTable[].class);

        for (TempTable b : tempArray) {

            System.out.println("Senaste temperaturen är " + b.getTemperature()
                    + " grader och loggades " + b.getDate());

        }

    }

    public void listLatestEcons() {
        String jsonString = service
                .path("rest/ServerHallDBService/econsumption/latest")
                .accept(MediaType.APPLICATION_JSON)
                .get(String.class);

        System.out.println(jsonString);

        EconsumptionTable[] econsArray = gson.fromJson(jsonString, EconsumptionTable[].class);

        for (EconsumptionTable b : econsArray) {

            System.out.println("Senaste elförbrukningen är " + b.getEconsumption()
                    + " kwh och loggades " + b.getDate());

        }

    }

    public void listLatestEprice() {
        String jsonString = service
                .path("rest/ServerHallDBService/eprice/latest")
                .accept(MediaType.APPLICATION_JSON)
                .get(String.class);

        System.out.println(jsonString);

        EpriceTable[] epriceArray = gson.fromJson(jsonString, EpriceTable[].class);

        for (EpriceTable b : epriceArray) {

            System.out.println("Senaste elpriset är " + b.getEprice()
                    + " kr/kwh och loggades " + b.getDate());
        }

    }

    public void listAvgTemp() {
        String jsonString = service
                .path("rest/ServerHallDBService/temperature/avg")
                .accept(MediaType.APPLICATION_JSON)
                .get(String.class);

        System.out.println(jsonString);

        AvgTemperature[] avgArray = gson.fromJson(jsonString, AvgTemperature[].class);
        for (AvgTemperature b : avgArray) {

            System.out.println("Avg temp: " + b.getAvg()
                    + "\nMax temp: " + b.getMax()
                    + "\nMin temp: " + b.getMin());

        }
    }

    public void listAvgEcons() {

        String jsonString = service
                .path("rest/ServerHallDBService/econsumption/avg")
                .accept(MediaType.APPLICATION_JSON)
                .get(String.class);

        System.out.println(jsonString);

        AvgEconsumption[] avgArray = gson.fromJson(jsonString, AvgEconsumption[].class);

        for (AvgEconsumption b : avgArray) {

            System.out.println("Avg econs: " + b.getAvg()
                    + "\nMax Econs: " + b.getMax()
                    + "\nMax econs price: " + (b.getMax() * b.getEprice() + " kr/kwh")
                    + "\nMin econs: " + b.getMin()
                    + "\nMin econs price: " + (b.getMin() * b.getEprice() + " kr/kwh ")
                    + "\nWhere Eprice is " + b.getEprice());

        }

    }

    public void addTemp(int i) {

        TempTable nyTemp = new TempTable(i);

        ClientResponse response = service.path("rest/ServerHallDBService/temperature/add")
                .accept(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, nyTemp);

        System.out.println("Respons " + response.getEntity(String.class));

    }

    public void addEprice(int i) {

        EpriceTable nyEprice = new EpriceTable(i);

        ClientResponse response = service.path("rest/ServerHallDBService/eprice/add")
                .accept(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, nyEprice);

        System.out.println("Respons " + response.getEntity(String.class));

    }

    public static void main(String[] args) {

        ServerClient client = new ServerClient();
        //Scanner in = new Scanner(System.in);

        while (true) {

            System.out.println("1. Senaste temperaturen");
            System.out.println("2. Senaste elförbrukningen");
            System.out.println("3. Senaste elpriset");
            System.out.println("4. Lägg till temperatur");
            System.out.println("5. Lägg till ny elkostnad");
            System.out.println("6. Rapport på senaste temp, elförbrukning och elkostnad");
            System.out.println("7. Rapport på avg temp");
            System.out.println("8. Rapport på avg econs + eprice");
            System.out.println("Ange ditt val");

            String val = in.nextLine();

            switch (val) {

                case "1":
                    client.listLatestTemp();
                    break;

                case "2":
                    client.listLatestEcons();
                    break;

                case "3":
                    client.listLatestEprice();
                    break;

                case "4":
                    System.out.println("Ange temperatur: ");
                    int i = in.nextInt();

                    client.addTemp(i);
                    break;

                case "5":
                    System.out.println("Ange ny elkostnad");
                    int b = in.nextInt();

                    client.addEprice(b);
                    break;

                case "6":
                    System.out.println("Rapport: ");
                    client.listLatestTemp();
                    client.listLatestEcons();
                    client.listLatestEprice();
                    break;

                case "7":
                    System.out.println("Rapport: ");
                    client.listAvgTemp();
                    break;
                
                case "8":
                    System.out.println("Rapport");
                    client.listAvgEcons();
                    break;
                
                 
            }

        }

    }
}
