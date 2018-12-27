import java.nio.file.Paths;
import java.io.IOException;

public class MainClass {
  public static void main(String[] args) {
    try {
      Dictionary wordDictionary = new Dictionary(Paths.get("WordProperties.txt"), 4, '0', '9', "Word dictionary");
      Dictionary numDictionary = new Dictionary(Paths.get("NumberProperties.txt"), 5, 'a', 'z', "Number dictionary");
      Menu consoleMenu = new ConsoleMenu(wordDictionary, numDictionary);
      consoleMenu.run();
    } catch (IOException e) {
      System.out.println(e);
    }
  }
}
