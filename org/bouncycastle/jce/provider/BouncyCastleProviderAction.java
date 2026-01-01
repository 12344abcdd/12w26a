package org.bouncycastle.jce.provider;

import java.security.PrivilegedAction;

class BouncyCastleProviderAction implements PrivilegedAction
{
    final BouncyCastleProvider field_56516_a;

    BouncyCastleProviderAction(BouncyCastleProvider par1BouncyCastleProvider)
    {
        field_56516_a = par1BouncyCastleProvider;
    }

    public Object run()
    {
        BouncyCastleProvider.func_57409_a(field_56516_a);
        return null;
    }
}
