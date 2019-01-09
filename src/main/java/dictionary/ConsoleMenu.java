package dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;
import java.util.Map;

@Component
public class ConsoleMenu implements Menu {
  @Autowired
  private Dictionary[] dictionaries;
  private boolean interrupt = false;

  public ConsoleMenu(Dictionary... dictionary) {
    dictionaries = dictionary;
  }

  public void run() {
    try (Scanner scanner = new Scanner(System.in)) {
      while (true) {
        int index = -1;
        boolean loopFlag = true;

        while (loopFlag) {
          clearConsole();

          System.out.println("Select dictionary:");
          printDictionarysList();
          System.out.println("exit-Exit");

          if (scanner.hasNextInt()) {
            index = scanner.nextInt();

            if (index < 0) System.out.println("Number can not be less than zero!");
            else if (index > 0) System.out.println("Number can not be greater than count of dictionaries!");
            else loopFlag = false;
          } else {
            interrupt = scanner.nextLine().equals("exit");
            loopFlag = false;
          }

          System.out.println("Press Enter");
          scanner.nextLine();
        }

        if (interrupt) break;
        
        Dictionary currentDictionary = dictionaries[index];
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
              putValue(currentDictionary, key, value);

              break;
            }

            case "exit": break;

            default: System.out.println("Invalid command!");
          }

          System.out.println("Press Enter");
          scanner.nextLine();
        }
      }
    }
  }

  public void printDictionary(Dictionary dictionary) {
    for (Map.Entry<String, String> entry : dictionary.getDictionary().entrySet()) {
      System.out.println(entry.getKey() + "=" + entry.getValue());
    }
  }

  public void removeValue(Dictionary dictionary, String key) {
    System.out.println(dictionary.remove(key));
  }

  public void getValue(Dictionary dictionary, String key) {
    System.out.println(dictionary.getValue(key));
  }

  public void putValue(Dictionary dictionary, String key, String value) {
    System.out.println(dictionary.put(key, value));
  }

  private void printDictionarysList() {
    for (int i = 0; i < dictionaries.length; i++) {
      System.out.println(i + "-" + dictionaries[i].getName());
    }
  }

  private void clearConsole() {
    for (int i = 0; i < 50; i++) {
      System.out.println();
    }
  }
}
