package net.minecraft.src;

import java.util.*;

class PlayerUsageSnooperThread extends TimerTask
{
    final PlayerUsageSnooper field_52012_a;

    PlayerUsageSnooperThread(PlayerUsageSnooper par1PlayerUsageSnooper)
    {
        field_52012_a = par1PlayerUsageSnooper;
    }

    public void run()
    {
        HashMap hashmap;

        synchronized (PlayerUsageSnooper.func_57204_a(field_52012_a))
        {
            hashmap = new HashMap(PlayerUsageSnooper.func_52020_b(field_52012_a));
        }

        hashmap.put("snooper_count", Integer.valueOf(PlayerUsageSnooper.func_57208_c(field_52012_a)));
        hashmap.put("snooper_time", Long.valueOf(System.currentTimeMillis()));
        HttpUtil.func_52018_a(PlayerUsageSnooper.func_57209_d(field_52012_a), hashmap, true);
    }
}
