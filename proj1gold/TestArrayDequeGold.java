import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testArrayDequeRandom() {
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        String errorSequence = "";

        for (int i = 0; i < 1000; i++) {
            double rng = StdRandom.uniform();

            if (rng < 0.25) {
                errorSequence += "addFirst(" + i + ")\n";
                ads.addFirst(i);
                sad.addFirst(i);
                assertEquals(errorSequence, ads.size(), sad.size());
            } else if (rng < 0.5) {
                errorSequence += "addLast(" + i + ")\n";
                ads.addLast(i);
                sad.addLast(i);
                assertEquals(errorSequence, ads.size(), sad.size());
            } else if (rng < 0.75) {
                if (ads.size() != 0 && sad.size() != 0) {
                    errorSequence += "removeFirst()\n";
                    Integer adsItem = ads.removeFirst();
                    Integer sadItem = sad.removeFirst();
                    assertEquals(errorSequence, ads.size(), sad.size());
                    assertEquals(errorSequence, adsItem, sadItem);
                }
            } else {
                if (ads.size() != 0 && sad.size() != 0) {
                    errorSequence += "removeLast()\n";
                    Integer adsItem = ads.removeLast();
                    Integer sadItem = sad.removeLast();
                    assertEquals(errorSequence, ads.size(), sad.size());
                    assertEquals(errorSequence, adsItem, sadItem);
                }
            }
        }
    }
}
