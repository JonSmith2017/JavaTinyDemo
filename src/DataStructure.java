import java.util.function.Function;

public class DataStructure {

    // Create an array
    private final static int SIZE = 15;
    private int[] arrayOfInts = new int[SIZE];

    private DataStructure() {
        // fill the array with ascending integer values
        for (int i = 0; i < SIZE; i++) {
            arrayOfInts[i] = i;
        }
    }

    private static boolean isEvenIndex(int index){
        return 0==index%2;
    }

    private static boolean isOddIndex(int index){
        return 1==index%2;
    }


    private void printEven() {

        // Print out values of even indices of the array
        DataStructureIterator iterator = this.new EvenIterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }

    interface DataStructureIterator extends java.util.Iterator<Integer> {
    }

    // Inner class implements the DataStructureIterator interface,
    // which extends the Iterator<Integer> interface

    private class EvenIterator implements DataStructureIterator {

        // Start stepping through the array from the beginning
        private int nextIndex = 0;

        public boolean hasNext() {

            // Check if the current element is the last in the array
            return (nextIndex <= SIZE - 1);
        }

        public Integer next() {

            // Record a value of an even index of the array
            Integer retValue = Integer.valueOf(arrayOfInts[nextIndex]);

            // Get the next even element
            nextIndex += 2;
            return retValue;
        }
    }

    private void print(DataStructureIterator iterator) {
        // Print out values of even indices of the array
//        DataStructureIterator iterator = this.new EvenIterator();
        while (iterator.hasNext()) {
            System.out.print(iterator.next() + " ");
        }
        System.out.println();
    }

    private void print(Function<Integer, Boolean> iterator){
        int nextIndex = 0;
        while(nextIndex<SIZE){
            if (iterator.apply(arrayOfInts[nextIndex]))
                System.out.print(arrayOfInts[nextIndex] + " ");
            nextIndex++;
        }
        System.out.println();
    }


    public static void main(String s[]) {

        // Fill the array with integer values and print out only
        // values of even indices
        DataStructure ds = new DataStructure();
        ds.printEven();

//         Invoke this method with an instance of the class EvenIterator so that it performs the same function as the method printEven.
        EvenIterator evenIterator = ds.new EvenIterator();
        ds.print(evenIterator);


//         Use an anonymous class as the method's argument instead of an instance of the interface DataStructureIterator.
        ds.print(new DataStructureIterator() {
            // Start stepping through the array from the beginning
            private int nextIndex = 1;

            public boolean hasNext() {

                // Check if the current element is the last in the array
                return (nextIndex <= SIZE - 1);
            }

            public Integer next() {

                // Record a value of an even index of the array
//                Integer retValue = Integer.valueOf(ds.arrayOfInts[nextIndex]);
                Integer retValue = ds.arrayOfInts[nextIndex];

                // Get the next even element
                nextIndex += 2;
                return retValue;
            }
        });
//print even
        ds.print(integer -> 0==integer%2);
//        print odd
        ds.print(integer -> 1==integer%2);

        ds.print(DataStructure::isEvenIndex);
        ds.print(DataStructure::isOddIndex);
    }
}