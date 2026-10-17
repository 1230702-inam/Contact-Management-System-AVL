package application;

public class ContactManager {

    private CategoryLinkedList list;

    public ContactManager() {
        list = new CategoryLinkedList();
        list.addCategory("Family");
        list.addCategory("Friend");
        list.addCategory("Colleague");
        list.addCategory("Client");
        list.addCategory("Other");
    }

    public boolean addContact(Contact c) {
        return list.addContact(c);
    }

    public boolean deleteContact(String name, String category) {
        return list.deleteContact(name, category);
    }

    public Contact search(String name) {
        return list.searchContact(name);
    }

    public boolean update(String name, String phone, String email, String category) {
        return list.updateContact(name, phone, email, category);
    }

    public String printAll() {
        return list.getAllContacts();
    }

    public String filterByCategory(String category) {
        return list.getContactsByCategory(category);
    }

    public int countByCategory(String category) {
        return list.countByCategory(category);
    }

    public int totalCount() {
        return list.totalCount();
    }

    public int treeHeight(String category) {
        return list.getTreeHeight(category);
    }
    
 // للحفظ
    public void getAllForSave(StringBuilder sb) {
        list.getAllForSave(sb);
    }
}