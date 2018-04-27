package ua.com.sqlcmd.model;

import java.util.Arrays;

public class InMemoryDatabaseManager implements DatabaseManager {

    public static final String TABLE_NAME = "user";
    private DataSet[] data = new DataSet[1000];
    private int freeIndex = 0;

    @Override
    public DataSet[] getTableData(String tableName) {
        validateTable(tableName);

        return Arrays.copyOf(data, freeIndex);
    }

    private void validateTable(String tableName) {
        if (!"user".equals(tableName)) {
            throw new UnsupportedOperationException("Only for 'user' table but you try to work with " + tableName);
        }
    }

    @Override
    public String[] getTableNames() {
        return new String[]{"test",TABLE_NAME};//TODO remove test
    }

    @Override
    public void connect(String database, String userName, String password) {
        //Do nothing
    }

    @Override
    public void clear(String tableName) {
        validateTable(tableName);
        data = new DataSet[1000];
        freeIndex = 0;
    }

    @Override
    public void create(String tableName, DataSet input) {
        validateTable(tableName);

        data[freeIndex] = input;
        freeIndex++;
    }

    @Override
    public void update(String tableName, int id, DataSet newValue) {
        for (int index = 0; index < freeIndex; index++) {
            if (data[index].get("id") == Integer.valueOf(id)) {
                data[index].updateFrom(newValue);
            }
        }
    }

    @Override
    public String[] getTableColumns(String tableName) {
        return new String[]{"id", "name", "password"};
    }

    @Override
    public boolean IsConnected() {
        return true;
    }
}
