package ua.com.sqlcmd.controller.command;

import ua.com.sqlcmd.view.View;

public class FakeView implements View {
    private String messages = "";
    private String input = null;

    @Override
    public void write(String message) {
        messages += message + "\n";
    }

    @Override
    public String read() {
        if (input == null) {
            throw new IllegalStateException("reset method read");
        }
        String result = input;
        this.input = null;
        return result;
    }

    public void addRead(String input) {
        this.input = input;
    }

    public String getContent() {
        return messages;
    }
}
