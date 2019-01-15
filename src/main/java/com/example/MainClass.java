package com.example;

import com.example.view.Menu;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

public class MainClass {
  public static void main(String[] args) {
    ApplicationContext context = new FileSystemXmlApplicationContext("src\\main\\resources\\dictionary-context.xml");
    Menu consoleMenu = (Menu) context.getBean("menu");
    consoleMenu.run();
  }
}
