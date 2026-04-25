import components.Printqueue.PrintQueue;
import components.Printqueue.PrintQueue1L;

/**
 * Use case 1: Print shop manager.
 *
 * Demonstrates using PrintQueue to manage a day's worth of 3D print jobs at a
 * small print shop. Shows loading filament, adding customer orders, checking
 * what can be printed, and processing the queue.
 *
 * @author Max Long
 */
public final class PrintShopDemo {

    /**
     * Private constructor to prevent instantiation.
     */
    private PrintShopDemo() {
    }

    /**
     * Main method demonstrating PrintQueue as a print shop manager.
     *
     * @param args
     *            command line arguments (not used)
     */
    public static void main(String[] args) {

        PrintQueue queue = new PrintQueue1L();

        /*
         * Morning setup: load filament for the day
         */
        System.out.println("=== Morning Setup ===");
        queue.loadFilament(1000.0);
        System.out.println("Loaded 1000g of filament for today's print jobs.");
        System.out.println();

        /*
         * Add customer orders
         */
        System.out.println("=== Customer Orders ===");
        queue.addJob("Phone Case", 45, 25.0);
        queue.addJob("Desk Organizer", 180, 120.0);
        queue.addJob("Figurine Set", 300, 200.0);
        queue.addJob("Replacement Gear", 30, 15.0);
        queue.addJob("Lamp Shade", 240, 350.0);
        queue.addJob("Miniature Chess Set", 360, 400.0);
        System.out.println("Added 6 customer orders to the queue.");
        System.out.println("Queue: " + queue);
        System.out.println();

        /*
         * Check daily capacity
         */
        System.out.println("=== Daily Capacity Check ===");
        System.out.println("Total print time: " + queue.totalPrintTime()
                + " minutes (" + (queue.totalPrintTime() / 60) + " hours)");
        System.out.println(
                "Filament available: " + queue.availableFilament() + "g");
        System.out
                .println("Filament deficit: " + queue.filamentDeficit() + "g");
        System.out.println("Jobs printable with current filament: "
                + queue.printableCount());
        System.out.println();

        /*
         * Process the queue — print jobs until we run out of filament
         */
        System.out.println("=== Processing Jobs ===");
        while (queue.size() > 0 && queue.canPrintNext()) {
            double filamentUsed = queue.frontFilament();
            int time = queue.frontTime();
            String completed = queue.removeFirst();
            System.out.println("Printed: " + completed + " (" + time + " min, "
                    + filamentUsed + "g)");
        }
        System.out.println();

        /*
         * End of day summary
         */
        System.out.println("=== End of Day ===");
        System.out.println(
                "Filament remaining: " + queue.availableFilament() + "g");
        System.out.println("Jobs remaining in queue: " + queue.size());
        if (queue.size() > 0) {
            System.out.println("Remaining jobs need " + queue.filamentDeficit()
                    + "g more filament.");
        }
    }

}
