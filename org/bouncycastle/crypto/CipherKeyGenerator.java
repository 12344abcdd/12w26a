package org.bouncycastle.crypto;

import java.security.SecureRandom;

public class CipherKeyGenerator
{
    protected SecureRandom field_57403_a;
    protected int field_57402_b;

    public CipherKeyGenerator()
    {
    }

    public void func_57400_a(KeyGenerationParameters par1KeyGenerationParameters)
    {
        field_57403_a = par1KeyGenerationParameters.func_57569_a();
        field_57402_b = (par1KeyGenerationParameters.func_57568_b() + 7) / 8;
    }

    public byte[] func_57401_a()
    {
        byte abyte0[] = new byte[field_57402_b];
        field_57403_a.nextBytes(abyte0);
        return abyte0;
    }
}
