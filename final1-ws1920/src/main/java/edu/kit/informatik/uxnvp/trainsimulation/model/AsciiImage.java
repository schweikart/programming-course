package edu.kit.informatik.uxnvp.trainsimulation.model;

import java.util.Arrays;

/**
 * An ascii art image.
 * @author Max Schweikart
 * @version 1.0
 */
public class AsciiImage {
    private final String[] lines;

    /**
     * Constructs a new ascii image based on an array of lines.
     * @param lines the lines of the ascii image. The top line should be at index 0 and all lines should have the same
     *              length.
     */
    public AsciiImage(String... lines) {
        this.lines = Arrays.copyOf(lines, lines.length);
    }

    /**
     * Returns the height of this ascii image which is the amount of lines.
     * @return the height of this ascii image.
     */
    public int getHeight() {
        return lines.length;
    }

    /**
     * Returns a specific line of this ascii image.
     * @param lineIndex the zero-based index of the line. The top line is at index 0.
     * @return the line at the given index
     */
    public String getLine(int lineIndex) {
        return lines[lineIndex];
    }

    /**
     * Concatenates two ascii images with one whitespace in between. If the images do not have the same height,
     * whitespace-lines are inserted at the top. Neither of the images is modified.
     * @param other the image to concatenate.
     * @return a new ascii image that contains the concatenated image.
     */
    public AsciiImage concat(AsciiImage other) {
        // adjust heights
        if (this.getHeight() < other.getHeight()) {
            return extend(other.getHeight()).concat(other);
        } else if (this.getHeight() > other.getHeight()) {
            return concat(other.extend(this.getHeight()));
        }

        // concatenate line by line
        String[] newLines = new String[getHeight()];
        for (int i = 0; i < getHeight(); i++) {
            newLines[i] = getLine(i) + ' ' + other.getLine(i);
        }

        return new AsciiImage(newLines);
    }

    /**
     * Extends the height of this ascii image to a given height without modifying this object. This method will insert
     * empty lines at the top (low indices) of this image.
     * @param newHeight the new height of the ascii image.
     * @return a new ascii image with the content of this image and the new height.
     */
    private AsciiImage extend(int newHeight) {
        if (newHeight < getHeight()) {
            throw new IllegalArgumentException("can not extend an ascii image to a smaller size.");
        }

        // prepare empty so we don't have to build it for each empty line again.
        String emptyLine = buildEmptyLine(getWidth());

        String[] newLines = new String[newHeight];
        int insertLineAmount = newHeight - getHeight();
        for (int i = 0; i < newHeight; i++) {
            if (i < insertLineAmount) {
                newLines[i] = emptyLine;
            } else {
                newLines[i] = getLine(i - insertLineAmount);
            }
        }
        return new AsciiImage(newLines);
    }

    /**
     * Finds the width of this image which is the width of each line.
     * @return the width of each line or 0 if this image does not have any lines (height 0)
     */
    private int getWidth() {
        return lines.length == 0 ? 0 : lines[0].length();
    }

    /**
     * Returns the String representation of this ascii image.
     * @return the String representation of this ascii image.
     */
    @Override
    public String toString() {
        return String.join("\n", lines);
    }

    /**
     * Helper method to build an empty line with a given number of whitespace characters.
     * @param length the number of whitespace characters to put in this line.
     * @return the built line.
     */
    private static String buildEmptyLine(int length) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            builder.append(' ');
        }
        return builder.toString();
    }
}
