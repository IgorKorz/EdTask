package model;

import controller.Dictionary;

public class DictionaryResult implements Result {
        private final String keyNotContainsMsg = "Key not contains!";
        private final String  keyIsTooShortMsg = "Key is too short!";
        private final String keyIsTooLongMsg = "Key is too long!";
        private final String keyNotMatchMsg = "Key does not match the restrictions!";
        private String result;
        private Checker checker;
        private Dictionary dictionary;

        public DictionaryResult(Dictionary dictionary) {
            this.checker = new DictionaryChecker(dictionary);
            this.dictionary = dictionary;
        }

        public String getResult() {
            return result;
        }

        public void resultForRemove(String key, String value) {
            if (isValidKey(key)) result = key + "=" + value + " removed";
        }

        public void resultForGet(String key, String value) {
            if (isValidKey(key)) result = key + "=" + value;
        }

        public void resultForPut(String key, String value) {
            if (isValidKey(key)) result = key + "=" + value + " put";
        }

        private boolean isValidKey(String key) {
            if (!checker.isValidKey(key)) {
                if (key.length() < dictionary.getKeyLength()) {
                    result = keyIsTooShortMsg;

                    return false;
                }

                if (key.length() > dictionary.getKeyLength()) {
                    result = keyIsTooLongMsg;

                    return false;
                }

                if (!key.matches(dictionary.getKeyRegex())) {
                    result = keyNotMatchMsg;

                    return false;
                }

                if (!dictionary.getDictionary().containsKey(key)) {
                    result = keyNotContainsMsg;

                    return false;
                }
            }

            return true;
        }
    }