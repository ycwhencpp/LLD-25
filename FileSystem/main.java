import java.util.*;



public class main {

    public static void main(String[] args) {
        FileSystem fs = new FileSystem();
        try {
            fs.addFolder("/anurag/");
            fs.addFolder("/anurag/two/");
            fs.addFile("anurag.txt", "anurag is here");
            fs.addFolder("hehe/");
            fs.addFile("anurag/two/hero.txt", "heri file");
            fs.addFile("anurag/two/hero", "meow file");
            fs.addFolder("anurag/two/hero");

            fs.viewFile("anurag.txt");
            // fs.viewFolder("");
            fs.viewFolder("/anurag/");
            fs.viewFolder("/anurag/two");

            fs.viewFile("anurag/two/hero.txt");
            fs.viewFile("anurag/two/hero");
        } catch (Exception e) {
            // 
        }
       


    }

}


class FileSystem {
    Folder root;
    Folder currentFolder;

    public FileSystem(){
        this.root = new Folder("/", null);
        this.currentFolder = root;
    }

    public Folder resolvePath(String path) throws  Exception{
        // cd a/b/c/d.
        //cd a/b/.(/d.txt
        //cd ../../../

        String[] arr = path.split("/");

        int len = arr.length;

        Folder curr = path.startsWith("/") ? root : currentFolder;

        for (int i = 0; i < len-1; i++) { //except last
            if(arr[i].equals("")) continue;
            if(arr[i].equals(".")){
                    throw new Exception ("Invalid Command");
            }
            if(arr[i].equals("..") && curr.parent != null){ //move up;
                curr = (Folder) curr.parent;
                continue;
            }
            if(!curr.structure.containsKey(arr[i]) || curr.type != NodeType.FOLDER){
                throw new Exception ("Folder doesnt exsit " + arr[i]);

            }
            curr = (Folder) curr.structure.get(arr[i]);
        }

        return curr;

        


    }

    public void addFile(String path, String data){
        try {
            Folder folder = resolvePath(path);

            String fileName = verifyName(path, folder, NodeType.FILE);

            File file = new File(fileName, data);

            folder.structure.put(fileName, file);

        } catch (Exception e) {
            
        }
    }

    public void addFolder(String path){
        try {
        Folder folder = resolvePath(path);
        String folderName = verifyName(path, folder, NodeType.FOLDER);
        folder.structure.put(folderName, new Folder(folderName, folder));
        } catch(Exception e) {
            
        }

    }
    public void removeFolder(String path){
        try {
        Folder folder = resolvePath(path);
        String name = extractName(path);
        folder.structure.remove(name);
        } catch(Exception e) {

        }
    }
    public void removeFile(String path){
        try {
        Folder folder = resolvePath(path);
        String name = extractName(path);
        folder.structure.remove(name);
        } catch(Exception e) {
            
        }
    }

    public void viewFile(String path){
        try {
        Folder folder = resolvePath(path);
        String name = extractName(path);
        if(!folder.structure.containsKey(name) || folder.structure.get(name).type != NodeType.FILE){
            throw new Exception("Invalid File Name");
        }
        File file = (File) folder.structure.get(name);
        System.out.println(file.data);
        } catch(Exception e) {
            
        }
    }
    public void viewFolder(String path){
        try {
            Folder folder = resolvePath(path);
            String name = extractName(path);
            if(!folder.structure.containsKey(name) || folder.structure.get(name).type != NodeType.FOLDER){
                throw new Exception("Invalid Folder Name");
            }
            Folder ff = (Folder) folder.structure.get(name);
            System.out.println(ff.structure);
            } catch(Exception e) {
                
        }
    }

    public String verifyName(String path, Folder folder, NodeType nodeType) throws Exception{
        String name = extractName(path);

        if(folder.structure.containsKey(name)){
            throw new Exception("duplicate " + nodeType +  " name");
        }
        return name;
    }

    public String extractName(String path){
        if (path.equals("/")) return ""; // Safe fallback for root
        String[] arr = path.split("/");
        if (arr.length == 0) return "";
        return arr[arr.length-1];
    }


}

class Node{
    String name;
    NodeType type;
    Node parent;

    public Node(String Name, NodeType nodeType, Node parent){
        this.name = Name;
        this.type = nodeType;
        this.parent = parent;
    }
}

enum NodeType{
    FILE, 
    FOLDER
}

class File extends Node {
    String data;

    public File(String name, String data){
        super(name, NodeType.FILE, null);
        this.data = data;
    }
}

class Folder extends Node{

    HashMap<String, Node> structure = new HashMap<>();

    public Folder(String name, Node parent){
        super(name, NodeType.FOLDER, parent);
    }


}