package chapter_07.item46;

import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.Map;

public enum Command {
    START("S"),
    PAUSE("P"),
    RESUME("R"),
    QUIT("Q"),;

    //각 hotkey를 key로, Command를 value로 하는 map 만들기
    private static final Map<String, Command> COMMAND_MAP = Arrays.stream(values())
            .collect(toMap(
                    command -> command.hotkey,
                    command -> command
            ));

    public static Command of(String hotkey) {
        return COMMAND_MAP.get(hotkey);
    }

    private final String hotkey;

    Command(String hotkey) {
        this.hotkey = hotkey;
    }
}
