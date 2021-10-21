package seedu.smartnus.ui;

import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.smartnus.commons.core.GuiSettings;
import seedu.smartnus.commons.core.LogsCenter;
import seedu.smartnus.commons.core.theme.Theme;
import seedu.smartnus.logic.Logic;
import seedu.smartnus.logic.commands.CommandResult;
import seedu.smartnus.logic.commands.ListCommand;
import seedu.smartnus.logic.commands.exceptions.CommandException;
import seedu.smartnus.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Logic logic;
    private Stage primaryStage;

    // Independent Ui parts residing in this Ui container
    private QuestionListPanel questionListPanel;
    private NoteListPanel noteListPanel;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane questionListPanelPlaceholder;

    @FXML
    private StackPane noteListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());
        setTheme(logic.getTheme());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window with questions.
     */
    void fillInnerParts() {
        questionListPanel = new QuestionListPanel(logic.getFilteredQuestionList());

        // toggle visibility of noteList and questionList
        questionListPanelPlaceholder.setVisible(true);
        noteListPanelPlaceholder.setVisible(false);
        noteListPanelPlaceholder.managedProperty().bind(noteListPanelPlaceholder.visibleProperty());

        questionListPanelPlaceholder.getChildren().add(questionListPanel.getRoot());
        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getSmartNusFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Fills up all the placeholders of this window with questions.
     */
    void fillInnerPartsWithQuestions() {
        questionListPanel = new QuestionListPanel(logic.getFilteredQuestionList());

        // toggle visibility of noteList and questionList
        questionListPanelPlaceholder.setVisible(true);
        noteListPanelPlaceholder.setVisible(false);
        noteListPanelPlaceholder.managedProperty().bind(noteListPanelPlaceholder.visibleProperty());

        questionListPanelPlaceholder.getChildren().add(questionListPanel.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getSmartNusFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Fills up all the placeholders of this window with notes.
     */
    void fillInnerPartsWithNotes() {
        noteListPanel = new NoteListPanel(logic.getFilteredNoteList());

        // toggle visibility of noteList and questionList
        noteListPanelPlaceholder.setVisible(true);
        questionListPanelPlaceholder.setVisible(false);
        questionListPanelPlaceholder.managedProperty().bind(questionListPanelPlaceholder.visibleProperty());

        noteListPanelPlaceholder.getChildren().add(noteListPanel.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    private void setTheme(Theme theme) {
        Scene scene = primaryStage.getScene();
        String pathToTheme = FXML_FILE_FOLDER;
        String themeCssFile = pathToTheme + theme.getThemeCssFile();
        String extensionCssFile = pathToTheme + theme.getExtensionsCssFile();
        scene.getStylesheets().remove(1, scene.getStylesheets().size());
        scene.getStylesheets().add(themeCssFile);
        scene.getStylesheets().add(extensionCssFile);
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Starts the quiz.
     */
    @FXML
    private void handleQuizStart() {
        // TODO: Check if quiz has any questions, throw error if doesn't
        QuizWindow quizWindow = new QuizWindow(primaryStage, logic);
        quizWindow.show();
        quizWindow.fillInnerParts();
        quizWindow.loadQuiz();
    }

    public QuestionListPanel getQuestionListPanel() {
        return questionListPanel;
    }

    public NoteListPanel getNoteListPanel() {
        return noteListPanel;
    }

    /**
     * Lists the specified items.
     */
    @FXML
    private void handleListCommand() {
        if (ListCommand.isDisplayQuestions()) {
            fillInnerPartsWithQuestions();
        } else {
            fillInnerPartsWithNotes();
        }
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.smartnus.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isQuizStart()) {
                handleQuizStart();
            }

            if (commandResult.isList()) {
                handleListCommand();
            }

            setTheme(logic.getTheme());

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
