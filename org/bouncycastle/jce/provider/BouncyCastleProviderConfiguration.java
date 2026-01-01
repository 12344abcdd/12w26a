package org.bouncycastle.jce.provider;

import java.security.Permission;
import org.bouncycastle.jcajce.provider.config.ProviderConfiguration;
import org.bouncycastle.jcajce.provider.config.ProviderConfigurationPermission;

class BouncyCastleProviderConfiguration implements ProviderConfiguration
{
    private static Permission field_56893_a;
    private static Permission field_56891_b;
    private static Permission field_56892_c;
    private static Permission field_56889_d;
    private ThreadLocal field_56890_e;
    private ThreadLocal field_56888_f;

    BouncyCastleProviderConfiguration()
    {
        field_56890_e = new ThreadLocal();
        field_56888_f = new ThreadLocal();
    }

    static
    {
        field_56893_a = new ProviderConfigurationPermission(BouncyCastleProvider.field_57419_a, "threadLocalEcImplicitlyCa");
        field_56891_b = new ProviderConfigurationPermission(BouncyCastleProvider.field_57419_a, "ecImplicitlyCa");
        field_56892_c = new ProviderConfigurationPermission(BouncyCastleProvider.field_57419_a, "threadLocalDhDefaultParams");
        field_56889_d = new ProviderConfigurationPermission(BouncyCastleProvider.field_57419_a, "DhDefaultParams");
    }
}
