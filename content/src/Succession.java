import java.util.*;

class Succession {

    private final Node root;
    private final Map<String, Node> personToNode;

    public Succession(String founder) {
        this.root = new Node(null, null, 0, new ArrayList<>());
        this.personToNode = new HashMap<>();

        var childNode = root.addChild(founder);
        personToNode.put(founder, childNode);
    }

    public void add(String parent, String child) {
        var childNode = personToNode.get(parent).addChild(child);
        personToNode.put(child, childNode);
    }

    public void remove(String person) {
        var node = personToNode.get(person);
        for (int childIndex = 0; childIndex < node.children.size(); childIndex++) {
            var childNode = node.children.get(childIndex);
            int newIndex = childIndex + node.siblingRank;
            var updatedNode = new Node(childNode.name, node.parent, newIndex, childNode.children);
            node.parent.children.add(newIndex, updatedNode);
        }
        personToNode.remove(person);
    }

    public String getCEO() {
        return root.children.get(0).name;
    }

    private static record Node(
        String name,
        Node parent,
        int siblingRank,
        List<Node> children
    ) {

        private Node addChild(String name) {
            var node = new Node(name, this, children.size(), new ArrayList<>());
            children.add(node);
            return node;
        }

        @Override
        public String toString() {
            return "Node{n:" + name + "p: " + parent + "r: " + siblingRank + children + "}";
        }
    }


    public static void main(String[] args) {
        var succession = new Succession("Hogan");
        System.out.println(succession.root);
        succession.add("Hogan", "Randal");
        succession.add("Hogan", "Liv");
        succession.remove("Hogan");
        System.out.println("Randal == " + succession.getCEO());

        succession.add("Randal", "Terry");
        succession.add("Liv", "Frank");
        succession.remove("Randal");
        System.out.println("Terry == " + succession.getCEO());

        succession.remove("Liv");
        System.out.println("Terry == " + succession.getCEO());

        succession.remove("Terry");
        System.out.println("Frank == " + succession.getCEO());
    }
}
