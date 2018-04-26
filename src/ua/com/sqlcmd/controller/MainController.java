package ua.com.sqlcmd.controller;

import ua.com.sqlcmd.controller.command.*;
import ua.com.sqlcmd.model.DatabaseManager;
import ua.com.sqlcmd.view.View;

public class MainController {
    private final Command[] commands;
    private View view;


    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.commands = new Command[]{
                new Connect(manager, view),
                new Exit(view),
                new Help(view),
                new IsConnected(manager, view),
                new List(manager, view),
                new Find(manager, view),
                new Unsupported(view)
        };
    }

    public void run() {
        view.write("Hello user");
        view.write("Input name of your database, username and password in next format: connect|database|userName|password");
        while (true) {
            String input = view.read();
            for (Command command : commands) {
                if (command.canProcess(input)) {
                    command.process(input);
                    break;
                }
            }
            view.write("insert command (or 'help' to help)");
        }
    }


}
