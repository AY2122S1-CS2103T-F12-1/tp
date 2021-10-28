package seedu.smartnus.logic.commands;

import static seedu.smartnus.model.Model.PREDICATE_SHOW_ALL_QUESTIONS;

import seedu.smartnus.model.Model;

/**
 * Terminates the program.
 */
public class ExitCommand extends Command {

    public static final String COMMAND_WORD = "exit";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Exiting SmartNUS as requested ...";

    @Override
    public CommandResult execute(Model model) {
        model.updateFilteredQuestionList(PREDICATE_SHOW_ALL_QUESTIONS);
        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT, false, true, false, false);
    }

}
