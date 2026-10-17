package application;

public class AVLNode {

    Contact data;
    AVLNode left;
    AVLNode right;
    int height;

    public AVLNode(Contact data) {
        this.data = data;
        this.left = null;
        this.right = null;
        this.height = 1;
    }
}
