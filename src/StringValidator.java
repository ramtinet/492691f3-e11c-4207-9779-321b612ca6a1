public class StringValidator extends NullValidator<String>{

    private String str;
    private boolean DEBUG = true;

    /**
     * The StringValidator constructor.
     * @param str: the string that will be evaluated.
     */
    public StringValidator(String str){
        super(str);
        this.str = str;
    }

    /**
     * This is a method that checks if a string is empty (i.e., "").
     * @return boolean : returns true if the string is empty, otherwise it returns false.
     */
    public boolean isEmpty(){
        if(str.isEmpty()){
            Logger.log("String-input is empty", DEBUG);
        }
        return str.isEmpty();
    }

    /**
     * This is a method that checks if a string is blank (i.e., "            ").
     * @return boolean : returns true if the string is blank, otherwise it returns false.
     */
    public boolean isBlank(){
        boolean isBlank = str.trim().isEmpty();
        if(isBlank){
            Logger.log("String-input is blank", DEBUG);
        }
        return isBlank;
    }

    /**
     * Logging should be optional. This method can enable and disable the logging functionality of the class itself,
     * but parent-classes which it extends.
     * @param b : if 'true' logging will be enabled, otherwise logging will be disabled.
     */
    public void setDebug(boolean b){
        DEBUG = b;
        super.setDebug(b);
    }

}
