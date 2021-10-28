package seedu.smartnus.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.smartnus.model.Model.PREDICATE_SHOW_ALL_NOTES;
import static seedu.smartnus.model.Model.PREDICATE_SHOW_ALL_QUESTIONS;

import seedu.smartnus.model.Model;

/**
 * Lists all questions in SmartNus to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String NOTE_KEYWORD = "note";
    public static final String QUESTION_KEYWORD = "question";

    public static final String MESSAGE_SUCCESS_QUESTIONS = "Listed all questions";
    public static final String MESSAGE_SUCCESS_NOTES = "Listed all notes";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": lists the contents of SmartNUS. "
            + "Parameters: "
            + NOTE_KEYWORD + ": list notes or "
            + QUESTION_KEYWORD + ": list questions "
            + "Example: " + COMMAND_WORD + " "
            + NOTE_KEYWORD;

    private static boolean displayQuestions = true;

    /**
     * Instantiates a new ListCommand
     * @param listArg argument passed to the list command
     */
    public ListCommand(String listArg) {
        if (listArg.equals(NOTE_KEYWORD)) {
            displayQuestions = false;
        } else {
            displayQuestions = true;
        }
    }

    /**
     * returns the boolean determining what to list.
     * @return displayQuestions
     */
    public static boolean isDisplayQuestions() {
        return displayQuestions;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String successMessage;
        if (displayQuestions) {
            model.updateFilteredQuestionList(PREDICATE_SHOW_ALL_QUESTIONS);
            successMessage = MESSAGE_SUCCESS_QUESTIONS;
        } else {
            model.updateFilteredNoteList(PREDICATE_SHOW_ALL_NOTES);
            successMessage = MESSAGE_SUCCESS_NOTES;
        }
        return new CommandResult(successMessage, false, false, false, true);
    }

    /**
     * Checks if two instances are the same.
     * @param other the other instance of listCommand.
     * @return true if both instances are the same, false otherwise
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ListCommand // instanceof handles nulls
                && displayQuestions == (((ListCommand) other).displayQuestions));
    }
}
