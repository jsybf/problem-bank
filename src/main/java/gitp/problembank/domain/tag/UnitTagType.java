package gitp.problembank.domain.tag;

public enum UnitTagType {
    BIG(0),
    MEDIUM(1),
    SMALL(2);

    public final int depth;

    private UnitTagType(int depth) {
        this.depth = depth;
    }
}
