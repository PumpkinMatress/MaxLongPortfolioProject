/**
 * Proof of concept for filament based printqueue.
 */
public class ProofOfConcept {

    /**
     * Array storing names of jobs.
     */
    private String[] jobNames;
    /** Array storing times of jobs. */
    private int[] jobTimes; // in minutes
    /** Array storing filament cost of jobs in grams. */
    private double[] jobFilament; // in grams
    /** size of queue. */
    private int size;
    /** available amount of filament. */
    private double availableFilament;

    /**
     * Constructor — initializes an empty queue with a max capacity.
     */
    public ProofOfConcept() {
        this.jobNames = new String[100];
        this.jobTimes = new int[100];
        this.jobFilament = new double[100];
        this.size = 0;
        this.availableFilament = 0.0;
    }

    // ---- Kernel Methods ----

    /**
     * Adds a job to the back of the queue.
     *
     * @param name
     * @param timeMinutes
     * @param filamentGrams
     */
    public void addJob(String name, int timeMinutes, double filamentGrams) {
        this.jobNames[this.size] = name;
        this.jobTimes[this.size] = timeMinutes;
        this.jobFilament[this.size] = filamentGrams;
        this.size++;
    }

    /**
     * Removes the front job from the queue and returns its name.
     *
     * @return String containing first job name.
     */
    public String removeFirst() {
        String name = this.jobNames[0];
        // shift everything forward
        for (int i = 0; i < this.size - 1; i++) {
            this.jobNames[i] = this.jobNames[i + 1];
            this.jobTimes[i] = this.jobTimes[i + 1];
            this.jobFilament[i] = this.jobFilament[i + 1];
        }
        this.size--;
        return name;
    }

    /**
     * Reports the filament usage of the front job.
     *
     * @return double containing filament cost of front job in grams.
     */
    public double frontFilament() {
        return this.jobFilament[0];
    }

    /**
     * Adds filament to the available supply.
     *
     * @param grams
     */
    public void loadFilament(double grams) {
        this.availableFilament += grams;
    }

    /**
     * Reports how much filament is currently available.
     *
     * @return double containing amt of available filament in grams.
     */
    public double availableFilament() {
        return this.availableFilament;
    }

    /**
     * Reports the number of jobs in the queue.
     *
     * @return int containing num of jobs in queue.
     */
    public int size() {
        return this.size;
    }

    // ---- Secondary Methods (built using kernel methods) ----

    /**
     * Checks if there's enough filament to print the front job.
     *
     * @return whether or not theres enough filament for the next job.
     */
    public boolean canPrintNext() {
        return this.availableFilament >= this.frontFilament();
    }

    /**
     * ks how many jobs are printable from the front of the queue considering
     * how much filament is available.
     *
     * @return how many jobs are printable from the front of the queue
     *         considering how much filament is available.
     */
    public int printableCount() {
        int count = 0;
        double filamentLeft = this.availableFilament;
        // cycle through using kernel methods
        int originalSize = this.size();
        for (int i = 0; i < originalSize; i++) {
            double needed = this.frontFilament();
            String name = this.removeFirst();
            if (filamentLeft >= needed) {
                count++;
                filamentLeft -= needed;
            }
            // add job back to preserve the queue
            this.addJob(name, 0, needed);
        }
        return count;
    }

    /**
     * Sums all print times in the queue.
     *
     * @return the sum of all print times in queue.
     */
    public int totalPrintTime() {
        int total = 0;
        int originalSize = this.size();
        for (int i = 0; i < originalSize; i++) {
            int time = this.jobTimes[0];
            double filament = this.jobFilament[0];
            String name = this.removeFirst();
            total += time;
            this.addJob(name, time, filament);
        }
        return total;
    }

    /**
     * Reports how much more filament is needed to print everything. Returns 0
     * if there's enough.
     *
     * @return how much more filament is necessary to print the entire queue,
     *         returning zero if enough.
     */
    public double filamentDeficit() {
        double totalNeeded = 0.0;
        int originalSize = this.size();
        for (int i = 0; i < originalSize; i++) {
            double filament = this.frontFilament();
            String name = this.removeFirst();
            totalNeeded += filament;
            this.addJob(name, 0, filament);
        }
        double deficit = totalNeeded - this.availableFilament;
        if (deficit < 0) {
            deficit = 0.0;
        }
        return deficit;
    }

    /**
     * @param args
     *            Main file that runs everything.
     */
    public static void main(String[] args) {
        ProofOfConcept queue = new ProofOfConcept();

        // Load some filament
        queue.loadFilament(500.0);
        System.out.println("Loaded 500g of filament.");
        System.out.println(
                "Available filament: " + queue.availableFilament() + "g");
        System.out.println();

        // Add some print jobs
        queue.addJob("Phone Stand", 45, 30.0);
        queue.addJob("Gear Set", 120, 150.0);
        queue.addJob("Helmet Visor", 200, 250.0);
        queue.addJob("Cable Clip", 15, 10.0);
        queue.addJob("Large Figurine", 360, 400.0);
        System.out.println("Added 5 print jobs.");
        System.out.println("Queue size: " + queue.size());

        System.out.println();

        // Check totals
        System.out.println(
                "Total print time: " + queue.totalPrintTime() + " minutes");
        System.out
                .println("Filament deficit: " + queue.filamentDeficit() + "g");
        System.out.println("Printable jobs with current filament: "
                + queue.printableCount());
        System.out.println();

        // Check if we can print the next job
        System.out.println("Can print next job? " + queue.canPrintNext());

        // Print the first job
        String completed = queue.removeFirst();
        System.out.println("Completed: " + completed);
        System.out.println("Remaining jobs: " + queue.size());
    }
}
