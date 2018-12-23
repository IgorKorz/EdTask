import java.util.Scanner;

public class ConsoleMenu implements Menu {
  private Dictionary[] dictionaries;
  private boolean interrupt = false;

  public ConsoleMenu(Dictionary... dictionary) {
    dictionaries = dictionary;
  }

  @Override
  public void run() {
    try (Scanner scanner = new Scanner(System.in)) {
      while (true) {
        clearConsole();

        Dictionary currentDictionary;

        System.out.println("Select dictionary:");
        printDictionarysList();
        System.out.println("exit-Exit");

        String choice = scanner.nextLine();

        if (choice.equals("exit")) break;

        try {
          int index = Integer.parseInt(choice);
          currentDictionary = dictionaries[index];
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

            switch(command) {
              case "1": printDictionary(currentDictionary); break;

              case "2": {
                System.out.println("Enter key:");
                String key = scanner.nextLine();
                removeValue(currentDictionary, key);

                break;
              }

              case "3": {
                System.out.println("Enter key:");
                String key = scanner.nextLine();
                getValue(currentDictionary, key);

                break;
              }

              case "4": {
                System.out.println("Enter key:");
                String key = scanner.nextLine();

                System.out.println("Enter value:");
                String value = scanner.nextLine();

                break;
              }
            }
          }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
          System.out.println("Invalid number");
        }
      }
    }
  }

  public void printDictionary(Dictionary dictionary) {
    System.out.println(dictionary);
  }

  public void removeValue(Dictionary dictionary, String key) {
    try {
      String value = dictionary.remove(key);

      if (value == null) System.out.println("Key is not contains");
      else System.out.println(key + "=" + value + " removed");
    } catch (DictionaryException e) {
      System.out.println(e.getMessage());
    }
  }

  public void getValue(Dictionary dictionary, String key) {
    try {
      String value = dictionary.getValue(key);

      System.out.println(key + "=" + value);
    } catch (DictionaryException e) {
      System.out.println(e.getMessage());
    }
  }

  public void putValue(Dictionary dictionary, String key, String value) {
    try {
      String newValue = dictionary.put(key, value);

      if (newValue == null) System.out.println("Value is not put");
      else System.out.println(key + "=" + value + " put");
    } catch (DictionaryException e) {
      System.out.println(e.getMessage());
    }
  }

  private void printDictionarysList() {
    for (int i = 0; i < dictionaries.length; i++) {
      System.out.println(i + "-" + dictionaries[i].getClass().getSimpleName());
    }
  }

  private void clearConsole() {
    for (int i = 0; i < 50; i++) {
      System.out.println();
    }
  }
}
