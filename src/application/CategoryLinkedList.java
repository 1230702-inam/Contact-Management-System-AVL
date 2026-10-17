package application;

public class CategoryLinkedList {
    private CategoryNode head;
    public CategoryLinkedList() {
        head = null;
    }
    // إضافة category جديدة للقائمة
    public void addCategory(String categoryName) {
        // لو القائمة فاضية
        if (head == null) {
            head = new CategoryNode(categoryName);
            return;
        }

        // نتأكد ما هي موجودة مسبقاً
        CategoryNode current = head;
        while (current != null) {
            if (current.categoryName.equalsIgnoreCase(categoryName)) {
                return;
            }
            current = current.next;
        }

        // نضيفها آخر القائمة
        current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = new CategoryNode(categoryName);
    }

    // البحث عن category معينة
    private CategoryNode findCategory(String categoryName) {
        CategoryNode current = head;
        while (current != null) {
            if (current.categoryName.equalsIgnoreCase(categoryName)) {
                return current;
            }
            current = current.next;
        }
        return null;
    }
    // إضافة contact للـ category الصح
 // إضافة contact مع التحقق من التكرار globally
    public boolean addContact(Contact contact) {
        // نتأكد ما في حدا بنفس الاسم بكل الـ categories
        CategoryNode current = head;
        while (current != null) {
            if (current.tree.search(contact.getFullName()) != null) {
                return false;
            }
            current = current.next;
        }
        // نضيفه للـ category الصح
        CategoryNode category = findCategory(contact.getCategory());
        if (category == null) {
            return false;
        }
        category.tree.insert(contact);
        return true;
    }

    // حذف contact
    public boolean deleteContact(String fullName, String categoryName) {
        CategoryNode category = findCategory(categoryName);
        if (category == null) {
            return false;
        }
        category.tree.delete(fullName);
        return true;
    }

    // البحث عن contact باسمه
    public Contact searchContact(String fullName) {
        CategoryNode current = head;
        while (current != null) {
            Contact found = current.tree.search(fullName);
            if (found != null) {
                return found;
            }
            current = current.next;
        }
        return null;
    }
    
 // عدد الـ contacts بكل category
    public int countByCategory(String categoryName) {
        CategoryNode category = findCategory(categoryName);
        if (category == null) {
            return 0;
        }
        return category.tree.count();
    }

    // مجموع كل الـ contacts
    public int totalCount() {
        int total = 0;
        CategoryNode current = head;
        while (current != null) {
            total += current.tree.count();
            current = current.next;
        }
        return total;
    }

    // كل الـ contacts مرتبة A-Z لكل category
    public String getAllContacts() {
        StringBuilder sb = new StringBuilder();
        CategoryNode current = head;
        while (current != null) {
            sb.append("=== ").append(current.categoryName).append(" ===\n");
            current.tree.inOrder(sb);
            current = current.next;
        }
        return sb.toString();
    }

    // كل الـ contacts تبع category معينة
    public String getContactsByCategory(String categoryName) {
        CategoryNode category = findCategory(categoryName);
        if (category == null) {
            return "Category not found.";
        }
        StringBuilder sb = new StringBuilder();
        category.tree.inOrder(sb);
        return sb.toString();
    }

    // ارتفاع شجرة category معينة
    public int getTreeHeight(String categoryName) {
        CategoryNode category = findCategory(categoryName);
        if (category == null) {
            return -1;
        }
        return category.tree.getHeight();
    }

    // تعديل contact
    public boolean updateContact(String fullName, String newPhone, String newEmail, String newCategory) {
        // نبحث عنه بكل الـ categories
        CategoryNode current = head;
        while (current != null) {
            Contact found = current.tree.search(fullName);
            if (found != null) {
                // لو تغيرت الـ category
                if (!found.getCategory().equalsIgnoreCase(newCategory)) {
                    current.tree.delete(fullName);
                    found.setPhoneNumber(newPhone);
                    found.setEmail(newEmail);
                    found.setCategory(newCategory);
                    addContact(found);
                } else {
                    found.setPhoneNumber(newPhone);
                    found.setEmail(newEmail);
                }
                return true;
            }
            current = current.next;
        }
        return false;
    }
 // للحفظ
    public void getAllForSave(StringBuilder sb) {
        CategoryNode current = head;
        while (current != null) {
            current.tree.inOrderForSave(sb);
            current = current.next;
        }
    }
}