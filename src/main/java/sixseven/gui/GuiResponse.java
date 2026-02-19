package sixseven.gui;

import java.util.Collections;
import java.util.List;

/** Holds the list of messages to display and whether the app should exit. */
public class GuiResponse {
    private final List<String> messages;
    private final boolean shouldExit;

    public GuiResponse(List<String> messages, boolean shouldExit) {
        this.messages = messages == null ? Collections.emptyList() : List.copyOf(messages);
        this.shouldExit = shouldExit;
    }

    public List<String> getMessages() {
        return messages;
    }

    public boolean shouldExit() {
        return shouldExit;
    }
}
