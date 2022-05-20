public class NullValidator<T> {

    T t;

    private boolean DEBUG = true;

    /**
     * The NullValidator constructor.
     * @param t: the generic instance that will be evaluated.
     */
    public NullValidator(T t){
        this.t = t;
    }

    /**
     * This is a method that checks if a 'generic java instance' is null or not.
     * @return boolean : returns true if the instance is null, otherwise it returns false.
     */
    public boolean isNull(){
        boolean isNull = t == null;
        if(isNull){
            Logger.log("The input-value is Null", DEBUG);
        }
        return isNull;
    }

    /**
     * Logging should be optional. This method can enable and disable the logging functionality of the class itself,
     * but parent-classes which it extends.
     * @param b : if 'true' logging will be enabled, otherwise logging will be disabled.
     */
    public void setDebug(boolean b){
        DEBUG = b;
    }
}
