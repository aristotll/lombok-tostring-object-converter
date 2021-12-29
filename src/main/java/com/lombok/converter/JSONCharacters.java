package com.lombok.converter;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum JSONCharacters {
    CURLY_BRACES_START('(', '{'),
    CURLY_BRACES_END(')', '}'),
    COLON('=', ':');

    private final char oldChar;
    private final char newChar;
}
