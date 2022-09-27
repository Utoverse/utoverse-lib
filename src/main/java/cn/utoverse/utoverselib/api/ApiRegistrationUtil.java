package cn.utoverse.utoverselib.api;

import cn.utoverse.utoverselibapi.UtoverseLib;
import cn.utoverse.utoverselibapi.UtoverseLibProvider;

import java.lang.reflect.Method;

public class ApiRegistrationUtil {
    private static final Method REGISTER;
    private static final Method UNREGISTER;
    static {
        try {
            REGISTER = UtoverseLibProvider.class.getDeclaredMethod("register", UtoverseLib.class);
            REGISTER.setAccessible(true);

            UNREGISTER = UtoverseLibProvider.class.getDeclaredMethod("unregister");
            UNREGISTER.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static void registerProvider(UtoverseLib utoverseLib) {
        try {
            REGISTER.invoke(null, utoverseLib);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void unregisterProvider() {
        try {
            UNREGISTER.invoke(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
