package net.whg.havens_ar;

import net.whg.utils.cmdformat.CommandHandler;

/**
 * Handles all AR related commands.
 */
public class ARCommand extends CommandHandler {
    public ARCommand() {
        actions.add(new AROpenAction());
    }

    @Override
    public String getName() {
        return "ar";
    }
}
