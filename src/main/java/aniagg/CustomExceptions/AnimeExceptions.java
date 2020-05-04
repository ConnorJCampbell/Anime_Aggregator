package aniagg.CustomExceptions;

public class AnimeExceptions {
    public static class NegativeIntegerException extends RuntimeException {

        public NegativeIntegerException(String errorMessage) {
            super(errorMessage);
        }
    }
}
