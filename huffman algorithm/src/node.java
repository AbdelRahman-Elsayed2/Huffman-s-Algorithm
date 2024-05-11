import java.util.ArrayList;
import java.util.List;

// this class represent the node of huffman tree
public class node implements Comparable<node>{
    private node left ;
    private node right ;
    private int frequency;
    private List<Byte>  stringCoded = new ArrayList<>();

   public node(node left, node right ){
          this.setLeft(left);
          this.setRight(right);
          this.stringCoded = new ArrayList<>();
          // each node internal or root its frequency equal the sum of its children (right and left)
          this.setFrequency(left.getFrequency()+right.getFrequency());
   }
   public node(List<Byte> str, int freq){

        this.setLeft(null);
        this.setRight(null);
        this.setFrequency(freq);
        this.setStringCoded(str);

   }
    public void setStringCoded(List<Byte> str ) {
         for(byte it : str){
            this.stringCoded.add(it);
         }
    }
    public List<Byte>  getStringCoded() {
        return this.stringCoded;
    }

    public node getLeft() {
        return left;
    }

    public void setLeft(node left) {
        this.left = left;
    }

    public node getRight() {
        return right;
    }

    public void setRight(node right) {
        this.right = right;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }


    @Override
    public int compareTo(node inNode) {
        return Integer.compare(frequency,inNode.getFrequency());
    }

    public boolean isLeaf (){
        return   !(this.getStringCoded().isEmpty());
    }
}
