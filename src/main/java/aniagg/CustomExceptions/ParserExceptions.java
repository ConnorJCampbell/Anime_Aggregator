package aniagg.CustomExceptions;

public class ParserExceptions {
    public static class InvalidColumnException extends RuntimeException {

        public InvalidColumnException(String errorMessage) {
            super(errorMessage);
        }
    }
}
