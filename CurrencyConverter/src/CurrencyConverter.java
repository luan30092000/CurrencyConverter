import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Scanner;

public class CurrencyConverter {

    public static void main(String[] args) {
        HashMap<Integer, String> currencyCodes = new HashMap<Integer, String>();

        String fromCode, toCode;
        double amountIn;
        double amountOUt;

        Scanner sc = new Scanner(System.in);

        System.out.println("Welcome to the currency converter!");
        System.out.println("1:USD (US Dollar) \t 2:CAD (Canadian Dollar) \t 3:EUR (Euro) \t 4:HKD (Hong Kong Dollar) \t 5:INR (Indian Rupee)");

        System.out.println("Currency converting FROM?");
        fromCode = sc.nextLine();

        System.out.println("Currency converting TO?");
        toCode = sc.nextLine();

        System.out.println("Amount you want to convert?");
        amountIn = sc.nextDouble();
        try {
            double amountOut = sendHttpGETRequest(fromCode, toCode, amountIn);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        System.out.println("Thank you for using the this currency converter!");


    }

    private static double sendHttpGETRequest(String fromCode, String toCode, double amount) throws IOException, InterruptedException {

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://currency-exchange.p.rapidapi.com/exchange?from=" + fromCode + "&to=" + toCode + "&q=1.0"))
                .header("X-RapidAPI-Key", "5e1583d02dmsh6bda716f8ca4fd8p10cf1cjsn4fcd3c389c9e")
                .header("X-RapidAPI-Host", "currency-exchange.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        double rate = Double.parseDouble(response.body());
        return amount * rate;


    }

}
