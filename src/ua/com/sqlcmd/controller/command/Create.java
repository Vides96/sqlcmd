package ua.com.sqlcmd.controller.command;

import ua.com.sqlcmd.model.DataSet;
import ua.com.sqlcmd.model.DatabaseManager;
import ua.com.sqlcmd.view.View;

public class Create implements Command {
    private DatabaseManager manager;
    private View view;

    public Create(DatabaseManager manager, View view) {

        this.manager = manager;
        this.view = view;
    }

    @Override
    public boolean canProcess(String command) {
        return command.startsWith("create|");
    }

    @Override
    public void process(String command) {
        String[] data = command.split("\\|");
        if (data.length % 2 != 0) {
            throw new IllegalArgumentException(String.format("Must be an even number " +
                    "of parameters in format" +
                    "'create|tableNmae|column1|value1|column2|value2|column3|value3', but there is: '%s'" , command));
        }
        String tableName = data[1];

        DataSet dataSet = new DataSet();
        for (int index = 1; index < data.length / 2; index++) {
            String columnName = data[index * 2];
            String value = data[index * 2 + 1];
            dataSet.put(columnName, value);
        }
        manager.create(tableName, dataSet);
        view.write(String.format("DB entry  %s was created succsefully in table '%s' ", dataSet, tableName));
    }
}
