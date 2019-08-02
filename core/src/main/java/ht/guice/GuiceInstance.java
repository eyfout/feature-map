package ht.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceInstance {
  private static Injector injector = Guice.createInjector(new ElementGuiceModule());

  public static <T> T get(Class<T> clazz) {
    return injector.getInstance(clazz);
  }
}
