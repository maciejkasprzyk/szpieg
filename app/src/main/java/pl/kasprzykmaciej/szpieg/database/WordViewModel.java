package pl.kasprzykmaciej.szpieg.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

/**
 * The WordViewModel provides the interface between the UI and the data layer of the app,
 * represented by the repository
 */

public class WordViewModel extends AndroidViewModel {

    private final WordRepository mRepository;

    private final LiveData<List<Word>> mAllWords;

    private final List<Word> mAllWordsRaw;


    public WordViewModel(Application application) {
        super(application);
        mRepository = new WordRepository(application);
        mAllWords = mRepository.getAllWords();
        mAllWordsRaw = mRepository.getAllWordsRaw();
    }

    public LiveData<List<Word>> getAllWords() {
        return mAllWords;
    }

    public List<Word> getAllWordsRaw() {
        return mAllWordsRaw;
    }

    public void insert(Word word) {
        mRepository.insert(word);
    }

    public void deleteWord(Word word) {
        mRepository.deleteWord(word);
    }
}
