package seedu.address.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.model.ReadOnlySmartNus;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * Manages storage of AddressBook data in local storage.
 */
public class StorageManager implements Storage {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private SmartNusStorage smartNusStorage;
    private UserPrefsStorage userPrefsStorage;

    /**
     * Creates a {@code StorageManager} with the given {@code AddressBookStorage} and {@code UserPrefStorage}.
     */
    public StorageManager(SmartNusStorage smartNusStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.smartNusStorage = smartNusStorage;
        this.userPrefsStorage = userPrefsStorage;
    }

    // ================ UserPrefs methods ==============================

    @Override
    public Path getUserPrefsFilePath() {
        return userPrefsStorage.getUserPrefsFilePath();
    }

    @Override
    public Optional<UserPrefs> readUserPrefs() throws DataConversionException, IOException {
        return userPrefsStorage.readUserPrefs();
    }

    @Override
    public void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException {
        userPrefsStorage.saveUserPrefs(userPrefs);
    }


    // ================ AddressBook methods ==============================

    @Override
    public Path getSmartNusFilePath() {
        return smartNusStorage.getSmartNusFilePath();
    }

    @Override
    public Optional<ReadOnlySmartNus> readSmartNus() throws DataConversionException, IOException {
        return readSmartNus(smartNusStorage.getSmartNusFilePath());
    }

    @Override
    public Optional<ReadOnlySmartNus> readSmartNus(Path filePath) throws DataConversionException, IOException {
        logger.fine("Attempting to read data from file: " + filePath);
        return smartNusStorage.readSmartNus(filePath);
    }

    @Override
    public void saveSmartNus(ReadOnlySmartNus smartNus) throws IOException {
        saveSmartNus(smartNus, smartNusStorage.getSmartNusFilePath());
    }

    @Override
    public void saveSmartNus(ReadOnlySmartNus smartNus, Path filePath) throws IOException {
        logger.fine("Attempting to write to data file: " + filePath);
        smartNusStorage.saveSmartNus(smartNus, filePath);
    }

}
