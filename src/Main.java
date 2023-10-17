public class Main {
    public static void main(String[] args) {
        try {
            String idCode1 = "38907260308";
            System.out.println(idCode1 + " code is correct? " + IDCodeValidator.checkControlNumber(idCode1));
            System.out.println(IDCodeValidator.getInformation(idCode1));

            String idCode2 = "98907260308";
            // String idCode2 = "48922260308";
            System.out.println(idCode2 + " code is correct? " + IDCodeValidator.checkControlNumber(idCode2));
            System.out.println(IDCodeValidator.getInformation(idCode2));
        } catch (IllegalArgumentException error) {
            System.out.println(error);
        }
    }
}