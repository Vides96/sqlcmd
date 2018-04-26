package ua.com.sqlcmd.controller.command;

import ua.com.sqlcmd.model.DatabaseManager;
import ua.com.sqlcmd.view.View;

import java.util.Arrays;

public class List implements Command {
    private DatabaseManager manager;
    private View view;

    public List(DatabaseManager manager, View view) {
        this.manager = manager;

        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equalsIgnoreCase("list");
    }

    @Override
    public void process(String command) {
        String[] tableNames = manager.getTableNames();

        String message = Arrays.toString(tableNames);

        view.write(message);
    }
}
