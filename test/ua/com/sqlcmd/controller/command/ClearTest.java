package ua.com.sqlcmd.controller.command;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import ua.com.sqlcmd.model.DatabaseManager;
import ua.com.sqlcmd.view.View;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

//import static org.mockito.Mockito.*;

public class ClearTest {
    private DatabaseManager databaseManager;
    private View view;
    private Command command;

    @Before
    public void setup() {
        databaseManager = Mockito.mock(DatabaseManager.class);
        view = Mockito.mock(View.class);
        command = new Clear(databaseManager, view);
    }

    @Test
    public void testClearTableData() {
        //given
        Mockito.when(databaseManager.getTableColumns("user"))
                .thenReturn(new String[]{"id", "name", "password"});

        //when

        command.process("clear|user");
        //then
        Mockito.verify(databaseManager).clear("user");
        Mockito.verify(view).write("Table user was cleared successful");
    }

    @Test
    public void testCanProcessClearWithParametersString() {
        //given

        //when
        boolean canProcess = command.canProcess("clear|user");
        //then
        assertTrue(canProcess);
    }

    @Test
    public void testCanProcessFindWithoutParametersString() {
        //given
        Command command = new Find(databaseManager, view);
        //when
        boolean canProcess = command.canProcess("clear");
        //then
        assertFalse(canProcess);
    }

    @Test
    public void testCanProcessClearWithtParametersQweString() {
        //given

        //when
        boolean canProcess = command.canProcess("qwe|user");
        //then
        assertFalse(canProcess);
    }

    @Test
    public void testValidationErrorWhenCountParametersLessThan2() {
        //given

        //when
        try {
            command.process("clear");
            fail();
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("command format 'clear|tableName', but you input: clear", e.getMessage());
        }
    }

    @Test
    public void testValidationErrorWhenCountParametersMoreThan2() {
        //given

        //when
        try {
            command.process("clear|table|qwe");
            fail();
        } catch (IllegalArgumentException e) {
            //then
            assertEquals("command format 'clear|tableName', but you input: clear|table|qwe", e.getMessage());
        }
    }
}
