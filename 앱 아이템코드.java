package com.example.real_fake;

import androidx.annotation.NonNull;

public class Item {
    private String num;
    private float fake;
    private float real;
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public float getFake() {
        return fake;
    }

    public void setFake(float fake) {
        this.fake = fake;
    }

    public float getReal() {
        return real;
    }

    public void setReal(float real) {
        this.real = real;
    }


    @NonNull
    @Override
    public String toString() {
        return "Item{" +
            "num: " + num +
                    ", fake: '" + fake + '\'' +
                    ", real: '" + real + '\'' +
                    '}';
        }

}

