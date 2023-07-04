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
            childNode.parent = node.parent;
            childNode.siblingRank = newIndex;
            node.parent.children.add(newIndex, childNode);
        }
        personToNode.remove(person);
    }

    public String getCEO() {
        return root.children.get(0).name;
    }
    
    private static class Node {

        final String name;
        Node parent;
        int siblingRank;
        final List<Node> children;

        public Node(String name, Node parent, int siblingRank, List<Node> children) {
            this.name = name;
            this.parent = parent;
            this.siblingRank = siblingRank;
            this.children = children;
        }

        private Node addChild(String name) {
            var node = new Node(name, this, children.size(), new ArrayList<>());
            children.add(node);
            return node;
        }
    }


    public static void main(String[] args) {
        var succession = new Succession("Hogan");
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
