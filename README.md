# YouTube Data Analytics - Kotlin Homework

Ez a projekt egy Kotlin alapú parancssoros (CLI) adatelemző alkalmazás, amely a YouTube "Trending" videóinak nyilvános adatkészletét dolgozza fel. Az alkalmazás képes statisztikai listákat készíteni és adatvizualizációt (grafikonokat) generálni.

## Funkciók

A program az alábbi 7 analitikai kérdésre ad választ:
1. **Kategóriák népszerűsége:** Mely kategóriákban van a legtöbb trendelő videó?
2. **Top Csatornák:** Mely csatornák szerepelnek a legtöbbször a listán?
3. **Legnézettebb videók:** A legnagyobb megtekintéssel rendelkező videók listája.
4. **Like/Dislike arány:** A legjobb és legrosszabb aránnyal rendelkező videók.
5. **Globális trendek:** Mely videók kerültek fel a trend listára több országban is?
6. **Idősoros elemzés:** Kategóriák népszerűségének változása időben (Vonaldiagram).
7. **Korreláció:** Összefüggés a nézettség és a kommentek száma között (Szórásdiagram).

> A 6-os és 7-es pont HTML formátumú interaktív grafikonokat generál a projekt gyökérmappájába (`.html` fájlokként).

## Követelmények

* **JDK:** Java Development Kit 21 (vagy újabb)
* **Adatok telepítése (FONTOS):**
    * A projekt méretkorlátok miatt **nem tartalmazza** a nyers adatfájlokat.
    * Kérlek, töltsd le a [Trending YouTube Video Statistics](https://www.kaggle.com/datasnaek/youtube-new) adatbázist a Kaggle-ről.
    * A letöltött zip fájl tartalmát (a `.csv` és `.json` fájlokat) csomagold ki a projekt gyökerében található **`data/`** mappába.
    * A program futtatásához szükséges, hogy a fájlok ebben a mappában legyenek (pl. `data/USvideos.csv`).

## Futtatás

A projekt **Gradle** build rendszert használ. A futtatáshoz nem szükséges a Gradle előzetes telepítése, használd a mellékelt wrapper szkripteket:

**Windows környezetben (CMD vagy PowerShell):**
```bash
gradlew.bat run
```

**Linux vagy macOS környezetben:**

```bash
./gradlew run
```

### Menü használata

Az alkalmazás elindulása után egy konzolos menüben választhatod ki a kívánt elemzést a számok megadásával:

  * **1-7**: A kívánt elemzés kiválasztása.
  * **0**: Kilépés az alkalmazásból.
