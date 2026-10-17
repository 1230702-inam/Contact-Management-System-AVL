package application;

public class CategoryNode {

    String categoryName;
    AVLTree tree;
    CategoryNode next;

    public CategoryNode(String categoryName) {
        this.categoryName = categoryName;
        this.tree = new AVLTree();
        this.next = null;
    }
}