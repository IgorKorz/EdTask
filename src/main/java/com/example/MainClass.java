package com.example;

import com.example.controller.DBProperties;
import com.example.view.Menu;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.util.Map;

public class MainClass {
  public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext(
            "dictionary-context.xml");
    Menu consoleMenu = (Menu) context.getBean("menu");
    consoleMenu.run();
  }
}
