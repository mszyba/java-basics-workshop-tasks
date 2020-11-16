package pl.coderslab;


import java.io.FileReader;
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

}
