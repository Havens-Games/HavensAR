package net.whg.havens_ar;

import net.whg.utils.cmdformat.Subcommand;
import net.whg.utils.exceptions.CommandException;
import net.whg.utils.exceptions.NoConsoleException;
import net.whg.utils.player.CmdPlayer;

/**
 * A subcommand that allows a player to open their personal AR menu.
 */
public class AROpenAction extends Subcommand {

    @Override
    public void execute(CmdPlayer sender, String[] args) throws CommandException {
        if (!sender.isPlayer())
            throw new NoConsoleException("You must be a player to preform this command!");

        var menu = new ARMenu(sender.getPlayer());
        new ARMenuOption(menu);

        sender.sendConfirmation("Menu opened.");
    }

    @Override
    public String getUsage() {
        return "";
    }

    @Override
    public String getName() {
        return "open";
    }
}
