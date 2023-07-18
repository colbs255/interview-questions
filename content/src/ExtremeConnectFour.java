import java.util.*;

class ExtremeConnectFour {

    enum Color {
        RED, BLACK;
    }

    private static final int WIN_COUNT = 4;
    private static final Map<Color, List<Color>> WIN_CONDITIONS = Map.of(
        Color.RED, List.of(Color.RED, Color.RED, Color.RED, Color.RED),
        Color.BLACK, List.of(Color.BLACK, Color.BLACK, Color.BLACK, Color.BLACK) 
    );

    private final Map<Integer, List<Color>> board = new HashMap<>();

    public Optional<Color> move(int columnIndex, Color chipColor) {
        // Place chip
        board.computeIfAbsent(columnIndex, k -> new ArrayList<>()).add(0, chipColor);

        // Check vertical
        List<Color> column = board.getOrDefault(columnIndex, new ArrayList<>());
        List<Color> truncatedColumn = column.subList(0, Math.min(WIN_COUNT, column.size()));
        if (WIN_CONDITIONS.get(chipColor).equals(truncatedColumn)) {
            return Optional.of(chipColor);
        }

        // Check horizontal
        for (int r = 0; r < column.size(); r++) {
            int leftBound = columnIndex - WIN_COUNT + 1;
            int rightBound = columnIndex + WIN_COUNT - 1;
            List<Color> row = range(r, leftBound, rightBound);
            for (var entry: WIN_CONDITIONS.entrySet()) {
                // Check if a win is inside our row
                if (Collections.indexOfSubList(row, entry.getValue()) != -1) {
                    return Optional.of(entry.getKey());
                }
            }
        }
        
        return Optional.empty();
    }

    private List<Color> range(int rowIndex, int leftBound, int rightBound) {
        var row = new ArrayList<Color>();
        for (int c = leftBound; c <= rightBound; c++) {
            row.add(get(c, rowIndex));
        }
        return row;
    }

    private Color get(int columnIndex, int rowIndex) {
        List<Color> column = board.getOrDefault(columnIndex, new ArrayList<>());
        if (column.size() <= rowIndex) {
            return null;
        }
        return column.get(rowIndex);
    }
    
    public static void main(String[] args) {
        var board = new ExtremeConnectFour();
        System.out.println(board.move(1, Color.RED));
        System.out.println(board.move(2, Color.RED));
        System.out.println(board.move(3, Color.RED));
        System.out.println(board.move(4, Color.RED));
    }
}
