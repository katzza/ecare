package com.ecare.domain;

public enum OptionType {
    BASE(0),
    CALLS(1),
    INTERNET(2),
    TRAVEL(3);

    private final int valueNumber;

    OptionType(int _valueNumber) {
        valueNumber = _valueNumber;
    }

    public int getValueNumber() {
        return valueNumber;
    }

}
