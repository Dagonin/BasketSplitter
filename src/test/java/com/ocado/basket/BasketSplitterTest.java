package com.ocado.basket;


import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BasketSplitterTest {

    private BasketSplitter splitter;

    @Before
    public void setUp() {
        splitter = new BasketSplitter("config.json");
    }

    @Test
    public void testSplitWithEmptyItemsList() {
        List<String> items = Arrays.asList();
        Map<String, List<String>> result = splitter.split(items);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSplitWithUnknownItems() {
        List<String> items = Arrays.asList("Unknown Item 1", "Unknown Item 2");
        Map<String, List<String>> result = splitter.split(items);
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSplitWithSingleItem() {
        List<String> items = Arrays.asList("Cookies Oatmeal Raisin");
        Map<String, List<String>> result = splitter.split(items);
        assertTrue(result.containsKey("Pick-up point"));
        assertEquals(Arrays.asList("Cookies Oatmeal Raisin"), result.get("Pick-up point"));
    }

    @Test
    public void testSplitWithMultipleItems() {
        List<String> items = Arrays.asList("Cocoa Butter", "Tart - Raisin And Pecan", "Table Cloth 54x72 White", "Flower - Daisies", "Fond - Chocolate", "Cookies - Englishbay Wht");
        Map<String, List<String>> result = splitter.split(items);
        assertTrue(result.containsKey("Pick-up point"));
        assertTrue(result.containsKey("Courier"));
        assertEquals(Arrays.asList("Fond - Chocolate"), result.get("Pick-up point"));
        assertEquals(Arrays.asList("Cocoa Butter", "Tart - Raisin And Pecan", "Table Cloth 54x72 White", "Flower - Daisies", "Cookies - Englishbay Wht"), result.get("Courier"));
    }

    @Test
    public void testSplitWithDuplicateItems() {
        List<String> items = Arrays.asList("Cookies Oatmeal Raisin", "Cookies Oatmeal Raisin", "Cheese Cloth");
        Map<String, List<String>> result = splitter.split(items);
        assertTrue(result.containsKey("Pick-up point"));
        assertEquals(Arrays.asList("Cookies Oatmeal Raisin", "Cookies Oatmeal Raisin", "Cheese Cloth"), result.get("Pick-up point"));
    }
}
