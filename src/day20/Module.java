package day20;

import java.util.Arrays;

public class Module<T> {
    char type;
    String name;
    T memory;
    String[] outputs;

    public Module(char type, String name, T memory, String[] outputs) {
        this.type = type;
        this.name = name;
        this.outputs = outputs;
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "Module{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", memory=" + memory +
                ", outputs=" + Arrays.toString(outputs) +
                '}';
    }
}
