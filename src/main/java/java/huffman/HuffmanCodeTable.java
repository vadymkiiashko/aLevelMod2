package huffman;



import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
public class HuffmanCodeTable {
    private List<HuffmanCode> table;

    public HuffmanCodeTable(List<HuffmanCode> table) {
        this.table = table;
    }

    public HuffmanCodeTable() {
        this.table = new ArrayList<>();
    }

    public HuffmanCode getBySymbol(int symbol) {
        Optional<HuffmanCode> first = table.stream().filter(huffmanCode -> huffmanCode.original == symbol).findFirst();
        if (first.isPresent())
            return first.get();
        else
            throw new IllegalArgumentException("Symbol is not contained in huffman table");
    }

    public Optional<HuffmanCode> getByCode(HuffmanCode hCode) {
        return table.stream().filter(huffmanCode -> huffmanCode.code == hCode.code
                && huffmanCode.length == hCode.length).findFirst();
    }

    public int getMaxCodeLenght() {
        Optional<HuffmanCode> minOpt = table.stream().max(((o1, o2) -> Integer.compare(o1.length, o2.length)));
        if (minOpt.isPresent())
            return minOpt.get().length;
        throw new IllegalArgumentException("Table is empty");
    }

    public HuffmanCode getEOF(){
        return table.get(table.size()-1);
    }
}
