package banking;

import java.util.ArrayList;
import java.util.List;

public class CommandStorer {
    private final ArrayList<String> invalid = new ArrayList<>();
    private final ArrayList<String> valid = new ArrayList<>();

    public void storeInvalid(String line) {
        invalid.add(line);
    }

    public void storeValid(String line) {
        valid.add(line);
    }

    public List<String> getInvalid() {
        return new ArrayList<>(invalid);
    }

    public List<String> getValid() {
        return new ArrayList<>(valid);
    }

    public void clear() {
        invalid.clear();
        valid.clear();
    }
}
