package ua.com.sqlcmd.controller.command;

import ua.com.sqlcmd.view.View;

public class Unsupported implements Command {
    private View view;

    public Unsupported(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return true;
    }

    @Override
    public void process(String command) {
        view.write("no exist command: " + command);
    }
}
