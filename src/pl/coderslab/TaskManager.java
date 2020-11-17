package pl.coderslab;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class TaskManager {

    static final String FILE_NAME = "tasks.csv";
    static final String[] OPTIONS = {"add", "remove", "list", "exit"};
    static String[][] tasks;

    public static void main(String[] args) {
        tasks = loadTasks(FILE_NAME); //wczytuje plik z tasks i ładuje do tablicy, która będzie dalej używana
        chooseOptions(OPTIONS); //możliwość wybrania dostępnych opcji , potem również w pętli

        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()) {
            String inputScanner = scanner.nextLine();

            switch (inputScanner) {
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask(tasks);
                    break;
                case "list":
                    listTasks(tasks);
                    break;
                case "exit":
                    saveTasksToFile(FILE_NAME, tasks);
                    System.out.println(ConsoleColors.RED_BOLD + "Bye, bye");
                    System.exit(1);
                    break;
                default:
                    System.out.println(ConsoleColors.RED + "Please select a correct option." + ConsoleColors.RESET);
                    break;
            }
            chooseOptions(OPTIONS);
        }
    }

    public static String[][] loadTasks(String fileName) {
        String[][] tasks = new String[0][0];

        try (FileReader fileReader = new FileReader(fileName);
             Scanner scanner = new Scanner(fileReader)) {
            while (scanner.hasNext()) {
                String[] oneTask = scanner.nextLine().split(",");
                tasks = Arrays.copyOf(tasks, tasks.length + 1);
                tasks[tasks.length - 1] = oneTask;
            }
        } catch (IOException e) {
            System.out.println("File not found");
        }
        return tasks;
    }

    public static void chooseOptions(String[] options) {
        System.out.println(ConsoleColors.BLUE + "\nPlease select an option:" + ConsoleColors.RESET);
        for (String option : options) {
            System.out.println(option);
        }
    }


    public static void saveTasksToFile(String fileName, String[][] tab) {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            for (String[] stringsArray : tab) {
                fileWriter.write(StringUtils.join(stringsArray, ",") + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void addTask() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add task description");
        String descriptionTask = scanner.nextLine();

        String dateTask;
        while (true) {
            System.out.println("Please add task due date (format yyyy-mm-dd)");
            dateTask = scanner.nextLine();
            if (dateTask.matches("\\d{4}-\\d{2}-\\d{2}")) {
                // brakuje jeszcze dokładnego sprawdzenia czy to jest poprawna data,
                // ale bez klasy Date to za dużo kodu
                break;
            } else {
                System.out.println(ConsoleColors.RED + "Date format: yyyy-mm-dd" + ConsoleColors.RESET);
            }
        }

        String importantTask;
        while (true) {
            System.out.println("Is your task is important: true/false");
            importantTask = scanner.nextLine();
            if (importantTask.equals("true") || importantTask.equals("false")) {
                break;
            } else {
                System.out.println(ConsoleColors.RED + "true or false" + ConsoleColors.RESET);
            }
        }

        String[] oneTaskAdd = {descriptionTask, dateTask, importantTask};
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = oneTaskAdd;

        System.out.println(ConsoleColors.YELLOW + "Value was successfully added" + ConsoleColors.RESET);
    }

    public static void removeTask(String[][] tab) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please select number to remove:");
        try {
            int taskNumber = Integer.parseInt(scanner.nextLine());

            if (taskNumber >= 0 && taskNumber < tab.length) {
                tasks = ArrayUtils.remove(tab, taskNumber);
                System.out.println(ConsoleColors.YELLOW + "Value was successfully deleted" + ConsoleColors.RESET);
            } else {
                System.out.println(ConsoleColors.RED
                        + "Task not exist. Please give number between 0 and " + (tab.length - 1)
                        + ". Try again"
                        + ConsoleColors.RESET);
            }
        } catch (NumberFormatException e) {
            System.out.println(ConsoleColors.RED + "Incorrect argument passed. Try again." + ConsoleColors.RESET);
        }
    }

    public static void listTasks(String[][] tab) {
        int i = 0;
        for (String[] oneTask : tab) {
            System.out.println(i + " : " + oneTask[0] + " " + oneTask[1] + " " + oneTask[2]);
            i++;
        }
    }

}
