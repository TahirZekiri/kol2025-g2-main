package mk.ukim.finki.wp.kol2025g2.model.exceptions;

public class InvalidSkiResortIdException extends RuntimeException {
    public InvalidSkiResortIdException(Long id) {
        super(String.format("Ski resort with id %d was not found", id));
    }
}
