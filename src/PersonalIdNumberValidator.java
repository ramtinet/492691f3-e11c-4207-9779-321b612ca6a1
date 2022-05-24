import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

public class PersonalIdNumberValidator extends StringValidator {

    private final String personalIdNumber;
    private boolean DEBUG = true;

    /**
     * The constructor.
     * I will assume that a personal id number is a string.
     * @param personalIdNumber: the personal id number that will be evaluated
     */
    public PersonalIdNumberValidator(String personalIdNumber){
        super(personalIdNumber);
        this.personalIdNumber = personalIdNumber;
    }

    /**
     * This is the method that runs all the validators for evaluating whether a personal id number is correct or not.
     * @return boolean : The method returns true if all the validations succeed, otherwise it returns false.
     */
    public boolean isPersonalIdNumber(){
        String str = personalIdNumber;
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
        // Remove hyphens
        str = str.replaceAll("\\D", ""); // Here, we are replacing "non-digit" characters with an empty string.
        // Remove the first two characters (if the string has length 12)
        // We know that the length of strInp is either 10 or 12. This is because we did a validFormat check, but also because we removed hyphens.
        str = str.length() > 10 ? str.substring(2) : str;
        // The last number of the "personal identity number" is a control-number
        int controlNumber = Character.getNumericValue(str.charAt(str.length()-1)); // Here, we extracting the control number
        str = str.substring(0, str.length()-1); // Remove the last character from strInp (it is no longer needed).
        int checkSum = checksumAlgorithm(str);
        return validPersonalIdNumber(controlNumber, checkSum);
    }

    /**
     * This is a method that checks if a 'checksum' is equal to a 'control-number'.
     * If the check is evaluated to false (i.e., they are not equal), the method will log to stdout.
     * @param controlNumber : first parameter
     * @param checkSum : second parameter
     * @return boolean : returns true if 'control-number' equals 'checksum', otherwise it returns false.
     */
    private boolean validPersonalIdNumber(int controlNumber, int checkSum){
        boolean valid = controlNumber == checkSum;
        if(!valid){
            Logger.log("The checksum is invalid: [ " + controlNumber + " == " + checkSum + " ] is evaluated to 'false'", DEBUG);
        }
        return valid;
    }

    /**
     * This is a method that computes the checksum of a personal id number.
     * (simply put: the first 9 digits of a 10-digit personal id number can be used to compute a checksum).
     * @param nineFirstDigits : the 9 first characters of a 10-digit personal id number
     * @return int : returns the computed checksum of 'str'
     */
    private int checksumAlgorithm(String nineFirstDigits){
        int[] numericValues = IntStream.range(0, nineFirstDigits.length()).map(index -> {
            int factor = isEven(index) ? 2 : 1;
            int numericValue = Character.getNumericValue(nineFirstDigits.charAt(index)) * factor;
            if(!isOneDigitNumber(numericValue)){
                int ones = numericValue%10;
                int tens = (numericValue/10)%10;
                return tens+ones;
            }
            return numericValue;
        }).toArray();
        int sum = Arrays.stream(numericValues).sum();
        return (10-(sum%10))%10;
    }

    /**
     * This is a method that checks whether a number consists of one digit or not.
     * It checks both positive and negative numbers, this is because an int can range between -2147483648 and 2147483647.
     * @param number : the number that is checked
     * @return boolean : returns true if 'number' consists of one digit, otherwise it returns false.
     */
    private boolean isOneDigitNumber(int number){
        return !(number >= 10 || number <= -10);
    }

    /**
     * This is a method that checks whether a number is 'even' or 'odd'
     * @param number : the number that is checked
     * @return boolean : returns true if 'number' is even, otherwise it returns false.
     */
    private boolean isEven(int number){
        return number%2 == 0;
    }

    /**
     * This is a method that checks the 'length' of the personal id number.
     * A lot of assumptions were made here about the 'length',
     * but it's just a "proof of concept", so I guess it's fine.
     * @return boolean : returns true if the 'format' is valid, otherwise it returns false.
     */
    private boolean validLength(){
        /*
            Assumption: a personal id number is allowed to have four different formats:
                * dddddddddddd | dddddddd-dddd | dddddddddd | dddddd-dddd
                    regex: ^(\d{8}|\d{6})\-?\d{4}$
                    max-length: 13
                    min-length: 10
        */
        int MAX_LENGTH = 13;
        int MIN_LENGTH = 10;
        if((personalIdNumber.length() > MAX_LENGTH) || (personalIdNumber.length() < MIN_LENGTH)){
            Logger.log("Invalid length: input = " + personalIdNumber + ", length = " + personalIdNumber.length(), DEBUG);
            return false;
        }
        return  true;
    }

    /**
     * This is a method that checks the 'format' of the personal id number.
     * A lot of assumptions were made here about the 'format' (regex was mainly used - but also length-check),
     * but it's just a "proof of concept", so I guess it's fine.
     * @return boolean : returns true if the 'format' is valid, otherwise it returns false.
     */
    private boolean validFormat(){
        /*
            Assumption: a personal id number is allowed to have four different formats:
                * dddddddddddd | dddddddd-dddd | dddddddddd | dddddd-dddd
                    regex: ^(\d{8}|\d{6})\-?\d{4}$
                    max-length: 13
                    min-length: 10
         */
        boolean valid = Pattern.compile("^(\\d{8}|\\d{6})\\-?\\d{4}$").matcher(personalIdNumber).matches();
        if(!valid)
            Logger.log("Invalid format: " + personalIdNumber, DEBUG);
        return valid;
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
