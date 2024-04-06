package com.ocado.basket;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class BasketSplitter {

    // Mapa przechowująca dane z config.JSON
    public Map<String, List<String>> config;
    // Mapa przechowująca informacje o możliwych środkach transportu dla każdego produktu z koszyka
    private Map<String, List<String>> MeansOfTransport;


    // Wczytanie danych z pliku
    public BasketSplitter(String absolutePathToConfigFile) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            config = objectMapper.readValue(new File(absolutePathToConfigFile), Map.class);
            InitializeMOT();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Funckja rozdzielająca produkty na środki transpotru
    public Map<String, List<String>> split(List<String> items) {
        // Stworzenie Mapy z wynikiem
        Map<String, List<String>> result = new HashMap<>();

        // Dodanie każdego produktu do Mapy ze środkami transportu
        for (String item : items) {
            List<String> transportList = config.get(item);
            if (transportList != null) {
                for (String transport : transportList) {
                    MeansOfTransport.get(transport).add(item);
                }
            }
        }

        String max = getMaxTransport();
        // Dopóki max jest większy od zera, to zapisuj środek transportu z najwiekszą liczbą produktów do wyniku, i usuń jego produkty z pozostałych
        while (MeansOfTransport.get(max).size()>0) {
            result.put(max, new ArrayList<>(MeansOfTransport.get(max)));
            removeFromOthers(max);
            max = getMaxTransport();
        }
        return result;
    }

    // Inicjalizacja MeansOfTransport, czyli dodanie każdego możliwego środka transportu
    private void InitializeMOT(){
        MeansOfTransport = new HashMap<>();
        for (List<String> transportList : config.values()) {
            for (String transport : transportList) {
                if (!MeansOfTransport.containsKey(transport)) {
                    MeansOfTransport.put(transport, new ArrayList<>());
                }
            }
        }
    }

    // Wybranie środka transportu z największą liczbą produktów
    private String getMaxTransport(){
        String maxTransport = MeansOfTransport.entrySet().iterator().next().getKey();
        for (Map.Entry<String, List<String>> entry : MeansOfTransport.entrySet()) {
            if (entry.getValue().size() > MeansOfTransport.get(maxTransport).size()) {
                maxTransport = entry.getKey();
            }
        }
        return maxTransport;
    }

    // Usunięcie produktu ze wszystkich środków transportu
    private void removeFromOthers(String maxTransport) {
        List<String> itemsToRemove = new ArrayList<>(MeansOfTransport.get(maxTransport));
        for (String item : itemsToRemove) {
            for (List<String> transportList : MeansOfTransport.values()) {
                transportList.remove(item);
            }
        }
    }

}
