package Command;

import Receiver.Receiver;
import Utils.InvalidInputException;

/**
 * Concrete command that deletes an entry from the receiver's data store.
 *
 * <p>This command follows the Command design pattern and encapsulates the
 * logic for removing an entry at a specified index. The deleted entry is
 * backed up to support undo functionality.</p>
 */
public class DeleteCommand implements Command {

    /** The receiver responsible for performing the delete operation. */
    private final Receiver receiver;

    /** The index of the entry to be deleted (1-based). */
    private String index;

    private int numIndex;

    /** Stores the deleted entry for potential restoration via undo. */
    private String deletedLine;

    /**
     * Constructs a {@code DeleteCommand} with the given receiver and index.
     *
     * @param receiver the receiver to perform the delete operation
     * @param index a string representing the 1-based index of the entry
     */
    public DeleteCommand(Receiver receiver, String index) {
        this.receiver = receiver;
        this.index = index;
//        try {
//            this.index = Integer.parseInt(index);
//        } catch (NumberFormatException e) {
//            System.out.println("Invalid index type provided");
//        }
    }

    /**
     * Executes the delete operation by removing the entry at the given index.
     *
     * <p>The removed entry is stored for use in {@link #undo()}. If the index
     * is out of bounds, an {@link InvalidInputException} is thrown.</p>
     *
     * @throws InvalidInputException if the index is invalid or out of bounds
     */
    @Override
    public void execute() {
        try {
            this.numIndex = Integer.parseInt(this.index);
            this.deletedLine = receiver.delete(numIndex - 1);
            if (!deletedLine.isEmpty()) {
                System.out.println("Entry deleted successfully.");
            } else {
                throw new InvalidInputException(
                        "Error: Index out of bounds, deletion not successful."
                );
            }
        }
        catch (NumberFormatException e) {
            throw new InvalidInputException("Error: index provided is not a " +
                    "number");
        }
    }

    /**
     * Undoes the delete operation by reinserting the previously deleted entry.
     *
     * <p>The entry is restored at its original index using the stored backup.</p>
     */
    @Override
    public void undo() {
        this.receiver.insertAtIndex(this.numIndex - 1, this.deletedLine);
        System.out.println("Undo command for delete executed successfully.");
    }

    /**
     * Indicates that this command supports undo.
     *
     * @return {@code true}, since the delete operation can be reverted
     */
    @Override
    public boolean isUndoable() {
        return true;
    }
}