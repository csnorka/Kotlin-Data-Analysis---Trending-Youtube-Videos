package api

import com.google.gson.Gson
import data.Categories
import data.Trending_Videos
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.nio.charset.StandardCharsets

fun loading(map: MutableMap<String, MutableList<Trending_Videos>>, categoryList: MutableList<Categories>) {
    println("Adatfájlok betöltése folyamatban (SAFE MODE)...")

    val countries: List<String> = listOf("CA", "DE", "FR", "GB", "RU", "IN", "JP", "KR", "MX", "US")
    val validCountries = countries.filter { File("data/${it}videos.csv").exists() }

    if (validCountries.isEmpty()) {
        println("HIBA: Nem találhatók a csv fájlok a 'data' mappában!")
        return
    }

    val csvFormat = CSVFormat.DEFAULT.builder()
        .setSkipHeaderRecord(true)
        .setIgnoreHeaderCase(true)
        .setTrim(true)
        .setQuote('"')
        .setIgnoreSurroundingSpaces(true)
        .build()

    for (countryCode in validCountries) {
        // 1. Kategória JSON
        val catFile = File("data/${countryCode}_category_id.json")
        if (catFile.exists()) {
            try {
                categoryList.add(
                    Gson().fromJson(catFile.readText(StandardCharsets.UTF_8), Categories::class.java)
                )
            } catch (e: Exception) { /* JSON hiba ignorálva */ }
        }

        // 2. CSV Parse - Manuális Sorolvasás
        val countryTrendingVideos: MutableList<Trending_Videos> = mutableListOf()
        var loadedCount = 0
        var errorCount = 0

        try {
            val file = File("data/${countryCode}videos.csv")
            val reader = BufferedReader(FileReader(file, StandardCharsets.UTF_8))

            reader.readLine()

            var line: String? = reader.readLine()
            val recordBuffer = StringBuilder()
            var index = 0

            while (line != null) {
                recordBuffer.append(line)

                val quoteCount = recordBuffer.count { it == '"' }

                if (quoteCount % 2 == 0) {
                    try {
                        val recordText = recordBuffer.toString()
                        val records = CSVParser.parse(recordText, csvFormat).records

                        if (records.isNotEmpty()) {
                            val record = records[0]

                            // RITKÍTÁS (minden 2. elem feldolgozása)
                            /*
                            index++
                            if (index % 2 != 0) {
                                if (record.size() >= 15) {
                                    countryTrendingVideos.add(Trending_Videos(record))
                                    loadedCount++
                                }
                            }*/

                            if (record.size() >= 15) {
                                countryTrendingVideos.add(Trending_Videos(record))
                                loadedCount++
                            }
                        }
                    } catch (e: Exception) {
                        errorCount++
                    }
                    recordBuffer.clear()
                } else {
                    recordBuffer.append("\n")
                }

                line = reader.readLine()
            }

            reader.close()
            map[countryCode] = countryTrendingVideos

        } catch (e: Exception) {
            println("KRITIKUS Hiba ($countryCode): ${e.message}")
        }
    }

    println("Betöltés kész! (${map.keys.size} ország)")
    map.forEach { (k, v) ->
        println("  -> $k: ${v.size} videó betöltve.")
    }
}