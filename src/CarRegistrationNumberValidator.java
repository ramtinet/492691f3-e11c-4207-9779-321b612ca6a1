import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/*
    Wikipedia was used to learn more about car registration numbers (i.e., blacklists, requirements and rules).
        link:
            https://sv.wikipedia.org/wiki/Registreringsskyltar_i_Sverige#Ol.C3.A4mpliga_bokstavskombinationer
 */
public class CarRegistrationNumberValidator extends StringValidator {
    private final String carRegistrationNumber;

    private boolean DEBUG = true;

    // This black-list was found on wikipedia
    private final String[] BLACKLIST = {
            "APA", "ARG", "ASS",
            "BAJ", "BSS",
            "CUC", "CUK",
            "DUM",
            "ETA", "ETT",
            "FAG", "FAN", "FEG", "FEL", "FEM", "FES", "FET", "FNL", "FUC", "FUK", "FUL",
            "GAM", "GAY", "GEJ", "GEY", "GHB", "GUD", "GYN",
            "HAT", "HBT", "HKH", "HOR", "HOT",
            "KGB", "KKK", "KUC", "KUF", "KUG", "KUK", "KYK",
            "LAM", "LAT", "LEM", "LOJ", "LSD", "LUS",
            "MAD", "MAO", "MEN", "MES", "MLB", "MUS",
            "NAZ", "NRP", "NSF", "NYP",
            "OND", "OOO", "ORM",
            "PAJ", "PKK", "PLO", "PMS", "PUB",
            "RAP", "RAS", "ROM", "RPS", "RUS",
            "SEG", "SEX", "SJU", "SOS", "SPY", "SUG", "SUP", "SUR",
            "TBC", "TOA", "TOK", "TRE", "TYP",
            "UFO", "USA",
            "WAM", "WAR", "WWW",
            "XTC", "XTZ", "XXL", "XXX",
            "ZEX", "ZOG", "ZPY", "ZUG", "ZUP", "ZOO"
    };

    /**
     * The constructor.
     * I will assume that a car registration number is a string.
     * @param carRegistrationNumber : the car registration number that will be evaluated.
     */
    public CarRegistrationNumberValidator(String carRegistrationNumber) {
        super(carRegistrationNumber);
        this.carRegistrationNumber = carRegistrationNumber;
    }

    /**
     * This is the method that runs all the validators for evaluating whether a car registration number is correct or not.
     * @return boolean : The method returns true if all the validations succeed, otherwise it returns false.
     */
    public boolean isCarRegistrationNumber() {
        if(super.isNull())
            return false;
        if(super.isNull())
            return false;
        if(super.isEmpty())
            return false;
        if(super.isBlank())
            return false;
        if(!validLength())
            return false;
        if(!validFormat())
            return false;
        String str = carRegistrationNumber;
        // Remove hyphens
        str = str.replaceAll("\\-", "");//Here, we are replacing hyphen characters with an empty string.
        // We know that the length of str is 6. This is because we did a validFormat check, but also because we removed hyphens.
        String chars = str.substring(0, 3);
        String digits = str.substring(3, str.length());
        if(!validChars(chars))
            return false;
        if(!validDigits(digits))
            return false;
        if(isBlackListed(chars))
            return false;

        return true;
    }

    /**
     * This method checks if the input string is "Black-listed".
     * @param str : the str that is checked
     * @return boolean : returns true if the string is black-listed, otherwise it returns false.
     */
    private boolean isBlackListed(String str){
        boolean isBlackListed = Arrays.stream(BLACKLIST)
                .sequential()
                .anyMatch(blackListed -> blackListed.equals(str.toUpperCase()));
        if(isBlackListed){
            Logger.log(str + " is blacklisted", DEBUG);
        }
        return isBlackListed;
    }

    /**
     * This is a method that checks the 'length' of the car-registration-number.
     * A lot of assumptions were made here about the 'length',
     * but it's just a "proof of concept", so I guess it's fine.
     * @return boolean : returns true if the 'format' is valid, otherwise it returns false.
     */
    private boolean validLength(){
        /*
        Assumption: a car registration number is allowed to have four different formats (lowercase letters are allowed)
                * formats: ccc-ddd | ccc-ddc | cccddd | cccddc
                    * regex: ^[a-zA-Z]{3}\-?\d{2}(\d|[a-zA-Z])$
                    * example: ABC-123
                    * max-length: 7
                    * min-length: 6
        */
        int MAX_LENGTH = 7;
        int MIN_LENGTH = 6;
        // if the input is larger than MAX_LENGTH, we don't need to do any regex-checks, we can just terminate (return false) at this point.
        if((carRegistrationNumber.length() > MAX_LENGTH) || (carRegistrationNumber.length() < MIN_LENGTH)){
            Logger.log("Invalid length: input = " + carRegistrationNumber + ", length = " + carRegistrationNumber.length(), DEBUG);
            return false;
        }
        return  true;
    }

