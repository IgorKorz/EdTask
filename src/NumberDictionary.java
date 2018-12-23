import java.nio.file.Path;
import java.io.IOException;

public class NumberDictionary extends Dictionary {
  private final int keyLength = 5;
  private final String invalidKeyMsg = "Invalid key!";
  private final String keyNotContainsMsg = "Key not contains!";
  private final char lowerKeyBound = '0';
  private final char upperKeyBound = '9';

  public NumberDictionary(Path file) throws IOException {
    super(file);
  }

  @Override
  public String remove(String key) throws DictionaryException {
    if (isInvalidKey(key)) throw new DictionaryException(invalidKeyMsg);

    if (!dictionary.contains(key)) throw new DictionaryException(invalidKeyMsg);

    return dictionary.remove(key);
  }

  @Override
  public String getValue(String key) throws DictionaryException {
    if (isInvalidKey(key)) throw new DictionaryException(invalidKeyMsg);

    if (!dictionary.contains(key)) throw new DictionaryException(invalidKeyMsg);

    return dictionary.get(key);
  }

  @Override
  public String put(String key, String value) throws DictionaryException {
    if (isInvalidKey(key)) throw new DictionaryException(invalidKeyMsg);

    if (!dictionary.contains(key)) throw new DictionaryException(invalidKeyMsg);

    return dictionary.put(key, value);
  }

  private boolean isInvalidKey(String key) {
    if (key.length() != keyLength) return false;

    return key.matches(String.format("[%c-%c]{%d}", lowerKeyBound, upperKeyBound, keyLength));
  }
}
