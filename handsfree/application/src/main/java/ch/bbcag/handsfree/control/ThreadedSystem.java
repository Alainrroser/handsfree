package ch.bbcag.handsfree.control;

public abstract class ThreadedSystem {

    protected boolean running;

    public boolean isRunning() {
        return running;
    }

    public void start() {
        this.running = true;

        Thread thread = new Thread(this::run);
        thread.setDaemon(true);
        thread.start();
    }

    public void stop() {
        this.running = false;
    }

    protected abstract void run();

}
