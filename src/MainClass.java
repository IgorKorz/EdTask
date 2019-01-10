import controller.Dictionary;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import view.ConsoleMenu;
import view.Menu;

public class MainClass {
  public static void main(String[] args) {
    ApplicationContext context = new FileSystemXmlApplicationContext("resources\\dictionary-context.xml");
    Dictionary wordProperties = (Dictionary) context.getBean("wordDictionary");
    Dictionary numberProperties = (Dictionary) context.getBean("numberDictionary");
    Menu consoleMenu = new ConsoleMenu(wordProperties, numberProperties);
    consoleMenu.run();
  }
}
