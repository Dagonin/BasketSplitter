Rozwiązanie zadania BasketSplitter:


Klasa BasketSplitter przechowuje 2 zmienne

    config - jest to wczytana wartość z pliku config
    MeansOfTransport - to mapa zawiejąca informacje o wszystkich środkach transportu i przypisanych do nich produktów z koszyka.
                        np. jeżeli w koszyku znajduje się: "Fond - Chocolate" to MeansOfTransport wygląda tak:
                        "Pick-up point": ["Fond - Chocolate"],
                        "Express Collection": ["Fond - Chocolate"],
                        "Mailbox delivery": ["Fond - Chocolate"],
                        niżej reszta środków transportu


Funkcja split wybiera klucz z MeansOfTransport, który posiada najwięcej wartości, zapisuje go do wyniku jago grupa dostaw i usuwa produkty znajdujące się tam z pozostałych środków transportu.
Powtarza się to, aż wszystkie klucze z mapy są puste.


Wykorzystałem bibliotekę Jackson aby przekonwertować plik config.JSON na Map<String, List<String>>