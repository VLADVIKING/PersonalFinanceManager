package netology.ru;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class MaxCategory {

    private long categorySum = 0;
    private String productName;
    private String date;
    private long productSum;
    private List<String> products = new ArrayList<>();
    private List<String> categories = new ArrayList<>();
    private Map<String, Long> maxResult = new HashMap<>();
    private long maxValue;
    private List<String> savedMaxCategories = new ArrayList<>();
    private List<Long> savedMaxSums = new ArrayList<>();
    private List<String> savedDates = new ArrayList<>();
    private List<LocalDate> localDates = new ArrayList<>();
    private List<String> dayMaxCategories = new ArrayList<>();
    private List<Long> dayMaxSums = new ArrayList<>();
    private long dayMaxValue;
    private List<String> monthMaxCategories = new ArrayList<>();
    private List<Long> monthMaxSums = new ArrayList<>();
    private long monthMaxValue;
    private List<String> yearMaxCategories = new ArrayList<>();
    private List<Long> yearMaxSums = new ArrayList<>();
    private long yearMaxValue;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");


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
        maxValue = Collections.max(maxResult.values());
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
        try (FileOutputStream fos = new FileOutputStream("data.bin", true);
             PrintStream out = new PrintStream(fos)) {
            for (var entry : maxResult.entrySet()) {
                out.println(entry.getKey() + "\t" + entry.getValue());
            }
            out.println(getMaxCategory() + "\t" + getMaxSum() + "\t" + getDate());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadDataBin() {
        try (BufferedReader in = new BufferedReader(new FileReader("data.bin"))) {
            String line;
            while ((line = in.readLine()) != null) {

                String[] arr = line.split("\\s+");
                if (arr.length == 2) {
                    maxResult.put(arr[0], Long.parseLong(arr[1]));
                    continue;
                }

                savedMaxCategories.add(arr[0]);
                savedMaxSums.add(Long.parseLong(arr[arr.length - 2]));
                savedDates.add(arr[arr.length - 1]);
            }
            for (int i = 0; i < savedDates.size(); i++) {
                localDates.add(LocalDate.parse(savedDates.get(i), formatter));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void daySortedMaxCollections() {
        for (int i = 0; i < localDates.size(); i++) {
            if (localDates.get(i).isEqual(LocalDate.now())) {
                dayMaxCategories.add(savedMaxCategories.get(i));
                dayMaxSums.add(savedMaxSums.get(i));
            }
            if ((LocalDate.parse(getDate(), formatter).isEqual(LocalDate.now()))) {
                dayMaxCategories.add(getMaxCategory().toString());
                dayMaxSums.add(getMaxSum());
            }
        }
    }

    public long getDayMaxSum() {
        daySortedMaxCollections();
        if (dayMaxSums.size() == 0) {
            dayMaxValue = getMaxSum();
            return dayMaxValue;
        }
        dayMaxValue = Collections.max(dayMaxSums);
        return dayMaxValue;
    }

    public List<String> getDayMaxCategory() {
        List<String> dayCategories = new ArrayList<>();
        if (dayMaxCategories.size() == 0) {
            dayCategories.addAll(getMaxCategory());
            return dayCategories;
        }
        for (int i = 0; i < dayMaxCategories.size(); i++) {
            if (dayMaxSums.get(i).equals(dayMaxValue)) {
                dayCategories.add(dayMaxCategories.get(i));
            }
        }
        return dayCategories;
    }

    public void monthSortedMaxCollections() {

        for (int i = 0; i < localDates.size(); i++) {
            if (localDates.get(i).isAfter(LocalDate.now().minusMonths(1))) {
                monthMaxCategories.add(savedMaxCategories.get(i));
                monthMaxSums.add(savedMaxSums.get(i));
            }
        }
        if ((LocalDate.parse(getDate(), formatter).isAfter(LocalDate.now().minusMonths(1)))) {
            monthMaxCategories.add(getMaxCategory().toString());
            monthMaxSums.add(getMaxSum());
        }
    }

    public long getMonthMaxSum() {
        monthSortedMaxCollections();
        if (monthMaxSums.size() == 0) {
            monthMaxValue = getMaxSum();
            return monthMaxValue;
        }
        monthMaxValue = Collections.max(monthMaxSums);
        return monthMaxValue;
    }

    public List<String> getMonthMaxCategory() {
        List<String> monthCategories = new ArrayList<>();
        if (monthMaxCategories.size() == 0) {
            monthCategories.addAll(getMaxCategory());
            return monthCategories;
        }
        for (int i = 0; i < monthMaxCategories.size(); i++) {
            if (monthMaxSums.get(i).equals(monthMaxValue)) {
                monthCategories.add(monthMaxCategories.get(i));
            }
        }
        return monthCategories;
    }

    public void yearSortedMaxCollections() {

        for (int i = 0; i < localDates.size(); i++) {
            if (localDates.get(i).isAfter(LocalDate.now().minusYears(1))) {
                yearMaxCategories.add(savedMaxCategories.get(i));
                yearMaxSums.add(savedMaxSums.get(i));
            }
        }
        if ((LocalDate.parse(getDate(), formatter).isAfter(LocalDate.now().minusYears(1)))) {
            yearMaxCategories.add(getMaxCategory().toString());
            yearMaxSums.add(getMaxSum());
        }
    }

    public long getYearMaxSum() {
        yearSortedMaxCollections();
        if (yearMaxSums.size() == 0) {
            yearMaxValue = getMaxSum();
            return yearMaxValue;
        }
        yearMaxValue = Collections.max(yearMaxSums);
        return yearMaxValue;
    }

    public List<String> getYearMaxCategory() {
        List<String> yearCategories = new ArrayList<>();
        if (yearMaxCategories.size() == 0) {
            yearCategories.addAll(getMaxCategory());
            return yearCategories;
        }
        for (int i = 0; i < yearMaxCategories.size(); i++) {
            if (yearMaxSums.get(i).equals(yearMaxValue)) {
                yearCategories.add(yearMaxCategories.get(i));
            }
        }
        return yearCategories;
    }

}
