import java.util.*;

class Conversions {

    private final Map<String, List<Edge>> unitToConversions;

    public Conversions() {
        this.unitToConversions = new HashMap<>();
    }

    public void addConversion(String from, String to, double scalar) {
        unitToConversions.computeIfAbsent(from, k -> new ArrayList<>()).add(new Edge(to, scalar));
        unitToConversions.computeIfAbsent(to, k -> new ArrayList<>()).add(new Edge(from, 1 / scalar));
    }

    public double convert(ConversionQuery query) {
        var q = new ArrayDeque<Edge>();
        var visited = new HashSet<String>();
        q.add(new Edge(query.from, query.qty));

        while (!q.isEmpty()) {
            var next = q.poll();
            if (next.unit.equals(query.to)) {
                return next.scalar;
            }
            if (visited.contains(next.unit)) {
                continue;
            }
            for (var edge: unitToConversions.getOrDefault(next.unit, Collections.emptyList())) {
                q.offer(new Edge(edge.unit, next.scalar * edge.scalar));
            }
            visited.add(next.unit);
        }

        throw new IllegalArgumentException("Impossible to convert: " + query);
    }

    private record ConversionQuery(String from, String to, double qty) {
    }

    private record Edge(String unit, double scalar) {
    }

    public static void main(String[] args) {
        var conversions = new Conversions();

        conversions.addConversion("ft", "in", 12.0);
        conversions.addConversion("yd", "ft", 3.0);
        conversions.addConversion("cup", "oz", 8.0);
        conversions.addConversion("pint", "cup", 12.0);

        System.out.println("72.0 == " + conversions.convert(new ConversionQuery("yd", "in", 2)));
        System.out.println("2.0 == " + conversions.convert(new ConversionQuery("in", "yd", 72)));
    }

}
