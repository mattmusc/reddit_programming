import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Degree class
 * Programming challange 273
 * @author matteo.muscella@usi.ch
 */
public class Degree {
    private BufferedReader buf;
    private HashMap<String, Class<?>> converters;

    public Degree() {
        buf = new BufferedReader(new InputStreamReader(System.in));
        converters = new HashMap<>();
        try {
            converters.put("fc", Class.forName("FahCel"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void work() throws IOException {
        String line = "212fc";

        final double number = Double.parseDouble(line.substring(0, line.length() - 2));
        final String conversion = line.substring(line.length() - 2, line.length());

        try {
            Constructor<?> constructor = converters.get(conversion).getConstructors()[0];
            UnitConverter object = (FahCel) constructor.newInstance(new Object[] { number, conversion });
            object.convert();
            System.out.print(object);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public double convert(final double value, final double factor) {
        return value * factor;
    }

    public static void main(String[] args) {
        Degree degree = new Degree();

        try {
            degree.work();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}

abstract class UnitConverter {
    protected double from;
    protected double to;
    protected String units;

    public UnitConverter(final double from, final String units) {
        this.from = from;
        this.units = units;
    }

    public abstract void convert();

    @Override
    public String toString() {
        return "" + to + units.charAt(1);
    }
}

/**
 * Fahrenheit to Celsius converter
 */
class FahCel extends UnitConverter {
    public FahCel(final double from, final String units) {
        super(from, units);
    }

    @Override
    public void convert() {
        to = (from - 32.0) * 5.0 / 9.0;
    }
}
