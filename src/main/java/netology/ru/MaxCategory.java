package netology.ru;

import java.io.*;
import java.util.*;

public class MaxCategory {

    private long categorySum = 0;
    private String productName;
    private String date;
    private long productSum;
    private List<String> products = new ArrayList<>();
    private List<String> categories = new ArrayList<>();
    private Map<String, Long> maxResult = new HashMap<>();


    public MaxCategory(String productName, String date, long productSum) {
        this.productName = productName;
        this.date = date;
        this.productSum = productSum;
    }

    public MaxCategory() {
    }

    public void loadCategoriesList() {

        try (BufferedReader in = new BufferedReader(new FileReader("categories.tsv"))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] arr = line.split("\\s+");
                products.add(arr[0]);
                categories.add(arr[1]);
                maxResult.put(arr[1], 0L);
            }
            maxResult.put("другое", 0L);

        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File("data.bin");
        if (file.exists()) {
            loadDataBin();
        }
    }

    public Map<String, Long> maxCategoryCalc() {

        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).equals((getProductName()))) {
                categorySum = maxResult.get(categories.get(i));
                categorySum += getProductSum();
                maxResult.put(categories.get(i), categorySum);
                saveDataBin();
                return maxResult;
            }
        }
        categorySum = maxResult.get("другое");
        categorySum += getProductSum();
        maxResult.put("другое", categorySum);
        saveDataBin();
        return maxResult;
    }

    public long getMaxSum() {
        long maxValue = Collections.max(maxResult.values());
        return maxValue;
    }

    public List<String> getMaxCategory() {
        List<String> keys = new ArrayList<>();
        maxResult.entrySet().stream()
                .filter(value -> value.getValue() == getMaxSum())
                .forEach(key -> keys.add(key.getKey()));
        return keys;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getProductSum() {
        return productSum;
    }

    public void setProductSum(long categorySum) {
        this.productSum = categorySum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void saveDataBin() {
        try (FileOutputStream fos = new FileOutputStream("data.bin");
             PrintStream out = new PrintStream(fos)) {
            for (var entry : maxResult.entrySet()) {
                out.println(entry.getKey() + "\t" + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void loadDataBin() {
        try (BufferedReader in = new BufferedReader(new FileReader("data.bin"))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] arr = line.split("\\s+");
                for (int i = 0; i < arr.length; i++) {
                    maxResult.put(arr[0], Long.parseLong(arr[1]));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
