package huffman;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@AllArgsConstructor
@Getter
public class HuffmanCodeTree {
    private TreeNode root;

    public List<HuffmanCode> walkAndMap() {
        List<HuffmanCode> huffmanCodes = new ArrayList<>();
        HuffmanCode huffmanCode = new HuffmanCode();
        walkAndMap(huffmanCodes, huffmanCode, root);
        return huffmanCodes;
    }

    private void walkAndMap(List<HuffmanCode> huffmanCodes, HuffmanCode huffmanCode, TreeNode newRoot) {
        if (newRoot.left != null && newRoot.right != null) {
            HuffmanCode leftCode = huffmanCode;
            HuffmanCode rightCode = huffmanCode.clone();
            leftCode.appendBit(0);
            rightCode.appendBit(1);

            walkAndMap(huffmanCodes, leftCode, newRoot.left);
            walkAndMap(huffmanCodes, rightCode, newRoot.right);
        } else {
            huffmanCode.original = newRoot.symbol;
            huffmanCodes.add(huffmanCode);
        }
    }

    public static class CodeTreeBuilder {
        private PriorityQueue<TreeNode> priorityQueue;
        private FrequencyTable frequencyTable;

        public HuffmanCodeTree fromFrequencyTable(FrequencyTable frequencyTable) {
            this.frequencyTable = frequencyTable;
            fillPriorityQueue();
            return buildTree();
        }

        private void fillPriorityQueue() {
            priorityQueue = new PriorityQueue<>(new Comparator<TreeNode>() {
                @Override
                public int compare(TreeNode o1, TreeNode o2) {
                    return Integer.compare(o1.count, o2.count);
                }
            });
            frequencyTable.getFrequencies().forEach(item -> priorityQueue.add(new TreeNode(item.frequency, item.symbol)));
            priorityQueue.add(new TreeNode(1, getEOF()));

        }

        private int getEOF() {
            for (int i = 0; i < 256; i++) {
                int finalI = i;
                if (frequencyTable.getFrequencies().stream().noneMatch(item -> item.symbol == finalI)) {
                    return i;
                }
            }
            throw new IllegalStateException("Can't find original code for eof");
        }

        private HuffmanCodeTree buildTree() {
            while (priorityQueue.size() != 1) {
                TreeNode lowestNode = priorityQueue.poll();
                TreeNode pairLowestNode = priorityQueue.poll();

                TreeNode newRoot = new TreeNode(lowestNode.count + pairLowestNode.count, 0);
                newRoot.setLeft(pairLowestNode);
                newRoot.setRight(lowestNode);
                priorityQueue.add(newRoot);
            }

            return new HuffmanCodeTree(priorityQueue.poll());
        }

    }
}