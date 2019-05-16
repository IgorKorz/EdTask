package com.example.view;

import com.example.controller.Dictionary;
import com.example.model.DictionaryType;
import com.example.model.Property;

import java.util.Scanner;

public class ConsoleMenu implements Menu {
    private Dictionary[] dictionaries;

    public ConsoleMenu(Dictionary... dictionaries) {
        this.dictionaries = dictionaries;
    }

    public void run() {
        if (initializationDictionaries()) {

            try (Scanner scanner = new Scanner(System.in)) {
                while (true) {
                    int index = -1;
                    boolean loopFlag = true;

                    while (loopFlag) {
                        clearConsole();

                        System.out.println(MenuMessages.SELECT_DICTIONARY_MSG);

                        printDictionariesList();

                        System.out.println(MenuMessages.EXIT_COMMAND_MSG);

                        if (scanner.hasNextInt()) {
                            index = scanner.nextInt();

                            if (index < 0) {
                                System.out.println(MenuMessages.TOO_SMALL_NUM_MSG);
                            } else if (index > dictionaries.length) {
                                System.out.println(MenuMessages.TOO_LARGE_NUM_MSG);
                            } else {
                                loopFlag = false;
                            }
                        } else if (scanner.nextLine().equals(MenuCommands.EXIT_CMD)) {
                            return;
                        } else {
                            System.out.println(MenuMessages.INVALID_COMMAND_MSG);
                        }
                    }

                    scanner.nextLine();

                    Dictionary dictionary = dictionaries[index];
                    String command;
                    loopFlag = true;

                    while (loopFlag) {
                        clearConsole();

                        System.out.println(dictionary.getName());
                        System.out.println(MenuMessages.SELECT_COMMAND_MSG);

                        command = scanner.nextLine();

                        switch (command) {
                            case MenuCommands.PRINT_CMD: {
                                printDictionary(dictionary);

                                break;
                            }

                            case MenuCommands.PUT_CMD: {
                                System.out.println(MenuMessages.ENTER_KEY_MSG);

                                String key = scanner.nextLine();

                                System.out.println(MenuMessages.ENTER_VALUE_MSG);

                                String value = scanner.nextLine();

                                putValue(dictionary, key, value);

                                break;
                            }

                            case MenuCommands.GET_CMD: {
                                System.out.println(MenuMessages.ENTER_KEY_MSG);

                                String key = scanner.nextLine();

                                getValue(dictionary, key);

                                break;
                            }

                            case MenuCommands.REMOVE_CMD: {
                                System.out.println(MenuMessages.ENTER_KEY_MSG);

                                String key = scanner.nextLine();

                                System.out.println(MenuMessages.ENTER_VALUE_MSG);

                                String value = scanner.nextLine();

                                removeValue(dictionary, key, value);

                                break;
                            }

                            case MenuCommands.EXIT_CMD: {
                                loopFlag = false;

                                continue;
                            }

                            default:
                                System.out.println(MenuMessages.INVALID_COMMAND_MSG);
                        }

                        System.out.println(MenuMessages.PRESS_ENTER_MSG);

                        scanner.nextLine();
                    }
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

        if (record.getType() != DictionaryType.ERROR) {
            System.out.printf(MenuMessages.PUT_MSG_PATTERN, record.toString());
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

        if (record.getType() != DictionaryType.ERROR) {
            System.out.printf(MenuMessages.REMOVED_MSG_PATTERN, record.toString());
        } else {
            System.out.println(record);
        }
    }

    private void printDictionariesList() {
        for (int i = 0; i < dictionaries.length; i++)
            System.out.printf(MenuMessages.PRINT_DICTIONARIES_LIST_MSG_PATTERN, i, dictionaries[i].getName());
    }

    private void clearConsole() {
        for (int i = 0; i < 5; i++)
            System.out.println();
    }

    private boolean initializationDictionaries() {
        if (!dictionaries[0].initialization()) {
            System.out.println(MenuMessages.NOT_BEEN_INITIALIZED);

            return false;
        }

        return true;
    }
}
