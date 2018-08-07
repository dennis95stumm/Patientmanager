package de.thm.stumm.kis;

import de.thm.Sha1;

public class Main {
    public static void main(String[] args) {
        System.out.println(new Sha1().getHash("Test"));
    }
}
