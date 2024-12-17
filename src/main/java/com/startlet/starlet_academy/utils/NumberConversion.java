package com.startlet.starlet_academy.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberConversion {

    private static final String[] tensNames = {
            "", " ten", " twenty", " thirty", " forty", " fifty",
            " sixty", " seventy", " eighty", " ninety"
    };

    private static final String[] numNames = {
            "", " one", " two", " three", " four", " five",
            " six", " seven", " eight", " nine", " ten",
            " eleven", " twelve", " thirteen", " fourteen",
            " fifteen", " sixteen", " seventeen", " eighteen", " nineteen"
    };

    private static String convertLessThanOneThousand(int number) {
        String current;

        if (number % 100 < 20) {
            current = numNames[number % 100];
            number /= 100;
        } else {
            current = numNames[number % 10];
            number /= 10;

            current = tensNames[number % 10] + current;
            number /= 10;
        }
        if (number == 0) return current;
        return numNames[number] + " hundred" + current;
    }

    public static String convert(long number) {
        if (number == 0) return "zero";

        String snumber = Long.toString(number);

        // Pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0, 3));
        // nnnXXXnnnnnn
        int millions = Integer.parseInt(snumber.substring(3, 6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9, 12));

        String tradBillions;
        switch (billions) {
            case 0:
                tradBillions = "";
                break;
            default:
                tradBillions = convertLessThanOneThousand(billions) + " billion ";
        }
        String result = tradBillions;

        String tradMillions;
        switch (millions) {
            case 0:
                tradMillions = "";
                break;
            default:
                tradMillions = convertLessThanOneThousand(millions) + " million ";
        }
        result = result + tradMillions;

        String tradHundredThousands;
        switch (hundredThousands) {
            case 0:
                tradHundredThousands = "";
                break;
            case 1:
                tradHundredThousands = "one thousand ";
                break;
            default:
                tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " thousand ";
        }
        result = result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result = result + tradThousand;

        // Remove extra spaces
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ").trim();
    }

    public static String convertBigDecimal(BigDecimal number) {
        // Split the number into integer and fractional parts
        BigDecimal[] parts = number.stripTrailingZeros().divideAndRemainder(BigDecimal.ONE);
        long integerPart = parts[0].longValue();
        int fractionalPart = parts[1].movePointRight(parts[1].scale()).intValue();

        String integerPartInWords = convert(integerPart);

        if (fractionalPart == 0) {
            return integerPartInWords;
        }

        String fractionalPartInWords = convert(fractionalPart);
        return integerPartInWords + " point " + fractionalPartInWords;
    }
}
