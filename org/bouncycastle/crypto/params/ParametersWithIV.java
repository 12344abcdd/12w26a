package org.bouncycastle.crypto.params;

import org.bouncycastle.crypto.CipherParameters;

public class ParametersWithIV implements CipherParameters
{
    private byte field_57456_a[];
    private CipherParameters field_57455_b;

    public ParametersWithIV(CipherParameters par1CipherParameters, byte par2ArrayOfByte[], int par3, int par4)
    {
        field_57456_a = new byte[par4];
        field_57455_b = par1CipherParameters;
        System.arraycopy(par2ArrayOfByte, par3, field_57456_a, 0, par4);
    }

    public byte[] func_57454_a()
    {
        return field_57456_a;
    }

    public CipherParameters func_57453_b()
    {
        return field_57455_b;
    }
}
