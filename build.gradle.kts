import org.gradle.kotlin.dsl.invoke
import org.gradle.kotlin.dsl.test

plugins {
    id("java")
    application
}

group = "org.example"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("org.mockito:mockito-core:5.+")
}

application {
    mainClass.set("org.example.Main")
}

tasks {
    jar {
        manifest {
            attributes["Main-Class"] = application.mainClass.get()
        }
    }
    test {
        useJUnitPlatform()
    }
}

