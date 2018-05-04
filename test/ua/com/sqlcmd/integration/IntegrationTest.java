package ua.com.sqlcmd.integration;

import org.junit.Before;
import org.junit.Test;
import ua.com.sqlcmd.controller.Main;
import ua.com.sqlcmd.model.DatabaseManager;
import ua.com.sqlcmd.model.JDBCDatabaseManager;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class IntegrationTest {
    private ConfigurableInputStream in;
    private ByteArrayOutputStream out;
    private DatabaseManager databaseManager;

    @Before
    public void setup() {
        databaseManager = new JDBCDatabaseManager();
        out = new ByteArrayOutputStream();
        in = new ConfigurableInputStream();

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
                "\tclear|tableName\r\n" +
                "\t\tclear all table\r\n" +
                "\tcreate|tableNmae|column1|value1|column2|value2|column3|value3\r\n" +
                "\t\tto create a DB entry\r\n"+
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

    public String getData() {
        try {
            String result = new String(out.toByteArray(), "UTF-8");
            out.reset();
            return result;

        } catch (UnsupportedEncodingException e) {
            return e.getMessage();
        }
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
               // "|13|Stiven|*****|\r\n" +
               // "|14|Eva|+++++|\r\n" +
                "----------------------------\r\n"+
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

    @Test
    public void testConnectWithError() {
        //given
        in.add("connect|sqlcmd|");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Hello user\r\n" +
                "Input name of your database, username and password in next format: connect|database|userName|password\r\n" +
                //connect sqlcmd with error
                "Fault, maybe number of parameters  don't match divided with, '|', must be 4 but we have: 2 \r\n" +
                "Enter again your datas\r\n"+
                "insert command (or 'help' to help)\r\n" +
                //exit
                "See you soon!\r\n", getData());

    }

    @Test
    public void testFindAfterConnectWithData() {
        //given
//        databaseManager.connect("sqlcmd", "postgres", "tiopampa2017");
//
//        databaseManager.clear("user");
//
//        DataSet user1 = new DataSet();
//        user1.put("id", 13);
//        user1.put("name", "Stiven");
//        user1.put("password", "*****");
//
//        DataSet user2 = new DataSet();
//        user2.put("id", 14);
//        user2.put("name", "Eva");
//        user2.put("password", "+++++");
//
//        databaseManager.create("user", user1);
//        databaseManager.create("user", user2);


        in.add("connect|sqlcmd|postgres|tiopampa2017");
        in.add("clear|user");
        in.add("create|user|id|13|name|Stiven|password|*****");
        in.add("create|user|id|14|name|Eva|password|+++++");
        in.add("find|user");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Hello user\r\n" +
                "Input name of your database, username and password in next format: connect|database|userName|password\r\n" +
                //connect
                "Ok\r\n" +
                "insert command (or 'help' to help)\r\n"+
                //clear|user
                "Table was cleared successful\r\n" +
                "insert command (or 'help' to help)\r\n"+
                //create|user|id|13|name|Stiven|password|*****
                "DB entry  {names=[id, name, password], values=[13, Stiven, *****]} was created succsefully in table 'user' \r\n" +
                "insert command (or 'help' to help)\r\n"+
                //create|user|id|14|name|Eva|password|+++++
                "DB entry  {names=[id, name, password], values=[14, Eva, +++++]} was created succsefully in table 'user' \r\n"+
                "insert command (or 'help' to help)\r\n"+
                //find|user
                "----------------------------\r\n" +
                "|id|name|password|\r\n" +
                "----------------------------\r\n" +
                "|13|Stiven|*****|\r\n"+
                "|14|Eva|+++++|\r\n"+
                "----------------------------\r\n" +
                "insert command (or 'help' to help)\r\n"+
                //exit
                "See you soon!\r\n", getData());


    }

    @Test
    public void testClearWithError() {
        //given
        in.add("connect|sqlcmd|postgres|tiopampa2017");
        in.add("clear|fsd|dfdf");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Hello user\r\n" +
                "Input name of your database, username and password in next format: connect|database|userName|password\r\n" +
                //connect
                "Ok\r\n" +
                "insert command (or 'help' to help)\r\n"+
                //clear|user
                "Fault, maybe command format 'clear|tableName', but you input: clear|fsd|dfdf\r\n" +
                "Enter again your datas\r\n"+
                "insert command (or 'help' to help)\r\n"+
                //exit
                "See you soon!\r\n", getData());


    }

    @Test
    public void testCreateWithErrors() {
        //given
        in.add("connect|sqlcmd|postgres|tiopampa2017");
        in.add("create|user|error");
        in.add("exit");

        //when
        Main.main(new String[0]);

        //then
        assertEquals("Hello user\r\n" +
                "Input name of your database, username and password in next format: connect|database|userName|password\r\n" +
                //connect
                "Ok\r\n" +
                "insert command (or 'help' to help)\r\n"+
                //create|user|error
                "Fault, maybe Must be an even number of parameters in format'create|tableNmae|column1|value1|column2|value2|column3|value3', but there is: 'create|user|error'\r\n"+
                "Enter again your datas\r\n" +
                "insert command (or 'help' to help)\r\n"+
                //exit
                "See you soon!\r\n", getData());


    }




}
