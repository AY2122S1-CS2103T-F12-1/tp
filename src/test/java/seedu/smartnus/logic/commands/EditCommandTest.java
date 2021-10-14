package seedu.smartnus.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.smartnus.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.smartnus.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.smartnus.logic.commands.CommandTestUtil.VALID_IMPORTANCE_BOB;
import static seedu.smartnus.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.smartnus.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.smartnus.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.smartnus.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.smartnus.logic.commands.CommandTestUtil.showQuestionAtIndex;
import static seedu.smartnus.testutil.TypicalIndexes.INDEX_FIRST_QUESTION;
import static seedu.smartnus.testutil.TypicalIndexes.INDEX_SECOND_QUESTION;
import static seedu.smartnus.testutil.TypicalQuestions.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.smartnus.commons.core.Messages;
import seedu.smartnus.commons.core.index.Index;
import seedu.smartnus.logic.commands.EditCommand.EditQuestionDescriptor;
import seedu.smartnus.model.AddressBook;
import seedu.smartnus.model.Model;
import seedu.smartnus.model.ModelManager;
import seedu.smartnus.model.UserPrefs;
import seedu.smartnus.model.question.Question;
import seedu.smartnus.testutil.EditQuestionDescriptorBuilder;
import seedu.smartnus.testutil.QuestionBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Question editedQuestion = new QuestionBuilder().build();
        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder(editedQuestion).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_QUESTION, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_QUESTION_SUCCESS, editedQuestion);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setQuestion(model.getFilteredQuestionList().get(0), editedQuestion);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastQuestion = Index.fromOneBased(model.getFilteredQuestionList().size());
        Question lastQuestion = model.getFilteredQuestionList().get(indexLastQuestion.getZeroBased());

        QuestionBuilder questionInList = new QuestionBuilder(lastQuestion);
        Question editedQuestion = questionInList.withName(VALID_NAME_BOB).withImportance(VALID_IMPORTANCE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withName(VALID_NAME_BOB)
                .withImportance(VALID_IMPORTANCE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(indexLastQuestion, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_QUESTION_SUCCESS, editedQuestion);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setQuestion(lastQuestion, editedQuestion);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_QUESTION, new EditQuestionDescriptor());
        Question editedQuestion = model.getFilteredQuestionList().get(INDEX_FIRST_QUESTION.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_QUESTION_SUCCESS, editedQuestion);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showQuestionAtIndex(model, INDEX_FIRST_QUESTION);

        Question questionInFilteredList = model.getFilteredQuestionList().get(INDEX_FIRST_QUESTION.getZeroBased());
        Question editedQuestion = new QuestionBuilder(questionInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_QUESTION,
                new EditQuestionDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_QUESTION_SUCCESS, editedQuestion);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setQuestion(model.getFilteredQuestionList().get(0), editedQuestion);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateQuestionUnfilteredList_failure() {
        Question firstQuestion = model.getFilteredQuestionList().get(INDEX_FIRST_QUESTION.getZeroBased());
        EditCommand.EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder(firstQuestion).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_QUESTION, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_QUESTION);
    }

    @Test
    public void execute_duplicateQuestionFilteredList_failure() {
        showQuestionAtIndex(model, INDEX_FIRST_QUESTION);

        // edit question in filtered list into a duplicate in address book
        Question questionInList = model.getAddressBook().getQuestionList().get(INDEX_SECOND_QUESTION.getZeroBased());
        EditCommand editCommand = new EditCommand(INDEX_FIRST_QUESTION,
                new EditQuestionDescriptorBuilder(questionInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_QUESTION);
    }

    @Test
    public void execute_invalidQuestionIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredQuestionList().size() + 1);
        EditQuestionDescriptor descriptor = new EditQuestionDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidQuestionIndexFilteredList_failure() {
        showQuestionAtIndex(model, INDEX_FIRST_QUESTION);
        Index outOfBoundIndex = INDEX_SECOND_QUESTION;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getQuestionList().size());

        EditCommand editCommand = new EditCommand(outOfBoundIndex,
                new EditQuestionDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_QUESTION_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_QUESTION, DESC_AMY);

        // same values -> returns true
        EditCommand.EditQuestionDescriptor copyDescriptor = new EditCommand.EditQuestionDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_QUESTION, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_QUESTION, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_QUESTION, DESC_BOB)));
    }

}
