package Boo;
import java.util.Stack;

public class main {

    public static void main(String[] args) {
        NotePad notepad = new NotePad();



        System.out.println("--- TEST 1: The 'Undo a Backspace' Test ---");
        // Why it's tricky: Proves you need 3 stacks instead of 2.
        notepad.append('A'); 
        notepad.Backspace(); 
        notepad.Undo(); 
               StringBuilder sb = new StringBuilder();
        for (Character ch : notepad.text){
            sb.append(ch);
        }
        System.out.println(sb.toString());

        System.out.println("\n--- TEST 2: The 'Redo Clearing' Rule ---");
        // Why it's tricky: Tests if typing clears the Redo stack.
        notepad.append('B'); // Text is AB
        notepad.Undo();      // Text is A (B is in redo stack)
        notepad.append('C'); // Text is AC (Redo stack MUST clear here)
        notepad.Redo();      // Should do nothing! 'B' is gone forever.
                 sb = new StringBuilder();
        for (Character ch : notepad.text){
            sb.append(ch);
        }
        System.out.println(sb.toString());

        System.out.println("\n--- TEST 3: Deep Time Travel ---");
        // Why it's tricky: Tests chaining multiple undos and redos.
        notepad.append('D'); // ACD
        notepad.append('E'); // ACDE
        notepad.Undo();      // ACD
        notepad.Undo();      // AC
        notepad.Redo();      // ACD
        notepad.Redo();      // ACDE
                sb = new StringBuilder();
        for (Character ch : notepad.text){
            sb.append(ch);
        }
        System.out.println(sb.toString());

        System.out.println("\n--- TEST 4: The Empty Stack Crash ---");
        // Why it's tricky: Interviewers will spam backspace or undo when it's empty to force an Exception.
        NotePad emptyPad = new NotePad();
        emptyPad.Backspace();
        emptyPad.Backspace();
        emptyPad.Undo();
        emptyPad.Redo();
         sb = new StringBuilder();
        for (Character ch : notepad.text){
            sb.append(ch);
        }
        System.out.println(sb.toString());        System.out.println("Passed empty stack test without crashing.");

        System.out.println("1. Typing 'A' and 'B'...");
        notepad.append('A'); 
        notepad.append('B'); 
        
        System.out.println("2. Hitting Backspace (Deletes 'B')...");
        notepad.Backspace(); 

        System.out.println("3. Hitting Undo (Should bring 'B' back)...");
        notepad.Undo(); 

        // Let's print out what is actually in the runningWord stack now
         sb = new StringBuilder();
        for (Character ch : notepad.text){
            sb.append(ch);
        }
        System.out.println(sb.toString());
    }

}

// Append - It will append the current string.
// Backspace - It will delete the last character of the editor.
// Undo - It will remove the effect of the last operation performed.
// Redo - It will perform the redo operation. 
// The condition is you can only perform this operation if the last operation is Redo/Undo operations.

class pair{
    Character ch;
    boolean is_append;
    public pair(Character ch, boolean is_append) {
        this.ch = ch;
        this.is_append = is_append;
    }
}
class NotePad {
    Stack<Character> text = new Stack<>();
    Stack<pair> undo = new Stack<>();
    Stack<pair> redo = new Stack<>();

    public void append(Character ch){
       undo.push(new pair(ch, true));
       text.push(ch);
       redo.clear();

    }
    public void Backspace(){
       if(text.isEmpty()) return;
       undo.push(new pair(text.pop(), false));
       redo.clear();
    }
    public void Undo(){
       if(undo.isEmpty()) return ;

       pair p = undo.pop();
       if(p.is_append){
        text.pop();
       } else {
        text.push(p.ch);
       }
       redo.push(p);

    }
    public void Redo(){
        if(redo.isEmpty()) return;
        pair p = redo.pop();
        if(p.is_append){
            text.push(p.ch);
        } else {
            text.pop();
        }
        undo.push(p);
    }

    
}