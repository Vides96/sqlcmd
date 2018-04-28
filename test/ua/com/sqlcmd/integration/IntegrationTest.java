package ua.com.sqlcmd.integration;

import org.junit.Before;
import org.junit.Test;
import ua.com.sqlcmd.controller.Main;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;

    @Before
    public  void setup() {
        in = new ConfigurableInputStream();
        out = new ByteArrayOutputStream();

        System.setIn(in);
        System.setOut(new PrintStream(out));
    }

//    @Before
//    public void clearIn(){
//        try {
//            in.reset();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testHelp() {
        //given
        in.add("help");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Hello user\r\n" +
                "Input name of your database, username and password in next format: connect|database|userName|password\r\n" +
                "commands:\r\n" +
                //help
                "\tconnect|databaseName|userName|password\r\n" +
                "\t\tto connect to DB, with which we will work\r\n" +
                "\tlist\r\n" +
                "\t\tlist all tables from database\r\n" +
                "\tfind|tableName\r\n" +
                "\t\taccess to table datas 'tableName'\r\n" +
                "\thelp\r\n" +
                "\t\tprint all these to screen\r\n" +
                "\texit\r\n" +
                "\t\tto exit from program\r\n" +
                "insert command (or 'help' to help)\r\n" +
                //exit
                "See you soon!\r\n", getData());
    }

    @Test
    public void testExit() {
        //given
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Hello user\r\n" +
                "Input name of your database, username and password in next format: connect|database|userName|password\r\n" +
                //exit
                "See you soon!\r\n", getData());

    }

    @Test
    public void testListWithoutConnect() {
        //given
        in.add("list");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Hello user\r\n" +
                "Input name of your database, username and password in next format: connect|database|userName|password\r\n" +
                //list
                "You can not use command 'list' while you mismatch: connect|databaseName|password\r\n" +
                "insert command (or 'help' to help)\r\n"+
                //exit
                "See you soon!\r\n", getData());

    }

    @Test
    public void testFindtWithoutConnect() {
        //given
        in.add("find|user");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Hello user\r\n" +
                "Input name of your database, username and password in next format: connect|database|userName|password\r\n" +
                //find
                "You can not use command 'find|user' while you mismatch: connect|databaseName|password\r\n" +
                "insert command (or 'help' to help)\r\n"+
                //exit
                "See you soon!\r\n", getData());

    }

    @Test
    public void testUnsupported() {
        //given
        in.add("unsupported");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Hello user\r\n" +
                "Input name of your database, username and password in next format: connect|database|userName|password\r\n" +
                //unsupported
                "You can not use command 'unsupported' while you mismatch: connect|databaseName|password\r\n" +
                "insert command (or 'help' to help)\r\n"+
                //exit
                "See you soon!\r\n", getData());

    }

    @Test
    public void testUnsupportedAfterConnect() {
        //given
        in.add("connect|sqlcmd|postgres|tiopampa2017");
        in.add("unsupported");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Hello user\r\n" +
                "Input name of your database, username and password in next format: connect|database|userName|password\r\n" +
                //connect
                "Ok\r\n"+
                "insert command (or 'help' to help)\r\n"+
                //unsupported after
                "no exist command: unsupported\r\n" +
                "insert command (or 'help' to help)\r\n" +
                //exit
                "See you soon!\r\n", getData());

    }

    @Test
    public void testListAfterConnect() {
        //given
        in.add("connect|sqlcmd|postgres|tiopampa2017");
        in.add("list");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Hello user\r\n" +
                "Input name of your database, username and password in next format: connect|database|userName|password\r\n" +
                //connect
                "Ok\r\n"+
                "insert command (or 'help' to help)\r\n"+
                //list
                "[test, user]\r\n" +
                "insert command (or 'help' to help)\r\n" +
                //exit
                "See you soon!\r\n", getData());

    }

//    @Test
//    public void testFindWithErrorAfterConnect() {
//        //given
//        in.add("connect|sqlcmd|postgres|tiopampa2017");
//        in.add("find|noExist");
//        in.add("exit");
//
//        //when
//        Main.main(new String[0]);
//
//        //then
//        assertEquals("Hello user\r\n" +
//                "Input name of your database, username and password in next format: connect|database|userName|password\r\n" +
//                //connect
//                "Ok\r\n"+
//                "insert command (or 'help' to help)\r\n"+
//                //find with error
//                "[test, user]\r\n" +
//                "insert command (or 'help' to help)\r\n" +
//                //exit
//                "See you soon!\r\n", getData());
//
//    }

    @Test
    public void testFindAfterConnect() {
        //given
        in.add("connect|sqlcmd|postgres|tiopampa2017");
        in.add("find|user");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Hello user\r\n" +
                "Input name of your database, username and password in next format: connect|database|userName|password\r\n" +
                //connect
                "Ok\r\n"+
                "insert command (or 'help' to help)\r\n"+
                //find|user
                "----------------------------\r\n" +
                "|id|name|password|\r\n" +
                "----------------------------\r\n" +
                "insert command (or 'help' to help)\r\n" +
                //exit
                "See you soon!\r\n", getData());

    }

    @Test
    public void testConnectAfterConnect() {
        //given
        in.add("connect|sqlcmd|postgres|tiopampa2017");
        in.add("list");
        in.add("connect|test|postgres|tiopampa2017");
        in.add("list");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Hello user\r\n" +
                "Input name of your database, username and password in next format: connect|database|userName|password\r\n" +
                //connect sqlcmd
                "Ok\r\n"+
                "insert command (or 'help' to help)\r\n"+
                //list
                "[test, user]\r\n" +
                "insert command (or 'help' to help)\r\n" +
                //connect test
                "Ok\r\n" +
                "insert command (or 'help' to help)\r\n" +
                //list
                "[department, company]\r\n" +
                "insert command (or 'help' to help)\r\n" +
                //exit
                "See you soon!\r\n", getData());

    }

    private String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

}
