package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.SupplierStatus;


/**
 * Marks a supplier as active or inactive in the address book.
 */
public class MarkSupplierCommand extends Command {

    public static final String COMMAND_WORD = "mark -m";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the status of the supplier identified by the index number used in the displayed supplier list.\n"
            + "Parameters: INDEX (must be a positive integer) STATUS (active, inactive)\n"
            + "Example: " + COMMAND_WORD + " 1 active";

    public static final String MESSAGE_MARK_SUPPLIER_SUCCESS = "Marked Supplier: %1$s as %2$s";

    private final Index targetIndex;
    private final SupplierStatus status;

    /**
     * Creates an MarkSupplierCommand to mark the supplier at {@code index} with new status {@code status}
     */
    public MarkSupplierCommand(Index targetIndex, SupplierStatus status) {
        this.targetIndex = targetIndex;
        this.status = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (targetIndex.getZeroBased() >= model.getFilteredSupplierList().size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_SUPPLIER_DISPLAYED_INDEX);
        }

        Supplier supplierToMark = model.getFilteredSupplierList().get(targetIndex.getZeroBased());
        Supplier markedSupplier = new Supplier(
                supplierToMark.getName(),
                supplierToMark.getPhone(),
                supplierToMark.getEmail(),
                supplierToMark.getCompany(),
                supplierToMark.getProduct(),
                status
        );

        model.setSupplier(supplierToMark, markedSupplier);
        model.updateFilteredSupplierList(Model.PREDICATE_SHOW_ALL_SUPPLIERS);
        return new CommandResult(String.format(MESSAGE_MARK_SUPPLIER_SUCCESS, supplierToMark, status));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MarkSupplierCommand // instanceof handles nulls
                && targetIndex.equals(((MarkSupplierCommand) other).targetIndex)); // state check
    }
}
