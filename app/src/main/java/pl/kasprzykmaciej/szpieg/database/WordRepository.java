package pl.kasprzykmaciej.szpieg.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

/**
 * This class holds the implementation code for the
 * methods that interact with the database.
 * Using a repository allows us to group the implementation
 * methods together, and allows the WordViewModel to be
 * a clean interface between the rest of the app and the database.
 * <p>
 * For insert, update and delete, and longer-running queries,
 * you must run the database interaction methods in the background.
 * Typically, all you need to do to implement a database method
 * is to call it on the data access object (DAO),
 * in the background if applicable.
 */

class WordRepository {

    private final WordDao mWordDao;
    private final LiveData<List<Word>> mAllWords;
    private final List<Word> mAllWordsRaw;

    WordRepository(Application application) {
        WordRoomDatabase db = WordRoomDatabase.getDatabase(application);
        mWordDao = db.wordDao();
        mAllWords = mWordDao.getAllWords();
        mAllWordsRaw = mWordDao.getAllWordsRaw();
    }

    LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    List<Word> getAllWordsRaw() {
        return mAllWordsRaw;
    }

    public void insert(Word word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    // Need to run off main thread
    public void deleteWord(Word word) {
        new deleteWordAsyncTask(mWordDao).execute(word);
    }

    // Static inner classes below here to run database interactions
    // in the background.

    /**
     * Insert a word into the database.
     */
    private static class insertAsyncTask extends AsyncTask<Word, Void, Void> {

        private final WordDao mAsyncTaskDao;

        insertAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }


    /**
     * Delete a single word from the database.
     */
    private static class deleteWordAsyncTask extends AsyncTask<Word, Void, Void> {
        private final WordDao mAsyncTaskDao;

        deleteWordAsyncTask(WordDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Word... params) {
            mAsyncTaskDao.deleteWord(params[0]);
            return null;
        }
    }
}
