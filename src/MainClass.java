import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import view.Menu;

public class MainClass {
  public static void main(String[] args) {
    ApplicationContext context = new FileSystemXmlApplicationContext("resources\\dictionary-context.xml");
    Menu consoleMenu = (Menu) context.getBean("menu");
    consoleMenu.run();
  }
}
