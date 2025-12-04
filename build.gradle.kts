plugins {
    kotlin("jvm") version "1.9.23"
}

group = "hu.bme.aut"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Adatfeldolgozás (Data Analysis)
    implementation("org.jetbrains.kotlinx:dataframe:0.13.1")

    // Vizualizáció (Charts)
    implementation("org.jetbrains.kotlinx:kandy-lets-plot:0.7.0")

    // CSV és JSON kezelés (A te kódodhoz)
    implementation("com.google.code.gson:gson:2.10.1")
    implementation("org.apache.commons:commons-csv:1.10.0")

    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
    implementation("org.slf4j:slf4j-nop:2.0.9")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}