package ua.com.sqlcmd.controller;

import ua.com.sqlcmd.controller.command.Command;
import ua.com.sqlcmd.controller.command.Exit;
import ua.com.sqlcmd.model.DataSet;
import ua.com.sqlcmd.model.DatabaseManager;
import ua.com.sqlcmd.view.View;

import java.util.Arrays;

public class MainController {
    private final Command[] commands;
    private View view;
    private DatabaseManager manager;
    

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
        this.commands = new Command[]{new Exit(view)};
    }

    public void run() {
        connectToDb();
        while (true) {

            view.write("insert comand (list or help)");
            String command = view.read();

            if (command.equalsIgnoreCase("list")) {
                doList();
            } else if (command.equalsIgnoreCase("help")) {
                doHelp();
            } else if (commands[0].canProcess(command)) {
                commands[0].process(command);
                System.exit(0);
            } else if (command.startsWith("find")) {
                doFind(command);
            } else {
                view.write("no exist command: " + command);
            }
        }
    }

    private void doFind(String command) {
        String[] data = command.split("\\|");
        String tableName = data[1];

        String[] tableColumns = manager.getTableColumns(tableName);
        printHeader(tableColumns);

        DataSet[] tableData = manager.getTableData(tableName);
        printTable(tableData);

    }

    private void printTable(DataSet[] tableData) {

        for (DataSet row : tableData) {
            printRow(row);
        }
    }

    private void printRow(DataSet row) {
        Object[] values = row.getValues();
        String result = "|";
        for (Object value : values) {
            result += value + "|";
        }
        view.write(result);

    }

    private void printHeader(String[] tableColumns) {
    //   String[] names = manager.getTableColumns();
        String result = "|";
        for (String name : tableColumns) {
            result += name + "|";
        }
        view.write("----------------------------");
        view.write(result);
        view.write("----------------------------");
    }

    private void doHelp() {
            view.write("Command list:");
            view.write("\tlist");
            view.write("\t\tlist all tables from database");

            view.write("\tfind|tableName");
            view.write(("\t\taccess to table datas 'tableName'"));

            view.write("\thelp");
            view.write("\t\tprint all these to screen");

            view.write("\texit");
            view.write("\t\tto exit from program");

    }

    private void doList() {
        String[] tableNames = manager.getTableNames();

        String message = Arrays.toString(tableNames);

        view.write(message);
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
