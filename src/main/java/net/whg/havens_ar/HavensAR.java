package net.whg.havens_ar;

import org.bukkit.plugin.java.JavaPlugin;

import net.whg.utils.messaging.MessageUtils;

public class HavensAR extends JavaPlugin {
  private static HavensAR instance;

  /**
   * Logs an informational message to the console.
   * 
   * @param message - The message to log.
   * @param args    - The message arguments.
   */
  public static void logInfo(String message, Object... args) {
    MessageUtils.logInfo(instance, message, args);
  }

  /**
   * Logs a warning message to the console.
   * 
   * @param message - The message to log.
   * @param args    - The message arguments.
   */
  public static void logWarning(String message, Object... args) {
    MessageUtils.logWarn(instance, message, args);
  }

  /**
   * Logs an error message to the console.
   * 
   * @param message - The message to log.
   * @param args    - The message arguments.
   */
  public static void logError(String message, Object... args) {
    MessageUtils.logError(instance, message, args);
  }

  @Override
  public void onEnable() {
    instance = this;

    getCommand("ar").setExecutor(new ARCommand());
  }

  @Override
  public void onDisable() {
    instance = null;
  }
}