import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<User> users = new ArrayList<>();
    private static List<Cinema> cinemas = new ArrayList<>();
    private static User currentUser = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initializeTestData();
        boolean running = true;
        while (running) {
            if (currentUser == null) {
                showLoginMenu();
            } else {
                if (currentUser.isAdmin()) {
                    showAdminMenu();
                } else {
                    showUserMenu();
                }
            }
        }
    }

    private static void initializeTestData() {
        users.add(new User("admin", "admin", true));
        users.add(new User("user", "user", false));

        Cinema cinema = new Cinema("Заря");
        Hall hall = new Hall(cinema, 1, "Зал 1", 5, 10);
        cinema.addHall(hall);
        cinemas.add(cinema);

        LocalDateTime startTime = LocalDateTime.now().plusHours(1);
        Session session = new Session("Аватар", 120, startTime, hall);
        hall.addSession(session);
    }

    private static void showLoginMenu() {
        System.out.println("1. Войти");
        System.out.println("2. Зарегистрироваться");
        System.out.println("3. Выход");
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: login(); break;
                case 2: register(); break;
                case 3: System.exit(0);
                default: System.out.println("Неверный выбор");
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода");
            scanner.nextLine();
        }
    }

    private static void login() {
        System.out.print("Логин: ");
        String login = scanner.nextLine();
        System.out.print("Пароль: ");
        String password = scanner.nextLine();

        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("Вход выполнен как " + (user.isAdmin() ? "Администратор" : "Пользователь"));
                return;
            }
        }
        System.out.println("Неверный логин или пароль");
    }

    private static void register() {
        System.out.print("Введите логин: ");
        String login = scanner.nextLine();
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                System.out.println("Логин уже занят");
                return;
            }
        }
        System.out.print("Введите пароль: ");
        String password = scanner.nextLine();
        users.add(new User(login, password, false));
        System.out.println("Регистрация успешна");
    }

    private static void showAdminMenu() {
        System.out.println("Меню администратора:");
        System.out.println("1. Добавить кинотеатр");
        System.out.println("2. Добавить зал");
        System.out.println("3. Создать сеанс");
        System.out.println("4. Выйти");
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: addCinema(); break;
                case 2: addHall(); break;
                case 3: createSession(); break;
                case 4: currentUser = null; break;
                default: System.out.println("Неверный выбор");
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода");
            scanner.nextLine();
        }
    }

    private static void addCinema() {
        System.out.print("Введите название кинотеатра: ");
        String name = scanner.nextLine();
        cinemas.add(new Cinema(name));
        System.out.println("Кинотеатр добавлен");
    }

    private static void addHall() {
        System.out.println("Выберите кинотеатр:");
        for (int i = 0; i < cinemas.size(); i++) {
            System.out.println((i + 1) + ". " + cinemas.get(i).getName());
        }
        int cinemaChoice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (cinemaChoice < 0 || cinemaChoice >= cinemas.size()) {
            System.out.println("Неверный выбор");
            return;
        }

        Cinema cinema = cinemas.get(cinemaChoice);
        System.out.print("Введите ID зала: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Введите название зала: ");
        String name = scanner.nextLine();
        System.out.print("Введите количество рядов: ");
        int rows = scanner.nextInt();
        System.out.print("Введите количество мест в ряду: ");
        int columns = scanner.nextInt();
        scanner.nextLine();

        Hall hall = new Hall(cinema, id, name, rows, columns);
        cinema.addHall(hall);
        System.out.println("Зал добавлен");
    }

    private static void createSession() {
        System.out.println("Выберите кинотеатр:");
        for (int i = 0; i < cinemas.size(); i++) {
            System.out.println((i + 1) + ". " + cinemas.get(i).getName());
        }
        int cinemaChoice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (cinemaChoice < 0 || cinemaChoice >= cinemas.size()) {
            System.out.println("Неверный выбор");
            return;
        }

        Cinema cinema = cinemas.get(cinemaChoice);
        List<Hall> halls = cinema.getHalls();
        if (halls.isEmpty()) {
            System.out.println("В кинотеатре нет залов");
            return;
        }

        System.out.println("Выберите зал:");
        for (int i = 0; i < halls.size(); i++) {
            System.out.println((i + 1) + ". " + halls.get(i).getName());
        }
        int hallChoice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (hallChoice < 0 || hallChoice >= halls.size()) {
            System.out.println("Неверный выбор");
            return;
        }

        Hall hall = halls.get(hallChoice);
        System.out.print("Введите название фильма: ");
        String movieTitle = scanner.nextLine();
        System.out.print("Введите длительность фильма (в минутах): ");
        int duration = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Введите дату и время начала сеанса (гггг-мм-ддTчч:мм): ");
        LocalDateTime startTime = LocalDateTime.parse(scanner.nextLine());

        Session session = new Session(movieTitle, duration, startTime, hall);
        hall.addSession(session);
        System.out.println("Сеанс создан");
    }

    private static void showUserMenu() {
        System.out.println("Меню пользователя:");
        System.out.println("1. Поиск ближайшего сеанса");
        System.out.println("2. Просмотреть план зала");
        System.out.println("3. Купить билет");
        System.out.println("4. Выйти");
        try {
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1: findNearestSession(); break;
                case 2: viewSeatPlan(); break;
                case 3: buyTicket(); break;
                case 4: currentUser = null; break;
                default: System.out.println("Неверный выбор");
            }
        } catch (Exception e) {
            System.out.println("Ошибка ввода");
            scanner.nextLine();
        }
    }

    private static void findNearestSession() {
        System.out.print("Введите название фильма: ");
        String movieTitle = scanner.nextLine();
        LocalDateTime now = LocalDateTime.now();
        Session nearestSession = null;

        for (Cinema cinema : cinemas) {
            for (Hall hall : cinema.getHalls()) {
                for (Session session : hall.getSessions()) {
                    if (session.getMovieTitle().equalsIgnoreCase(movieTitle)
                            && session.getStartTime().isAfter(now)
                            && session.getFreeSeatsCount() > 0) {
                        if (nearestSession == null || session.getStartTime().isBefore(nearestSession.getStartTime())) {
                            nearestSession = session;
                        }
                    }
                }
            }
        }

        if (nearestSession != null) {
            System.out.println("Ближайший сеанс:");
            System.out.println("Кинотеатр: " + nearestSession.getHall().getCinema().getName());
            System.out.println("Зал: " + nearestSession.getHall().getName());
            System.out.println("Время: " + nearestSession.getStartTime());
            System.out.println("Свободных мест: " + nearestSession.getFreeSeatsCount());
        } else {
            System.out.println("Нет доступных сеансов");
        }
    }

    private static void viewSeatPlan() {
        System.out.println("Выберите кинотеатр:");
        for (int i = 0; i < cinemas.size(); i++) {
            System.out.println((i + 1) + ". " + cinemas.get(i).getName());
        }
        int cinemaChoice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (cinemaChoice < 0 || cinemaChoice >= cinemas.size()) {
            System.out.println("Неверный выбор");
            return;
        }

        Cinema cinema = cinemas.get(cinemaChoice);
        List<Hall> halls = cinema.getHalls();
        System.out.println("Выберите зал:");
        for (int i = 0; i < halls.size(); i++) {
            System.out.println((i + 1) + ". " + halls.get(i).getName());
        }
        int hallChoice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (hallChoice < 0 || hallChoice >= halls.size()) {
            System.out.println("Неверный выбор");
            return;
        }

        Hall hall = halls.get(hallChoice);
        List<Session> sessions = hall.getSessions();
        System.out.println("Выберите сеанс:");
        for (int i = 0; i < sessions.size(); i++) {
            Session s = sessions.get(i);
            System.out.println((i + 1) + ". " + s.getMovieTitle() + " в " + s.getStartTime());
        }
        int sessionChoice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (sessionChoice < 0 || sessionChoice >= sessions.size()) {
            System.out.println("Неверный выбор");
            return;
        }

        Session session = sessions.get(sessionChoice);
        session.printSeats();
    }

    private static void buyTicket() {
        System.out.println("Выберите кинотеатр:");
        for (int i = 0; i < cinemas.size(); i++) {
            System.out.println((i + 1) + ". " + cinemas.get(i).getName());
        }
        int cinemaChoice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (cinemaChoice < 0 || cinemaChoice >= cinemas.size()) {
            System.out.println("Неверный выбор");
            return;
        }

        Cinema cinema = cinemas.get(cinemaChoice);
        List<Hall> halls = cinema.getHalls();
        System.out.println("Выберите зал:");
        for (int i = 0; i < halls.size(); i++) {
            System.out.println((i + 1) + ". " + halls.get(i).getName());
        }
        int hallChoice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (hallChoice < 0 || hallChoice >= halls.size()) {
            System.out.println("Неверный выбор");
            return;
        }

        Hall hall = halls.get(hallChoice);
        List<Session> sessions = hall.getSessions();
        System.out.println("Выберите сеанс:");
        for (int i = 0; i < sessions.size(); i++) {
            Session s = sessions.get(i);
            System.out.println((i + 1) + ". " + s.getMovieTitle() + " в " + s.getStartTime());
        }
        int sessionChoice = scanner.nextInt() - 1;
        scanner.nextLine();

        if (sessionChoice < 0 || sessionChoice >= sessions.size()) {
            System.out.println("Неверный выбор");
            return;
        }

        Session session = sessions.get(sessionChoice);
        System.out.print("Введите номер ряда: ");
        int row = scanner.nextInt() - 1;
        System.out.print("Введите номер места: ");
        int column = scanner.nextInt() - 1;
        scanner.nextLine();

        if (row < 0 || row >= session.getHall().getRows() || column < 0 || column >= session.getHall().getColumns()) {
            System.out.println("Неверный ряд или место");
            return;
        }

        if (session.occupySeat(row, column)) {
            System.out.println("Билет куплен успешно");
        } else {
            System.out.println("Место уже занято");
        }
    }
}