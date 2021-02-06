import static org.junit.Assert.*;
import org.junit.Test;

public class TestArrayDequeGold {
    @Test
    public void testArrayDequeRandom() {
        ArrayDequeSolution<Integer> ads = new ArrayDequeSolution<>();
        StudentArrayDeque<Integer> sad = new StudentArrayDeque<>();
        String errorSequence = "";

        for (int i = 0; i < 100; i++) {
            double rng = StdRandom.uniform();

            if (rng < 0.25) {
                errorSequence += "addFirst(" + i + ")\n";
                ads.addFirst(i);
                sad.addFirst(i);
                assertEquals(errorSequence, ads.get(0), sad.get(0));
            } else if (rng < 0.5) {
                errorSequence += "addLast(" + i + ")\n";
                ads.addLast(i);
                sad.addLast(i);
                assertEquals(errorSequence, ads.get(ads.size() - 1), sad.get(sad.size() - 1));
            } else if (rng < 0.75) {
                if (ads.size() != 0 && sad.size() != 0) {
                    errorSequence += "removeFirst()\n";
                    Integer ads_item = ads.removeFirst();
                    Integer sad_item = sad.removeFirst();
                    assertEquals(errorSequence, ads_item, sad_item);
                }
            } else {
                if (ads.size() != 0 && sad.size() != 0) {
                    errorSequence += "removeLast()\n";
                    Integer ads_item = ads.removeLast();
                    Integer sad_item = sad.removeLast();
                    assertEquals(errorSequence, ads_item, sad_item);
                }
            }
        }
    }
}
