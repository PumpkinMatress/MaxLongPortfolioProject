import components.standard.Standard;

/**
 * Kernel interface for the PrintQueue component. A PrintQueue models a
 * first-in-first-out queue of 3D print jobs, where each job has a name,
 * estimated print time in minutes, and filament usage in grams. The component
 * also tracks the amount of filament currently available for printing.
 */
public interface PrintQueueKernel extends Standard<PrintQueue> {

    /**
     * Adds a job with the given name, estimated time, and filament usage to the
     * back of this queue.
     *
     * @param name
     *            the name of the print job
     * @param timeMinutes
     *            the estimated print time in minutes
     * @param filamentGrams
     *            the filament required in grams
     * @updates this
     * @requires name is not empty and timeMinutes >= 0 and filamentGrams >= 0.0
     * @ensures this.entries = #this.entries * <(name, timeMinutes,
     *          filamentGrams)> and this.availableFilament =
     *          #this.availableFilament
     */
    void addJob(String name, int timeMinutes, double filamentGrams);

    /**
     * Removes the front job from this queue and returns its name.
     *
     * @return the name of the front job
     * @updates this
     * @requires |this.entries| > 0
     * @ensures <pre>
     *          #this.entries = <(removeFirst, ?, ?)> * this.entries and
     *          this.availableFilament = #this.availableFilament
     *          </pre>
     */
    String removeFirst();

    /**
     * Reports the print time of the front job in this queue.
     *
     * @return the print time in minutes of the front job
     * @requires |this.entries| > 0
     * @ensures frontTime = the time of the first entry in this.entries
     */
    int frontTime();

    /**
     * Reports the filament usage of the front job in this queue.
     *
     * @return the filament usage in grams of the front job
     * @requires |this.entries| > 0
     * @ensures frontFilament = the filament of the first entry in this.entries
     */
    double frontFilament();

    /**
     * Adds the given amount of filament to the available supply.
     *
     * @param grams
     *            the amount of filament to add in grams
     * @updates this
     * @requires grams >= 0.0
     * @ensures this.availableFilament = #this.availableFilament + grams and
     *          this.entries = #this.entries
     */
    void loadFilament(double grams);

    /**
     * Reports how much filament is currently available.
     *
     * @return the amount of available filament in grams
     * @ensures availableFilament = this.availableFilament
     */
    double availableFilament();

    /**
     * Reports the number of jobs in this queue.
     *
     * @return the number of jobs
     * @ensures size = |this.entries|
     */
    int size();

}
