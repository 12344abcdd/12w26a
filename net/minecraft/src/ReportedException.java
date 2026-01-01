package net.minecraft.src;

public class ReportedException extends RuntimeException
{
    private final CrashReport field_57398_a;

    public ReportedException(CrashReport par1CrashReport)
    {
        field_57398_a = par1CrashReport;
    }

    public CrashReport func_57397_a()
    {
        return field_57398_a;
    }
}
