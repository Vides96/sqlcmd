package ua.com.sqlcmd.controller.command;

import ua.com.sqlcmd.model.DatabaseManager;
import ua.com.sqlcmd.view.View;

public class IsConnected implements Command {
    private DatabaseManager manager;
    private View view;

    public IsConnected(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return !manager.IsConnected();
    }

    @Override
    public void process(String command) {
        view.write(String.format("You can not use command '%s' while you mismatch: " +
                "connect|databaseName|password", command));
    }
}
