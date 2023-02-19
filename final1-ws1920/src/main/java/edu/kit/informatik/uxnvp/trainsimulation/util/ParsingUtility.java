package edu.kit.informatik.uxnvp.trainsimulation.util;

import edu.kit.informatik.uxnvp.trainsimulation.cli.SyntaxException;
import edu.kit.informatik.uxnvp.trainsimulation.model.*;
import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.Direction;
import edu.kit.informatik.uxnvp.trainsimulation.model.geometry.Point;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.CoachType;
import edu.kit.informatik.uxnvp.trainsimulation.model.train.EngineType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class for parsing and validating various data types.
 * @author Max Schweikart
 * @version 1.0
 */
public final class ParsingUtility {
    private static final Pattern INTEGER = Pattern.compile("[\\-+]?\\d+");
    private static final Pattern INTEGER_PAIR = Pattern.compile("(" + INTEGER + "),(" + INTEGER + ")");
    private static final Pattern POINT = Pattern.compile("\\(" + INTEGER_PAIR + "\\)");
    private static final Pattern TWO_POINTS = Pattern.compile("(" + POINT + "),(" + POINT + ")");
    private static final Pattern ALPHANUMERICAL = Pattern.compile("[a-zA-Z0-9]+");
    private static final Pattern SERIES = Pattern.compile("[a-zA-Z0-9&&[^W]]+");
    private static final Pattern BOOLEAN = Pattern.compile("(true|false)");
    private static final Pattern ROLLING_STOCK_ID = Pattern.compile("(W" + INTEGER
            + "|" + ALPHANUMERICAL + "-" + ALPHANUMERICAL + ")");

    /**
     * Utility classes should never be instantiated.
     */
    private ParsingUtility() {
        throw new AssertionError("utility classes should never be instantiated.");
    }

    /**
     * Parses a {@link String} into a {@link Point}.
     * @param input the text to parse.
     * @return the parsed point.
     * @throws SyntaxException if the input text does not match the point syntax.
     */
    public static Point parsePoint(String input) throws SyntaxException {
        Matcher matcher = POINT.matcher(input);
        if (!matcher.find()) {
            throw new SyntaxException(String.format("'%s' is not a valid point.", input));
        } else {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            return new Point(x, y);
        }
    }

    /**
     * Parses a {@link String} into a pair of {@link Point}s.
     * @param input the text to parse.
     * @return the parsed pair of points.
     * @throws SyntaxException if the input text does not match the syntax of a point pair.
     */
    public static Pair<Point> parsePointPair(String input) throws SyntaxException {
        Matcher matcher = TWO_POINTS.matcher(input);
        if (!matcher.find()) {
            throw new SyntaxException(String.format("'%s' not a valid point list.", input));
        } else {
            Point point1 = parsePoint(matcher.group(1));
            Point point2 = parsePoint(matcher.group(4));
            return new Pair<>(point1, point2);
        }
    }

    /**
     * Checks if the given text is an arrow ("->").
     * @param input the text to check.
     * @throws SyntaxException if the given text is not an arrow ("->").
     */
    public static void checkIsArrow(String input) throws SyntaxException {
        checkIsString("->", input);
    }

    /**
     * Checks if the given text is equal to
     * @param expectedString the expected string value.
     * @param input the text to check.
     * @throws SyntaxException if the given text is not equal to the expected string.
     */
    public static void checkIsString(String expectedString, String input) throws SyntaxException {
        if (!expectedString.equals(input)) {
            throw new SyntaxException(String.format("expected '%s' but got '%s'.", expectedString, input));
        }
    }

    /**
     * Parses a {@link String} into an int.
     * @param input the text to parse
     * @return the parsed integer.
     * @throws SyntaxException if the input text does not match the integer syntax.
     */
    public static int parseInteger(String input) throws SyntaxException {
        if (!INTEGER.matcher(input).matches()) {
            throw new SyntaxException(String.format("'%s' is not an integer number.", input));
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new SyntaxException(String.format("'%s' is not an integer number.", input));
        } //todo this is essentially not a syntax check
    }

    /**
     * Parses a {@link String} into a {@link Boolean}.
     * @param input the text to parse.
     * @return the parsed boolean value.
     * @throws SyntaxException if the input does not match the boolean syntax.
     */
    public static boolean parseBoolean(String input) throws SyntaxException {
        if (!BOOLEAN.matcher(input).matches()) {
            throw new SyntaxException(String.format("'%s' is not a valid boolean value.", input));
        } else {
            return Boolean.parseBoolean(input);
        }
    }

    /**
     * Parses a series / class {@link String}.
     * @param input the text to parse.
     * @return the input (if it is valid).
     * @throws SyntaxException if the input contains a 'W' or if it is not alphanumerical.
     */
    public static String parseSeries(String input) throws SyntaxException {
        if (!SERIES.matcher(input).matches()) {
            throw new SyntaxException(String.format("'%s' is not a valid series.", input));
        } else {
            return input;
        }
    }

    /**
     * Parses a engine or train-set name from a {@link String}.
     * @param input the text to parse.
     * @return the input (if it is valid).
     * @throws SyntaxException if the input is not alphanumerical.
     */
    public static String parseName(String input) throws SyntaxException {
        if (!ALPHANUMERICAL.matcher(input).matches()) {
            throw new SyntaxException(String.format("'%s' is not a valid name.", input));
        } else {
            return input;
        }
    }

    /**
     * Parses a {@link String} into a rolling stock identifier.
     * @param input the text to parse.
     * @return the input (if it is valid).
     * @throws SyntaxException if the input is not a valid rolling stock id.
     */
    public static String parseRollingStockId(String input) throws SyntaxException {
        if (!ROLLING_STOCK_ID.matcher(input).matches()) {
            throw new SyntaxException(String.format("'%s' is not a valid rolling stock id.", input));
        } else {
            return input;
        }
    }

    /**
     * Parses a {@link String} into a {@link CoachType}.
     * @param input the text to parse.
     * @return the parsed coach type.
     * @throws SyntaxException if the input text is not a coach-type code-name.
     */
    public static CoachType parseCoachType(String input) throws SyntaxException {
        CoachType type = CoachType.getByCodeName(input);
        if (type == null) {
            throw new SyntaxException(String.format("'%s' is not a valid coach type.", input));
        } else {
            return type;
        }
    }

    /**
     * Parses a {@link String} into an {@link EngineType}.
     * @param input the text to parse.
     * @return the parsed engine type.
     * @throws SyntaxException if the input text is not an engine-type code-name.
     */
    public static EngineType parseEngineType(String input) throws SyntaxException {
        //TODO don't we want that regex?
        EngineType type = EngineType.getByCodeName(input);
        if (type == null) {
            throw new SyntaxException(String.format("'%s' is not a valid engine type.", input));
        }
        return type;
    }

    /**
     * Parses a {@link String} into a {@link Direction}.
     * @param input the text to parse.
     * @return the parsed direction.
     * @throws SyntaxException if the input does not match the direction syntax.
     */
    public static Direction parseDirection(String input) throws SyntaxException {
        Matcher matcher = INTEGER_PAIR.matcher(input);
        if (!matcher.find()) {
            throw new SyntaxException(String.format("'%s' not a valid integer pair.", input));
        } else {
            int x = Integer.parseInt(matcher.group(1));
            int y = Integer.parseInt(matcher.group(2));
            return Direction.fromPoint(new Point(x, y));
        }
    }
}
