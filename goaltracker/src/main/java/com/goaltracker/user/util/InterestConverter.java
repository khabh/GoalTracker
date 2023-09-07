package com.goaltracker.user.util;

import com.goaltracker.user.domain.Interest;

public class InterestConverter {
    private InterestConverter() {}

    public static Interest toInterest(String tagName) {
        Interest interest = new Interest();
        interest.setTagName(tagName);

        return interest;
    }
}
