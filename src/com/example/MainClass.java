package com.example;

import com.example.controller.Dictionary;
import com.example.controller.FileProperties;
import com.example.view.ConsoleMenu;
import com.example.view.Menu;

public class MainClass {
  public static void main(String[] args) {
      Dictionary wordDictionary = new FileProperties("resources\\wordproperties.txt", 4, "[0-9]", "Word dictionary");
      Dictionary numDictionary = new FileProperties("resources\\numberproperties.txt", 5, "[a-zA-Z]", "Number dictionary");
      Menu consoleMenu = new ConsoleMenu(wordDictionary, numDictionary);
      consoleMenu.run();
  }
}
