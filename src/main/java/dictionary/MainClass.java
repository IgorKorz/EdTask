package dictionary;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.Paths;
import java.io.IOException;

@Configuration
public class MainClass {
  @Bean
  Dictionary wordDictionary() throws IOException {
    return new Dictionary(
            Paths.get("C:\\Users\\igork\\IdeaProjects\\EdTaskSpring\\src\\main\\resources\\WordProperties.txt"),
            4, "[a-z]{4}", "Word dictionary");
  }

  @Bean
  Dictionary numberDictionary() throws IOException {
    return new Dictionary(
            Paths.get("C:\\Users\\igork\\IdeaProjects\\EdTaskSpring\\src\\main\\resources\\NumberProperties.txt"),
            5, "[0-9]{5}", "Number dictionary");
  }

  @Bean
  Menu consoleMenu() {
    try {
      return new ConsoleMenu(wordDictionary(), numberDictionary());
    } catch (IOException e) {
      System.out.println(e);
    }

    return new Menu() {
      @Override
      public void run() {
        System.out.println("Dictionaries not initialized!");
      }

      @Override
      public void printDictionary(Dictionary dictionary) {
        System.out.println("Dictionaries not initialized!");
      }

      @Override
      public void removeValue(Dictionary dictionary, String key) {
        System.out.println("Dictionaries not initialized!");
      }

      @Override
      public void getValue(Dictionary dictionary, String key) {
        System.out.println("Dictionaries not initialized!");
      }

      @Override
      public void putValue(Dictionary dictionary, String key, String value) {
        System.out.println("Dictionaries not initialized!");
      }
    };
  }

  public static void main(String[] args) {
    ApplicationContext context = new AnnotationConfigApplicationContext(MainClass.class);
    Menu consoleMenu = context.getBean(Menu.class);
    consoleMenu.run();
  }
}
