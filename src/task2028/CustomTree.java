package task2028;

import java.io.Serializable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/* 
Построй дерево(1)
*/

public class CustomTree extends AbstractList<String> implements Cloneable, Serializable {
    Entry<String> root;
    List<Entry<String>> entries = new ArrayList<>();

    public CustomTree() {
        root = new Entry<>("0");
        root.parent = root;
        entries.add(root);
    }

    @Override
    public String get(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int size() {
        return entries.size() - 1;
    }

    public String set(int index, String element) {
        throw new UnsupportedOperationException();
    }

    public void add(int index, String element) {
        throw new UnsupportedOperationException();
    }

    public String remove(int index) {
        throw new UnsupportedOperationException();
    }

    public List<String> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    protected void removeRange(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    public boolean addAll(int index, Collection<? extends String> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(String s) {
        boolean added = false;
        Entry<String> newEntry = new Entry<String>(s);
        for (Entry<String> entry : entries) {
            if (entry.isAvailableToAddChildren()) {
                newEntry.parent = entry;
                entries.add(newEntry);
                if (entry.availableToAddLeftChildren) {
                    entry.leftChild = newEntry;
                    entry.availableToAddLeftChildren = false;
                } else {
                    entry.rightChild = newEntry;
                    entry.availableToAddRightChildren = false;
                }
                added = true;
                return true;
            }
        }
        if (added == false) {
            for (Entry<String> entry : entries) {
                if (entry.rightChild == null && entry.leftChild == null) {
                    entry.availableToAddRightChildren = true;
                    entry.availableToAddLeftChildren = true;
                }
            }
        }
        add(s);
        return true;
    }

    public String getParent(String s) {
        String parent = null;
        for (Entry<String> entry : entries) {
            if (entry.elementName.equals(s))
                parent = entry.parent.elementName;
        }
        return parent;
    }

    public boolean remove(Object o) {
        if (!(o instanceof String))
            throw new UnsupportedOperationException();

        String string = (String) o;
        for (Entry<String> entry : entries) {
            if (entry.elementName.equals(string)) {
                if (entry.leftChild == null && entry.rightChild == null) {
                    if (entry.parent.leftChild == entry)
                        entry.parent.leftChild = null;
                    if (entry.parent.rightChild == entry)
                        entry.parent.rightChild = null;
                    entries.remove(entry);
                    return true;
                } else {
                    removeChildren(entry);
                    return true;
                }
            }
        }

        return true;
    }
    public void removeChildren (Entry<String> entry) {
        if (entry.parent.leftChild == entry)
            entry.parent.leftChild = null;
        if (entry.parent.rightChild == entry)
            entry.parent.rightChild = null;

        if (entry.leftChild != null) {
            removeChildren(entry.leftChild);
        }
        if (entry.rightChild != null) {
            removeChildren(entry.rightChild);
        }
        if (entry.leftChild == null && entry.rightChild == null) {
            entries.remove(entry);
        }
    }

    static class Entry<T> implements Serializable {

        String elementName;
        boolean availableToAddLeftChildren, availableToAddRightChildren;
        Entry<T> parent, leftChild, rightChild;


        public Entry(String elementName) {
            this.elementName = elementName;
            availableToAddLeftChildren = true;
            availableToAddRightChildren = true;

        }

        public boolean isAvailableToAddChildren() {
            return availableToAddLeftChildren || availableToAddRightChildren;
        }
    }
}
