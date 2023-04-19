package netology.ru;

import java.util.*;

public class MaxCategoryMock extends MaxCategory {

    long maxSum;
    long categorySumTotal;
    Map<String, Long> maxResult = new HashMap<>();
    List<String> products = new ArrayList<>();
    List<String> categories = new ArrayList<>();


    @Override
    public String getProductName() {
        return "колбаса";
    }

    public String getCategoryName() {
        return "еда";
    }

    public Map<String, Long> loadMaxResultMap() {
        maxResult.put("еда", 0L);
        maxResult.put("одежда", 0L);
        maxResult.put("быт", 0L);
        maxResult.put("финансы", 0L);
        maxResult.put("другое", 0L);
        return maxResult;
    }

    @Override
    public long getCategorySum() {
        return 300L;
    }
    @Override
    public Map<String, Long> maxCategoryCalc() {
loadMaxResultMap();
        products = Arrays.asList("булка", "колбаса", "сухарики", "курица", "тапки", "шапка", "мыло", "акции");
        categories = Arrays.asList("еда", "еда", "еда", "еда", "одежда", "одежда", "быт", "финансы");
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).equals((getProductName()))) {
                categorySumTotal = maxResult.get(categories.get(i));
                categorySumTotal += getCategorySum();
                maxResult.put(categories.get(i), categorySumTotal);
                return maxResult;
            }
        }
        categorySumTotal = maxResult.get("другое");
        categorySumTotal += getCategorySum();
        maxResult.put("другое", categorySumTotal);
        return maxResult;
    }

    @Override
    public List<String> getMaxCategory() {
        List<String> keys = new ArrayList<>();
        loadMaxResultMap();
        maxResult.entrySet().stream()
                .filter(value -> value.getValue() == getMaxSum())
                .forEach(key -> keys.add(key.getKey()));
        return keys;
            }


    public long getMaxSum() {
        long maxValue = Collections.max(maxResult.values());
        return maxValue;
    }

}
