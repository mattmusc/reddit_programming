import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Degree class
 * Programming challange 273
 */
public class Degree {
    private BufferedReader buf;
    private HashMap<String, Class<? extends UnitConverter>> converters;

    public Degree() {
        buf = new BufferedReader(new InputStreamReader(System.in));
        converters = new HashMap<>();
        converters.put("fc", FC.class);
        converters.put("cf", CF.class);
    }

    public void start() {
        String line;
        try {
            while((line = buf.readLine()) != null) {
                parseAndConvert(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void parseAndConvert(final String line) {
        final double number = Double.parseDouble(line.substring(0, line.length() - 2));
        final String conversion = line.substring(line.length() - 2, line.length());

        try {
            Constructor<? extends UnitConverter> c = converters.get(conversion).getConstructor(double.class, String.class);
            UnitConverter obj = c.newInstance(number, conversion);
            obj.convert();
            System.out.println(obj);

        } catch (NullPointerException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("No candidate for conversion");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Degree degree = new Degree();
        degree.start();
    }
}

/**
 * Base class for UnitConverters
 */
abstract class UnitConverter {
    protected double from;
    protected double to;
    protected String units;

    public UnitConverter() {
        System.out.println("called");
    }

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
class FC extends UnitConverter {
    public FC() {
        super();
    }

    public FC(final double from, final String units) {
        super(from, units);
    }

    @Override
    public void convert() {
        to = (from - 32.0) * 5.0 / 9.0;
    }
}

class CF extends UnitConverter {

    public CF(double from, String units) {
        super(from, units);
    }

    @Override
    public void convert() {
        to = from * 9.0 / 5.0 + 32.0;
    }
}