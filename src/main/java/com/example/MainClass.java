package com.example;

import com.example.view.Menu;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainClass {
    public static void main(String[] args) {
        String contextFile;

        switch (args[0]) {
            case "file": contextFile = "file-dictionary-context.xml"; break;
            case "database": contextFile = "db-dictionary-context.xml"; break;
            default: System.out.println("Invalid input parameter!"); return;
        }

        ApplicationContext context = new ClassPathXmlApplicationContext(contextFile);
        Menu consoleMenu = (Menu) context.getBean("menu");
        consoleMenu.run();
    }
}
