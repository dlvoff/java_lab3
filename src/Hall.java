import java.util.ArrayList;
import java.util.List;

public class Hall {
    private Cinema cinema;
    private int id;
    private String name;
    private int rows;
    private int columns;
    private List<Session> sessions;

    public Hall(Cinema cinema, int id, String name, int rows, int columns) {
        this.cinema = cinema;
        this.id = id;
        this.name = name;
        this.rows = rows;
        this.columns = columns;
        this.sessions = new ArrayList<>();
    }

    public Cinema getCinema() { return cinema; }
    public int getId() { return id; }
    public String getName() { return name; }
    public int getRows() { return rows; }
    public int getColumns() { return columns; }
    public List<Session> getSessions() { return sessions; }
    public void addSession(Session session) { sessions.add(session); }
}