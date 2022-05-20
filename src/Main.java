public class Main {
    public static void main(String[] args) {

        PersonalIdNumberValidator personalIdNumberValidator = new PersonalIdNumberValidator("19780202-2389");
        boolean test1 = personalIdNumberValidator.isPersonalIdNumber();
        System.out.println("Should be evaluated to 'true': " + test1);

        CarRegistrationNumberValidator carRegistrationNumberValidator = new CarRegistrationNumberValidator("ABA-130");
        boolean test2 = carRegistrationNumberValidator.isCarRegistrationNumber();
        System.out.println("Should be evaluated to 'true': " + test2);
    }
}
