package Service;

import Model.Team;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FileService {

    public void saveToFile(List<Team> result){
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
        try {
            File file = new File(dateFormat.format(date) + ".csv");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));

            for (Team team : result) {
                out.write(team.toString());
            }
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
