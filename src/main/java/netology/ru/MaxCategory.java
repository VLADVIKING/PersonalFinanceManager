package netology.ru;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class MaxCategory {

    private static long maxSum = 0;
    private static String productName;
    private static String date;
    private static long categorySum;
    private static List<String> products = new ArrayList<>();
    private static List<String> categories = new ArrayList<>();
    private static Map<String, Long> maxResult = new HashMap<>();


    public MaxCategory(String productName, String date, long categorySum) {
        this.productName = productName;
        this.date = date;
        this.categorySum = categorySum;
    }

    public static void loadCategoriesList() {
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

    public static Map<String, Long> maxCategoryCalc() {
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

    public static long getMaxSum() {
        return maxSum;
    }

    public static String getMaxCategory() {
        for (String maxCategory : maxResult.keySet()) {
            if (MaxCategory.getMaxSum() == maxResult.get(maxCategory)) {
                return maxCategory;
            }
        }
        return null; }

    public static String getProductName() {
        return productName;
    }

    public static void setProductName(String productName) {
        MaxCategory.productName = productName;
    }

    public static long getCategorySum() {
        return categorySum;
    }

    public static void setCategorySum(long categorySum) {
        MaxCategory.categorySum = categorySum;
    }

    public static String getDate() {
        return date;
    }

    public static void setDate(String date) {
        MaxCategory.date = date;
    }

}
