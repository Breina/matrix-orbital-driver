package commands;

import lombok.experimental.UtilityClass;

/**
 * Commands that are allowed through multiple packages.
 */
@UtilityClass
public class CommonCommands extends AbstractCommands {

    public byte[] clear() {
        return chain("58");
    }
}
