package ua.com.sqlcmd.controller.command;

import ua.com.sqlcmd.model.DatabaseManager;
import ua.com.sqlcmd.view.View;

public class Clear implements Command {
    private DatabaseManager manager;
    private View view;

    public Clear(DatabaseManager manager, View view) {
        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("clear|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        //TODO qty of parameters
        if (data.length != 2) {
            throw new IllegalArgumentException("command format 'clear|tableName', but you input: " + command);
        }
        manager.clear(data[1]);
       // view.write(String.format("Table was cleared successful", data[1]));
        view.write(String.format("Table %s was cleared successful", data[1]));
    }
}
