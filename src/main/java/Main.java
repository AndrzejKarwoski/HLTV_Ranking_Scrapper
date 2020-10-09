import Model.Team;
import Service.FileService;
import webscrapper.Scrapper;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        long startTime = System.nanoTime();

        Scrapper scrapper = new Scrapper();
        List<Team> result = scrapper.downloadData();
        FileService fileService = new FileService();
        fileService.saveToFile(result);

        long endTime = System.nanoTime();

        long duration = (endTime - startTime);
        long seconds = TimeUnit.SECONDS.convert(duration, TimeUnit.NANOSECONDS);

        System.out.println("Czas trwania " + seconds);
    }
}
