package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMPORTANCE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.SmartNus;
import seedu.address.model.question.NameContainsKeywordsPredicate;
import seedu.address.model.question.Question;
import seedu.address.testutil.EditQuestionDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_IMPORTANCE_AMY = "1";
    public static final String VALID_IMPORTANCE_BOB = "2";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_IMPORTANCE_1 = "1";
    public static final String VALID_QUESTION_1 = "What is 1+1?";
    public static final String VALID_QUESTION_2 = "What is 2+2?";
    public static final String VALID_ANSWER_1 = "2";
    public static final String VALID_ANSWER_2 = "4";
    public static final String VALID_OPTION_1 = "1";
    public static final String VALID_OPTION_3 = "3";
    public static final String VALID_OPTION_4 = "4";
    public static final String VALID_OPTION_5 = "5";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String IMPORTANCE_DESC_AMY = " " + PREFIX_IMPORTANCE + VALID_IMPORTANCE_AMY;
    public static final String IMPORTANCE_DESC_BOB = " " + PREFIX_IMPORTANCE + VALID_IMPORTANCE_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String IMPORTANCE_DESC_1 = " " + PREFIX_IMPORTANCE + VALID_IMPORTANCE_1;
    public static final String QUESTION_DESC_1 = " " + PREFIX_QUESTION + VALID_QUESTION_1;
    public static final String QUESTION_DESC_2 = " " + PREFIX_QUESTION + VALID_QUESTION_2;
    public static final String ANSWER_DESC_1 = " " + PREFIX_ANSWER + VALID_ANSWER_1;
    public static final String ANSWER_DESC_2 = " " + PREFIX_ANSWER + VALID_ANSWER_2;
    public static final String OPTION_DESC_1 = " " + PREFIX_OPTION + VALID_OPTION_1;
    public static final String OPTION_DESC_3 = " " + PREFIX_OPTION + VALID_OPTION_3;
    public static final String OPTION_DESC_4 = " " + PREFIX_OPTION + VALID_OPTION_4;
    public static final String OPTION_DESC_5 = " " + PREFIX_OPTION + VALID_OPTION_5;
    public static final String OPTIONS_DESC_1 = " " + OPTION_DESC_1 + OPTION_DESC_3 + OPTION_DESC_4;
    public static final String OPTIONS_DESC_2 = OPTIONS_DESC_1 + OPTION_DESC_5;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + " "; // empty string not allowed for names
    // 'a' not allowed in importance
    public static final String INVALID_IMPORTANCE_DESC = " " + PREFIX_IMPORTANCE + "1a";
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_QUESTION_DESC = " " + PREFIX_QUESTION; // empty string not allowed for question
    public static final String INVALID_ANSWER_DESC = " " + PREFIX_ANSWER; // empty string not allowed for answer
    public static final String INVALID_OPTION_DESC = " " + PREFIX_OPTION; // empty string not allowed for option

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditQuestionDescriptor DESC_AMY;
    public static final EditCommand.EditQuestionDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditQuestionDescriptorBuilder().withName(VALID_NAME_AMY)
                .withImportance(VALID_IMPORTANCE_AMY).withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditQuestionDescriptorBuilder().withName(VALID_NAME_BOB)
                .withImportance(VALID_IMPORTANCE_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the returned {@link CommandResult} matches {@code expectedCommandResult} <br>
     * - the {@code actualModel} matches {@code expectedModel}
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandResult expectedCommandResult,
            Model expectedModel) {
        try {
            CommandResult result = command.execute(actualModel);
            assertEquals(expectedCommandResult, result);
            assertEquals(expectedModel, actualModel);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Convenience wrapper to {@link #assertCommandSuccess(Command, Model, CommandResult, Model)}
     * that takes a string {@code expectedMessage}.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, String expectedMessage,
            Model expectedModel) {
        CommandResult expectedCommandResult = new CommandResult(expectedMessage);
        assertCommandSuccess(command, actualModel, expectedCommandResult, expectedModel);
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book, filtered question list and selected question in {@code actualModel} remain unchanged
     */
    public static void assertCommandFailure(Command command, Model actualModel, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        SmartNus expectedAddressBook = new SmartNus(actualModel.getSmartNus());
        List<Question> expectedFilteredList = new ArrayList<>(actualModel.getFilteredQuestionList());

        assertThrows(CommandException.class, expectedMessage, () -> command.execute(actualModel));
        assertEquals(expectedAddressBook, actualModel.getSmartNus());
        assertEquals(expectedFilteredList, actualModel.getFilteredQuestionList());
    }
    /**
     * Updates {@code model}'s filtered list to show only the question at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showQuestionAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredQuestionList().size());

        Question question = model.getFilteredQuestionList().get(targetIndex.getZeroBased());
        final String[] splitName = question.getName().fullName.split("\\s+");
        model.updateFilteredQuestionList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredQuestionList().size());
    }

}
