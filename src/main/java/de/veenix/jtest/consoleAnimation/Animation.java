package de.veenix.jtest.consoleAnimation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.function.Supplier;

public class Animation {

    private final LinkedList<Frame> frames = new LinkedList<>();
    private int frameIndex = 0;

    public void animateLoop(Supplier<Boolean> callback) {
        animate(callback, false, true);
    }

    public void animate(Supplier<Boolean> callback, Boolean executeFirst) {
        animate(callback, executeFirst, false);
    }

    public void animate(Supplier<Boolean> callback) {
        animate(callback, false, false);
    }

    public void animate(Supplier<Boolean> callback, boolean executeFirst, boolean loop) {
        if(frames.isEmpty()) throw new IllegalStateException("Animation is empty add at least 1 frame");

        Thread thread = new Thread(() -> {
            while(frameIndex < frames.size() || loop) {
                if(loop && frameIndex == frames.size()) {
                    frameIndex = 0;
                }

                Frame currentFrame = frames.get(frameIndex++);

                if(executeFirst) {
                    if(!callback.get()) {
                        break;
                    }
                }

                System.out.printf("%s\r", currentFrame.getFrame());

                if(!executeFirst) {
                    if(!callback.get()) {
                        break;
                    }
                }
            }
        });

        thread.start();
    }

    private void playNextFrame() {
        if(frames.isEmpty()) throw new IllegalStateException("Animation is empty add at least 1 frame");
        if(!(frameIndex + 1 < frames.size())) {
            throw new ArrayIndexOutOfBoundsException("Found no Frame with index: " + frameIndex + 1);
        }
        Frame frame = frames.get(++frameIndex);
        System.out.printf("%s\r", frame.getFrame());
    }

    public void animate(long delay) {
        if(frames.isEmpty()) throw new IllegalStateException("Animation is empty add at least 1 frame");

        Thread thread = new Thread(() -> {
            try {
                while(frameIndex < frames.size()) {
                    Frame currentFrame = frames.get(frameIndex++);

                    System.out.printf("%s\r", currentFrame.getFrame());
                    Thread.sleep(delay);
                }
            } catch (InterruptedException ignored) { }
        });

        thread.start();
    }

    public void addFrame(Frame frame) {
        this.frames.add(frame);
    }

    public void addFrames(Frame... frames) {
        for (Frame frame : frames) {
            addFrame(frame);
        }
    }

    public Frame removeLastFrame() {
        return frames.removeLast();
    }

    public Frame removeFirstFrame() {
        return frames.removeFirst();
    }

    public Frame remove(int index) {
        return frames.remove(index);
    }

    public Frame getFrame(int index) {
        return frames.get(index);
    }

    static class Frame {
        private final String frame;
        private final ArrayList<Ref<?>> formatValues = new ArrayList<>();

        public Frame(String frame, Ref<?>... values) {
            this.frame = frame;
            formatValues.addAll(Arrays.asList(values));
        }

        public String getFrame() {
            return frame.formatted(formatValues.toArray());
        }
    }
}
