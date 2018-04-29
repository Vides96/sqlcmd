package ua.com.sqlcmd.controller.command;

import ua.com.sqlcmd.model.DatabaseManager;
import ua.com.sqlcmd.view.View;

public class Connect implements Command {
    private static String COMMAND_SAMPLE = "connect|sqlcmd|postgres|tiopampa2017";

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


            String[] data = command.split("\\|");

            if (data.length != count()) {
                throw new IllegalArgumentException(String.format("number of parameters  don't match divided with, '|', " +
                        "must be %s but we have: %s ", count(), data.length));
            }
            String databaseName = data[1];
            String userName = data[2];
            String password = data[3];
            manager.connect(databaseName, userName, password);
            view.write("Ok");

    }

    public int count() {
        return COMMAND_SAMPLE.split("\\|").length;
    }



}
