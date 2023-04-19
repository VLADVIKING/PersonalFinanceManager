package netology.ru;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {

        MaxCategory maxCategory = new MaxCategory();
        maxCategory.loadCategoriesList();

        try (ServerSocket server = new ServerSocket(8989)) {

            while (true) {

                try (Socket client = server.accept();
                     PrintWriter writer = new PrintWriter(client.getOutputStream(), true);
                     BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                ) {
                    writer.println("Server ready!");
                    JSONParser parser = new JSONParser();
                    try {
                        Object object = parser.parse(new StringReader(reader.readLine()));
                        JSONObject jsonObject = (JSONObject) object;
                        maxCategory.setProductName((String) jsonObject.get("title"));
                        maxCategory.setDate((String) jsonObject.get("date"));
                        maxCategory.setCategorySum((Long) jsonObject.get("sum"));

                        maxCategory.maxCategoryCalc();

                        JSONObject maxOutput = new JSONObject();
                        maxOutput.put("sum", maxCategory.getMaxSum());
                        maxOutput.put("category", maxCategory.getMaxCategory());
                        System.out.println("{\n" +
                                "'maxCategory':\n" +
                                maxOutput.toJSONString() + "\n}");
                    } catch (IOException | ParseException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
