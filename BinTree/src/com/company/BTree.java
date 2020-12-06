package com.company;

public class BTree {

    private Node root;

    class Node{
        int value;
        Node left;
        Node right;

        Node(int x){
            this.value = x;
        }
    }

    private class NodePos{
        int value;
        int offset;
        int level;

        NodePos(int x, int offset, int level){
            this.value = x;
            this.offset = offset;
            this.level = level;
        }
    }

    private NodePos[] map;
    private int count;
    private int i;
    private int maxLevel;

    void forward(Node root){
        if(root != null){
            System.out.print(root.value + " ");
            forward(root.left);
            forward(root.right);
        }
    }

    void symmetric(Node root){
        if(root != null){
            symmetric(root.left);
            System.out.print(root.value + " ");
            symmetric(root.right);
        }
    }

    void backward(Node root){
        if(root != null){
            backward(root.left);
            backward(root.right);
            System.out.print(root.value + " ");
        }
    }

    void add(int value){
        Node new_node = new Node(value);
        if(this.root == null){
            this.root = new_node;
            return;
        }
        Node curr_node = this.root;
        Node prev_node = null;
        while(curr_node != null){
            prev_node = curr_node;
            if(value > curr_node.value)
                curr_node = curr_node.right;
            else if(value < curr_node.value)
                curr_node = curr_node.left;
            else{
                System.out.println("Element is ignored.");
                return;
            }
        }
        if(value > prev_node.value)
            prev_node.right = new_node;
        else
            prev_node.left = new_node;
    }

    Node search(int value){
        Node curr_node = this.root;
        while(curr_node != null){
            if(value == curr_node.value)
                return curr_node;
            if(value > curr_node.value)
                curr_node = curr_node.right;
            else
                curr_node = curr_node.left;
        }
        return  null;
    }

    boolean remove(int value){
        Node curr_node = this.root;
        Node parent_node = null;
        while(curr_node != null && curr_node.value != value) {
            parent_node = curr_node;
            if (value > curr_node.value)
                curr_node = curr_node.right;
            else
                curr_node = curr_node.left;
        }
        if(curr_node == null)
            return false;
        if(parent_node == null){
            if(curr_node.left == null && curr_node.right == null){
                this.root = null;
                return true;
            }
            if(curr_node.left == null || curr_node.right == null){
                this.root = curr_node.left == null ?  curr_node.right : curr_node.left;
                return true;
            }
            Node replacement = curr_node.left;
            while (replacement.right != null)
                replacement = replacement.right;
            remove(replacement.value);
            this.root = replacement;
            replacement.left = curr_node.left;
            replacement.right = curr_node.right;
            return true;
        }
        else{
            if(curr_node.left == null && curr_node.right == null){
                if(curr_node.value > parent_node.value)
                    parent_node.right = null;
                else
                    parent_node.left = null;
                return true;
            }
            if(curr_node.left == null || curr_node.right == null){
                if(curr_node.value > parent_node.value)
                    parent_node.right = curr_node.left == null ?  curr_node.right : curr_node.left;
                else
                    parent_node.left = curr_node.left == null ?  curr_node.right : curr_node.left;
                return true;
            }
            Node replacement = curr_node.left;
            while (replacement.right != null)
                replacement = replacement.right;
            remove(replacement.value);
            if(curr_node.value > parent_node.value)
                parent_node.right = replacement;
            else
                parent_node.left = replacement;
            replacement.left = curr_node.left;
            replacement.right = curr_node.right;
            return true;
        }
    }

    private int count(Node root){
        if(root != null){
            count++;
            int left = count(root.left) + 1;
            int right = count(root.right) + 1;
            return Math.max(right, left);
        }
        return 0;
    }

    private void offset(int n){
        for(int i = 0; i < n; i++)
            System.out.print("   ");
    }

    void print(){
        System.out.println();
        if(root == null){
            System.out.println("Empty");
            return;
        }
        count = 0;
        i = 0;
        maxLevel = count(this.root);
        map = new NodePos[count];
        loadMap(this.root, 0, (int)Math.pow(2, maxLevel - 1) - 1);
        sort(map);
        offset(map[0].offset);
        System.out.print(map[0].value);
        for (int i = 1; i < map.length; i++) {
            if (map[i - 1].level != map[i].level) {
                System.out.println();
                offset(map[i].offset);
            }
            else
                offset(map[i].offset - map[i - 1].offset);
            System.out.print(map[i].value);
        }
        System.out.println();
    }

    private void loadMap(Node root, int level, int offset){
        if(root != null){
            level++;
            map[i] = new NodePos(root.value, offset, level);
            i++;
            int nextOffset = (int)Math.pow(2, maxLevel - level - 1);
            loadMap(root.left, level, offset - nextOffset);
            loadMap(root.right, level, offset + nextOffset);
        }

    }

    private boolean compare(NodePos a, NodePos b) {
        if (a.level > b.level)
            return true;
        return a.level == b.level && a.offset > b.offset;
    }

    private void sort(NodePos[] map) {
        for (int i = map.length - 1; i >= 1; i--)
            for (int j = 0; j < i; j++)
            {
                if (compare(map[j], map[j + 1]))
                {
                    NodePos temp = map[j];
                    map[j] = map[j + 1];
                    map[j + 1] = temp;
                }
            }
    }

    public Node getRoot(){
        return this.root;
    }

}
