package application;

public class AVLTree {

    private AVLNode root;

    // ارتفاع العقدة
    private int height(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    // الأكبر بين رقمين
    private int max(int a, int b) {
        if (a > b) {
            return a;
        }
        return b;
    }

    // فرق الارتفاع يسار ناقص يمين
    private int balanceFactor(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }
    
 // دوران يمين
    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode t2 = x.right;

        x.right = y;
        y.left = t2;

        y.height = max(height(y.left), height(y.right)) + 1;
        x.height = max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // دوران يسار
    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode t2 = y.left;

        y.left = x;
        x.right = t2;

        x.height = max(height(x.left), height(x.right)) + 1;
        y.height = max(height(y.left), height(y.right)) + 1;

        return y;
    }
    
 // insert public
    public void insert(Contact contact) {
        root = insert(root, contact);
    }

    // insert helper
    private AVLNode insert(AVLNode node, Contact contact) {
        // لو ما في node نحط هون
        if (node == null) {
            return new AVLNode(contact);
        }

        // نقارن الاسم case-insensitive
        int compare = contact.getFullName().compareToIgnoreCase(node.data.getFullName());
        if (compare < 0) {
            node.left = insert(node.left, contact);
        } else if (compare > 0) {
            node.right = insert(node.right, contact);
        } else {
            // الاسم موجود مش نضيفه
            return node;
        }
        // نحدث الارتفاع
        node.height = max(height(node.left), height(node.right)) + 1;
        // نحسب الـ balance factor
        int bf = balanceFactor(node);

        
        
        // LL
        if (bf > 1 && contact.getFullName().compareToIgnoreCase(node.left.data.getFullName()) < 0) {
            return rotateRight(node);
        }
        // RR
        if (bf < -1 && contact.getFullName().compareToIgnoreCase(node.right.data.getFullName()) > 0) {
            return rotateLeft(node);
        }
        // LR
        if (bf > 1 && contact.getFullName().compareToIgnoreCase(node.left.data.getFullName()) > 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        // RL
        if (bf < -1 && contact.getFullName().compareToIgnoreCase(node.right.data.getFullName()) < 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }
    
 // delete public
    public void delete(String fullName) {
        root = delete(root, fullName);
    }

    // delete helper
    private AVLNode delete(AVLNode node, String fullName) {

        // ما لقيناش
        if (node == null) {
            return null;
        }
        int compare = fullName.compareToIgnoreCase(node.data.getFullName());
        // نبحث يسار
        if (compare < 0) {
            node.left = delete(node.left, fullName);
        }
        // نبحث يمين
        else if (compare > 0) {
            node.right = delete(node.right, fullName);
        }
        // لقيناه
        else {
            // عقدة بدون أولاد أو ولد واحد
            if (node.left == null || node.right == null) {
                if (node.left != null) {
                    node = node.left;
                } else {
                    node = node.right;
                }
            }
            // عقدة عندها ولدين
            else {
                AVLNode successor = minValueNode(node.right);
                node.data = successor.data;
                node.right = delete(node.right, successor.data.getFullName());
            }
        }
        // لو صارت null بعد الحذف
        if (node == null) {
            return null;
        }
        // نحدث الارتفاع
        node.height = max(height(node.left), height(node.right)) + 1;
        int bf = balanceFactor(node);
        // LL
        if (bf > 1 && balanceFactor(node.left) >= 0) {
            return rotateRight(node);
        }
        // LR
        if (bf > 1 && balanceFactor(node.left) < 0) {
            node.left = rotateLeft(node.left);
            return rotateRight(node);
        }
        // RR
        if (bf < -1 && balanceFactor(node.right) <= 0) {
            return rotateLeft(node);
        }
        // RL
        if (bf < -1 && balanceFactor(node.right) > 0) {
            node.right = rotateRight(node.right);
            return rotateLeft(node);
        }
        return node;
    }

    // أصغر قيمة يسار يسار
    private AVLNode minValueNode(AVLNode node) {
        AVLNode current = node;
        while (current.left != null) {
            current = current.left;
        }
        return current;
    }
    
 // search
    public Contact search(String fullName) {
        return search(root, fullName);
    }

    private Contact search(AVLNode node, String fullName) {
        if (node == null) {
            return null;
        }

        int compare = fullName.compareToIgnoreCase(node.data.getFullName());
        if (compare == 0) {
            return node.data;
        } else if (compare < 0) {
            return search(node.left, fullName);
        } else {
            return search(node.right, fullName);
        }
    }

    // inOrder - مرتبة A-Z
    public void inOrder(StringBuilder sb) {
        inOrder(root, sb);
    }

    private void inOrder(AVLNode node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        inOrder(node.left, sb);
        sb.append(node.data.toString()).append("\n");
        inOrder(node.right, sb);
    }

    // عدد الـ contacts
    public int count() {
        return count(root);
    }

    private int count(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return 1 + count(node.left) + count(node.right);
    }

    // ارتفاع الشجرة
    public int getHeight() {
        return height(root) - 1;
    }
    
 // للحفظ بالملف
    public void inOrderForSave(StringBuilder sb) {
        inOrderForSave(root, sb);
    }

    private void inOrderForSave(AVLNode node, StringBuilder sb) {
        if (node == null) {
            return;
        }
        inOrderForSave(node.left, sb);
        sb.append(node.data.getFullName()).append(";").append(node.data.getPhoneNumber()).append(";")
          .append(node.data.getEmail()).append(";").append(node.data.getCategory()).append("\n");
        inOrderForSave(node.right, sb);
    }
}