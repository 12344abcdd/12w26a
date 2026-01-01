package org.bouncycastle.crypto;

public interface BlockCipher
{
    public abstract void func_57221_a(boolean flag, CipherParameters cipherparameters) throws IllegalArgumentException;

    public abstract String func_57219_a();

    public abstract int func_57222_b();

    public abstract int func_57223_a(byte abyte0[], int i, byte abyte1[], int j) throws DataLengthException, IllegalStateException;

    public abstract void func_57220_c();
}
