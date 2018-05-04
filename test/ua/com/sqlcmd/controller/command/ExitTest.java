package ua.com.sqlcmd.controller.command;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ExitTest {
    private FakeView view = new FakeView();

    @Test
    public void testCanProcessExitString() {
        //given
        Command command = new Exit(view);
        //when
        boolean canProcess = command.canProcess("exit");
        //then
        assertTrue(canProcess);
    }
    @Test
    public void testCanProcessQweString() {
        //given
        Command command = new Exit(view);
        //when
        boolean canProcess = command.canProcess("qwer");
        //then
        assertFalse(canProcess);
    }

    @Test
    public void testProcessExitCommand_ThrowsExitException() {
        //given
        Command command = new Exit(view);
        //when
        try {
            command.process("exit");
            fail("Expected ExitException");
        } catch (ExitException e) {
            // do nothing
        }
        //then
        assertEquals("See you soon!\n", view.getContent());
        //throws ExitException
    }
}
