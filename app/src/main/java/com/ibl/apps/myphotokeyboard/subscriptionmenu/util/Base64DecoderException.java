package com.ibl.apps.myphotokeyboard.subscriptionmenu.util;

/**
 * Exception thrown when encountering an invalid Base64 input character.
 *
 */
public class Base64DecoderException extends Exception {
    public Base64DecoderException() {
        super();
    }

    Base64DecoderException(String s) {
        super(s);
    }

    private static final long serialVersionUID = 1L;
}
