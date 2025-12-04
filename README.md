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
* **Adatok:** A forrásfájloknak (CSV és JSON) a projekt gyökerében lévő `data/` mappában kell elhelyezkedniük.

## Futtatás

A projekt **Gradle** build rendszert használ. A futtatáshoz nem szükséges a Gradle előzetes telepítése, használd a mellékelt wrapper szkripteket:

**Windows környezetben (CMD vagy PowerShell):**
```bash
gradlew.bat run
./gradlew run
