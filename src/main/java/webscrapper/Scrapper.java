package webscrapper;

import Model.Team;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Scrapper {

    public List<Team> downloadData(){
        WebClient client = setupWebClient();
        List<Integer> ids = getListOfTeams(client);
        return getTeamsData(ids,client);
    }

    private List<Team> sortResult(List<Team> result){
        // sorting ranking
        result.sort((o1, o2) -> {
            if(o1.getPlace() > o2.getPlace()) return 1;
            else return -1;
        });
        return result;
    }

    private List<Team> getTeamsData(List<Integer> ids, WebClient client){
        List<Team> result = new ArrayList<>();

        for(Integer id : ids){
            try {
                Thread.sleep(500); // 2 requests per second limited by site owner
                String URL = "https://www.hltv.org/team/" + id + "/Tiger";
                HtmlPage page = client.getPage(URL);

                List<HtmlDivision> stats = (List<HtmlDivision>) page.getByXPath("//div[@class='profile-team-stat']");
                String ranking_place = stats.get(0).asText().split("World ranking")[1].replace("#","");
                if(!ranking_place.equals("-")){
                    String name = page.getTitleText().split(" team overview")[0];
                    result.add(new Team(Integer.parseInt(ranking_place),name));
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }

        return sortResult(result);

    }

    private List<Integer> getListOfTeams(WebClient client){

        String URL = "https://www.hltv.org/stats/teams?startDate=2020-07-09&endDate=2020-10-09&minMapCount=1";   // teams that have played at least one map in the last three months

        List<Integer> ids = new ArrayList<>();

        try {
            HtmlPage page = client.getPage(URL);
            final HtmlTable table = (HtmlTable) page.getByXPath("//table[@class='stats-table player-ratings-table']").get(0);

            for (final HtmlTableRow row : table.getRows().subList(1,table.getRows().size())) {
                int id = Integer.parseInt(row.asXml().split("/stats/teams/")[1].split("/")[0]);
                ids.add(id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ids;
    }

    private WebClient setupWebClient(){
        WebClient client = new WebClient();
        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setThrowExceptionOnFailingStatusCode(false);
        client.getOptions().setThrowExceptionOnScriptError(false);
        client.getOptions().setPrintContentOnFailingStatusCode(false);
        return client;

    }
}
