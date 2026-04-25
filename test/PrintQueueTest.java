import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.Printqueue.PrintQueue;
import components.Printqueue.PrintQueue1L;

/**
 * JUnit test fixture for {@code PrintQueue} secondary methods implemented in
 * {@code PrintQueueSecondary}.
 */
public class PrintQueueTest {

    /**
     * Helper method to create a fresh PrintQueue for testing.
     *
     * @return Fresh PrintQueue to be used for testing.
     */
    private PrintQueue createFromNewInstance() {
        return new PrintQueue1L();
    }

    /** Tests canPrintNext when there is enough filament. */
    @Test
    public void testCanPrintNextTrue() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("Test", 10, 50.0);
        q.loadFilament(100.0);
        assertEquals(true, q.canPrintNext());
        // Verify state unchanged
        PrintQueue qCopy = this.createFromNewInstance();
        qCopy.addJob("Test", 10, 50.0);
        qCopy.loadFilament(100.0);
        assertEquals(qCopy, q);
    }

    /** Tests canPrintNext when there is not enough filament. */
    @Test
    public void testCanPrintNextFalse() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("Test", 10, 200.0);
        q.loadFilament(100.0);
        assertEquals(false, q.canPrintNext());
    }

    /** Tests canPrintNext when filament is exactly enough. */
    @Test
    public void testCanPrintNextExact() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("Test", 10, 100.0);
        q.loadFilament(100.0);
        assertEquals(true, q.canPrintNext());
    }

    /** Tests printableCount on an empty queue. */
    @Test
    public void testPrintableCountEmpty() {
        PrintQueue q = this.createFromNewInstance();
        q.loadFilament(500.0);
        assertEquals(0, q.printableCount());
    }

    /** Tests printableCount when all jobs are printable. */
    @Test
    public void testPrintableCountAllPrintable() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("A", 10, 50.0);
        q.addJob("B", 20, 50.0);
        q.addJob("C", 30, 50.0);
        q.loadFilament(500.0);
        assertEquals(3, q.printableCount());
        // Verify state unchanged
        assertEquals(3, q.size());
    }

    /** Tests printableCount when only some jobs are printable. */
    @Test
    public void testPrintableCountPartial() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("A", 10, 50.0);
        q.addJob("B", 20, 50.0);
        q.addJob("C", 30, 50.0);
        q.loadFilament(120.0);
        assertEquals(2, q.printableCount());
    }

    /** Tests printableCount when no jobs are printable. */
    @Test
    public void testPrintableCountNone() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("A", 10, 200.0);
        q.loadFilament(50.0);
        assertEquals(0, q.printableCount());
    }

    /** Tests totalPrintTime on an empty queue. */
    @Test
    public void testTotalPrintTimeEmpty() {
        PrintQueue q = this.createFromNewInstance();
        assertEquals(0, q.totalPrintTime());
    }

    /** Tests totalPrintTime with a single job. */
    @Test
    public void testTotalPrintTimeSingleJob() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("Test", 45, 10.0);
        assertEquals(45, q.totalPrintTime());
        // Verify state unchanged
        assertEquals(1, q.size());
    }

    /** Tests totalPrintTime with multiple jobs. */
    @Test
    public void testTotalPrintTimeMultipleJobs() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("A", 30, 10.0);
        q.addJob("B", 60, 20.0);
        q.addJob("C", 90, 30.0);
        assertEquals(180, q.totalPrintTime());
    }

    /** Tests filamentDeficit when there is enough filament. */
    @Test
    public void testFilamentDeficitEnoughFilament() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("A", 10, 50.0);
        q.addJob("B", 20, 50.0);
        q.loadFilament(200.0);
        assertEquals(0.0, q.filamentDeficit(), 0.001);
    }

    /** Tests filamentDeficit when there is not enough filament. */
    @Test
    public void testFilamentDeficitNotEnough() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("A", 10, 100.0);
        q.addJob("B", 20, 200.0);
        q.loadFilament(150.0);
        assertEquals(150.0, q.filamentDeficit(), 0.001);
        // Verify state unchanged
        assertEquals(2, q.size());
    }

    /** Tests filamentDeficit when no filament is loaded. */
    @Test
    public void testFilamentDeficitNoFilament() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("A", 10, 100.0);
        assertEquals(100.0, q.filamentDeficit(), 0.001);
    }

    /** Tests filamentDeficit on an empty queue. */
    @Test
    public void testFilamentDeficitEmpty() {
        PrintQueue q = this.createFromNewInstance();
        assertEquals(0.0, q.filamentDeficit(), 0.001);
    }

    /** Tests contains when the job exists. */
    @Test
    public void testContainsTrue() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("Phone Stand", 45, 30.0);
        q.addJob("Gear Set", 120, 150.0);
        assertEquals(true, q.contains("Gear Set"));
        // Verify state unchanged
        assertEquals(2, q.size());
    }

    /** Tests contains when the job does not exist. */
    @Test
    public void testContainsFalse() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("Phone Stand", 45, 30.0);
        assertEquals(false, q.contains("Helmet"));
    }

    /** Tests contains on an empty queue. */
    @Test
    public void testContainsEmpty() {
        PrintQueue q = this.createFromNewInstance();
        assertEquals(false, q.contains("Anything"));
    }

    /** Tests contains on the first job in the queue. */
    @Test
    public void testContainsFirstJob() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("First", 10, 5.0);
        q.addJob("Second", 20, 10.0);
        assertEquals(true, q.contains("First"));
    }

    /** Tests contains on the last job in the queue. */
    @Test
    public void testContainsLastJob() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("First", 10, 5.0);
        q.addJob("Last", 20, 10.0);
        assertEquals(true, q.contains("Last"));
    }

    /** Tests removeUnprintable when no jobs need to be removed. */
    @Test
    public void testRemoveUnprintableNoneRemoved() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("A", 10, 50.0);
        q.addJob("B", 20, 50.0);
        q.loadFilament(500.0);
        q.removeUnprintable();
        assertEquals(2, q.size());
    }

    /** Tests removeUnprintable when all jobs need to be removed. */
    @Test
    public void testRemoveUnprintableAllRemoved() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("A", 10, 200.0);
        q.addJob("B", 20, 300.0);
        q.loadFilament(50.0);
        q.removeUnprintable();
        assertEquals(0, q.size());
    }

    /** Tests removeUnprintable when only some jobs need to be removed. */
    @Test
    public void testRemoveUnprintableSomeRemoved() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("Small", 10, 20.0);
        q.addJob("Big", 60, 500.0);
        q.addJob("Medium", 30, 50.0);
        q.loadFilament(100.0);
        q.removeUnprintable();
        assertEquals(2, q.size());
        // Verify the right jobs remain
        String first = q.removeFirst();
        assertEquals("Small", first);
        String second = q.removeFirst();
        assertEquals("Medium", second);
    }

}