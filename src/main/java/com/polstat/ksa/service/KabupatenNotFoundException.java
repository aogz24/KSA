package com.polstat.ksa.service;

public class KabupatenNotFoundException extends RuntimeException {
    public KabupatenNotFoundException(Long message) {
        super(String.valueOf(message));
    }

    public String getIdKab() {
        return this.getIdKab();
    }
}

