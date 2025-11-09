public class MoveParser {
    static Move parse(String s) {
        if (s.length() < 4) return null;
        int fc = s.charAt(0) - 'a';
        int fr = 8 - Character.getNumericValue(s.charAt(1));
        int tc = s.charAt(2) - 'a';
        int tr = 8 - Character.getNumericValue(s.charAt(3));
        Type promo = null;
        if (s.length() >= 5) {
            char c = Character.toLowerCase(s.charAt(4));
            switch (c) {
                case 'q': promo = Type.QUEEN; break;
                case 'r': promo = Type.ROOK; break;
                case 'b': promo = Type.BISHOP; break;
                case 'n': promo = Type.KNIGHT; break;
                default: return null;
            }
        }

        return new Move(new Position(fr, fc), new Position(tr, tc), promo);
    }

    public static Move parse(int fromR, int fromC, int toR, int toC) {
        // Validate coordinates
        if (fromR < 0 || fromR > 7 || fromC < 0 || fromC > 7 ||
                toR < 0 || toR > 7 || toC < 0 || toC > 7) {
            return null;
        }

        // Simply create the move
        return new Move(new Position(fromR, fromC), new Position(toR, toC), null);
    }

}
