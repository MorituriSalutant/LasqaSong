package org.example;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.setWebDriver;
import static java.lang.System.setProperty;

public class Main {
    static String pathToDriver = "C:\\Users\\a\\IdeaProjects\\LasqaSong\\src\\main\\resources\\operadriver.exe";
    static String url = "https://moo.bot/r/songlist#lasqa";
    static String filePath = "C:\\Users\\a\\Desktop\\Lasqa Song.txt";


    public static void main(String[] args) {
        configurationDriver();
        createFile();
        searchAndPrintSong();
    }

    public static void configurationDriver() {
        setProperty("webdriver.chrome.driver", pathToDriver);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        WebDriver webDriver = new ChromeDriver(options);
        setWebDriver(webDriver);
    }

    public static void searchAndPrintSong() {
        open(url);
        try {
            $(By.xpath(".//th[text()='Song']")).shouldBe(visible);
            ElementsCollection songElements = elements(".td-information.column-type-text.word-break");
            System.out.println("\n\n");
            writeFile("\n\n******************************\n\n");
            for (SelenideElement el : songElements) {
                String songName = el.text().replaceAll("By.*", "");
                System.out.print(songName);
                writeFile(songName);
            }
        } catch (NoSuchElementException ignored) {
        } finally {
            closeWebDriver();
        }
    }

    public static void createFile() {
        try {
            File myObj = new File(filePath);
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writeFile(String songName) {
        try {
            FileWriter myWriter = new FileWriter(filePath, true);
            myWriter.write(songName);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }


}





