package org.example;

import org.example.ui.UIController;

public class Main { //TODO: app startup
    static void main() {
        UIController ui = new UIController();
        ui.inputLoop();
    }
}
