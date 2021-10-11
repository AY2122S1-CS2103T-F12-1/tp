package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalQuestions.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.question.Question;
import seedu.address.testutil.QuestionBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code AddCommand}.
 */
public class AddCommandIntegrationTest {

    private Model model;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_newQuestion_success() {
        Question validQuestion = new QuestionBuilder().build();

        Model expectedModel = new ModelManager(model.getSmartNus(), new UserPrefs());
        expectedModel.addQuestion(validQuestion);

        assertCommandSuccess(new AddCommand(validQuestion), model,
                String.format(AddCommand.MESSAGE_SUCCESS, validQuestion), expectedModel);
    }

    @Test
<<<<<<< HEAD
    public void execute_duplicateQuestion_throwsCommandException() {
        Question questionInList = model.getAddressBook().getQuestionList().get(0);
=======
    public void execute_duplicatePerson_throwsCommandException() {
        Question questionInList = model.getSmartNus().getQuestionList().get(0);
>>>>>>> 29d5d814a54d7b119ed6816407f131024b45df58
        assertCommandFailure(new AddCommand(questionInList), model, AddCommand.MESSAGE_DUPLICATE_QUESTION);
    }

}
