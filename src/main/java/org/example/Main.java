package org.example;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private static AtomicInteger atomicCounterFor3 = new AtomicInteger(0);
    private static AtomicInteger atomicCounterFor4 = new AtomicInteger(0);
    private static AtomicInteger atomicCounterFor5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread thread1 = new Thread(() -> {
            for (String text : texts) {
                if (asNiceAsAAA(text)) {
                    counterSelector(text.length());
                }
            }
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            for (String text : texts) {
                if (asNiceAsABBA(text) && !asNiceAsAAA(text)) {
                    counterSelector(text.length());
                }
            }
        });
        thread2.start();

        Thread thread3 = new Thread(() -> {
            for (String text : texts) {
                if (asNiceAsAACCC(text)) {
                    counterSelector(text.length());
                }
            }
        });
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();

        System.out.println("Красивых слов с длиной 3: " + atomicCounterFor3.get() + " шт.");
        System.out.println("Красивых слов с длиной 4: " + atomicCounterFor4.get() + " шт.");
        System.out.println("Красивых слов с длиной 5: " + atomicCounterFor5.get() + " шт.");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean asNiceAsAAA(String text) {
        boolean isNiceAAA = false;
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) == text.charAt(0)) {
                isNiceAAA = true;
            } else {
                return false;
            }
        }
        return isNiceAAA;
    }
    public static boolean asNiceAsABBA(String text) {
        boolean isNiceABBA = false;
        for (int i = 0; i < text.length() / 2; i++) {
            if (text.charAt(i) == text.charAt(text.length() - 1 - i)) {
                isNiceABBA = true;
            } else {
                return false;
            }
        }
        return isNiceABBA;
    }
    public static boolean asNiceAsAACCC(String text) {
        boolean isNiceAACCC = false;
        for (int i = 1; i < text.length(); i++) {
            if (text.charAt(i) >= text.charAt(i - 1)) {
                isNiceAACCC = true;
            } else {
                return false;
            }
        }
        return isNiceAACCC;
    }
    public static void counterSelector(int length) {
        switch (length) {
            case 3: atomicCounterFor3.getAndIncrement();
            case 4: atomicCounterFor4.getAndIncrement();
            case 5: atomicCounterFor5.getAndIncrement();
        }
    }
}