import java.time.LocalDateTime;

public class Session {
    private String movieTitle;
    private int durationMinutes;
    private LocalDateTime startTime;
    private Hall hall;
    private boolean[][] occupiedSeats;

    public Session(String movieTitle, int durationMinutes, LocalDateTime startTime, Hall hall) {
        this.movieTitle = movieTitle;
        this.durationMinutes = durationMinutes;
        this.startTime = startTime;
        this.hall = hall;
        this.occupiedSeats = new boolean[hall.getRows()][hall.getColumns()];
    }

    public String getMovieTitle() { return movieTitle; }
    public LocalDateTime getStartTime() { return startTime; }
    public Hall getHall() { return hall; }

    public boolean isSeatOccupied(int row, int column) {
        return occupiedSeats[row][column];
    }

    public boolean occupySeat(int row, int column) {
        if (!occupiedSeats[row][column]) {
            occupiedSeats[row][column] = true;
            return true;
        }
        return false;
    }

    public int getFreeSeatsCount() {
        int count = 0;
        for (boolean[] row : occupiedSeats) {
            for (boolean seat : row) {
                if (!seat) count++;
            }
        }
        return count;
    }

    public void printSeats() {
        System.out.println("План зала (O - свободно, X - занято):");
        for (int i = 0; i < occupiedSeats.length; i++) {
            System.out.print("Ряд " + (i + 1) + ": ");
            for (int j = 0; j < occupiedSeats[i].length; j++) {
                System.out.print(occupiedSeats[i][j] ? "X " : "O ");
            }
            System.out.println();
        }
    }
}