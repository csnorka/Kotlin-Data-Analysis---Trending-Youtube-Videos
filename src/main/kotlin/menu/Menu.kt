package menu

import api.Analytic_Questions
import java.util.*

fun questionMenu(){
    val scanner = Scanner(System.`in`)

    while(true){
        println("""
            
            ==========================================
            --- YOUTUBE DATA ANALYTICS ---
            ==========================================
            Kérlek válassz elemzést:
            1. Mely kategóriák a legnépszerűbbek? (LISTA)
            2. Mely csatornák szerepelnek a legtöbbször? (LISTA)
            3. Mely videóknak van a legnagyobb nézettsége? (LISTA)
            4. Melyek a legjobb/legrosszabb Like/Dislike arányok? (LISTA)
            5. Mely videók trendeltek több országban is? (LISTA)
            6. Hogyan változott a kategóriák népszerűsége időben? (GRAFIKON)
            7. Van összefüggés a kommentek és a nézettség között? (GRAFIKON)
            
            0. Kilépés
        """.trimIndent())

        print("Választásod: ")

        val input = scanner.nextLine()
        val choice = input.toIntOrNull()

        if (choice == null) {
            println("Érvénytelen bemenet! Kérlek számot adj meg.")
            continue
        }

        when(choice){
            1 -> Analytic_Questions.question1()
            2 -> Analytic_Questions.question2()
            3 -> Analytic_Questions.question3()
            4 -> Analytic_Questions.question4()
            5 -> Analytic_Questions.question5()
            6 -> Analytic_Questions.question6()
            7 -> Analytic_Questions.question7()
            0 -> {
                println("Viszlát!")
                return
            }
            else -> println("Nincs ilyen menüpont.")
        }

        println("\nNyomj Entert a folytatáshoz...")
        scanner.nextLine()
    }
}