import java.nio.file.Path;
import java.io.IOException;

public class WordDictionary extends Dictionary {
  private final int keyLength = 4;
  private final String invalidKeyMsg = "Invalid key!";
  private final char lowerKeyBound = 'a';
  private final char upperKeyBound = 'z';

  public WordDictionary(Path file) throws IOException {
    super(file);
  }

  @Override
  public String remove(String key) throws DictionaryException {
    if (isInvalidKey(key)) throw new DictionaryException(invalidKeyMsg);

    return dictionary.remove(key);
  }

  @Override
  public String getValue(String key) throws DictionaryException {
    if (isInvalidKey(key)) throw new DictionaryException(invalidKeyMsg);

    return dictionary.get(key);
  }

  @Override
  public String put(String key, String value) throws DictionaryException {
    if (isInvalidKey(key)) throw new DictionaryException(invalidKeyMsg);

    return dictionary.put(key, value);
  }

  private boolean isInvalidKey(String key) {
    if (key.length() != keyLength) return false;

    return key.toLowerCase().matches(String.format("[%c-%c]{%d}", lowerKeyBound, upperKeyBound, keyLength));
  }
}
