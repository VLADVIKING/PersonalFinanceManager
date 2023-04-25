package netology.ru;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {



        try (ServerSocket server = new ServerSocket(8989)) {

            while (true) {

                try (Socket client = server.accept();
                     PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                ) {
                    writer.println("Server ready!");
                    JSONParser parser = new JSONParser();
                    try {
                        MaxCategory maxCategory = new MaxCategory();

                        Object object = parser.parse(new StringReader(reader.readLine()));
                        JSONObject jsonObject = (JSONObject) object;
                        maxCategory.setProductName((String) jsonObject.get("title"));
                        maxCategory.setDate((String) jsonObject.get("date"));
                        maxCategory.setProductSum((Long) jsonObject.get("sum"));

                        maxCategory.loadCategoriesList();
                        maxCategory.maxCategoryCalc();

                        JSONObject maxOutput = new JSONObject();
                        maxOutput.put("sum", maxCategory.getMaxSum());
                        maxOutput.put("category", maxCategory.getMaxCategory());
                        System.out.println("{\n" +
                                "'maxCategory':\n" +
                                maxOutput.toJSONString() + "\n}");
                        JSONObject dayMaxOutput = new JSONObject();
                        dayMaxOutput.put("sum", maxCategory.getDayMaxSum());
                        dayMaxOutput.put("category", maxCategory.getDayMaxCategory());
                        System.out.println("{\n" +
                                "'maxDayCategory':\n" +
                                dayMaxOutput.toJSONString() + "\n}");
                        JSONObject monthMaxOutput = new JSONObject();
                        monthMaxOutput.put("sum", maxCategory.getMonthMaxSum());
                        monthMaxOutput.put("category", maxCategory.getMonthMaxCategory());
                        System.out.println("{\n" +
                                "'maxMonthCategory':\n" +
                                monthMaxOutput.toJSONString() + "\n}");
                        JSONObject yearMaxOutput = new JSONObject();
                        yearMaxOutput.put("sum", maxCategory.getYearMaxSum());
                        yearMaxOutput.put("category", maxCategory.getYearMaxCategory());
                        System.out.println("{\n" +
                                "'maxYearCategory':\n" +
                                yearMaxOutput.toJSONString() + "\n}");
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
