package ua.com.sqlcmd.controller;

import ua.com.sqlcmd.controller.command.*;
import ua.com.sqlcmd.model.DatabaseManager;
import ua.com.sqlcmd.view.View;

public class MainController {
    private final Command[] commands;
    private View view;
    private DatabaseManager manager;


    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
        this.commands = new Command[]{new Exit(view), new Help(view),
                new List(manager, view), new Find(manager, view)};
    }

    public void run() {
        connectToDb();
        while (true) {

            view.write("insert comand (or 'help' to help)");
            String command = view.read();

            if (commands[2].canProcess(command)) {
                commands[2].process(command);
            } else if (commands[1].canProcess(command)) {
                commands[1].process(command);
            } else if (commands[0].canProcess(command)) {
                commands[0].process(command);
                System.exit(0);
            } else if (commands[3].canProcess(command)) {
                commands[3].process(command);
            }else {
                view.write("no exist command: " + command);
            }
        }
    }





    private void connectToDb() {
        view.write("Hello user");
        view.write("Input, names of your database, username and password in next format: database|userName|password");
        while (true) {
            try {
                String string = view.read();
                String[] data = string.split("\\|");
                if (data.length != 3) {
                    throw new IllegalArgumentException("number of parameters  don't match divided with, '|', must be 3 but we have: " + data.length);
                }
                String databaseName = data[0];
                String userName = data[1];
                String password = data[2];
                manager.connect(databaseName, userName, password);
                break;
            } catch (Exception e) {
                printError(e);
            }

        }
        view.write("Ok");
    }

    private void printError(Exception e) {
        String message = e.getMessage();
        if (e.getCause() != null) {
            message += " " + e.getCause().getMessage();
        }
        view.write("Fault, maybe " + message);
        view.write("Enter again your datas");
    }
}
