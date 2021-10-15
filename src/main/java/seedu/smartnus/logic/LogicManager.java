package seedu.smartnus.logic;

import java.io.IOException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.smartnus.commons.core.GuiSettings;
import seedu.smartnus.commons.core.LogsCenter;
import seedu.smartnus.logic.commands.Command;
import seedu.smartnus.logic.commands.CommandResult;
import seedu.smartnus.logic.commands.exceptions.CommandException;
import seedu.smartnus.logic.parser.SmartNusParser;
import seedu.smartnus.logic.parser.QuizInputParser;
import seedu.smartnus.logic.parser.exceptions.ParseException;
import seedu.smartnus.model.Model;
import seedu.smartnus.model.ReadOnlySmartNus;
import seedu.smartnus.model.question.Question;
import seedu.smartnus.model.quiz.QuizManager;
import seedu.smartnus.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final SmartNusParser smartNusParser;
    private final QuizInputParser quizInputParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        smartNusParser = new SmartNusParser();
        quizInputParser = new QuizInputParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = smartNusParser.parseCommand(commandText);
        commandResult = command.execute(model);

        try {
            storage.saveSmartNus(model.getSmartNus());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public CommandResult execute(String commandText, QuizManager quizManager) throws CommandException, ParseException {
        logger.info("----------------[USER QUIZ COMMAND][" + commandText + "]");
        Command command = quizInputParser.parseCommand(commandText, quizManager);
        CommandResult commandResult = command.execute(model);

        try {
            storage.saveSmartNus(model.getSmartNus());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }

        return commandResult;
    }

    @Override
    public ReadOnlySmartNus getSmartNus() {
        return model.getSmartNus();
    }

    @Override
    public ObservableList<Question> getFilteredQuestionList() {
        return model.getFilteredQuestionList();
    }

    @Override
    public Path getSmartNusFilePath() {
        return model.getSmartNusFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }
}