    /**
     * A car registration number usually consists of three letters followed by three digits.
     * This is a method that checks the format of a car-registration number.
     * A lot of assumptions were made here about the 'format' (regex was mainly used - but also length-check),
     * but it's just a "proof of concept", so I guess it's fine.
     * @return boolean : returns true if the format is valid, otherwise it returns false.
     */
    private boolean validFormat(){
        /*
        Assumption: a car registration number is allowed to have four different formats (lowercase letters are allowed)
                * formats: ccc-ddd | ccc-ddc | cccddd | cccddc
                    * regex: ^[a-zA-Z]{3}\-?\d{2}(\d|[a-zA-Z])$
                    * example: ABC-123
                    * max-length: 7
                    * min-length: 6
        */
        int MAX_LENGTH = 7;
        int MIN_LENGTH = 6;
        // if the input is larger than MAX_LENGTH, we don't need to do any regex-checks, we can just terminate (return false) at this point.
        if((carRegistrationNumber.length() > MAX_LENGTH) || (carRegistrationNumber.length() < MIN_LENGTH)){
            Logger.log("Invalid length: input = " + carRegistrationNumber + ", length = " + carRegistrationNumber.length(), DEBUG);
            return false;
        }
        boolean valid = Pattern.compile("^[a-zA-Z]{3}\\-?\\d{2}(\\d|[a-zA-Z])$").matcher(carRegistrationNumber).matches();
        if(!valid)
            Logger.log("Invalid format: " + carRegistrationNumber, DEBUG);
        return valid;
    }

    /**
     * A car registration number usually consists of three letters followed by three digits (e.g., cccddd).
     * However, there are some "forbidden characters", these are iI, qQ, vV, åÅ, äÄ, öÖ.
     * This method validates the first three letters of a car-registration number.
     * @param firstThreeAlphabeticCharacters : the first-three-characters that we want to evaluate.
     * @return boolean : returns true if the "firstThreeAlphabeticCharacters" are valid, otherwise it returns false.
     */
    private boolean validChars(String firstThreeAlphabeticCharacters){
        /*
            Assumption: invalid characters are: iI, qQ, vV, åÅ, äÄ, öÖ. It is also not allowed to have a oO at the end
                * regex: [iIqQvVåÅäÄöÖ]    (matches a single character in the list)
        */
        boolean valid = !(Pattern.compile("[iIqQvVåÅäÄöÖ]").matcher(firstThreeAlphabeticCharacters).find());
        if(!valid)
            Logger.log("Invalid characters: " + firstThreeAlphabeticCharacters, DEBUG);
        return valid;
    }

    /**
     * A car registration number usually consists of three letters followed by three digits (e.g., cccddd).
     * However, there are some "edge-cases", the last character (of the three digits) can also be a alphabetic letter.
     * Another "edge-case" is that the last charcter (of the three digits) can't be the alphabetic letter 'O',
     * this is because it resembles the zero-digit. Other forbidden characters are iI, qQ, vV, åÅ, äÄ, öÖ.
     * This method validates the three last digits of a car-registration number.
     * @param lastThreeDigits : the last-three-digits that we want to evaluate.
     * @return boolean : returns true if the "lastThreeDigits" are valid, otherwise it returns false.
     */
    private boolean validDigits(String lastThreeDigits){
        /*
            Assumption: the first two symbols must be digits, and the last symbol can either be a number or a alphabetic symbol.
            The alphabetic symbol is not allowed to be: oO, iI, qQ, vV, åÅ, äÄ, öÖ.
        */
        String firstTwoSymbols = lastThreeDigits.substring(0,lastThreeDigits.length()-1);
        boolean firstTwoSymbolsValid = (Pattern.compile("^\\d{2}$").matcher(firstTwoSymbols).matches());
        if(!firstTwoSymbolsValid)
            Logger.log("The first two digits are invalid: " + lastThreeDigits, DEBUG);
        String lastSymbol = lastThreeDigits.substring(lastThreeDigits.length()-1,lastThreeDigits.length());
        boolean lastSymbolValid = (!(Pattern.compile("[oOiIqQvVåÅäÄöÖ]").matcher(lastSymbol).find()));
        if(!lastSymbolValid)
            Logger.log("The last symobl is invalid: " + lastThreeDigits, DEBUG);
        return firstTwoSymbolsValid && lastSymbolValid;
    }

    /**
     * Logging should be optional. This method can enable and disable the logging functionality of the class itself,
     * but parent-classes which it extends.
     * @param b : if 'true' logging will be enabled, otherwise logging will be disabled.
     */
    public void setDebug(boolean b){
        super.setDebug(b);
        DEBUG = b;
    }

}