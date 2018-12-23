import java.nio.file.Paths;
import java.io.IOException;

public class MainClass {
  public static void main(String[] args) {
    try {
      Dictionary wordDictionary = new WordDictionary(Paths.get("WordProperties.txt"));
      Dictionary numDictionary = new NumberDictionary(Paths.get("NumberProperties.txt"));
      Menu consoleMenu = new ConsoleMenu(wordDictionary, numDictionary);
      consoleMenu.run();
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
