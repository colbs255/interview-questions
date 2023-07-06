import java.util.*;

class Succession {

    private final Node root;
    private final Map<String, Node> nameToNode;

    public Succession(String founder) {
        this.root = new Node(null, null, 0, new ArrayList<>());
        this.nameToNode = new HashMap<>();

        Node founderNode = root.addChild(founder);
        nameToNode.put(founder, founderNode);
    }

    public void add(String parent, String child) {
        Node childNode = nameToNode.get(parent).addChild(child);
        nameToNode.put(child, childNode);
    }

    public void remove(String person) {
        Node nodeToRemove = nameToNode.get(person);
        // Remove the node from the children list of its parent
        nodeToRemove.parent.children.remove(nodeToRemove.siblingRank);
        nameToNode.remove(person);

        for (int childIndex = 0; childIndex < nodeToRemove.children.size(); childIndex++) {
            Node childNode = nodeToRemove.children.get(childIndex);
            int newChildRank = nodeToRemove.siblingRank + childIndex;
            var updatedNode = new Node(childNode.name, nodeToRemove.parent, newChildRank, childNode.children);

            // Update the node map since we are creating a new node
            nameToNode.put(childNode.name, updatedNode);
            // Promote the child up one level to the children of the nodeToRemove's parent, maintaining birth order
            nodeToRemove.parent.children.add(newChildRank, updatedNode);
        }
    }

    public String getCEO() {
        // We use a dummy root to make remove operations simpler, so the CEO is the first child of the root
        return root.children.get(0).name;
    }

    private record Node(String name, Node parent, int siblingRank, List<Node> children) {

        private Node addChild(String name) {
            Node childNode = new Node(name, this, children.size(), new ArrayList<>());
            children.add(childNode);
            return childNode;
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
