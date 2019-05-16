package com.example.view;

class MenuMessages {
    public static final String SELECT_DICTIONARY_MSG = "Select dictionary:";
    public static final String EXIT_COMMAND_MSG = String.format("%s-Exit", MenuCommands.EXIT_CMD);
    public static final String TOO_SMALL_NUM_MSG = "Number can not be less than zero!";
    public static final String TOO_LARGE_NUM_MSG = "Number can not be greater than count of dictionaries!";
    public static final String INVALID_COMMAND_MSG = "Invalid command!";
    public static final String SELECT_COMMAND_MSG =
            String.format("Select command:" +
                    "\n%s-Print" +
                    "\n%s-Put" +
                    "\n%s-Get" +
                    "\n%s-Remove" +
                    "\n%s-Exit",
                    MenuCommands.PRINT_CMD,
                    MenuCommands.PUT_CMD,
                    MenuCommands.GET_CMD,
                    MenuCommands.REMOVE_CMD,
                    MenuCommands.EXIT_CMD);
    public static final String NOT_BEEN_INITIALIZED = "Dictionaries not been initialized!";
    public static final String ENTER_KEY_MSG = "Enter key:";
    public static final String ENTER_VALUE_MSG = "Enter value:";
    public static final String PRESS_ENTER_MSG = "Press Enter";
    public static final String PUT_MSG_PATTERN = "%s put\n";
    public static final String REMOVED_MSG_PATTERN = "%s removed\n";
    public static final String PRINT_DICTIONARIES_LIST_MSG_PATTERN = "%d - %s\n";
}
