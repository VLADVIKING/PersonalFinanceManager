package netology.ru;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.*;

public class MaxCategoryTest extends TestCase {
    MaxCategoryMock maxCategoryMock = new MaxCategoryMock();

    @Test
    public void testMaxCategoryCalc() {

        Map<String, Long> expect = new HashMap<>();
        expect.put(maxCategoryMock.getCategoryName(), maxCategoryMock.getCategorySum());
        Map<String, Long> result = new HashMap<>();
        result.putAll(maxCategoryMock.maxCategoryCalc());
        assertEquals(expect.get(maxCategoryMock.getCategoryName()), result.get(maxCategoryMock.getCategoryName()));
    }

    @Test
    public void testGetMaxCategory() {
        String expect = maxCategoryMock.getCategoryName();
        List<String> keys = maxCategoryMock.getMaxCategory();
        keys.stream()
                .filter(x -> x.equals(maxCategoryMock.getCategoryName()))
                .forEach((result) -> assertEquals(expect, result));
    }
}