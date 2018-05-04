package ua.com.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
//import static org.mockito.Mockito.*;
import ua.com.sqlcmd.model.DataSet;
import ua.com.sqlcmd.model.DatabaseManager;
import ua.com.sqlcmd.view.View;

import static org.junit.Assert.assertEquals;

public class FindTest {
    private DatabaseManager databaseManager;
    private View view;

    @Before
    public void setup() {
        databaseManager = Mockito.mock(DatabaseManager.class);
        view = Mockito.mock(View.class);
    }

    @Test
    public void testPrintTableData() {
        //given
        Command command = new Find(databaseManager, view);

        Mockito.when(databaseManager.getTableColumns("user"))
                .thenReturn(new String[]{"id", "name", "password"});

        DataSet user1 = new DataSet();
        user1.put("id", 12);
        user1.put("name", "Stiven");
        user1.put("password", "*****");
        DataSet user2 = new DataSet();
        user2.put("id", 13);
        user2.put("name", "Eva");
        user2.put("password", "+++++");
        DataSet[] data = new DataSet[]{user1, user2};
        Mockito.when(databaseManager.getTableData("user"))
                .thenReturn(data);

        //when
        command.process("find|user");

        //then
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        Mockito.verify(view, Mockito.atLeastOnce()).write(captor.capture());
        assertEquals("[----------------------------, " +
                            "|id|name|password|, " +
                            "----------------------------, " +
                            "|12|Stiven|*****|, |13|Eva|+++++|, " +
                            "----------------------------]",
                captor.getAllValues().toString());
    }
}