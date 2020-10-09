package Model;

public class Team {  // POJO
    private int place;
    private String Name;

    public Team(int place, String name) {
        this.place = place;
        Name = name;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return place + ";" + Name + "\n";
    }
}
