package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandStorer {
    private final List<String> invalid = new ArrayList<>();

    public void storeInvalid(String command) {
        invalid.add(command);
    }

    public List<String> getInvalid() {
        return new ArrayList<>(invalid); // return a copy
    }

    public void clear() {
        invalid.clear();
    }
}


