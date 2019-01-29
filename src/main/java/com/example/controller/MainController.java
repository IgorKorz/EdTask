package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class MainController {
    private final List<String> dictionaryNameList;

    @Autowired
    public MainController(List<String> dictionaryNameList) {
        this.dictionaryNameList = dictionaryNameList;
    }

    @GetMapping
    public String getDictionaryList(Model model) {
        model.addAttribute("dictionaries", dictionaryNameList);

        return "index";
    }
}
