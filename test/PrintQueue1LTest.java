import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.Printqueue.PrintQueue;
import components.Printqueue.PrintQueue1L;

/**
 * JUnit test fixture for {@code PrintQueue1L} kernel and Standard methods.
 */
public class PrintQueue1LTest {

    /**
     * Helper method to create a fresh PrintQueue for testing.
     *
     * @return Fresh PrintQueue to be used for testing.
     */
    private PrintQueue createFromNewInstance() {
        return new PrintQueue1L();
    }

    /**
     * Tests for an empty constructor.
     */
    @Test
    public void testConstructorEmpty() {
        PrintQueue q = this.createFromNewInstance();
        assertEquals(0, q.size());
        assertEquals(0.0, q.availableFilament(), 0.001);
    }

    /**
     * Tests adding one job.
     */
    @Test
    public void testAddJobOneJob() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("Test", 10, 5.0);
        assertEquals(1, q.size());
    }

    /** Tests adding multiple jobs. */
    @Test
    public void testAddJobMultipleJobs() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("Job1", 10, 5.0);
        q.addJob("Job2", 20, 10.0);
        q.addJob("Job3", 30, 15.0);
        assertEquals(3, q.size());
    }

    /**
     * Tests removing single job with removeFirst.
     */
    @Test
    public void testRemoveFirstSingleJob() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("Test", 10, 5.0);
        String name = q.removeFirst();
        assertEquals("Test", name);
        assertEquals(0, q.size());
    }

    /**
     * Tetss removing first order.
     */
    @Test
    public void testRemoveFirstOrder() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("First", 10, 5.0);
        q.addJob("Second", 20, 10.0);
        String name = q.removeFirst();
        assertEquals("First", name);
        assertEquals(1, q.size());
    }

    /**
     * Tests removing first all Jobs.
     */
    @Test
    public void testRemoveFirstAllJobs() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("A", 10, 5.0);
        q.addJob("B", 20, 10.0);
        String first = q.removeFirst();
        String second = q.removeFirst();
        assertEquals("A", first);
        assertEquals("B", second);
        assertEquals(0, q.size());
    }

    /**
     * Tests frontTime on a single job.
     */
    @Test
    public void testFrontTimeSingleJob() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("Test", 45, 5.0);
        int time = q.frontTime();
        assertEquals(45, time);
        // Verify state is unchanged
        PrintQueue qCopy = this.createFromNewInstance();
        qCopy.addJob("Test", 45, 5.0);
        assertEquals(qCopy, q);
    }

    /**
     * Tests frontTime on multiple Jobs.
     */
    @Test
    public void testFrontTimeMultipleJobs() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("First", 30, 5.0);
        q.addJob("Second", 60, 10.0);
        int time = q.frontTime();
        assertEquals(30, time);
    }

    /**
     * Tests frontFilament on a single job.
     */

    @Test
    public void testFrontFilamentSingleJob() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("Test", 10, 25.5);
        double filament = q.frontFilament();
        assertEquals(25.5, filament, 0.001);
        // Verify state is unchanged
        PrintQueue qCopy = this.createFromNewInstance();
        qCopy.addJob("Test", 10, 25.5);
        assertEquals(qCopy, q);
    }

    /** Tests frontFilament on several jobs. */
    @Test
    public void testFrontFilamentMultipleJobs() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("First", 10, 50.0);
        q.addJob("Second", 20, 100.0);
        double filament = q.frontFilament();
        assertEquals(50.0, filament, 0.001);
    }

    /** Tests using loadFilament one time. */
    @Test
    public void testLoadFilamentOnce() {
        PrintQueue q = this.createFromNewInstance();
        q.loadFilament(500.0);
        assertEquals(500.0, q.availableFilament(), 0.001);
    }

    /**
     * Tests using loadFilament multiple timnems.
     */
    @Test
    public void testLoadFilamentMultipleTimes() {
        PrintQueue q = this.createFromNewInstance();
        q.loadFilament(200.0);
        q.loadFilament(300.0);
        assertEquals(500.0, q.availableFilament(), 0.001);
    }

    /**
     * Tests using loadFilament with zero grams.
     */
    @Test
    public void testLoadFilamentZero() {
        PrintQueue q = this.createFromNewInstance();
        q.loadFilament(0.0);
        assertEquals(0.0, q.availableFilament(), 0.001);
    }

    /** Tests size on an empty queue. */

    @Test
    public void testSizeEmpty() {
        PrintQueue q = this.createFromNewInstance();
        assertEquals(0, q.size());
    }

    /** Tests size after using add and remove. */
    @Test
    public void testSizeAfterAddAndRemove() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("A", 10, 5.0);
        q.addJob("B", 20, 10.0);
        assertEquals(2, q.size());
        q.removeFirst();
        assertEquals(1, q.size());
    }

    /** Tests clear on a queue. */

    @Test
    public void testClear() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("Test", 10, 5.0);
        q.loadFilament(100.0);
        q.clear();
        assertEquals(0, q.size());
        assertEquals(0.0, q.availableFilament(), 0.001);
    }

    /** Tests creating a new instance. */

    @Test
    public void testNewInstance() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("Test", 10, 5.0);
        PrintQueue q2 = q.newInstance();
        assertEquals(0, q2.size());
        assertEquals(0.0, q2.availableFilament(), 0.001);
        // Original should be unchanged
        assertEquals(1, q.size());
    }

    /** Testing transferFrom. */
    @Test
    public void testTransferFrom() {
        PrintQueue q1 = this.createFromNewInstance();
        q1.addJob("Job1", 30, 50.0);
        q1.addJob("Job2", 60, 100.0);
        q1.loadFilament(500.0);

        PrintQueue q2 = this.createFromNewInstance();
        q2.transferFrom(q1);

        // q2 should have the data
        assertEquals(2, q2.size());
        assertEquals(500.0, q2.availableFilament(), 0.001);
        // q1 should be cleared
        assertEquals(0, q1.size());
        assertEquals(0.0, q1.availableFilament(), 0.001);
    }

    /** Tests for equals on empty queues. */

    @Test
    public void testEqualsEmpty() {
        PrintQueue q1 = this.createFromNewInstance();
        PrintQueue q2 = this.createFromNewInstance();
        assertEquals(q1, q2);
    }

    /** Tests for equals on queues with the same job added. */
    @Test
    public void testEqualsSameJobs() {
        PrintQueue q1 = this.createFromNewInstance();
        q1.addJob("Test", 10, 5.0);
        q1.loadFilament(100.0);

        PrintQueue q2 = this.createFromNewInstance();
        q2.addJob("Test", 10, 5.0);
        q2.loadFilament(100.0);

        assertEquals(q1, q2);
    }

    /** Tests for equals on queues with different jobs added. */
    @Test
    public void testEqualsDifferentJobs() {
        PrintQueue q1 = this.createFromNewInstance();
        q1.addJob("A", 10, 5.0);

        PrintQueue q2 = this.createFromNewInstance();
        q2.addJob("B", 20, 10.0);

        assertEquals(false, q1.equals(q2));
    }

    /** Tests toString on an empty queue. */
    @Test
    public void testToStringEmpty() {
        PrintQueue q = this.createFromNewInstance();
        String result = q.toString();
        assertEquals("<>\nFilament available: 0.0g", result);
    }

    /** Tests toString on a queue iwth one job. */
    @Test
    public void testToStringOneJob() {
        PrintQueue q = this.createFromNewInstance();
        q.addJob("Test", 10, 5.0);
        String result = q.toString();
        assertEquals("<(Test, 10, 5.0)>\nFilament available: 0.0g", result);
        // Verify state unchanged
        assertEquals(1, q.size());
    }

}
