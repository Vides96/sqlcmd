package ua.com.sqlcmd.controller.command;

import ua.com.sqlcmd.view.View;

public class Exit implements Command {

    private View view;

    public Exit(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equalsIgnoreCase("exit");
    }

    @Override
    public void process(String command) {
        view.write("See you soon!");
        System.exit(0);
    }
}
