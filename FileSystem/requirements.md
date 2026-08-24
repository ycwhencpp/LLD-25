# question 
Design an in-memory file system that supports creating files and directories, navigating paths, and basic file operations.

primary capabilities 
error handling 
scope boundaries 

# requirement gathering 
1. should create, remove files and folders 
2. should navigate to files and folders with valid path
3. should create nested folders as well 
4. should return error for invalid command
5. no same name file or folder should be there at same level
6. how to visit folders ?? command or api or what. ??
7. support basic command 
    - ls : (display file and folder )
    - cd {{folder name}} : move one level inside
    - cat {{file name}} : display file data
    - cd {{../}} : move out one level 
    - touch {{file name}} : create file 
    - mkdir/ rmdir {{folder name}} : remove folder with all his data 
6. out of scope :
    - ui
    - command instead expose API for now
    - inputing data in files 
7. extensibility
    - concurrent creation/removal of file/folder 
    - add data inside files 


/** 
Thinking how to store data 

- root
    - folder 1
        - file 1
        - fole 2 
        - folder 1
            - file 1
    - folder 2
        - file 1
    - file 1
question is how to store ?(like trie ds)
- folder 
    - map of folder with name and value as folder
    - map of file name with data 
    - name
    - id
    - parent folder (since we need to trace back as well)

**/

# entities (look for noun or something that standout)
1. file
2. folder 
3. file system 
4. Node 

- instead of 2 maps, what if i store name to something(that both file and folder extend)



# class design (define state and methods it exposes)
1. file System 
    - map of name to node 
    - isRoot boolean 
    - RootFolder Folder (/)
    - currentFolder folder
    - idk what else this do 

    - resolvePath(string) -> folder 

    # these should be delegated right /or should be here 
    # since inside folder hard to track multiple joins so better here
    - add file/folder, 
    - remove file/folder, 
    - view file/folder

2. Node 
    - name 
    - id 
    - type(enum)[file, folder]
    - parent

3. file extends node 
    - string data 
    - type : file 
    - read() -> return string (data)
    - add() -> append data 

4. folder extends node 
    - map of name to node
    - type : folder 
    - remove child()
    - add child()
    - list all child ()


# Implementation 
 - define the core logic 
 - consider edge cases 