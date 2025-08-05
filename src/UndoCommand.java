public class Undo implements Command {
    private Receiver receiver;

    public Undo(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Calls to receiver to execute undo operation.
     */
    public void execute() {
        this.receiver.undo();
    }

    /**
     * Prints error statement for invalid undo operation.
     */
    public void undo() {
        System.out.println("Undo command cannot be undone.");
    }
}
