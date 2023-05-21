plugins {
    id("java")
}

group = "org.ericaBot"
version = "1.0.0"

repositories {
    mavenCentral()
    maven {
        url = uri("https://m2.dv8tion.net/releases")
    }
}

dependencies {
    implementation("net.dv8tion:JDA:5.0.0-beta.9")
    implementation("com.sedmelluq:lavaplayer:1.3.77")
}