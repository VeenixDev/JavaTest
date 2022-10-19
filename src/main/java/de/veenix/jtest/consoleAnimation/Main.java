package de.veenix.jtest.consoleAnimation;

import static de.veenix.jtest.consoleAnimation.Animation.Frame;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Animation anim = new Animation();
        Ref<Integer> value = new Ref<>(0);
        anim.addFrames(new Frame("[ | ]: %s / 100", value),
                new Frame("[ / ]: %s / 100", value),
                new Frame("[ - ]: %s / 100", value),
                new Frame("[ | ]: %s / 100", value),
                new Frame("[ \\ ]: %s / 100", value),
                new Frame("[ - ]: %s / 100", value));
        anim.animateLoop(() -> {
            if(value.getValue() == 100) return false;
            value.setValue(value.getValue() + 1);
            try { Thread.sleep(500); } catch (InterruptedException ignored) { }
            return true;
        });
    }
}
