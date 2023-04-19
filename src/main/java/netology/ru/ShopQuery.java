package netology.ru;

import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ShopQuery {

    private static String title;
    private static String date;
    private static long sum;

    public static void main(String[] args) {

        try (Socket socket = new Socket("127.0.0.1", 8989);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            reader.readLine();
            JSONObject obj = new JSONObject();
            obj.put("title", scannerShopTitle());
            obj.put("date", scannerShopDate());
            obj.put("sum", scannerShopSum());
            writer.println(obj.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String scannerShopTitle() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введи название продукта:");
        title = scanner.nextLine().toLowerCase();
        return title;
    }

    public static String scannerShopDate() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введи дату покупки в формате 'год.месяц.число':");
        date = scanner.nextLine();
        return date;
    }

    public static Long scannerShopSum() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Введи сумму покупки:");
        sum = scanner.nextLong();
        return sum;
    }
}
