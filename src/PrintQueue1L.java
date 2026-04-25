import components.queue.Queue;
import components.queue.Queue1L;

/**
 * {@code PrintQueue} represented as three parallel {@code Queue}s and a
 * {@code double} for available filament, with implementations of primary
 * methods.
 *
 * @convention <pre>
 * |$this.names| = |$this.times| = |$this.filaments| and
 * $this.availableFilament >= 0.0
 * </pre>
 * @correspondence <pre>
 * this = (entries: the i-th elements of $this.names, $this.times,
 * and $this.filaments together represent the i-th print job,
 * availableFilament: $this.availableFilament)
 * </pre>
 *
 * @author Max Long
 *
 */
public class PrintQueue1L extends PrintQueueSecondary {

    /**
     * Queue of job names.
     */
    private Queue<String> names;

    /**
     * Queue of job print times in mins.
     */
    private Queue<Integer> times;

    /**
     * Queue of job filament usage in grams.
     */
    private Queue<Double> filaments;

    /**
     * Available filament in grams.
     */
    private double availableFilament;

    /**
     * Creator of initial representation.
     */
    private void createNewRep() {
        this.names = new Queue1L<>();
        this.times = new Queue1L<>();
        this.filaments = new Queue1L<>();
        this.availableFilament = 0.0;
    }

    /*
     * Constructors -----------------------------------------------------------
     */

    /**
     * No-argument constructor.
     */
    public PrintQueue1L() {

        this.createNewRep();

    }

    /*
     * Standard methods -------------------------------------------------------
     */

    @Override
    public final PrintQueue newInstance() {
        try {
            return this.getClass().getConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new AssertionError(
                    "Cannot construct object of type " + this.getClass());
        }
    }

    @Override
    public final void clear() {
        this.createNewRep();
    }

    @Override
    public final void transferFrom(PrintQueue source) {
        assert source != null : "Violation of: source is not null";
        assert source != this : "Violation of: source is not this";
        assert source instanceof PrintQueue1L : ""
                + "Violation of: source is of dynamic type PrintQueue1L";
        /*
         * This cast cannot fail since the assert above wouldve stopped
         * execution.
         */
        PrintQueue1L localSource = (PrintQueue1L) source;
        this.names = localSource.names;
        this.times = localSource.times;
        this.filaments = localSource.filaments;
        this.availableFilament = localSource.availableFilament;
        localSource.createNewRep();
    }

    /*
     * Kernel methods ---------------------------------------------------------
     */

    @Override
    public final void addJob(String name, int timeMinutes,
            double filamentGrams) {
        assert name != null && !name.isEmpty() : ""
                + "Violation of: name is not empty";
        assert timeMinutes >= 0 : "Violation of: timeMinutes >= 0";
        assert filamentGrams >= 0.0 : "Violation of: filamentGrams >= 0.0";

        this.names.enqueue(name);
        this.times.enqueue(timeMinutes);
        this.filaments.enqueue(filamentGrams);
    }

    @Override
    public final String removeFirst() {
        assert this.names.length() > 0 : ""
                + "Violation of: |this.entries| > 0";

        this.times.dequeue();
        this.filaments.dequeue();
        return this.names.dequeue();
    }

    @Override
    public final int frontTime() {
        assert this.names.length() > 0 : ""
                + "Violation of: |this.entries| > 0";

        return this.times.front();
    }

    @Override
    public final double frontFilament() {
        assert this.names.length() > 0 : ""
                + "Violation of: |this.entries| > 0";

        return this.filaments.front();
    }

    @Override
    public final void loadFilament(double grams) {
        assert grams >= 0.0 : "Violation of: grams >= 0.0";

        this.availableFilament += grams;
    }

    @Override
    public final double availableFilament() {
        return this.availableFilament;
    }

    @Override
    public final int size() {
        return this.names.length();
    }

}
