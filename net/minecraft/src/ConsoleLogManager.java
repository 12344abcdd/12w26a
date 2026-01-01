package net.minecraft.src;

import java.util.logging.*;

public class ConsoleLogManager
{
    public static Logger field_57437_a = Logger.getLogger("Minecraft");

    public ConsoleLogManager()
    {
    }

    public static void func_57436_a()
    {
        ConsoleLogFormatter consolelogformatter = new ConsoleLogFormatter();
        field_57437_a.setUseParentHandlers(false);
        ConsoleHandler consolehandler = new ConsoleHandler();
        consolehandler.setFormatter(consolelogformatter);
        field_57437_a.addHandler(consolehandler);

        try
        {
            FileHandler filehandler = new FileHandler("server.log", true);
            filehandler.setFormatter(consolelogformatter);
            field_57437_a.addHandler(filehandler);
        }
        catch (Exception exception)
        {
            field_57437_a.log(Level.WARNING, "Failed to log to server.log", exception);
        }
    }
}
