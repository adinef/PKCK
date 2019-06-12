package pl.lodz.p.it.pkck.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RunActionEventHolder {
    private Map<String, List<Runnable>> eventsBound = new HashMap<>();

    public void addListener(String name, Runnable runnable) {
        List<Runnable> runnables = eventsBound.getOrDefault(name, null);
        if (runnables != null) {
            runnables.add(runnable);
        } else {
            runnables = new ArrayList<>();
            runnables.add(runnable);
            eventsBound.put(name, runnables);
        }
    }

    public void start(String name) {
        List<Runnable> runnables = eventsBound.getOrDefault(name, null);
        if (runnables != null) {
            for (Runnable runnable : runnables) {
                runnable.run();
            }
        }
    }
}
