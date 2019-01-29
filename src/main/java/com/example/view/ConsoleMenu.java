package com.example.view;

import com.example.controller.Dictionary;
import com.example.model.Property;

import java.util.Scanner;

public class ConsoleMenu implements Menu {
    private Dictionary[] dictionaries;
    private boolean interrupt = false;

    public ConsoleMenu(Dictionary... dictionaries) {
        this.dictionaries = dictionaries;
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                int index = -1;
                boolean loopFlag = true;

                while (loopFlag) {
                    clearConsole();

                    System.out.println("Select controller:");
                    printDictionariesList();
                    System.out.println("exit-Exit");

                    if (scanner.hasNextInt()) {
                        index = scanner.nextInt();

                        if (index < 0) System.out.println("Number can not be less than zero!");
                        else if (index > dictionaries.length) System.out.println("Number can not be greater than count of dictionaries!");
                        else loopFlag = false;
                    } else if (scanner.nextLine().equals("exit")){
                        interrupt = true;
                        loopFlag = false;
                    } else {
                        System.out.println("Invalid command!");
                    }

                    System.out.println("Press Enter");
                    scanner.nextLine();
                }

                if (interrupt) break;

                Dictionary currentFileProperties = dictionaries[index];
                String command = "";

                while (!command.equals("exit")) {
                    clearConsole();
                    System.out.println("Select command:");
                    System.out.println("1-Print");
                    System.out.println("2-Remove");
                    System.out.println("3-Get");
                    System.out.println("4-Put");
                    System.out.println("exit-Exit");

                    command = scanner.nextLine();

                    switch (command) {
                        case "1":
                            printDictionary(currentFileProperties);
                            break;

                        case "2": {
                            System.out.println("Enter key:");
                            String key = scanner.nextLine();
                            removeValue(currentFileProperties, key);

                            break;
                        }

                        case "3": {
                            System.out.println("Enter key:");
                            String key = scanner.nextLine();
                            getValue(currentFileProperties, key);

                            break;
                        }

                        case "4": {
                            System.out.println("Enter key:");
                            String key = scanner.nextLine();

                            System.out.println("Enter value:");
                            String value = scanner.nextLine();
                            putValue(currentFileProperties, key, value);

                            break;
                        }

                        case "exit":
                            break;

                        default:
                            System.out.println("Invalid command!");
                    }

                    System.out.println("Press Enter");
                    scanner.nextLine();
                }
            }
        }
    }

    private void printDictionary(Dictionary dictionary) {
        for (Property property : dictionary.getDictionary())
            System.out.println(property);
    }

    private void putValue(Dictionary dictionary, String key, String value) {
        if (checkAndWrite(dictionary.put(key, value)))
            System.out.print(" put");
    }

    private void getValue(Dictionary dictionary, String key) {
        System.out.println(dictionary.get(key));
    }

    private void removeValue(Dictionary dictionary, String key) {
        if (checkAndWrite(dictionary.remove(key)))
            System.out.print(" removed");
    }

    private void printDictionariesList() {
        for (int i = 0; i < dictionaries.length; i++)
            System.out.println(i + "-" + dictionaries[i].getName());
    }

    private void clearConsole() {
        for (int i = 0; i < 50; i++)
            System.out.println();
    }

    private boolean checkAndWrite(Property property) {
        if (property.getId() <= 0) {
            System.out.println(property.getValue());

            return false;
        }
        else {
            System.out.println(property);

            return true;
        }
    }
}
