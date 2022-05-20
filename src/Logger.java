public class Logger {

    /**
     * This is a method that logs to stdout, but only if a display-condition is true.
     * @param message : the message that will be displayed on stdout
     * @param display : boolean that determines whether the message should be shown or not.
     */
    public static void log(String message, boolean display){
        if(display){
            System.out.println(message);
        }
    }

}
