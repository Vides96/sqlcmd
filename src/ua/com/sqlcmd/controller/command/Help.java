package ua.com.sqlcmd.controller.command;

import ua.com.sqlcmd.view.View;

public class Help implements Command {
    private View view;

    public Help(View view) {
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.equalsIgnoreCase("help");
    }

    @Override
    public void process(String command) {
        view.write("commands:");
        view.write("\tconnect|databaseName|userName|password");
        view.write("\t\tto connect to DB, with which we will work");

        view.write("\tlist");
        view.write("\t\tlist all tables from database");

        view.write("\tfind|tableName");
        view.write(("\t\taccess to table datas 'tableName'"));

        view.write("\thelp");
        view.write("\t\tprint all these to screen");

        view.write("\texit");
        view.write("\t\tto exit from program");
    }
}
