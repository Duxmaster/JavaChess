public class MoveResult {
    private final MoveResultType type;
    private final String message;

    private MoveResult(MoveResultType type, String message) {
        this.type = type;
        this.message = message;
    }

    public static MoveResult success() {
        return new MoveResult(MoveResultType.SUCCESS, null);
    }

    public static MoveResult failure(MoveResultType type, String message) {
        if (type == MoveResultType.SUCCESS) {
            throw new IllegalArgumentException("Cannot use failure() for SUCCESS type.");
        }
        return new MoveResult(type, message);
    }

    public MoveResultType getType() {
        return type;
    }

    public boolean isSuccessful() {
        return type == MoveResultType.SUCCESS;
    }

    public String getMessage() {
        return message;
    }

    public boolean isFailure() {
        return type != MoveResultType.SUCCESS;
    }
}