package hw3.hash;

import java.util.ArrayList;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        List<Oomage>[] buckets = new List[M];
        for (int i = 0; i < M; i++) {
            buckets[i] = new ArrayList<>();
            buckets[i].clear();
        }
        for (Oomage o : oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketNum].add(o);
        }

        int N = oomages.size();
        for (List<Oomage> b : buckets) {
            if (b.size() > 1.0 * N / 2.5 || b.size() < 1.0 * N / 50) {
                return false;
            }
        }
        return true;
    }
}
