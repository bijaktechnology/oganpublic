package com.incendiary.ambulanceapp.dagger.helper;

import android.util.Log;

import com.incendiary.androidcommon.etc.Logger;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public final class ComponentReflectionInjector<T> implements Injector {

  private final Class<T> componentClass;
  private final T component;
  private final HashMap<Class<?>, Method> methods;

  public ComponentReflectionInjector(Class<T> componentClass, T component) {
    this.componentClass = componentClass;
    this.component = component;
    this.methods = getMethods(componentClass);
  }

  public T getComponent() {
    return component;
  }

  @Override
  public void inject(Object target) {

    Class targetClass = target.getClass();
    Method method = methods.get(targetClass);
    while (method == null && targetClass != null) {
      targetClass = targetClass.getSuperclass();
      method = methods.get(targetClass);
    }

    if (method == null) {
      Logger.log(Log.WARN, String.format("--------\n INJECTION ERROR \n No %s injecting method exists in %s component\n----------",
        target.getClass(), componentClass));
      return;
    }

    try {
      method.invoke(component, target);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static final ConcurrentHashMap<Class<?>, HashMap<Class<?>, Method>> cache = new ConcurrentHashMap<>();

  private static HashMap<Class<?>, Method> getMethods(Class componentClass) {
    HashMap<Class<?>, Method> methods = cache.get(componentClass);
    if (methods == null) {
      synchronized (cache) {
        methods = cache.get(componentClass);
        if (methods == null) {
          methods = new HashMap<>();
          for (Method method : componentClass.getMethods()) {
            Class<?>[] params = method.getParameterTypes();
            if (params.length == 1)
              methods.put(params[0], method);
          }
          cache.put(componentClass, methods);
        }
      }
    }
    return methods;
  }
}