
import java.util.HashMap;

public class main2 {
    public static void main(String[] args) {
        Terminal t = new Terminal(new Folder("/", "", null));
        try {
            t.ls();
            t.mkdir("anurag1");
            t.pwd();
            t.cd("anurag1/");
            t.mkdir("anurag2");
            t.ls();
            t.pwd();
            t.cd("/../");
            t.pwd();
            t.cdWildcard("/*/anurag2");
            t.pwd();
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
}

/**
 * File System API Implementation

Design an in-memory file system that mimics basic Linux directory operations.

Required APIs:

mkdir(dirname: string)
pwd()
cd(path: string)
Functional requirements

mkdir(dirname)
Creates a new directory inside the current working directory.

pwd()
Returns the absolute path of the current working directory.

cd(path)
Changes the current working directory.

Path rules

If the path starts with /, it represents an absolute path from root
Otherwise it is a relative path from the current directory
The path may contain a wildcard *.

* can match:

the current directory (.)
the parent directory (..)
any child directory
The APIs should behave similarly to Linux directory navigation commands.
 * 
 */


class Terminal {
    Folder currentFolder;
    Folder root;

    public Terminal(Folder f){
        this.currentFolder =f;
        this.root =f;
    }


    public void ls(){
        currentFolder.ls();
    }

    public void pwd(){
        StringBuilder sb = new StringBuilder("/");
        if(currentFolder == root) {
            System.out.println("/");
            return;
        }

        FileSystem curr = currentFolder;
        // users/anurag/temp/
        while(curr != root && curr.getParent() != null){
            sb.insert(0,"/" + curr.name());
            curr = curr.getParent();
        }
        System.out.println(sb.toString());
    }

    public void cd(String path) throws  Exception{
        String[] arr = path.split("/");
        FileSystem tempDirectory = path.startsWith("/") ? root : currentFolder;
        for(int i=0; i<arr.length; i++){
            if(arr[i].equals("") || arr[i].equals(".")){
                continue;
            }

            if(arr[i].equals("..")){
                tempDirectory = tempDirectory == root ? root : tempDirectory.getParent();
                continue;
            }

            if(arr[i].equals("*")){
                //mathc current , parent , children

            }

            FileSystem newDirectory = ((Folder)tempDirectory).children.getOrDefault(arr[i], null);

            if(newDirectory == null || !newDirectory.isFolder() ){
                throw new Exception ("invalid path");
            }
            tempDirectory = newDirectory;

        }

        currentFolder = (Folder) tempDirectory;
    }

    public void cdWildcard(String path) throws Exception{
        String[] arr = path.split("/");
        FileSystem tempDirectory = path.startsWith("/") ? root : currentFolder;
        FileSystem destinationDirectory = dfs(0,arr,tempDirectory);
        if(destinationDirectory == null){
            throw new Exception("invlid apth");

        }

        currentFolder = (Folder) destinationDirectory;
    }

    public FileSystem dfs(int index, String[] arr, FileSystem tempDirectory){
        if(tempDirectory == null) return null;
        if(!tempDirectory.isFolder() && index>=arr.length) return null;

        for(int i=index; i<arr.length; i++){
            if(arr[i].equals("") || arr[i].equals(".")){
                continue;
            }

            if(arr[i].equals("..")){
                tempDirectory = tempDirectory == root ? root : tempDirectory.getParent();
                continue;
            }

            if(arr[i].equals("*")){
                //mathc current , parent , children
                FileSystem curDir = dfs(i+1, arr, tempDirectory);
                if(curDir != null ){
                   return curDir;
                }
                curDir = dfs(i+1, arr, tempDirectory.getParent());
                if(curDir != null){
                    return curDir;
                }
                
                for(FileSystem child : ((Folder) (tempDirectory)).children.values()){
                    if(child.isFolder()){
                        curDir = dfs(i+1, arr, child);
                        if(curDir != null) return curDir;
                    } 
                }
                return curDir;

            }

            FileSystem newDirectory = ((Folder)tempDirectory).children.getOrDefault(arr[i], null);

            if(newDirectory == null || !newDirectory.isFolder() ){
                return null;
            }
            tempDirectory = newDirectory;

        }
        return tempDirectory;
    }

    public void mkdir(String name){
        currentFolder.children.put(name, new Folder(name, "", currentFolder));
    }


}

interface FileSystem{
    public FileSystem getParent();
    public void ls();
    public String name();
    public boolean isFolder();

}



class File implements FileSystem{
    String name;
    String data;
    boolean isFolder;
    FileSystem parent;
    public File(String name, String data, FileSystem parent){
        this.name = name;
        this.data = data;
        this.isFolder = false;
        this.parent= parent;
    }
     public void ls(){
        System.out.println(name);
     }
    public String name(){
        return name;
    }
    public boolean isFolder(){
        return isFolder;
    }
    public FileSystem getParent(){
        return this.parent;
    }
}

class Folder implements FileSystem{
    String name;
    HashMap<String, FileSystem> children;
    boolean isFolder;
    FileSystem parent;
    public Folder(String name, String data, FileSystem parent){
        this.name = name;
        this.children = new HashMap<>();
        this.isFolder = true;
        this.parent= parent;
    }
    public void ls(){
        for(FileSystem fs : children.values()){
            System.out.println(fs.name());
        }
    }
    public String name(){
        return name;
    }
    public boolean isFolder(){
        return isFolder;
    }
    public FileSystem getParent(){
        return this.parent;
    }
}