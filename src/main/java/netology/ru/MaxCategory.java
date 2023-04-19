package netology.ru;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MaxCategory {

    private long maxSum = 0;
    private String productName;
    private String date;
    private long categorySum;
    private List<String> products = new ArrayList<>();
    private List<String> categories = new ArrayList<>();
    private Map<String, Long> maxResult = new HashMap<>();


    public MaxCategory(String productName, String date, long categorySum) {
        this.productName = productName;
        this.date = date;
        this.categorySum = categorySum;
    }

    public MaxCategory() {}

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
    }

    public Map<String, Long> maxCategoryCalc() {
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).equals((getProductName()))) {
                maxSum = maxResult.get(categories.get(i));
                maxSum += getCategorySum();
                maxResult.put(categories.get(i), maxSum);
                return maxResult;
            }
        }
        maxSum = maxResult.get("другое");
        maxSum += getCategorySum();
        maxResult.put("другое", maxSum);
        return maxResult;
    }

    public long getMaxSum() {
        return maxSum;
    }

    public String getMaxCategory() {
        for (String maxCategory : maxResult.keySet()) {
            if (getMaxSum() == maxResult.get(maxCategory)) {
                return maxCategory;
            }
        }
        return null; }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public long getCategorySum() {
        return categorySum;
    }

    public void setCategorySum(long categorySum) {
        this.categorySum = categorySum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
