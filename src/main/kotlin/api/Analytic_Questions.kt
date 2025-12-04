package api

import data.Categories
import data.Trending_Videos
import org.jetbrains.kotlinx.dataframe.api.*
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.kandy.dsl.*
import org.jetbrains.kotlinx.kandy.letsplot.*
import org.jetbrains.kotlinx.kandy.letsplot.export.save
import org.jetbrains.kotlinx.kandy.letsplot.feature.layout
import org.jetbrains.kotlinx.kandy.letsplot.layers.line
import org.jetbrains.kotlinx.kandy.letsplot.layers.points
import org.jetbrains.kotlinx.kandy.util.color.Color
import java.awt.Desktop
import java.io.File
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

object Analytic_Questions {

    private var trendingVideosMap: MutableMap<String, MutableList<Trending_Videos>> = mutableMapOf()
    private var categoryList: MutableList<Categories> = mutableListOf()
    private var categoryIdToName = mutableMapOf<String, String>()

    private val usDataFrame: DataFrame<Trending_Videos> by lazy {
        println("DataFrame építése...")
        val videos = trendingVideosMap["US"] ?: emptyList()
        if (videos.isEmpty()) {
            println("FIGYELEM: Nincs adat az US kulcshoz! (Lefutott az initData?)")
        }
        videos.toDataFrame()
    }

    fun initData() {
        println("Adatok betöltésének indítása...")
        loading(trendingVideosMap, categoryList)

        categoryList.firstOrNull()?.items?.forEach {
            categoryIdToName[it.id] = it.snippet.title
        }
    }

    private fun openHtml(fileName: String) {
        val file = File(fileName)
        println("Grafikon generálva: ${file.absolutePath}")
        if (file.exists()) {
            try {
                if (Desktop.isDesktopSupported()) {
                    Desktop.getDesktop().browse(file.toURI())
                } else {
                    println("A böngésző automatikus megnyitása nem támogatott. Kérlek nyisd meg a fájlt kézzel!")
                }
            } catch (e: Exception) {
                println("Nem sikerült megnyitni a böngészőt: ${e.message}")
            }
        }
    }

    fun question1() {
        println("\n--- 1. Legnépszerűbb Kategóriák ---")
        if (usDataFrame.rowsCount() == 0) { println("Nincs adat."); return }

        usDataFrame
            .add("CategoryName") { categoryIdToName[it["categoryId"].toString()] ?: "Egyéb" }
            .groupBy("CategoryName")
            .aggregate { count() into "count" }
            .sortByDesc("count")
            .take(10)
            .print()
    }

    fun question2() {
        println("\n--- 2. Leggyakoribb Csatornák ---")
        if (usDataFrame.rowsCount() == 0) return

        usDataFrame
            .groupBy("channelTitle")
            .aggregate { count() into "count" }
            .sortByDesc("count")
            .take(10)
            .print()
    }

    fun question3() {
        println("\n--- 3. Legnézettebb videók - DataFrame módon ---")
        if (usDataFrame.rowsCount() == 0) return

        usDataFrame
            .groupBy("title")
            .maxBy("views")
            .concat()
            .sortByDesc("views")
            .take(5)
            .select("title", "views", "channelTitle")
            .print()
    }

    fun question4() {
        println("\n--- 4. Like/Dislike Arányok ---")
        if (usDataFrame.rowsCount() == 0) return

        val uniqueVideosDf = usDataFrame
            .groupBy("title")
            .maxBy("views")
            .concat()

        val df = uniqueVideosDf
            .filter { "views"<Long>() > 100_000 && "dislikes"<Long>() > 0 }
            .add("ratio") { "likes"<Long>().toDouble() / "dislikes"<Long>().toDouble() }

        println(">>> LEGJOBB ARÁNY (Top 3):")
        df.sortByDesc("ratio").take(3)
            .select("title", "ratio")
            .print(valueLimit = 40)

        println(">>> LEGROSSZABB ARÁNY (Bottom 3):")
        df.sortBy("ratio").take(3)
            .select("title", "ratio")
            .print(valueLimit = 40)
    }

    fun question5() {
        println("\n--- 5. Globális Trendek ---")

        val videoCountryMap = mutableMapOf<String, MutableSet<String>>()

        trendingVideosMap.forEach { (country, videos) ->
            videos.forEach { video ->
                videoCountryMap.getOrPut(video.title) { mutableSetOf() }.add(country)
            }
        }

        val trends = videoCountryMap.entries
            .filter { it.value.size >= 2 }
            .sortedByDescending { it.value.size }
            .take(5)

        if (trends.isEmpty()) {
            println("Nincs közös trend.")
        } else {
            trends.forEach { (title, countries) ->
                println("$title - ${countries.size} országban (${countries.joinToString()})")
            }
        }
    }

    fun question6() {
        println("\n--- 6. Kategóriák népszerűsége időben ---")
        if (usDataFrame.rowsCount() == 0) return

        val topCategories = listOf("10", "24", "20")

        val df = usDataFrame
            .filter { it["categoryId"].toString() in topCategories }
            .add("CategoryName") {
                val id = it["categoryId"].toString()
                categoryIdToName[id] ?: id
            }
            .groupBy("trendingDate", "CategoryName")
            .aggregate { count() into "count" }
            .convert { "trendingDate"<LocalDate>() }.with {
                Date.from(it.atStartOfDay(ZoneId.systemDefault()).toInstant())
            }

        try {
            df.plot {
                x(column = "trendingDate") { axis.name = "Dátum" }
                y(column = "count") { axis.name = "Videók száma" }

                line {
                    width = 2.0
                    color(column = "CategoryName")
                }

                layout {
                    title = "Kategória trendek időben"
                    size = 1000 to 600
                }
            }.save("q6_timeline.html")

            openHtml("q6_timeline.html")
        } catch (e: Exception) {
            println("Hiba a Q6 grafikon mentésekor: ${e.message}")
        }
    }

    fun question7() {
        println("\n--- 7. Korreláció: Nézettség vs Kommentek ---")
        if (usDataFrame.rowsCount() == 0) return

        val df = usDataFrame.take(2000)
            .convert { "views"<Long>() }.to<Double>()
            .convert { "commentCount"<Long>() }.to<Double>()

        try {
            df.plot {
                x(column = "views") { axis.name = "Megtekintések" }
                y(column = "commentCount") { axis.name = "Kommentek" }

                points {
                    size = 3.0
                    alpha = 0.5
                    color = Color.BLUE
                }

                layout { title = "Korreláció: Views vs Comments" }
            }.save("q7_correlation.html")

            openHtml("q7_correlation.html")
        } catch (e: Exception) {
            println("Hiba a Q7 grafikon mentésekor: ${e.message}")
        }
    }
}