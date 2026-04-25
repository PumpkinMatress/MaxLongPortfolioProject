/**
 * Enhanced interface for the PrintQueue component. Provides secondary methods
 * that are layered on top of the kernel methods. All methods in this interface
 * can be implemented using only the kernel methods and Standard methods.
 */
public interface PrintQueue extends PrintQueueKernel {

    /**
     * Says whether theres enough available filament to print the front job in
     * queue.
     *
     * @return true if available filament is greater than or equal to the
     *         filament required by the front job, false otherwise
     * @requires |this.entries| > 0
     * @ensures canPrintNext = (this.availableFilament >= filament of first
     *          entry in this.entries)
     */
    boolean canPrintNext();

    /**
     * Reports how many jobs, can be printed using current available filament.
     *
     * @return the number of consecutive jobs from the front that can be printed
     * @ensures <pre>
     *          printableCount is the largest n such that the sum of filament
     *          for the first n entries in this.entries <= this.availableFilament
     *          </pre>
     */
    int printableCount();

    /**
     * Reports the total print time of queue.
     *
     * @return the sum of all print times in minutes
     * @ensures totalPrintTime = sum of time for all entries in this.entries
     */
    int totalPrintTime();

    /**
     * Reports how much more filament is needed to print queue, returning 0 if
     * theres enough.
     *
     * @return the filament deficit in grams, or 0 if sufficient
     * @ensures <pre>
     *          if total filament of all entries > this.availableFilament then
     *            filamentDeficit = total filament - this.availableFilament
     *          else
     *            filamentDeficit = 0
     *          </pre>
     */
    double filamentDeficit();

    /**
     * Reports whether this queue contains a job with the given name.
     *
     * @param name
     *            the name to search for
     * @return true if a job with the given name exists in the queue
     * @requires name is not empty
     * @ensures contains = (there exists an entry in this.entries whose name
     *          equals the given name)
     */
    boolean contains(String name);

    /**
     * Removes all jobs from this queue whose filament usage exceeds the
     * currently available filament.
     *
     * @updates this
     * @ensures <pre>
     *          this.entries is #this.entries with all entries removed whose
     *          filament > this.availableFilament and
     *          this.availableFilament = #this.availableFilament
     *          </pre>
     */
    void removeUnprintable();

}
