package Command;

import Receiver.Receiver;

public class DeleteCommand implements Command {
    private Receiver receiver;
    private int index;
    private String deletedLine;

    public DeleteCommand(Receiver receiver, int index) {
        this.index = index;
        this.receiver = receiver;
    }

    /**
     * Executes deletion of entry in data array list.
     * Stores deleted line in case of undo for restoration.
     */
    @Override
        public void execute() {
        this.deletedLine = receiver.delete(this.index - 1);
        System.out.println("Entry deleted successfully.");
    }

    /**
     * Undo deletion by adding back the deleted line stored using add command.
     */
    @Override
    public void undo() {
        this.receiver.insertAtIndex(this.index - 1, this.deletedLine);
        System.out.println("Undo command for delete executed successfully.");
    }
}
