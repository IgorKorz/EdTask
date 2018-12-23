public interface Menu {
  void run();
  void printDictionary(Dictionary dictionary);
  void removeValue(Dictionary dictionary, String key);
  void getValue(Dictionary dictionary, String key);
  void putValue(Dictionary dictionary, String key, String value);
}
