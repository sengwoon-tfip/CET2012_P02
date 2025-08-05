
public class List implements Command {
    private Receiver receiver;

    public List(Receiver receiver) {
        this.receiver = receiver;
    }

    /**
     * Call to receiver to list out items in the data entry array list.
     */
    @Override
    public void execute() {
        this.receiver.list();
    }

    /**
     * Print error message for invalid undo operation.
     */
    public void undo() {
        System.out.println("List command cannot be undone.");
    }
}
