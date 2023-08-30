package Webscraper;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

public class webscraper {
    public static void main(String[] args) {
        // Setup WebDriver
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.wsj.com/market-data/stocks");

        // Pause to let the page load
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Get HTML content
        String htmlContent = driver.getPageSource();

        // Pause
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Parse HTML content
        Document document = Jsoup.parse(htmlContent);

        // Pause
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Initialize a StringBuilder to hold the scraped data
        StringBuilder scrapedText = new StringBuilder();

        // Scrape and print data
        Elements rows = document.select("tr.WSJTables--table__row--2VdwxeeP");
        for (Element row : rows) {
            Elements cells = row.select("td");
            for (Element cell : cells) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(cell.text());
                scrapedText.append(cell.text()).append("\n");
            }
        }

        // Close the driver
        driver.close();

        // Create a unique identifier using the current timestamp
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String uniqueIdentifier = sdf.format(new Date());

        // Create a filename using the unique identifier
        String filename = "scraped_data_" + uniqueIdentifier + ".txt";

        // Write scraped data to a file
        try {
            FileWriter fw = new FileWriter(filename);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(scrapedText.toString());

            bw.close();
            fw.close();

            System.out.println("Data written to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}