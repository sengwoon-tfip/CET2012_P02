package Command;

import Receiver.Receiver;
import Helper.InvalidInputException;

public class DeleteCommand implements Command {
    private final Receiver receiver;
    private int index;
    private String deletedLine;

    public DeleteCommand(Receiver receiver, String index) {
        this.receiver = receiver;
        try {
            this.index = Integer.parseInt(index);
        }
        catch (NumberFormatException e) {
            System.out.println("Invalid index type provided");
        }
    }

    /**
     * Executes deletion of entry in data array list.
     * Stores deleted line in case of undo for restoration.
     */
    @Override
        public void execute(){
        this.deletedLine = receiver.delete(this.index - 1);
        if (deletedLine != "") {
            System.out.println("Entry deleted successfully.");
        }
        else {
            throw new InvalidInputException("Error: Index out of bounds, deletion not successful.");
        }
    }

    /**
     * Undo deletion by adding back the deleted line stored using add command.
     */
    @Override
    public void undo() {
        this.receiver.insertAtIndex(this.index - 1, this.deletedLine);
        System.out.println("Undo command for delete executed successfully.");
    }
    public boolean isUndoable() {
        return true;
    }
}
