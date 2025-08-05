public class Delete implements Command {
    private Receiver receiver;
    private int index;
    private String deletedLine;

    public Delete(Receiver receiver, int index) {
        this.index = index;
        this.receiver = receiver;
    }

    /**
     * Executes deletion of entry in data array list.
     * Stores deleted line in case of undo for restoration.
     */
    @Override
        public void execute() {
        this.deletedLine = receiver.delete(this.index);
    }

    /**
     * Undo deletion by adding back the deleted line stored using add command.
     */
    @Override
    public void undo() {
        this.receiver.indexAdd(this.index, this.deletedLine);
    }
}
