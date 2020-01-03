package huffman;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TreeNode {
    public int count;
    public int symbol;
    public TreeNode left;
    public TreeNode right;

    public TreeNode(int count, int symbol) {
        this.count = count;
        this.symbol = symbol;
    }
}