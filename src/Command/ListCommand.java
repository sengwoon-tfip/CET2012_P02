package Command;

import Receiver.Receiver;

public class ListCommand implements Command {
    private final Receiver receiver;

    public ListCommand(Receiver receiver) {
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

    public boolean isUndoable() {
        return false;
    }
}
