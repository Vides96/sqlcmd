package ua.com.sqlcmd.controller.command;

import ua.com.sqlcmd.model.DatabaseManager;
import ua.com.sqlcmd.view.View;

public class Connect implements Command {
    private DatabaseManager manager;
    private View view;

    public Connect(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("connect|");
    }

    @Override
    public void process(String command) {

        try {
            String[] data = command.split("\\|");

            if (data.length != 4) {//TODO - magic number
                throw new IllegalArgumentException("number of parameters  don't match divided with, '|', must be 4 but we have: " + data.length);
            }
            String databaseName = data[1];
            String userName = data[2];
            String password = data[3];
            manager.connect(databaseName, userName, password);
            view.write("Ok");
        } catch (Exception e) {
            printError(e);
        }
    }


    private void printError(Exception e) {
        String message = e.getMessage();
        Throwable cause = e.getCause();
        if (cause != null) {
            message += " " + e.getCause().getMessage();
        }
        view.write("Fault, maybe " + message);
        view.write("Enter again your datas");
    }
}
