Program podzielony jest na 4 klasy. Pierwsza - ImportXML ma wykonywać konwersje plików XML do plików CSV. Zawiera funkcje ImportXML której argumentami są 2 stringi, w pierwszym należy podać ścieżke do pliku do konwersji, a w drugim ścieżke gdzie plik przekonwertowany ma zostać zapisany. Analogicznie jest w klasie ImportCSV.

Założenia na temat pliku XML:
- max głębokość danych - 2
- brak ';' w nazwach

Przykłdowy plik:
```
<?xml version="1.0"?>
<Tests name="Tests">
  <Test TestId="0001" TestType="Type1">
    <Data1>Data1.1</Data1>
    <Data2>Data2.1</Data2>
    <Data3>Data3.1</Data3>
  </Test>
  <Test TestId="0002" TestType="Type2">
    <Data1>Data1.2</Data1>
    <Data2>Data2.2</Data2>
    <Data3>Data3.2</Data3>
  </Test>
</Tests>
```

Obie klasy wykorzystują funkcje zmieniające ciągi znaków takie jak:
- delString(String input, int index) - usuwa z ciągu znak o podanym indeksie
- delChar(String input, char c) - usuwa z ciągu podany znak
- delString(String input, int startIndex, int endIndex) - usuwa z ciągu ciąg od startIndex do endIndex
- changeStringChar(String input, char startChar, char endChar) - zmienia znaki w ciagu na inne
- countChars(String input, char letter) - zwraca ilość liter
- nextIndexOf(String input, int index, char letter) - znajduje n-ta litere
- RemoveNullVal(String[] input) - usuwa z tablicy nulle
Następnie każda z głównych funkcji wywołuje funkcje trzeciej klasy toFile, której przekazywana jest tablica, która jest zapisywana w pliku o podanej ścieżce.
Ostatnią klasą jest main w której wywoływane są poszczególne funkcje.
