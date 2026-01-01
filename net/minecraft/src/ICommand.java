package net.minecraft.src;

import java.util.List;

public interface ICommand extends Comparable
{
    public abstract String func_55223_a();

    public abstract String func_55224_a(ICommandSender icommandsender);

    public abstract List func_55221_b();

    public abstract void func_55225_a(ICommandSender icommandsender, String as[]);

    public abstract boolean func_55222_c(ICommandSender icommandsender);

    public abstract List func_55226_b(ICommandSender icommandsender, String as[]);
}
