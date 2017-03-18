package base;

import models.Owner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by shrralis on 3/16/17.
 */
public abstract class DataFormComboBoxControllerAdditional extends DataFormComboBoxController {
    public final <T extends Owner> void setObjectToSearch(T object) {
        for (Method method : objectToProcess.getClass().getDeclaredMethods()) {
            System.out.println(method.getName());
            if (method.getName().matches("^set" + object.getClass().getSimpleName() + "(\\d|\\D)*$")) {
                try {
                    method.invoke(objectToProcess, object);
                } catch (IllegalAccessException | InvocationTargetException ignored) {}
            }
        }
    }
}
