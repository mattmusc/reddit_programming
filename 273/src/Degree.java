import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Degree class
 * Programming challenge 273
 */
public class Degree {
    private BufferedReader buf;
    private HashMap<String, Class<? extends UnitConverter>> converters;

    public Degree() {
        // buf = new BufferedReader(new InputStreamReader(System.in));
        try {
            buf = new BufferedReader(new FileReader(new File("/Users/muscellm/Git/reddit_challenges/273/src/test")));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        converters = new HashMap<>();
        converters.put("fc", FC.class);
        converters.put("cf", CF.class);
        converters.put("kc", KC.class);
        converters.put("ck", CK.class);
        converters.put("rd", RD.class);
        converters.put("dr", DR.class);
    }

    public void start() {
        String line;
        try {
            while ((line = buf.readLine()) != null) {
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

        UnitConverter converter = getConverter(number, conversion);
        //System.out.println("\nNumber: " + number + "\nConversion: " + conversion);

        if (converter != null) {
            converter.convert();
            System.out.println(converter);
        }
    }

    private UnitConverter getConverter(final double number, final String conversion) {
        UnitConverter converter = null;
        try {
            Constructor<? extends UnitConverter> c = converters.get(conversion).getConstructor(double.class, String.class);
            converter = c.newInstance(number, conversion);

        } catch (NullPointerException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            System.out.println("No candidate for conversion");
        }
        return converter;
    }

    public static void main(String[] args) {
        Degree degree = new Degree();
        degree.start();
    }
}

/**
 * Base class for UnitConverter
 */
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
        return "" + String.format("%.0f", to) + units.charAt(1);
    }
}

/**
 * Fahrenheit to Celsius converter
 */
class FC extends UnitConverter {
    public FC(final double from, final String units) {
        super(from, units);
    }

    @Override
    public void convert() {
        to = (from - 32.f) * 5.f / 9.f;
    }
}

/**
 * Celsius to Fahrenheit converter
 */
class CF extends UnitConverter {
    public CF(double from, String units) {
        super(from, units);
    }

    @Override
    public void convert() {
        to = from * 9.f / 5.f + 32.f;
    }
}

/**
 * Kelvin to Celsius converter
 */
class KC extends UnitConverter {
    public KC(double from, String units) {
        super(from, units);
    }

    @Override
    public void convert() {
        to = from - 273.15f;
    }
}

/**
 * Celsius to Kelvin converter
 */
class CK extends UnitConverter {
    public CK(double from, String units) {
        super(from, units);
    }

    @Override
    public void convert() {
        to = from + 273.15f;
    }
}

/**
 * Radians to Degree angles converter
 */
class RD extends UnitConverter {
    public RD(double from, String units) {
        super(from, units);
    }

    @Override
    public void convert() {
        to = from * 180.0 / Math.PI;
    }
}

/**
 * Degree to Radians angles converter
 */
class DR extends UnitConverter {
    public DR(double from, String units) {
        super(from, units);
    }

    @Override
    public void convert() {
        to = from * Math.PI / 180.0;
    }
}