package mk.ukim.finki.wp.kol2025g2.model.exceptions;

public class InvalidSkiSlopeIdException extends RuntimeException {
    public InvalidSkiSlopeIdException(Long id) {
        super(String.format("Ski slope with id %d was not found", id));
    }
}
