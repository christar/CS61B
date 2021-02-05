public class OffByN implements CharacterComparator {
    private final int offset;

    public OffByN(int N) {
        this.offset = N;
    }

    @Override
    public boolean equalChars(char x, char y) {
        return (x - y == this.offset) || (y - x == this.offset);
    }
}
