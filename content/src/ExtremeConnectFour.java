import java.util.*;

class ExtremeConnectFour {

    enum Color {
        RED, BLACK;
    }

    private static final int WIN_COUNT = 4;
    private static final List<Color> RED_WIN = List.of(Color.RED, Color.RED, Color.RED, Color.RED);
    private static final List<Color> BLACK_WIN = List.of(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK);

    private final Map<Integer, List<Color>> board = new HashMap<>();


    Optional<Color> move(int columnIndex, Color chipColor) {
        // move
        board.computeIfAbsent(columnIndex, k -> new ArrayList<>()).add(0, chipColor);

        // Check vertical
        if (board.get(columnIndex).equals(RED_WIN)) {
            return Optional.of(Color.RED);
        }

        if (board.get(columnIndex).equals(BLACK_WIN)) {
            return Optional.of(Color.BLACK);
        }

        // Check horizontal
        return Optional.empty();
    }

    Chip get(int column, int row) {

    }
    
    public static void main(String[] args) {
        var board = new ExtremeConnectFour();
        board.move(0, Color.RED);
        board.move(0, Color.RED);
        board.move(0, Color.RED);
        System.out.println(board.move(0, Color.RED));
    }
}

/*
Ideas:

pattern match
*/
