/**
 * Abstract class implementing the secondary methods of {@code PrintQueue} using
 * only kernel methods and Standard methods. Also implements {@code toString}
 * and {@code equals} from {@code Object}.
 */
public abstract class PrintQueueSecondary implements PrintQueue {

    /*
     * Object methods toString and equals must be implemented using only kernel
     * methods since we do not have access to the rep.
     */

    /**
     * Returns a string representation of this PrintQueue.
     */
    @Override
    public String toString() {
        String result = "<";
        int originalSize = this.size();
        for (int i = 0; i < originalSize; i++) {
            int time = this.frontTime();
            double filament = this.frontFilament();
            String name = this.removeFirst();
            if (i > 0) {
                result = result + ", ";
            }
            result = result + "(" + name + ", " + time + ", " + filament + ")";
            this.addJob(name, time, filament);
        }
        result = result + ">";
        result = result + "\nFilament available: " + this.availableFilament()
                + "g";
        return result;
    }

    /**
     * Checks if this PrintQueue is equal to the given object, with equal being
     * if two PrintQueues have the same jobs, order and available filament.
     *
     *
     * @param obj
     *            the object to compare to
     * @return true if obj is a PrintQueue with the same contents
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PrintQueue)) {
            return false;
        }
        PrintQueue other = (PrintQueue) obj;

        // Check sizes
        if (this.size() != other.size()) {
            return false;
        }
        // Check available filament
        if (Double.compare(this.availableFilament(),
                other.availableFilament()) != 0) {
            return false;
        }
        // Check each job
        boolean equal = true;
        int originalSize = this.size();
        for (int i = 0; i < originalSize; i++) {
            int thisTime = this.frontTime();
            double thisFilament = this.frontFilament();
            String thisName = this.removeFirst();

            int otherTime = other.frontTime();
            double otherFilament = other.frontFilament();
            String otherName = other.removeFirst();

            if (!thisName.equals(otherName) || thisTime != otherTime
                    || Double.compare(thisFilament, otherFilament) != 0) {
                equal = false;
            }
            this.addJob(thisName, thisTime, thisFilament);
            other.addJob(otherName, otherTime, otherFilament);
        }
        return equal;
    }

    /*
     * Secondary methods -------------------------------------------------------
     * All implemented using only kernel methods.
     */

    @Override
    public boolean canPrintNext() {
        assert this.size() > 0 : "Violation of: |this.entries| > 0";
        return this.availableFilament() >= this.frontFilament();
    }

    @Override
    public int printableCount() {
        int count = 0;
        double filamentLeft = this.availableFilament();
        int originalSize = this.size();
        for (int i = 0; i < originalSize; i++) {
            int time = this.frontTime();
            double needed = this.frontFilament();
            String name = this.removeFirst();
            if (filamentLeft >= needed) {
                count++;
                filamentLeft -= needed;
            }
            this.addJob(name, time, needed);
        }
        return count;
    }

    @Override
    public int totalPrintTime() {
        int total = 0;
        int originalSize = this.size();
        for (int i = 0; i < originalSize; i++) {
            int time = this.frontTime();
            double filament = this.frontFilament();
            String name = this.removeFirst();
            total += time;
            this.addJob(name, time, filament);
        }
        return total;
    }

    @Override
    public double filamentDeficit() {
        double totalNeeded = 0.0;
        int originalSize = this.size();
        for (int i = 0; i < originalSize; i++) {
            int time = this.frontTime();
            double filament = this.frontFilament();
            String name = this.removeFirst();
            totalNeeded += filament;
            this.addJob(name, time, filament);
        }
        double deficit = totalNeeded - this.availableFilament();
        if (deficit < 0) {
            deficit = 0.0;
        }
        return deficit;
    }

    @Override
    public boolean contains(String name) {
        assert name != null
                && !name.isEmpty() : "Violation of: name is not empty";
        boolean found = false;
        int originalSize = this.size();
        for (int i = 0; i < originalSize; i++) {
            int time = this.frontTime();
            double filament = this.frontFilament();
            String current = this.removeFirst();
            if (current.equals(name)) {
                found = true;
            }
            this.addJob(current, time, filament);
        }
        return found;
    }

    @Override
    public void removeUnprintable() {
        double available = this.availableFilament();
        int originalSize = this.size();
        for (int i = 0; i < originalSize; i++) {
            int time = this.frontTime();
            double filament = this.frontFilament();
            String name = this.removeFirst();
            if (filament <= available) {
                this.addJob(name, time, filament);
            }
        }
    }

}
