package com.example.view;

import com.example.controller.Dictionary;
import com.example.model.Property;

import java.util.List;
import java.util.Scanner;

public class ConsoleMenu implements Menu {
    private Dictionary[] dictionaries;

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

                        if (index < 0) {
                            System.out.println("Number can not be less than zero!");
                        } else if (index > dictionaries.length) {
                            System.out.println("Number can not be greater than count of dictionaries!");
                        } else {
                            loopFlag = false;
                        }
                    } else if (scanner.nextLine().equals("exit")) {
                        return;
                    } else {
                        System.out.println("Invalid command!");
                    }
                }

                scanner.nextLine();

                Dictionary dictionary = dictionaries[index];
                String command;
                loopFlag = true;

                while (loopFlag) {
                    clearConsole();

                    System.out.println(dictionary.getName());
                    System.out.println("Select command:");
                    System.out.println("1-Print");
                    System.out.println("2-Remove");
                    System.out.println("3-Get");
                    System.out.println("4-Put");
                    System.out.println("exit-Exit");

                    command = scanner.nextLine();

                    switch (command) {
                        case "1":
                            printDictionary(dictionary);

                            break;

                        case "2": {
                            System.out.println("Enter key:");

                            String key = scanner.nextLine();

                            System.out.println("Enter value:");

                            String value = scanner.nextLine();

                            removeValue(dictionary, key, value);

                            break;
                        }

                        case "3": {
                            System.out.println("Enter key:");

                            String key = scanner.nextLine();

                            getValue(dictionary, key);

                            break;
                        }

                        case "4": {
                            System.out.println("Enter key:");

                            String key = scanner.nextLine();

                            System.out.println("Enter value:");

                            String value = scanner.nextLine();

                            putValue(dictionary, key, value);

                            break;
                        }

                        case "": break;

                        case "exit": {
                            loopFlag = false;

                            continue;
                        }

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
        Property record = dictionary.put(key, value);

        if (record.getType() > -1) {
            System.out.println(record.toString() + " put");
        } else {
            System.out.println(record);
        }
    }

    private void getValue(Dictionary dictionary, String key) {
        for (Property property : dictionary.get(key))
            System.out.println(property);
    }

    private void removeValue(Dictionary dictionary, String key, String value) {
        Property record = dictionary.remove(key, value);

        if (record.getType() > -1) {
            System.out.println(record.toString() + " removed");
        } else {
            System.out.println(record);
        }
    }

    private void printDictionariesList() {
        for (int i = 0; i < dictionaries.length; i++)
            System.out.println(i + "-" + dictionaries[i].getName());
    }

    private void clearConsole() {
        for (int i = 0; i < 5; i++)
            System.out.println();
    }
}
