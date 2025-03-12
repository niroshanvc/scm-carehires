package com.carehires.actions.agreements;

public enum RateType {

    NORMAL_RATE("Normal Rate"),
    SPECIAL_HOLIDAY_RATE("Special Holiday Rate"),
    BANK_HOLIDAY_RATE("Bank Holiday Rate"),
    FRIDAY_NIGHT_RATE("Friday Night Rate");

    private String name;

    RateType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
