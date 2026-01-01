package org.bouncycastle.crypto;

import java.security.SecureRandom;

public class KeyGenerationParameters
{
    private SecureRandom field_57571_a;
    private int field_57570_b;

    public KeyGenerationParameters(SecureRandom par1SecureRandom, int par2)
    {
        field_57571_a = par1SecureRandom;
        field_57570_b = par2;
    }

    public SecureRandom func_57569_a()
    {
        return field_57571_a;
    }

    public int func_57568_b()
    {
        return field_57570_b;
    }
}
