package ch.bbcag.handsfree.control;

public abstract class ThreadedSystem {

    private boolean running;

    public final boolean isRunning() {
        return running;
    }

    public final void start() {
        this.running = true;

        Thread thread = new Thread(this::run);
        thread.setDaemon(true);
        thread.start();
    }

    public final void stop() {
        this.running = false;
    }

    protected abstract void run();

}
