import components.Printqueue.PrintQueue;
import components.Printqueue.PrintQueue1L;

/**
 * Use case 2: A simple Printer class that uses PrintQueue as part of its
 * internal representation. Demonstrates how PrintQueue can be a building block
 * inside another component.
 *
 * @author Max Long
 */
public class Printer {

    /**
     * The queue of pending print jobs.
     */
    private PrintQueue jobQueue;

    /**
     * Number of jobs completed so far.
     */
    private int completedCount;

    /**
     * Constructor that creates a Printer with an empty job queue.
     */
    public Printer() {
        this.jobQueue = new PrintQueue1L();
        this.completedCount = 0;
    }

    /**
     * Submits a new print job to the printer.
     *
     * @param name
     *            the name of the job
     * @param timeMinutes
     *            estimated time in minutes
     * @param filamentGrams
     *            filament required in grams
     */
    public void submitJob(String name, int timeMinutes, double filamentGrams) {
        this.jobQueue.addJob(name, timeMinutes, filamentGrams);
    }

    /**
     * Prints the next job if there is enough filament.
     *
     * @return the name of the printed job, or "No job printed"
     */
    public String printNext() {
        if (this.jobQueue.size() == 0 || !this.jobQueue.canPrintNext()) {
            return "No job printed";
        }
        String name = this.jobQueue.removeFirst();
        this.completedCount++;
        return name;
    }

    /**
     * Reports the number of pending jobs.
     *
     * @return the number of jobs waiting
     */
    public int pendingJobs() {
        return this.jobQueue.size();
    }

    /**
     * Reports the number of completed jobs.
     *
     * @return the number of jobs printed so far
     */
    public int completedJobs() {
        return this.completedCount;
    }

    /**
     * Main method demonstrating the Printer class.
     *
     * @param args
     *            command line arguments (not used)
     */
    public static void main(String[] args) {
        Printer printer = new Printer();
        printer.jobQueue.loadFilament(200.0);

        printer.submitJob("Bracket", 20, 15.0);
        printer.submitJob("Enclosure", 90, 80.0);
        printer.submitJob("Full Helmet", 480, 600.0);

        System.out.println("Pending: " + printer.pendingJobs());

        String result = printer.printNext();
        while (!result.equals("No job printed")) {
            System.out.println("Printed: " + result);
            result = printer.printNext();
        }

        System.out.println("Completed: " + printer.completedJobs());
        System.out.println("Still pending: " + printer.pendingJobs());
    }

}
