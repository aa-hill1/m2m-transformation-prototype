package org.example;

import org.example.ui.FileRead;
import org.example.ui.UIController;

import java.util.ArrayList;

public class Main { //TODO: app startup
    static void main() {
        //UIController ui = new UIController();
        ArrayList<String> strs = new ArrayList<>();
        strs.add("one");
        strs.add("two");
        strs.add("three");
        if (strs.contains("one")) {
            System.out.println("TRUE");
        } else {
            System.out.println("FALSE");
        }
    }
}
