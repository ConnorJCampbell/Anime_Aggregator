package aniagg.CustomExceptions;

public class DBExceptions {
    public static class EmptyResultException extends RuntimeException {

        public EmptyResultException(String errorMessage) {
            super(errorMessage);
        }
    }

    public static class UnknownException extends RuntimeException {

        public UnknownException(String errorMessage) {
            super(errorMessage);
        }
    }
}
