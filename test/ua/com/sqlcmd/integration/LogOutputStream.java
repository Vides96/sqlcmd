package ua.com.sqlcmd.integration;

import java.io.IOException;
import java.io.OutputStream;

public class LogOutputStream extends OutputStream {
    private String log;

    @Override
    public void write(int b) throws IOException {
//        System.out.println(Charset.defaultCharset()); //print windows-1252
//        String s = "éàè";
//        System.out.println(new String(s.getBytes(Charset.forName("UTF-8")))); //OK Print éàè
//        System.out.println(new String(s.getBytes(Charset.forName("windows-1252")))); //Not OK Print
        log += String.valueOf((char) b);
    }

    public String getData() {
        return log;
    }
}
