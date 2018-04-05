package ua.com.sqlcmd.controller;

import ua.com.sqlcmd.model.DatabaseManager;
import ua.com.sqlcmd.view.View;

public class MainController {
    private View view;
    private DatabaseManager manager;

    public MainController(View view, DatabaseManager manager) {
        this.view = view;
        this.manager = manager;
    }

    public void run(){
        connectToDb();
    }
    private void connectToDb() {
        view.write("Hello user");
        view.write("Input, names of your database, username and password in next format: database|userName|password");
        while (true) {
            String string = view.read();
            String[] data = string.split("\\|");
            String databaseName = data[0];
            String userName = data[1];
            String password = data[2];
            try {
                manager.connect(databaseName, userName, password);
                break;
            } catch (Exception e) {
                String message = e.getMessage();
                if (e.getCause() != null) {
                    message += " " + e.getCause().getMessage();
                }
                view.write("Fault, maybe " + message);
                view.write("Enter again your datas");
            }
        }
        view.write("Ok");
    }
}
